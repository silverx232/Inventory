package model;

/**
 * A Part outsourced for production.
 *
 * This class defines a Part that was outsourced for production, which the inventory management system can store.
 * It contains the additional parameter for the company name the outsourced part came from.
 */
public class Outsourced extends Part{
    private String companyName;

    /**
     * Constructor for Outsourced.
     *
     * This is the constructor for the Outsourced class, which is a Part.
     * @param id The ID of the Part
     * @param name The name of the Part
     * @param price The price of the Part
     * @param stock The number in inventory of the Part
     * @param min The minimum number that should be in stock
     * @param max The maximum number that can be in stock
     * @param companyName The name of the company the Part was outsourced from
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Getter for the company name.
     *
     * This method returns the name of the company the Part was outsourced from.
     * @return The company name of the product
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Setter for the company name.
     *
     * This method sets the company name of the Part.
     * @param companyName The company name the Part will have
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}
