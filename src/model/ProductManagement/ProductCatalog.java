package model.ProductManagement;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ProductCatalog {

    String name;
    ArrayList<Product> products;

    public ProductCatalog(String name) {
        this.name = name;
        products = new ArrayList<Product>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Product addNewProduct(String name, double price, LocalDate productionDate, Period shelfLife, int stock) {
        Product product = new Product(name, price, productionDate, shelfLife,100);
        products.add(product);
        return product;
    }

    public Product addNewProductWithStockOnly(String name, int stock) {
        Product product = new Product(name, stock);
        products.add(product);
        return product;
    }

    public Product pickrandomProduct() {
        int random = (int) (Math.random() * products.size());
        return products.get(random);
    }

    public Product findProduct (String productName) {
        for (Product product : products) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }

    public void printInfo() {
        System.out.println("Product Name   Stock");
        for (Product product : products) {
            product.printInfo();
        }
    }

    
}
