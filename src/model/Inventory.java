package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory used by the inventory management system.
 *
 * This is the Class used by the inventory management system to track the stored Parts and Products.
 * It contains ObservableLists of Parts and Products and methods to act on those lists.
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();


    /**
     *Adds a Part to the ObservableList of Parts.
     *
     * This method adds a Part to the ObservableList of all Parts in Inventory.
     * @param newPart The new Part to be added to the list
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a Product to the ObservableList of Products.
     *
     * This method adds a Product to the ObservableList of all Products in Inventory.
     * @param newProduct The new Product to be added to the list
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Finds a Part using the given ID.
     *
     * This method finds the Part in allParts list using the given ID of the Part.
     * @param partId The ID of the Part to be looked up
     * @return Returns the first Part found in allParts list with given partId, or null if not found
     */
    public static Part lookupPart(int partId) {
        // Iterate through the allParts list and compare Part.id to partId
        for (Part currentPart : allParts) {
            if (currentPart.getId() == partId)
                return currentPart;
        }

        //No matching Part found
        return null;
    }

    /**
     * Finds a Product using the given ID.
     *
     * This method finds the Product in allProducts list using the given ID of the Product.
     * @param productId The ID of the Product to be looked up
     * @return Returns the first Product found in allProducts list with given productId, or null if not found
     */
    public static Product lookupProduct(int productId) {
        for (Product currentProduct : allProducts) {
            if (currentProduct.getId() == productId)
                return currentProduct;
        }

        // No matching Product found
        return null;
    }

    /**
     * Forms a list of all Parts whose name contains the given String.
     *
     * This method creates an ObservableList of Parts in Inventory whose name contains the given String partName.
     * It ignores case. It returns the entire allParts list if partName is an empty String.
     * @param partName The String to be compared to the Parts' names
     * @return Returns an ObservableList of parts found whose name contains the given String
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchParts = FXCollections.observableArrayList();
        partName = partName.toLowerCase();

        for (Part currentPart : allParts) {
            if (currentPart.getName().toLowerCase().contains(partName))
                searchParts.add(currentPart);
        }

        return searchParts;
    }

    /**
     * Forms a list of all Products whose name contains the given String.
     *
     * This method creates an ObservableList of Products in Inventory whose name contains the given String productName.
     * It ignores case. It returns the entire allProducts list if productName is an empty String.
     * @param productName The String to be compared to the Products' names
     * @return Returns an ObservableList of Products found whose name contains the given String
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchProducts = FXCollections.observableArrayList();
        productName = productName.toLowerCase();

        for (Product currentProduct: allProducts) {
            if (currentProduct.getName().toLowerCase().contains(productName))
                searchProducts.add(currentProduct);
        }

        return searchProducts;
    }

    /**
     * Assigns the given Part to the specified index location.
     *
     * This method places the given Part into the allParts list at the specified index.
     * @param index Index where the Part will be placed in the list
     * @param selectedPart The Part that will be placed in the list
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Assigns the given Product to the specified index location.
     *
     * This method places the given Product into the allProducts list at the specified index.
     * @param index Index where the Product will be placed in the list
     * @param newProduct The Product that will be placed in the list
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Removes the given Part from the list of Parts.
     *
     * This method removes the selected Part from the list of Parts found in Inventory.
     * @param selectedPart The Part that will be removed from the list
     * @return Returns true if the Part was found and deleted. (If the list changed)
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Removes the given Product from the list of Products.
     *
     * This method removes the selected Product from the list of Products found in Inventory.
     * @param selectedProduct The Product that will be removed from the list
     * @return Returns true if the Product was found and deleted. (If the list changed)
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Getter for allParts.
     *
     * This method is the getter for the ObservableList allParts.
     * @return Returns the list of Parts found in Inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Getter for allProducts.
     *
     * This method is the getter for the ObservableList allProducts.
     * @return Returns the list of Products found in Inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }


//    //    THE FOLLOWING CODE IS FOR TESTING PURPOSES
//    static {
//        try {
//            allParts.add(new InHouse(1, "wheel", 2.50, 15, 0, 15, 1234));
//            allParts.add(new Outsourced(2, "Handlebars", 1.2, 3, 0, 10, "Wheeler's Co."));
//            allParts.add(new Outsourced(3, "Door", 4.2, 1, 0, 4, "Door Co."));
//            allParts.add(new InHouse(4, "Screw", 0.5, 100, 10, 500, 3214));
//
//            Product car = new Product(1, "car", 10_000, 3, 1, 10);
//            car.addAssociatedPart(allParts.get(0));
//            car.addAssociatedPart(allParts.get(1));
//            car.addAssociatedPart(allParts.get(2));
//            allProducts.add(car);
//
//            allProducts.add(new Product(2, "Bicycle", 99.99, 12, 0, 15));
//            allProducts.add(new Product(3, "Some Product", 25.5, 3, 1, 6));
//            allProducts.add(new Product(4, "Expensive and Rare", 400.99, 1, 0, 1));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
