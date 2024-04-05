package model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class LoginGUI extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, exitButton;

    public LoginGUI() {
        
        // Set the window details
        setTitle("Login");
        setSize(500, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create a panel
        JPanel panel = new JPanel();
        panel.setLayout(null);

        // Create label and text field for username
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(100, 50, 100, 30);
        panel.add(usernameLabel);
        
        usernameField = new JTextField();
        usernameField.setBounds(200, 50, 200, 30);
        panel.add(usernameField);

        // Create label and text field for password
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(100, 100, 100, 30);
        panel.add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setBounds(200, 100, 200, 30);
        panel.add(passwordField);

        // Create a button to login
        loginButton = new JButton("Login");
        loginButton.setBounds(150, 150, 100, 30);
        loginButton.addActionListener(this);
        panel.add(loginButton);

        // Create a button to exit
        exitButton = new JButton("Exit");
        exitButton.setBounds(250, 150, 100, 30);
        exitButton.addActionListener(this);
        panel.add(exitButton);
            

        add(panel);
    }

    @Override
    // Handle button click events
    public void actionPerformed(ActionEvent e) {

        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        // Exit the program
        if (e.getSource() == exitButton) {
            System.exit(0);
        }

        // Check if the username and password are correct
        if (username.equals("Kevin") && password.equals("123")) {
            JOptionPane.showMessageDialog(this, "Login successful!");

            // Open the main panel
            dispose(); 
            new MainPanel().setVisible(true); 

        } else {

            // Show an error message if the login fails
            JOptionPane.showMessageDialog(this, "Login failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }

}

