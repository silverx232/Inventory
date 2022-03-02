package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for a Product that can have associated Parts.
 *
 * This class defines a Product that the inventory management system can store.
 * A Product can have associated Parts which are stored in an ObservableList.
 */
public class Product {
    private ObservableList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for Product.
     *
     * This is the constructor for the Product class.
     * associatedParts is the ObservableList of Parts that are associated with the Product.
     * @param id The ID of the Product
     * @param name The name of the Product
     * @param price The price of the Product
     * @param stock The number in inventory of the Product
     * @param min The minimum number that should be in stock
     * @param max The maximum number that can be in stock
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;

        associatedParts = FXCollections.observableArrayList();
    }

    /**
     * Getter for ID.
     *
     * This is the getter for the ID of the Product.
     * @return The ID of the Product
     */
    public int getId() {
        return id;
    }

    /**
     * Setter for ID.
     *
     * This is the setter for the ID of the Product
     * @param id The ID the Product will have
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter for name.
     *
     * This is the getter for the name of the Product.
     * @return The name of the product
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for name.
     *
     * This is the setter for the name of the Product.
     * @param name The name the Product will have
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for price.
     *
     * This is the getter for the price of the Product.
     * @return The price of the Product
     */
    public double getPrice() {
        return price;
    }

    /**
     * Setter for price.
     *
     * This is the setter for the price of the Product.
     * @param price The price the Product will have
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Getter for stock.
     *
     * This is the getter for the stock of the Product.
     * @return The number in stock of the Product
     */
    public int getStock() {
        return stock;
    }

    /**
     * Setter for stock.
     *
     * This is the setter for the stock of the Product.
     * @param stock The number in stock the Product will have
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Getter for min.
     *
     * This is the getter for the min of the Product.
     * @return The minimum number that should be in stock
     */
    public int getMin() {
        return min;
    }

    /**
     * Setter for min.
     *
     * This is the setter for the min of the Product.
     * @param min The minimum number that should be in stock that the Product will have
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Getter for max.
     *
     * This is the getter for the max of the Product.
     * @return The maximum number that can be in stock
     */
    public int getMax() {
        return max;
    }

    /**
     * Setter for max.
     *
     * This is the setter for the max of the Product.
     * @param max The maximum number that can be in stock that the Product will have
     */
    public void setMax(int max) {
        this.max = max;
    }

    /**
     * Adds a Part to the associated parts list.
     *
     * This method adds a Part to the ObservableList associatedParts, which contains a list of all Parts associated
     * with the Product.
     * @param part The Part to be added to the list
     */
    //Add a part to the associatedParts list
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes a Part from the associated parts list.
     *
     * This method deletes a Part from the ObservableList associatedParts, which contains a list of all Parts
     * associated with the Product.
     * @param selectedAssociatedPart The Part to be deleted from the list
     * @return Returns true if the Part was found and deleted. (The list changed as a result of the call.)
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        return associatedParts.remove(selectedAssociatedPart);
    }

    /**
     * Returns a list of all Parts associated with the Product.
     *
     * This method returns an ObservableList of all Parts associated with the Product.
     * It returns a new copy of associatedParts.
     * @return Returns a new ObservableList that matches the associatedParts list.
     */
    public ObservableList<Part> getAllAssociatedParts() {
        ObservableList<Part> copyAssociatedParts = FXCollections.observableArrayList();
        copyAssociatedParts.addAll(associatedParts);

        return copyAssociatedParts;
    }
}
