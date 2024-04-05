package model.Business;

import model.OrderManagement.MasterOrderList;
import model.Suppliers.SupplierDirectory;
import model.OrderManagement.Order;
import model.Suppliers.Supplier;
import model.ProductManagement.Product;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Scanner;
import model.OrderManagement.Order;

import model.OrderManagement.OrderItem;

public class Business {

    String supermarketname;
    MasterOrderList masterOrderList;
    SupplierDirectory suppliers;

    public Business(String supermarketname) {
        this.supermarketname = supermarketname;
        masterOrderList = new MasterOrderList();
        suppliers = new SupplierDirectory();
    }

    public String getSupermarketname() {
        return supermarketname;
    }

    public void setSupermarketname(String supermarketname) {
        this.supermarketname = supermarketname;
    }

    public MasterOrderList getMasterOrderList() {
        return masterOrderList;
    }

    public void setMasterOrderList(MasterOrderList masterOrderList) {
        this.masterOrderList = masterOrderList;
    }

    public SupplierDirectory getSupplierDirectory() {
        return suppliers;
    }

    public void setSupplierDirectory(SupplierDirectory suppliers) {
        this.suppliers = suppliers;
    }

    public void addNewOrder(String orderID, boolean isPurchase) {
        masterOrderList.addNewOrder(orderID, isPurchase);
    }

    public void addOrder(Order order) {
        masterOrderList.addOrder(order);
    }

    public void addPurchaseOrderItem(Order order, String productName, LocalDate productionDate,
            Period shelfLife, double price, int quantity, int stock) {

        SupplierDirectory suppliers = getSupplierDirectory();
        Supplier supplier = suppliers.findSupplierbyProduct(productName);
        Product productinorder = new Product(productName, productionDate, shelfLife, price, quantity, stock);
        Product product = supplier.getProductCatalog().findProduct(productName);
        order.addNewOrderItem(quantity, price, productinorder);

        product.setStock(product.getStock() + stock);
        
    }

    public void addPurchaseOrderItemByOrderID(String orderID, String productName, LocalDate productionDate,
            Period shelfLife, double price, int quantity, int stock) {
        Order order = masterOrderList.findOrder(orderID);
        addPurchaseOrderItem(order, productName, productionDate, shelfLife, price, quantity, stock);
    }

    public void addPurchaseOrder(String orderID) {

        Scanner scanner = new Scanner(System.in);

        boolean isPurchase = true;
        Order order = new Order(orderID, isPurchase);
        masterOrderList.addOrder(order);

        boolean continueAddingProducts = true;

        while (continueAddingProducts) {
            System.out.println("Enter the orderItem ID: ");
            int OrderItemID = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            System.out.println("Enter the product name: ");
            String productName = scanner.nextLine();

            System.out.println("Enter the production date (YYYY-MM-DD):");
            String productionDateString = scanner.nextLine();
            LocalDate productionDate = LocalDate.parse(productionDateString);

            System.out.println("Enter the shelf life in days:");
            int shelfLifeDays = scanner.nextInt();
            Period shelfLife = Period.ofDays(shelfLifeDays);

            System.out.println("Enter the quantity: ");
            int quantity = scanner.nextInt();
            int stock = quantity;
            System.out.println("Enter the price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // consume the newline

            addPurchaseOrderItem(order, productName, productionDate, shelfLife, price, quantity,
                    stock);

            // printInfo();

            System.out.println("Do you want to add another product? (yes/no)");
            String continueInput = scanner.nextLine();
            continueAddingProducts = continueInput.equals("yes");

        }
    }

    public void addSaleOrderItem(Order order, String productName, LocalDate productionDate, Period shelfLife, double price, int quantity) {

        SupplierDirectory suppliers = getSupplierDirectory();
        MasterOrderList masterOrderList = getMasterOrderList();
        Supplier supplier = suppliers.findSupplierbyProduct(productName);
        Product productinorder = new Product(productName, productionDate, shelfLife, price, quantity);
        Product product = supplier.getProductCatalog().findProduct(productName);
        order.addNewOrderItem(quantity, price, productinorder);
        
        product.setStock(product.getStock() - quantity);

        for(Order o: masterOrderList.getOrders()){
            for(OrderItem oi: o.getOrderitems()){
                if(oi.getSelectedproduct().getName().equals(productName)&&o.isPurchase()&&oi.getSelectedproduct().getProductionDate().isEqual(productionDate)){
                    oi.getSelectedproduct().setStock(oi.getSelectedproduct().getStock()-quantity);
                }
            }
        }

    }

    public void loadSaleOrderItem(Order order, String productName, LocalDate productionDate, Period shelfLife, double price, int quantity) {

        SupplierDirectory suppliers = getSupplierDirectory();
        MasterOrderList masterOrderList = getMasterOrderList();
        Supplier supplier = suppliers.findSupplierbyProduct(productName);
        Product productinorder = new Product(productName, productionDate, shelfLife, price, quantity);
        Product product = supplier.getProductCatalog().findProduct(productName);
        order.addNewOrderItem(quantity, price, productinorder);
        
    }

    public void addSaleOrder(String orderID) {

        Scanner scanner = new Scanner(System.in);
        
        boolean isPurchase = false;
        Order order = new Order(orderID, isPurchase);
        masterOrderList.addOrder(order);

        boolean continueAddingProducts = true;

        while (continueAddingProducts) {
            System.out.println("Enter the orderItem ID: ");
            int OrderItemID = scanner.nextInt();
            scanner.nextLine(); // consume the newline

            System.out.println("Enter the product name: ");
            String productName = scanner.nextLine();

            System.out.println("Enter the production date (YYYY-MM-DD):");
            String productionDateString = scanner.nextLine();
            LocalDate productionDate = LocalDate.parse(productionDateString);

            System.out.println("Enter the shelf life in days:");
            int shelfLifeDays = scanner.nextInt();
            Period shelfLife = Period.ofDays(shelfLifeDays);

            System.out.println("Enter the quantity: ");
            int quantity = scanner.nextInt();
            System.out.println("Enter the price: ");
            double price = scanner.nextDouble();
            scanner.nextLine(); // consume the newline

            addSaleOrderItem(order, productName, productionDate, shelfLife, price, quantity);

            // printInfo();

            System.out.println("Do you want to add another product? (yes/no)");
            String continueInput = scanner.nextLine();
            continueAddingProducts = continueInput.equals("yes");

        }
    }

    public void checkStock(String productName) {
        SupplierDirectory suppliers = getSupplierDirectory();
        Supplier supplier = suppliers.findSupplierbyProduct(productName);
        Product product = supplier.getProductCatalog().findProduct(productName);
        System.out.println("Product Name: " + product.getName() + " Stock: " + product.getStock());
    }

    public void removeOrder(String orderID) {
        // 对应的减少库存
        Order order = masterOrderList.findOrder(orderID);
        for (int i = 0; i < order.getOrderitems().size(); i++) {
            OrderItem orderItem = order.getOrderitems().get(i);
            String productname = orderItem.getSelectedproduct().getName();
            Supplier supplier = suppliers.findSupplierbyProduct(productname);
            Product product = supplier.getProductCatalog().findProduct(productname);
            if (order.isPurchase()) {
                product.setStock(product.getStock() - orderItem.getStock());
            } else {
                product.setStock(product.getStock() + orderItem.getStock());

            }
        }
        masterOrderList.removeOrder(orderID);
    }

    public void removeOrderItem(String orderID, String productName, LocalDate productionDate, Period shelfLife,
            double price) {
        Order order = masterOrderList.findOrder(orderID);
        order.printInfo();
        OrderItem orderItem = order.findOrderItem(productName, productionDate, shelfLife, price);
        // System.out.println(orderItem);
        Supplier supplier = suppliers.findSupplierbyProduct(productName);
        Product product = supplier.getProductCatalog().findProduct(productName);
        product.setStock(product.getStock() - orderItem.getStock());

        order.removeOrderItem(orderItem);

    }

    public String[] getAllProductNames() {
        ArrayList<String> productNames = new ArrayList<>();
        for (Supplier supplier : suppliers.getSuppliers()) {
            for (Product product : supplier.getProductCatalog().getProducts()) {
                productNames.add(product.getName());
            }
        }
        return productNames.toArray(new String[0]);
    }
        

    public void printInfo() {
        System.out.println("Supermarket Name: " + supermarketname);
        System.out.println();
        System.out.println("---------- Here are suppliers ----------");
        suppliers.printInfo();
        System.out.println("---------- Here are orders ----------");
        masterOrderList.printInfo();
    }

}
