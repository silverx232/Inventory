package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Controller for the form that adds Parts to the inventory management system.
 *
 * This class is the controller for the GUI form that allows the user to add Parts to Inventory.
 */
public class AddPartForm implements Initializable {
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private Label inOutLabel;
    @FXML
    private TextField inOutField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField stockField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField maxField;
    @FXML
    private TextField minField;
    @FXML
    private Label errorLabel;

    /**
     * The initializer for the controller.
     *
     * This method initializes the controller class.
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    // Used by other methods to load the MainForm controller
    private void toMainForm(ActionEvent actionEvent) {
        //Load MainForm Scene (titled "Inventory Management System")
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Inventory Management System");
        stage.show();
    }

    /**
     * Creates a new Part and saves it in Inventory.
     *
     * This method creates a new Part using the information given by the user. If the information doesn't pass the
     * validation check, a new part will not be created and an error message will be displayed.
     * @param event The event that called the method
     */
    public void savePart(ActionEvent event) {
        boolean inHouse = inHouseRadio.isSelected();
        String name = nameField.getText();
        String stockString = stockField.getText();
        String priceString = priceField.getText();
        String maxString = maxField.getText();
        String minString = minField.getText();
        String inOutString = inOutField.getText();

        //Check whether entered information is valid
        String errorMessage = ErrorAlerts.formValidation(name, stockString, priceString, maxString, minString);
        if (inHouse) {
            try {
                Integer.parseInt(inOutString);
            } catch (NumberFormatException e) {
                errorMessage += "Machine ID must be a valid integer.\n";
            }
        }
        else if (inOutString.isBlank())
            errorMessage += "Company Name must contain data.\n";

        if (!errorMessage.isEmpty()) {
            errorLabel.setText("Exception: " + errorMessage);
            return;
        }

        int stock = Integer.parseInt(stockString);
        double price = Double.parseDouble(priceString);
        int max = Integer.parseInt(maxString);
        int min = Integer.parseInt(minString);
        int id = 0;

        // Find the max id in Inventory.allParts and increment for id
        for (Part currentPart : Inventory.getAllParts()) {
            if (currentPart.getId() > id)
                id = currentPart.getId();
        }
        id++;

        // Determine whether Part is InHouse or Outsourced
        if (inHouse) {
            int machineID = Integer.parseInt(inOutString);
            Part userPart = new InHouse(id, name, price, stock, min, max, machineID);
            Inventory.addPart(userPart);
        }
        else {  // outsourcedRadio is selected
            Part userPart = new Outsourced(id, name, price, stock, min, max, inOutString);
            Inventory.addPart(userPart);
        }

        //Go back to MainForm
        toMainForm(event);
    }

    /**
     * Returns the user to the home page.
     *
     * This method cancels the add part form and returns the user to the main page of the inventory management system.
     * @param event The event that called the method
     */
    public void onCancel(ActionEvent event) {
        toMainForm(event);
    }

    /**
     * Sets the Part as being of type InHouse.
     *
     * This method sets the labels to the correct parameters for an InHouse Part.
     * @param event The event that called the method
     */
    public void onInHouseRadio(ActionEvent event) {
        inOutLabel.setText("Machine ID");
    }

    /**
     * Sets the Part as being of type Outsourced.
     *
     * This method sets the labels to the correct parameters for an Outsourced Part.
     * @param event The event that called the method
     */
    public void onOutsourcedRadio(ActionEvent event) {
        inOutLabel.setText("Company Name");
    }
}
