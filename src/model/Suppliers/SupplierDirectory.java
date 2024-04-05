package model.Suppliers;

import java.util.ArrayList;

public class SupplierDirectory {
    ArrayList<Supplier> suppliers;

    public SupplierDirectory() {
        suppliers = new ArrayList<Supplier>();
    }

    public ArrayList<Supplier> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(ArrayList<Supplier> suppliers) {
        this.suppliers = suppliers;
    }

    public Supplier addNewSupplier(String supplierName) {
        Supplier supplier = new Supplier(supplierName);
        suppliers.add(supplier);
        return supplier;
    }

    public Supplier findSupplierbyProduct (String productName) {
        for (Supplier supplier : suppliers) {
            if (supplier.getProductCatalog().findProduct(productName) != null) {
                return supplier;
            }
        }
        return null;
    }

    public Supplier findSupplierbyName(String supplierName) {
        for (Supplier supplier : suppliers) {
            if (supplier.getSupplierName().equals(supplierName)) {
                return supplier;
            }
        }
        return null;
    }

    public void printInfo() {
        for (Supplier supplier : suppliers) {
            System.out.println("Supplier Name: " + supplier.getSupplierName());
            supplier.getProductCatalog().printInfo();
            System.out.println();
        }
    }

   

}
