package model;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import model.Business.Business;
import ui.SupermarketInventoryApplication;
import model.OrderManagement.Order;
import model.OrderManagement.OrderItem;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;


public class ViewStockGUI extends JFrame implements ActionListener {
    private JTable table;
    private DefaultTableModel tableModel;
    private JTextField orderIDField;
    private JButton deleteButton, exitButton, searchButton, clearButton, searchAllButton;
    private JComboBox<String> productNameComboBox;

    Business business = SupermarketInventoryApplication.getBusiness();

    public ViewStockGUI() {

        // Set the window details
        setTitle("View Stock");
        setSize(1000, 600); 
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a label and combo box for product name
        JLabel productNameLabel = new JLabel("Product Name:");
        productNameLabel.setBounds(50, 40, 130, 20); 
        add(productNameLabel);
        String[] productNames = business.getAllProductNames();
        productNameComboBox = new JComboBox<>(productNames);
        productNameComboBox.setBounds(170, 40, 150, 20); 
        add(productNameComboBox);

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

        // 
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(30, 110, 950, 350);
        add(scrollPane);

        // add a delete button
        deleteButton = new JButton("Delete");
        deleteButton.setBounds(30, 500, 100, 30); 
        deleteButton.addActionListener(this);
        add(deleteButton);

        // add an exit button
        exitButton = new JButton("Back");
        exitButton.setBounds(860, 500, 100, 30); 
        exitButton.addActionListener(this);
        add(exitButton);

        // add a search button
        searchButton = new JButton("Search");
        searchButton.setBounds(330, 40, 100, 20); 
        searchButton.addActionListener(this);
        add(searchButton);

        // add a search all button
        searchAllButton = new JButton("Search All");
        searchAllButton.setBounds(440, 40, 100, 20); 
        searchAllButton.addActionListener(this);
        add(searchAllButton);

        // add a clear button
        clearButton = new JButton("Clear Search");
        clearButton.setBounds(550, 40, 150, 20); 
        clearButton.addActionListener(this);
        add(clearButton);

        // Set the layout
        setLayout(null);
    }

    @Override

    // Handle button click events
    public void actionPerformed(ActionEvent e) {

        // search for a specific order
        if (e.getSource() == searchButton) {
            
            String productName = (String) productNameComboBox.getSelectedItem();

            // if the user didn't select a product, show an error message
            if (productName == null) {
                JOptionPane.showMessageDialog(this, "Please select a product", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if there is already data in the table, do not add duplicate data
            business.getSupplierDirectory().getSuppliers().forEach(supplier -> {
                supplier.getProductCatalog().getProducts().forEach(product -> {
                    if (product.getName().equals(productName)) {
                        tableModel.addRow(
                                new Object[] { "", product.getName(), "", "", "", "", "Total stock: ",
                                        product.getStock() });
                    }
                });
            });

            // show the purchase orders for the selected product
            business.getMasterOrderList().getOrders().forEach(order -> {
                // if it is a purchase order, find the order item and show it in the table
                if (order.isPurchase()) {
                    order.getOrderitems().forEach(orderItem -> {
                        if (orderItem.getSelectedproduct().getName().equals(productName)) {
                            tableModel.addRow(new Object[] { order.getOrderID(),
                                    orderItem.getSelectedproduct().getName(),
                                    orderItem.getSelectedproduct().getProductionDate(),
                                    orderItem.getSelectedproduct().getShelfLife(),
                                    orderItem.getSelectedproduct().getExpirationDate(), orderItem.getActualPrice(),
                                    orderItem.getQuantity(), orderItem.getSelectedproduct().getStock() });
                        }
                    });
                }
            });

        }

        // clear the search result
        if (e.getSource() == clearButton) {
            tableModel.setRowCount(0);

        }

        // search all orders
        if (e.getSource() == searchAllButton) {
            for (String productName : business.getAllProductNames()) {
                business.getSupplierDirectory().getSuppliers().forEach(supplier -> {
                    supplier.getProductCatalog().getProducts().forEach(product -> {
                        if (product.getName().equals(productName)) {
                            tableModel.addRow(new Object[] { "", product.getName(), "", "", "", "", "Total stock: ",
                                    product.getStock() });
                        }
                    });
                });

                // 然后展示对应的purchase订单信息
                business.getMasterOrderList().getOrders().forEach(order -> {
                    // if it is a purchase order, find the order item and show it in the table
                    if (order.isPurchase()) {
                        order.getOrderitems().forEach(orderItem -> {
                            if (orderItem.getSelectedproduct().getName().equals(productName)) {
                                Object[] rowData = { order.getOrderID(),
                                        orderItem.getSelectedproduct().getName(),
                                        orderItem.getSelectedproduct().getProductionDate(),
                                        orderItem.getSelectedproduct().getShelfLife(),
                                        orderItem.getSelectedproduct().getExpirationDate(), orderItem.getActualPrice(),
                                        orderItem.getQuantity(), orderItem.getSelectedproduct().getStock() };
                                tableModel.addRow(rowData);
                            }
                        });
                    }
                });

                // take a blank row
                tableModel.addRow(new Object[] { "", "", "", "", "", "", "", "" });
            }
        }

        // delete an order item
        if (e.getSource() == deleteButton) {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Please select a row to delete", "Error",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // if the order ID is blank, user can't delete it 
            if (tableModel.getValueAt(selectedRow, 0).equals("")) {
                JOptionPane.showMessageDialog(this, "You can't delete this row", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // get the order item details
            String orderID = (String) tableModel.getValueAt(selectedRow, 0);
            String productName = (String) tableModel.getValueAt(selectedRow, 1);
            LocalDate productionDate = (LocalDate) tableModel.getValueAt(selectedRow, 2);
            Period shelfLife = (Period) tableModel.getValueAt(selectedRow, 3);
            double price = (double) tableModel.getValueAt(selectedRow, 5);
            int quantity = (int) tableModel.getValueAt(selectedRow, 6);
            int stock = (int) tableModel.getValueAt(selectedRow, 7);
            business.removeOrderItem(orderID, productName, productionDate, shelfLife, price);
            tableModel.removeRow(selectedRow);

            try {
                // Call the method to delete the item from CSV
                deletePurchaseOrderItemFromCSV(orderID, productName, productionDate, shelfLife, price, stock);
                updateStockCSV(productName, stock);
                // Remove the row from the table
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            // update the stock
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals("")) {
                    if (tableModel.getValueAt(i, 1).equals(productName)) {
                        int totalstock = (int) tableModel.getValueAt(i, 7);
                        tableModel.setValueAt(totalstock - stock, i, 7);
                    }
                }
            }

        }

        // exit the program
        if (e.getSource() == exitButton) {
            MainPanel mainPanel = new MainPanel();
            mainPanel.setVisible(true);
            this.dispose();
        }
    }

    private void deletePurchaseOrderItemFromCSV(String orderID, String productName, LocalDate productionDate, Period shelfLife, double price, int stock) throws IOException {
        String filePath = "src/purchaseorders.csv";
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        List<String> lines = new ArrayList<>();
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            // 检查每一行是否与要删除的订单项匹配
            if (parts.length >= 8 && parts[0].trim().equals(orderID) && parts[1].trim().equals(productName)
                    && LocalDate.parse(parts[2].trim()).isEqual(productionDate)
                    && parts[3].trim().equals(shelfLife.toString()) && Double.parseDouble(parts[5].trim()) == price) {
                // 从订单文件中删除匹配的行
                continue;
            }
            lines.add(line);
        }
        reader.close();

        // 将更新后的内容写回 purchaseorders.csv 文件
        BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
        for (String updatedLine : lines) {
            writer.write(updatedLine);
            writer.newLine();
        }
        writer.close();


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
                int newStock = currentStock - quantity;
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
