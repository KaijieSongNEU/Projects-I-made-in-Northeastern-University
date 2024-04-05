package model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.*;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import ui.SupermarketInventoryApplication;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;

public class AddPurchaseOrderGUI extends JFrame implements ActionListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField priceField, quantityField, productionDateField, shelfLifeField, orderIDField;
    private JButton addButton, deleteButton, arrangeButton, submitButton, backButton;
    private JComboBox<String> productNameComboBox;

    Business business = SupermarketInventoryApplication.getBusiness();

    public AddPurchaseOrderGUI() {
        // Set the window details
        setTitle("Place new purchase order");
        setSize(1000, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // set the label and text field
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(30, 40, 130, 20); 
        add(productNameLabel);
        String[] productNames = business.getAllProductNames();
        productNameComboBox = new JComboBox<>(productNames);
        productNameComboBox.setBounds(170, 40, 150, 20); 
        add(productNameComboBox);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setBounds(30, 80, 130, 20); 
        add(priceLabel);
        priceField = new JTextField();
        priceField.setBounds(170, 80, 150, 20); 
        add(priceField);

        JLabel quantityLabel = new JLabel("Quantity:");
        quantityLabel.setBounds(30, 120, 130, 20); 
        add(quantityLabel);
        quantityField = new JTextField();
        quantityField.setBounds(170, 120, 150, 20);
        add(quantityField);

        JLabel productionDateLabel = new JLabel("Production Date:");
        productionDateLabel.setBounds(520, 40, 130, 20); 
        add(productionDateLabel);
        productionDateField = new JTextField();
        productionDateField.setBounds(660, 40, 150, 20); 
        add(productionDateField);

        // check the input production date is valid
        productionDateField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    LocalDate.parse(productionDateField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddPurchaseOrderGUI.this, "Please enter the correct format of date", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    productionDateField.setText("");
                }
            }
        });

        JLabel shelfLifeLabel = new JLabel("Shelf Life:");
        shelfLifeLabel.setBounds(520, 80, 130, 20); 
        add(shelfLifeLabel);
        shelfLifeField = new JTextField();
        shelfLifeField.setBounds(660, 80, 150, 20); 
        add(shelfLifeField);

        // check the input shelf life is valid
        shelfLifeField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                try {
                    Integer.parseInt(shelfLifeField.getText());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(AddPurchaseOrderGUI.this, "Please enter the correct number", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    shelfLifeField.setText("");
                }
            }
        });

        JLabel orderIDLabel = new JLabel("OrderID");
        orderIDLabel.setBounds(520, 120, 130, 20); 
        add(orderIDLabel);
        orderIDField = new JTextField();
        orderIDField.setBounds(660, 120, 150, 20); 
        add(orderIDField);

        // set the default value of orderID to a random orderID
        orderIDField.setText(business.getMasterOrderList().generaterandomOrderID());  

        // create a table to show the order items
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Order ID");
        tableModel.addColumn("Product Name");
        tableModel.addColumn("Production Date");
        tableModel.addColumn("Shelf Life");
        tableModel.addColumn("Expiration Date");
        tableModel.addColumn("Price");
        tableModel.addColumn("Quantity");
        tableModel.addColumn("Current stock");

        // create a table and add it to a scroll pane
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 180, 950, 280);
        add(scrollPane);

        // create buttons and add them to the frame
        addButton = new JButton("Add");
        addButton.setBounds(30, 500, 100, 30); 
        addButton.addActionListener(this);
        add(addButton);

        deleteButton = new JButton("Delete");
        deleteButton.setBounds(140, 500, 100, 30); 
        deleteButton.addActionListener(this);
        add(deleteButton);

        arrangeButton = new JButton("Arrange");
        arrangeButton.setBounds(250, 500, 100, 30); 
        arrangeButton.addActionListener(this);
        add(arrangeButton);

        backButton = new JButton("Back");
        backButton.setBounds(770, 500, 100, 30); 
        backButton.addActionListener(this);
        add(backButton);

        submitButton = new JButton("Submit");
        submitButton.setBounds(860, 500, 100, 30); 
        submitButton.addActionListener(this);
        add(submitButton);

        // set the layout to null
        setLayout(null);
    }

    // 

    // Implement the actionPerformed method
    public void actionPerformed(ActionEvent e) {

        // add the order item to the table
        if (e.getSource() == addButton) {
            String orderID = orderIDField.getText();

            // Get the text from the input fields
            String productName = (String) productNameComboBox.getSelectedItem();
            Double price = Double.parseDouble(priceField.getText());
            int quantity = Integer.parseInt(quantityField.getText());
            LocalDate productionDate = LocalDate.parse(productionDateField.getText());

            String shelfLifeInput = shelfLifeField.getText().trim();
            Period shelfLife = parseShelfLife(shelfLifeInput);
            int stock = quantity;

            // Add the order item to the table
            tableModel.addRow(new Object[] { orderID, productName, productionDate, shelfLife,
                    productionDate.plus(shelfLife), price, quantity, stock });

            // 弹出窗口 tell the user that the item has been added
            JOptionPane.showMessageDialog(this, "Successfully added", "Success", JOptionPane.INFORMATION_MESSAGE);

        }

        // delete the selected row  
        if (e.getSource() == deleteButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select the row(s) to delete", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            tableModel.removeRow(selectedRow);
        }

        // arrange the order items in the table by combining the order items with the same product name, production date, shelf life, price and orderID
        if (e.getSource() == arrangeButton) {
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = i + 1; j < tableModel.getRowCount(); j++) {
                    if (tableModel.getValueAt(i, 0).equals(tableModel.getValueAt(j, 0))
                            && tableModel.getValueAt(i, 1).equals(tableModel.getValueAt(j, 1))
                            && tableModel.getValueAt(i, 2).equals(tableModel.getValueAt(j, 2))
                            && tableModel.getValueAt(i, 3).equals(tableModel.getValueAt(j, 3))
                            && tableModel.getValueAt(i, 5).equals(tableModel.getValueAt(j, 5))) {
                        int quantity = (int) tableModel.getValueAt(i, 6) + (int) tableModel.getValueAt(j, 6);
                        int stock = (int) tableModel.getValueAt(i, 7) + (int) tableModel.getValueAt(j, 7);
                        tableModel.setValueAt(quantity, i, 6);
                        tableModel.setValueAt(stock, i, 7);
                        tableModel.removeRow(j);
                        j--;
                    }
                }
            }
            
        }

        // submit the order items to the business
        if (e.getSource() == submitButton) {

            // check if there are any duplicate order items in the table
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                for (int j = i + 1; j < tableModel.getRowCount(); j++) {
                    if (tableModel.getValueAt(i, 0).equals(tableModel.getValueAt(j, 0))
                            && tableModel.getValueAt(i, 1).equals(tableModel.getValueAt(j, 1))
                            && tableModel.getValueAt(i, 2).equals(tableModel.getValueAt(j, 2))
                            && tableModel.getValueAt(i, 3).equals(tableModel.getValueAt(j, 3))
                            && tableModel.getValueAt(i, 5).equals(tableModel.getValueAt(j, 5))) {
                        JOptionPane.showMessageDialog(this, "You have unmerged items, please merge them", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }
            }

            // check if the order items are empty
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "Please add order items", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // add the order items to the business 
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String orderID = (String) tableModel.getValueAt(i, 0);
                String productName = (String) tableModel.getValueAt(i, 1);
                LocalDate productionDate = (LocalDate) tableModel.getValueAt(i, 2);
                Period shelfLife = (Period) tableModel.getValueAt(i, 3);
                double price = (double) tableModel.getValueAt(i, 5);
                int quantity = (int) tableModel.getValueAt(i, 6);
                int stock = (int) tableModel.getValueAt(i, 7);

                Order order = business.getMasterOrderList().findOrder(orderID);
                if (order == null) {
                    order = new Order(orderID, true);
                    business.getMasterOrderList().addOrder(order);
                }

                business.addPurchaseOrderItem(order, productName, productionDate, shelfLife, price, quantity, stock);
                
            }

            try {
                // Write to purchase_orders.csv
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/purchaseorders.csv", true));
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String orderID = (String) tableModel.getValueAt(i, 0);
                    String productName = (String) tableModel.getValueAt(i, 1);
                    LocalDate productionDate = (LocalDate) tableModel.getValueAt(i, 2);
                    Period shelfLife = (Period) tableModel.getValueAt(i, 3);
                    LocalDate expirationDate = productionDate.plus(shelfLife);
                    double price = (double) tableModel.getValueAt(i, 5);
                    int quantity = (int) tableModel.getValueAt(i, 6);
                    int stock = (int) tableModel.getValueAt(i, 7);

                    writer.write(orderID + "," + productName + "," + productionDate + "," + shelfLife + ","
                            + expirationDate + "," + price + "," + quantity + "," + stock + ",");
                    writer.newLine();
                }
                writer.close();

                // Update stock.csv
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String productName = (String) tableModel.getValueAt(i, 1);
                    int quantity = (int) tableModel.getValueAt(i, 6);
                    updateStockCSV(productName, quantity);
                }

                JOptionPane.showMessageDialog(this, "Submission successful", "Success", JOptionPane.INFORMATION_MESSAGE);

                // back to the main panel
                MainPanel nextGUI = new MainPanel();
                nextGUI.setVisible(true);
                this.dispose();

            } catch (IOException ex) {
                ex.printStackTrace();
            }

            
        }

        // back to the main panel
        if (e.getSource() == backButton) {
            MainPanel mainPanel = new MainPanel();
            mainPanel.setVisible(true);
            this.dispose();
        }
    }


    // Parse the shelf life input
    private Period parseShelfLife(String input) {
        int days = Integer.parseInt(input);
        return Period.ofDays(days);
    }

    private void updateStockCSV(String productName, int quantity) throws IOException {
        // Read stock.csv and update the stock for the corresponding product
        String filePath = "src/stock.csv";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[1].trim().equals(productName)) {
                int currentStock = Integer.parseInt(parts[2].trim());
                int newStock = currentStock + quantity;
                parts[2] = Integer.toString(newStock);
                line = String.join(",", parts);
            }
            lines.add(line);
        }
        reader.close();

        // Write back to stock.csv
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (String updatedLine : lines) {
            writer.write(updatedLine);
            writer.newLine();
        }
        writer.close();
    }

}
