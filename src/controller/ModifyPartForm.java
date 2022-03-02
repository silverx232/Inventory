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
 * Controller for the form that modifies Parts in the inventory management system.
 *
 * This class is the controller for the GUI form that allows the user to modify a Part in Inventory.
 */
public class ModifyPartForm implements Initializable {
    @FXML
    private RadioButton inHouseRadio;
    @FXML
    private RadioButton outsourcedRadio;
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
    private TextField idField;
    @FXML
    private Label errorLabel;

    private Part selectedPart;

    /**
     * The initiliazer for the controller.
     *
     * This method initializes the controller class.
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Passes the user selected Part from the Home page to the Modify Part page.
     *
     * This method is used by the MainForm controller to pass the user selected Part into this controller
     * @param selectedPart The Part the user wants to modify
     */
    public void setSelectedPart(Part selectedPart) {
        this.selectedPart = selectedPart;

        idField.setText(Integer.toString(selectedPart.getId()));
        nameField.setText(selectedPart.getName());
        stockField.setText(Integer.toString(selectedPart.getStock()));
        priceField.setText(Double.toString(selectedPart.getPrice()));
        maxField.setText(Integer.toString(selectedPart.getMax()));
        minField.setText(Integer.toString(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            inOutField.setText(Integer.toString(((InHouse)selectedPart).getMachineId()));
            inHouseRadio.setSelected(true);
            inHouseRadio.fireEvent(new ActionEvent());
        }
        else {  // selectedPart is an instanceof Outsourced
            inOutField.setText(((Outsourced)selectedPart).getCompanyName());
            outsourcedRadio.setSelected(true);
            outsourcedRadio.fireEvent(new ActionEvent());
        }
    }

    /**
     * Modifies the selected Part and saves it in Inventory.
     *
     * This method creates a new Part using the information given by the user. If the information doesn't pass the
     * validation check, a new Part will not be created and an error message will be displayed. The new Part is
     * then saved in place of the old Part, overwriting the old Part.
     * @param event The event that called the method
     */
    public void onModifyPart(ActionEvent event) {
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
        int id = selectedPart.getId();
        int index = Inventory.getAllParts().indexOf(selectedPart);

        // Determine whether Part is InHouse or Outsourced
        if (inHouse) {
            int machineID = Integer.parseInt(inOutString);
            selectedPart = new InHouse(id, name, price, stock, min, max, machineID);
        }
        else {  // outsourcedRadio is selected
            selectedPart = new Outsourced(id, name, price, stock, min, max, inOutString);
        }

        Inventory.updatePart(index, selectedPart);

        // Return to MainForm
        toMainForm(event);
    }

    //Load MainForm Scene (titled "Inventory Management System")
    private void toMainForm(ActionEvent actionEvent) {
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
     * Returns the user to the home page.
     *
     * This method cancels any changes made to the Part returns the user to the main page of the inventory
     * management system.
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
