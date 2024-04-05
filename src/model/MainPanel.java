package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainPanel extends JFrame {
    public MainPanel() {

        // Set the window details
        setTitle("Main Panel");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Create a label for the title
        JLabel label = new JLabel("W&S Supermarket Inventory Management System");
        label.setBounds(100, 20, 400, 30);
        panel.add(label);

        // Create buttons for different functionalities
        JButton addPurchaseOrderButton = new JButton("添加购入订单");
        addPurchaseOrderButton.setBounds(150, 100, 200, 30);
        addPurchaseOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // add purchase order button clicked, open a new window
                new AddPurchaseOrderGUI().setVisible(true);
                dispose(); 
            }
        });
        panel.add(addPurchaseOrderButton);

        JButton addExpenseOrderButton = new JButton("添加消费订单");
        addExpenseOrderButton.setBounds(150, 150, 200, 30);
        addExpenseOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // add expense order button clicked, open a new window
                new AddExpenseOrderGUI().setVisible(true);
                dispose(); 
            }
        });
        panel.add(addExpenseOrderButton);

        JButton searchOrderButton = new JButton("搜索订单");
        searchOrderButton.setBounds(150, 200, 200, 30);
        searchOrderButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // search order button clicked, open a new window
                new SearchOrderGUI().setVisible(true);
                dispose(); 
            }
        });
        panel.add(searchOrderButton);

        JButton viewStockButton = new JButton("查看库存");
        viewStockButton.setBounds(150, 250, 200, 30);
        viewStockButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // view stock button clicked, open a new window
                new ViewStockGUI().setVisible(true);
                dispose(); 
            }
        });
        panel.add(viewStockButton);

        JButton addNewProductButton = new JButton("添加新产品");
        addNewProductButton.setBounds(150, 300, 200, 30);
        addNewProductButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // add new product button clicked, open a new window
                new AddSupplierProducts().setVisible(true);
                dispose(); 
            }
        });
        panel.add(addNewProductButton);

        JButton exitButton = new JButton("退出");
        exitButton.setBounds(150, 350, 200, 30);
        exitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                // exit button clicked, close the window
                dispose();
            }
        });
        panel.add(exitButton);

        add(panel);
    }

}
