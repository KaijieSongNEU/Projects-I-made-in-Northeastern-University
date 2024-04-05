package model.Suppliers;

import model.ProductManagement.Product;
import model.ProductManagement.ProductCatalog;

public class Supplier {
    String supplierName;
    ProductCatalog productCatalog;

    public Supplier(String supplierName, ProductCatalog productCatalog) {
        this.supplierName = supplierName;
        this.productCatalog = productCatalog;
    }

    public Supplier(String supplierName) {
        this.supplierName = supplierName;
        this.productCatalog = new ProductCatalog("Product Catalog");
    }

    

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public ProductCatalog getProductCatalog() {
        return productCatalog;
    }

    public void setProductCatalog(ProductCatalog productCatalog) {
        this.productCatalog = productCatalog;
    }

    public Product findProduct(String productName) {
        return productCatalog.findProduct(productName);
    }

    public void printInfo() {
        System.out.println("Supplier Name: " + supplierName);
        productCatalog.printInfo();
    }

    
}
