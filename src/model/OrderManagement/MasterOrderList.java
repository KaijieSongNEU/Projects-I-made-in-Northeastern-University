package model.OrderManagement;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MasterOrderList {
    ArrayList<Order> orders;

    public MasterOrderList() {
        orders = new ArrayList<Order>();
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public void addNewOrder(String orderID, boolean isPurchase) {
        Order order = new Order(orderID, isPurchase);
        orders.add(order);
    }

    public Order addOrder(Order order) {
        orders.add(order);
        return order;
    }

    public Order findOrder(String orderID) {
        for (Order order : orders) {
            if (order.getOrderID().equals(orderID)) {
                return order;
            }
        }
        return null;
    }

    public void removeOrder(String orderID) {
        for (Order order : orders) {
            if (order.getOrderID().equals(orderID)) {
                orders.remove(order);
                break;
            }
        }
    }

    public void searchOrderbyProduct(String productName) {
        System.out.println("it works ");
        for (Order order : orders) {
            for (OrderItem orderItem : order.getOrderitems()) {
                if (orderItem.getSelectedproduct().getName().equals(productName)&&order.isPurchase()) {
                    System.out.println("Order ID: " + order.getOrderID());
                    orderItem.printopurchaseOIInfo();
                } else if (orderItem.getSelectedproduct().getName().equals(productName)&&!order.isPurchase()) {
                    System.out.println("Order ID: " + order.getOrderID());
                    orderItem.printSaleOIInfo();
                }
            }
        }
    }

    public Order searchOrderbyOrderID(String orderID) {
        for (Order order : orders) {
            if (order.getOrderID().equals(orderID)) {
                return order;
            }
        }
        return null;
    }   

    public String generaterandomOrderID() {
        // 生成10位随机数
        String orderID = "";
        for (int i = 0; i < 10; i++) {
            orderID += (int) (Math.random() * 10);
        }
        return orderID;
    }




    public void printInfo() {
        for (Order order : orders) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("ProductName   ProductionDate   ShelfLife   ExpirationDate   Price   Quantity   Current stock" );
            System.out.println(order.printInfo());
            
        }
    }

}
