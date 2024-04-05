package model.ProductManagement;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import model.OrderManagement.OrderItem;

public class Product {
    private String name;
    private double purchasePrice;
    private LocalDate productionDate;
    private Period shelfLife;
    // 库存量
    private int stock;
    private int quantity;

    ArrayList<OrderItem> orderItems;

    public Product(String name, double purchasePrice, LocalDate productionDate, Period shelfLife, int stock) {
        this.name = name;
        this.purchasePrice = purchasePrice;
        this.productionDate = productionDate;
        this.shelfLife = shelfLife;
        this.stock = stock;
        orderItems = new ArrayList<OrderItem>();
        
    }

    public Product(String name, int stock) {
        this.name = name;
        this.stock = stock;
    }

    public Product(String name, LocalDate productionDate, Period shelfLife, Double purchasePrice, int quantity) {
        this.name = name;
        this.productionDate = productionDate;
        this.shelfLife = shelfLife;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
    }

    public Product(String name, LocalDate productionDate, Period shelfLife, Double purchasePrice, int quantity, int stock) {
        this.name = name;
        this.productionDate = productionDate;
        this.shelfLife = shelfLife;
        this.purchasePrice = purchasePrice;
        this.quantity = quantity;
        this.stock = stock;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(double purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public LocalDate getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(LocalDate productionDate) {
        this.productionDate = productionDate;
    }

    public Period getShelfLife() {
        return shelfLife;
    }

    public void setShelfLife(Period shelfLife) {
        this.shelfLife = shelfLife;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public ArrayList<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(ArrayList<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public LocalDate getExpirationDate() {
        return productionDate.plus(shelfLife);
    }

    

    public void printInfo() {
        System.out.printf("%-15s%d%n", name, stock);
    }

    public void printPurchaseOrderProductInfo() {
        System.out.println("Product Name: " + name + " Purchase Price: " + purchasePrice + " Production Date: " + productionDate + " Shelf Life: " + shelfLife + " Quantity: " + quantity + " Stock: " + stock);
    }

    public void printSaleOrderProductInfo() {
        System.out.println("Product Name: " + name + " Sale Price: " + purchasePrice + " Production Date: " + productionDate + " Shelf Life: " + shelfLife + " Quantity: " + quantity);

    }

    public void printallinfo(){
        System.out.println("Product Name: " + name + " Purchase Price: " + purchasePrice + " Production Date: " + productionDate + " Shelf Life: " + shelfLife + " Stock: " + stock);
    }

}
