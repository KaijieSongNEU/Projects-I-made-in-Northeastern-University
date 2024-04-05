package ui;

import model.Business.Business;
import model.Business.ConfigureABusiness;
import model.OrderManagement.MasterOrderList;
import model.Suppliers.SupplierDirectory;
import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;
import model.Suppliers.Supplier;
import model.OrderManagement.OrderItem;
import model.OrderManagement.Order;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import model.LoginGUI;

public class SupermarketInventoryApplication {

    private static Business business;

    public static void main(String[] args) {

        // create a business
        business = ConfigureABusiness.createABusinessAndLoadALotOfDatabyHand("Safeway");

        // create a supermarket inventory application
        SwingUtilities.invokeLater(() -> {
            LoginGUI loginGUI = new LoginGUI();
            loginGUI.setVisible(true);
        });

    }

    public static Business getBusiness() {
        return business;
    }

    // print out the business details
    public static void printBusinessDetails() {
        business.getSupplierDirectory().printInfo();
        business.getMasterOrderList().printInfo();
    }

    
}