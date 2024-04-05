package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import model.Business.Business;
import ui.SupermarketInventoryApplication;
import model.Suppliers.Supplier;


class AddSupplierProducts extends JFrame implements ActionListener {

    Business business = SupermarketInventoryApplication.getBusiness();

    private JTextField supplierNameField, productNameField;
    private JButton addButton, exitButton;

    public AddSupplierProducts() {

        // Set the window details
        setTitle("Add new Supplier & Products");
        setSize(500, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Create a label and text field for supplier name
        JLabel label = new JLabel("New Supplier Name: ");
        label.setBounds(140, 20, 200, 30);
        panel.add(label);

        supplierNameField = new JTextField();
        supplierNameField.setBounds(140, 50, 200, 30);
        panel.add(supplierNameField);

        JLabel label2 = new JLabel("New Product Name: ");
        label2.setBounds(140, 100, 200, 30);
        panel.add(label2);

        productNameField = new JTextField();
        productNameField.setBounds(140, 130, 200, 30);
        panel.add(productNameField);

        // Create a button to add the supplier and product
        addButton = new JButton("Add");
        addButton.setBounds(120, 200, 100, 30);
        addButton.addActionListener(this);
        panel.add(addButton);

        exitButton = new JButton("Back");
        exitButton.setBounds(270, 200, 100, 30);
        exitButton.addActionListener(this);
        panel.add(exitButton);

        add(panel);

    }

    // Handle button click events
    public void actionPerformed(ActionEvent e) {

        // 
        if (e.getSource() == addButton) {

            String supplierName = supplierNameField.getText();
            String productName = productNameField.getText();
            int stock = 0;

            // check if the supplier name is empty
            if (supplierName.equals("")) {
                JOptionPane.showMessageDialog(null, "Supplier name cannot be empty!");
                return;
            }

            // check if the product name is empty
            if (productName.equals("")) {
                JOptionPane.showMessageDialog(null, "Product name cannot be empty!");
                return;
            }

            // check if the supplier already exists
            if (business.getSupplierDirectory().findSupplierbyName(supplierName) == null) {
                
                // if not, add the supplier and product
                business.getSupplierDirectory().addNewSupplier(supplierName);
                business.getSupplierDirectory().findSupplierbyName(supplierName).getProductCatalog()
                        .addNewProductWithStockOnly(productName, stock);
            } else {

                // if the supplier already exists, add the product only
                // if the product already exists, update the stock
                if (business.getSupplierDirectory().findSupplierbyName(supplierName).getProductCatalog()
                        .findProduct(productName) != null) {
                    JOptionPane.showMessageDialog(null, "Product already exists, please update the stock instead!");
                    return;
                } else {
                    business.getSupplierDirectory().findSupplierbyName(supplierName).getProductCatalog()
                            .addNewProductWithStockOnly(productName, stock);}
            }

            // Write to stock.csv
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter("src/stock.csv", true));
                writer.write(supplierName + "," + productName + "," + stock);
                writer.newLine();
                writer.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            JOptionPane.showMessageDialog(null, "Supplier and product added successfully!");

        }

        // back to main panel
        if (e.getSource() == exitButton) {
            MainPanel mainPanel = new MainPanel();
            mainPanel.setVisible(true);
            this.dispose();

        }

    }
}