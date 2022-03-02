package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.ErrorAlerts;
import model.Inventory;
import model.Part;
import model.Product;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * Controller for the form that adds Products to the inventory management system.
 *
 * This class is the controller for the GUI form that allows the user to add Products to Inventory.
 */
public class AddProductForm implements Initializable {
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Double> partPriceColumn;
    @FXML
    private TableView<Part> partsTable;
    @FXML
    private TableColumn<Part, Integer> associatedIDCol;
    @FXML
    private TableColumn<Part, Integer> associatedNameCol;
    @FXML
    private TableColumn<Part, String> associatedInventoryCol;
    @FXML
    private TableColumn<Part, Double> associatedPriceCol;
    @FXML
    private TableView<Part> associatedPartsTable;
    @FXML
    private TextField partsSearchField;
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

    private ObservableList<Part> associatedParts;

    /**
     * The initiliazer for the controller.
     *
     * This method initializes the controller class. It sets up the TableViews to show the lists of allParts in
     * Inventory and associatedParts with the Product.
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        associatedParts = FXCollections.observableArrayList();

        //Set Table View
        partsTable.setItems(Inventory.getAllParts());
        associatedPartsTable.setItems(associatedParts);

        //Bind arguments to columns in Table
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedIDCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedInventoryCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Searches the list of Parts using the given partial Name or ID.
     *
     * This method searches the list of allParts in Inventory for Parts whose names contain the given String or whose
     * IDs match the given ID. It displays the entire list of Parts if an empty String is entered.
     * @param event The event that called the method
     */
    public void onSearchPart(ActionEvent event) {
        String userSearch = partsSearchField.getText();
        ObservableList<Part> userList;

        // Search by partial or full name
        userList = Inventory.lookupPart(userSearch);

        // If no matches found, check ID
        if (userList.size() == 0) {
            try {
                Part result = Inventory.lookupPart(Integer.parseInt(userSearch));
                if (!Objects.isNull(result))
                    userList.add(result);
            }
            catch (NumberFormatException e) {
                // userSearch is not a valid integer
            }
        }

        if (userList.size() == 0) {  // No parts found
            ErrorAlerts.searchNotFoundError("part");
            return;
        }

        partsTable.setItems(userList);
    }

    /**
     * Creates a new Product and saves it in Inventory.
     *
     * This method creates a new Product using the information given by the user. If the information doesn't pass the
     * validation check, a new Product will not be created and an error message will be displayed.
     * @param event The event that called the method
     */
    public void onAddProduct(ActionEvent event) {
        String name = nameField.getText();
        String stockString = stockField.getText();
        String priceString = priceField.getText();
        String maxString = maxField.getText();
        String minString = minField.getText();

        //Check whether entered information is valid
        String errorMessage = ErrorAlerts.formValidation(name, stockString, priceString, maxString, minString);
        if (!errorMessage.isBlank()) {
            errorLabel.setText(errorMessage);
            return;
        }

        int stock = Integer.parseInt(stockField.getText());
        double price = Double.parseDouble(priceField.getText());
        int max = Integer.parseInt(maxField.getText());
        int min = Integer.parseInt(minField.getText());
        int id = 0;

        // Find the max id in Inventory.allProducts and increment for id
        for (Product currentProduct : Inventory.getAllProducts()) {
            if (currentProduct.getId() > id)
                id = currentProduct.getId();
        }
        id++;

        // Add new Product and set its associatedParts list
        Product userProduct = new Product(id, name, price, stock, min, max);

        // Add all associated Parts
        for (Part currentPart: associatedParts) {
            userProduct.addAssociatedPart(currentPart);
        }

        Inventory.addProduct(userProduct);

        // Go back to MainForm
        toMainForm(event);
    }

    /**
     * Returns the user to the home page.
     *
     * This method cancels the add product form and returns the user to the main page of the inventory management system.
     * @param event The event that called the method
     */
    public void onCancel(ActionEvent event) {
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
     * Adds the selected Part into the Product's associated Parts list.
     *
     * This method takes the selected Part from Inventory's list of parts and adds it into the associated Parts list
     * for the new Product. The Part will still remain in Inventory.
     * @param event The event that called the method
     */
    public void onAddPart(ActionEvent event) {
        if (partsTable.getSelectionModel().isEmpty()) {  // No items were selected
            ErrorAlerts.selectionError("part");
            return;
        }

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        associatedParts.add(selectedPart);
    }

    /**
     * Removes the selected Part from the Product's associated Parts list.
     *
     * This method removes the selected Part from the new Product's associated Parts list.
     * @param event The event that called the method
     */
    public void onRemovePart(ActionEvent event) {
        if (associatedPartsTable.getSelectionModel().isEmpty()) {  // No items were selected
            ErrorAlerts.selectionError("part");
            return;
        }

        if (!ErrorAlerts.deleteConfirmation("part"))
            return;

        int selectedIndex = associatedPartsTable.getSelectionModel().getSelectedIndex();
        associatedParts.remove(selectedIndex);
    }
}
