package model.OrderManagement;

import model.ProductManagement.Product;

public class OrderItem {

    // OrderItem的序列号
    Product selectedproduct;
    double actualPrice;
    int quantity;

    public OrderItem(Product selectedproduct, double actualPrice, int quantity) {
        this.selectedproduct = selectedproduct;
        this.actualPrice = actualPrice;
        this.quantity = quantity;
    }

    public Product getSelectedproduct() {
        return selectedproduct;
    }

    public void setSelectedproduct(Product selectedproduct) {
        this.selectedproduct = selectedproduct;
    }

    public double getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(int actualPrice) {
        this.actualPrice = actualPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getStock() {
        return selectedproduct.getStock();
    }

    public void printopurchaseOIInfo() {
        selectedproduct.printPurchaseOrderProductInfo();
    }

    public void printSaleOIInfo() {
        selectedproduct.printSaleOrderProductInfo();
    }

    public void printOIInfo() {
        selectedproduct.printallinfo();
        System.out.println("Actual Price: " + actualPrice);
        System.out.println("Quantity: " + quantity);
    }

    
}
