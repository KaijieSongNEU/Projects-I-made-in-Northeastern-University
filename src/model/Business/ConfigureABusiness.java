package model.Business;

import model.Suppliers.SupplierDirectory;
import model.Suppliers.Supplier;
import model.ProductManagement.ProductCatalog;
import model.ProductManagement.Product;
import model.Business.Business;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import model.OrderManagement.Order;

public class ConfigureABusiness {

    public static Business createABusinessAndLoadALotOfDatabyHand(String name) {
        Business business = new Business(name);

        // // Add suppliers
        // SupplierDirectory supplierDirectory = business.getSupplierDirectory();
        // Supplier supplier1 = supplierDirectory.addNewSupplier("Coca-Cola");
        // Supplier supplier2 = supplierDirectory.addNewSupplier("Procter & Gamble");
        // Supplier supplier3 = supplierDirectory.addNewSupplier("Nestlé");

        // // Add products
        // ProductCatalog productCatalog1 = supplier1.getProductCatalog();
        // ProductCatalog productCatalog2 = supplier2.getProductCatalog();
        // ProductCatalog productCatalog3 = supplier3.getProductCatalog();

        // // load products for supplier 1
        // Product product1 = productCatalog1.addNewProductWithStockOnly("Coca-Cola", 0);
        // Product product2 = productCatalog1.addNewProductWithStockOnly("Sprite",0);
        // Product product3 = productCatalog1.addNewProductWithStockOnly("Fanta",0);

        // // load products for supplier 2
        // Product product4 = productCatalog2.addNewProductWithStockOnly("Pantene",0);
        // Product product5 = productCatalog2.addNewProductWithStockOnly("Tide",0);
        // Product product6 = productCatalog2.addNewProductWithStockOnly("Oral-B",0);
        
        // // load products for supplier 3
        // Product product7 = productCatalog3.addNewProductWithStockOnly("Nescafé",0);
        // Product product8 = productCatalog3.addNewProductWithStockOnly("KitKat",0);
        // Product product9 = productCatalog3.addNewProductWithStockOnly("Maggi",0);

        // Order order1 = new Order("0001", true);
        // business.addPurchaseOrderItem(order1, "Coca-Cola", LocalDate.of(2024, 1, 1), Period.ofDays(365), 10.0, 50, 50);
        // business.addPurchaseOrderItem(order1, "Sprite", LocalDate.of(2024, 10, 1), Period.ofDays(365), 10.0, 20, 20);
        // business.addPurchaseOrderItem(order1, "Fanta", LocalDate.of(2020, 10, 1), Period.ofDays(365), 10.0, 30, 30);
        // business.getMasterOrderList().addOrder(order1);

        // Order order2 = new Order("0002", true);
        // business.addPurchaseOrderItem(order2, "Coca-Cola", LocalDate.of(2021, 1, 1), Period.ofDays(365), 10.0, 50, 50);
        // business.addPurchaseOrderItem(order2, "Sprite", LocalDate.of(2021, 9, 1), Period.ofDays(365), 10.0, 20, 20);
        // business.getMasterOrderList().addOrder(order2);

        // Order order3 = new Order("1000", false);
        // business.addSaleOrderItem(order3, "Coca-Cola", LocalDate.of(2020, 1, 1), Period.ofDays(365), 1.5, 10);
        // business.addSaleOrderItem(order3, "Sprite", LocalDate.of(2020, 10, 1), Period.ofDays(365), 1.5, 5);
        // business.addSaleOrderItem(order3, "Coca-Cola", LocalDate.of(2021, 1, 1), Period.ofDays(365), 1.5, 5);
        // business.getMasterOrderList().addOrder(order3);

        // Order order4 = new Order("2000", false);
        // business.addSaleOrderItem(order4, "Fanta", LocalDate.of(2020, 10, 1), Period.ofDays(365), 1.5, 10);
        // business.getMasterOrderList().addOrder(order4);

        
        loadStockFromCSV("src/stock.csv", business);
        loadPurchaseOrdersFromCSV("src/PurchaseOrders.csv", business);

        loadSaleOrdersFromCSV("src/saleorders.csv", business);

        return business;

    }

    // Method to load stock information from stock.CSV file
    private static void loadStockFromCSV(String filename, Business business) {
        String line = "";
        String csvSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                // Split the line into parts
                String[] data = line.split(csvSplitBy);

                // Extract data
                String supplierName = data[0].trim();
                String productName = data[1].trim();
                int totalStock = Integer.parseInt(data[2].trim());


                // Find or create the supplier
                Supplier supplier = business.getSupplierDirectory().findSupplierbyName(supplierName);
                if (supplier == null) {
                    supplier = business.getSupplierDirectory().addNewSupplier(supplierName);
                }

                // Find or create the product and set its stock
                Product product = supplier.getProductCatalog().findProduct(productName);
                if (product == null) {
                    product = supplier.getProductCatalog().addNewProductWithStockOnly(productName, 0);
                } else {
                    product.setStock(totalStock);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load stock information from purchaseorder CSV file
    private static void loadPurchaseOrdersFromCSV(String filename, Business business) {
        String line = "";
        String csvSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                // Split the line into parts
                String[] data = line.split(csvSplitBy);

                // Extract data
                String orderID = data[0].trim();
                String productName = data[1].trim();
                LocalDate productionDate = LocalDate.parse(data[2].trim());
                Period shelfLife = Period.parse(data[3].trim());
                LocalDate expirationDate = LocalDate.parse(data[4].trim());
                double price = Double.parseDouble(data[5].trim());
                int quantity = Integer.parseInt(data[6].trim());
                int stock = Integer.parseInt(data[7].trim());

                // create the order and orderitem
                if(business.getMasterOrderList().searchOrderbyOrderID(orderID) == null) {
                    Order order = new Order(orderID, true);
                    business.addPurchaseOrderItem(order, productName, productionDate, shelfLife, price, quantity, stock);
                    business.getMasterOrderList().addOrder(order);
                } else {
                    Order order = business.getMasterOrderList().searchOrderbyOrderID(orderID);
                    business.addPurchaseOrderItem(order, productName, productionDate, shelfLife, price, quantity, stock);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load stock information from saleorder CSV file
    private static void loadSaleOrdersFromCSV(String filename, Business business) {
        String line = "";
        String csvSplitBy = ",";
        
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            // Skip header line
            br.readLine();
            
            while ((line = br.readLine()) != null) {
                // Split the line into parts
                String[] data = line.split(csvSplitBy);

                // Extract data
                String orderID = data[0].trim();
                String productName = data[1].trim();
                LocalDate productionDate = LocalDate.parse(data[2].trim());
                Period shelfLife = Period.parse(data[3].trim());
                LocalDate expirationDate = LocalDate.parse(data[4].trim());
                double price = Double.parseDouble(data[5].trim());
                int quantity = Integer.parseInt(data[6].trim());
                String stock = data[7].trim();

                // create the order and orderitem
                if(business.getMasterOrderList().searchOrderbyOrderID(orderID) == null) {
                    Order order = new Order(orderID, false);
                    business.loadSaleOrderItem(order, productName, productionDate, shelfLife, price, quantity);
                    business.getMasterOrderList().addOrder(order);
                } else {
                    Order order = business.getMasterOrderList().searchOrderbyOrderID(orderID);
                    business.loadSaleOrderItem(order, productName, productionDate, shelfLife, price, quantity);
                }
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
