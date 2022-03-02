package model;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

/**
 * Class that contains methods to display Alert windows.
 *
 * This class is used by the program controllers to display Alert windows and perform validation checking for the forms.
 */
public class ErrorAlerts {
    /**
     * Informs the user of a selection error.
     *
     * This method displays a window informing the user that they need to select a part or product before they can
     * modify or delete it.
     * @param objectType Contains the string for "part" or "product", depending on which controller is calling it
     */
    public static void selectionError(String objectType) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Selection Error");
        alert.setHeaderText("Selection Error");
        alert.setContentText("Please select a " + objectType + ".");
        alert.showAndWait();
    }

    /**
     * Informs that user that the searched for item does not exist.
     *
     * This method displays a window informing the user that the product or part they searched for does not exist.
     * @param objectType Contains the string for "part" or "product", depending on which controller is calling it
     */
    public static void searchNotFoundError(String objectType) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Search Not Found");
        alert.setContentText("No such " + objectType + " exists.");
        alert.showAndWait();
    }

    /**
     * Asks for confirmation from the user before deleting a part or product.
     *
     * This method asks for confirmation from the user before deleting a part or product.
     * @param objectType Contains the string for "part" or "product", depending on which controller is calling it
     * @return Returns true if the user selects OK
     */
    public static boolean deleteConfirmation(String objectType) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete");
        alert.setContentText("Do you want to delete this " + objectType + "?");
        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && (result.get() == ButtonType.OK);
    }

    /**
     * Displays an Alert warning the user that the product cannot be deleted if it has associated parts.
     *
     * This method displays an Alert windows informing the user that a Product cannot be deleted if it still has a
     * Part associated with it.
     */
    public static void associatedPartDeleteError() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setHeaderText("Associated Part");
        alert.setContentText("A product cannot be deleted if it contains associated parts.");
        alert.showAndWait();
    }

    /**
     * Validates the information entered by the user.
     *
     * This method validates the information entered when adding or modifying Parts or Products.
     * A name must contain data.
     * Inventory must be an integer. Inventory must be between max and min.
     * Price must be a double.
     * Max and min must be integers. Max cannot be smaller than min.
     * (InHouse and Outsourced Parts have an additional parameter that is checked elsewhere.)
     * @param name Name of the Product or Part
     * @param inventory The number of items in stock
     * @param price The price of the Product or Part
     * @param max The maximum number that can be in stock
     * @param min The minimum number that can be in stock
     * @return Returns a String containing messages from any failed checks
     */
    public static String formValidation(String name, String inventory, String price, String max, String min) {
        StringBuilder errorMessage = new StringBuilder("");
        int maxNum = 0;
        int minNum = 0;
        int inventoryNum = 0;
        boolean isValidMinMax = true;
        boolean isValidInventory = true;

        if (name.isBlank())
            errorMessage.append("Name field must contain data.\n");

        try {
            inventoryNum = Integer.parseInt(inventory);
        } catch (NumberFormatException e) {
            errorMessage.append("Inventory must be a valid integer.\n");
            isValidInventory = false;
        }

        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            errorMessage.append("Price must be a valid double.\n");
        }

        try {
            maxNum = Integer.parseInt(max);
        } catch (NumberFormatException e) {
            errorMessage.append("Max must be a valid integer.\n");
            isValidMinMax = false;
        }

        try {
            minNum = Integer.parseInt(min);
        } catch (NumberFormatException e) {
            errorMessage.append("Min must be a valid integer.\n");
            isValidMinMax = false;
        }

        if (isValidMinMax && maxNum < minNum)
            errorMessage.append("Max cannot be less than Min.\n");

        if (isValidInventory && isValidMinMax && (inventoryNum < minNum || inventoryNum > maxNum))
            errorMessage.append("Inventory cannot be less than Min or greater than Max.\n");

        return errorMessage.toString();
    }
}
