package model.OrderManagement;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import model.ProductManagement.Product;
import java.time.Period;

public class Order {
    ArrayList<OrderItem> orderitems;
    private String orderID;
    // 判断是进货还是出货
    private boolean isPurchase;


    public Order(String orderID, boolean isPurchase) {
        this.orderID = orderID;
        orderitems = new ArrayList<OrderItem>();
        this.isPurchase = isPurchase;
    }

    public ArrayList<OrderItem> getOrderitems() {
        return orderitems;
    }

    public void setOrderitems(ArrayList<OrderItem> orderitems) {
        this.orderitems = orderitems;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public boolean isPurchase() {
        return isPurchase;
    }

    public void addNewOrderItem(int quantity, double price, Product product) {
        OrderItem orderItem = new OrderItem(product, price, quantity);
        orderitems.add(orderItem);
    }

    public void removeOrderItem(OrderItem orderItem) {
        orderitems.remove(orderItem);
    }

    public OrderItem findOrderItem(String productName, LocalDate productionDate, Period shelfLife, Double price) {
        for (OrderItem orderItem : orderitems) {
            System.out.println(orderItem.getSelectedproduct().getName() + " " + orderItem.getSelectedproduct().getProductionDate() + " " + orderItem.getSelectedproduct().getShelfLife() + " " + orderItem.getActualPrice());
            if (orderItem.getSelectedproduct().getName().equals(productName)
                    && orderItem.getSelectedproduct().getProductionDate().equals(productionDate)
                    && orderItem.getSelectedproduct().getShelfLife().equals(shelfLife)
                    && orderItem.getActualPrice() == price) {
                return orderItem;
            }
        }
        System.out.println("OrderItem not found");
        return null;
    }

    // public OrderItem addNewOrderItem1(int quantity, double price, )

    public String printInfo() {
        StringBuilder sb = new StringBuilder();
        for (OrderItem orderItem : orderitems) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            if (isPurchase) {
                sb.append(String.format("%-15s%-18s%-12s%-15s%-10.2f%-13d%d%n",
                        orderItem.getSelectedproduct().getName(),
                        orderItem.getSelectedproduct().getProductionDate().format(formatter),
                        orderItem.getSelectedproduct().getShelfLife().toString(),
                        orderItem.getSelectedproduct().getExpirationDate().format(formatter),
                        orderItem.getActualPrice(),
                        orderItem.getQuantity(),
                        orderItem.getSelectedproduct().getStock()));
            } else {
                sb.append(String.format("%-15s%-18s%-12s%-15s%-10.2f%-13d%s%n",
                        orderItem.getSelectedproduct().getName(),
                        orderItem.getSelectedproduct().getProductionDate().format(formatter),
                        orderItem.getSelectedproduct().getShelfLife().toString(),
                        orderItem.getSelectedproduct().getExpirationDate().format(formatter),
                        orderItem.getActualPrice(),
                        orderItem.getQuantity(),
                        "N/A"));
            }
        }
        return sb.toString();
    }

    public void printInfo2() {
        for(OrderItem orderItem : orderitems){
            orderItem.printOIInfo();
        }
    }
      
}
