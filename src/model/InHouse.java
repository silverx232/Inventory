package model;

/**
 * A Part produced In-House.
 *
 * This class defines a Part that was produced In-House, which the inventory management system can store.
 * It contains the additional parameter for a machine ID.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for InHouse.
     *
     * This is the constructor for the InHouse class, which is a Part.
     * @param id The ID of the Part
     * @param name The name of the Part
     * @param price The price of the Part
     * @param stock The number in inventory of the Part
     * @param min The minimum number that should be in stock
     * @param max The maximum number that can be in stock
     * @param machineId The Machine ID of the Part
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Getter for the machine ID.
     *
     * This method returns the machineID of the Part
     * @return The machine ID for the Part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Setter for the machine ID.
     *
     * This method sets the name of the machineID of the Part
     * @param machineId The machine ID the Part will have
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
