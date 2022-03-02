package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
 * Controller for the main page of the inventory management system.
 *
 * This class is the controller for the GUI form that displays the Parts and Products in Inventory.
 * Allows the user to search, add, modify, or delete those Parts and Products.
 */
public class MainForm implements Initializable {
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
    private TableColumn<Product, Integer> productIDColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;
    @FXML
    private TableView<Product> productsTable;
    @FXML
    private TextField partsSearchField;
    @FXML
    private TextField productsSearchField;

    /**
     * The initiliazer for the controller.
     *
     * This method initializes the controller class.
     * It sets up the TableViews to show the lists of Parts and Products in Inventory.
     * @param url The location for the controller
     * @param resourceBundle The resources for the controller
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //Set Table View
        partsTable.setItems(Inventory.getAllParts());
        productsTable.setItems(Inventory.getAllProducts());

        //Bind arguments to columns in Table
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Loads the Add Part Form.
     *
     * This method loads the form that allows the user to add new Parts.
     * @param actionEvent The event that called the method
     */
    public void goAddPartForm(ActionEvent actionEvent) {
        // Go to AddPartForm
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AddPartForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Add Part Form");
        stage.show();
    }

    /**
     * Loads the Modify Part Form.
     *
     * This method loads the form that allows the user to modify an existing Part.
     * It passes the selected Part into the ModifyPartForm controller.
     * @param event The event that called the method
     */
    public void onGoModifyPart(ActionEvent event) {
        if (partsTable.getSelectionModel().isEmpty()) {  // No items were selected
            ErrorAlerts.selectionError("part");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyPartForm.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModifyPartForm modifyPart = loader.getController();
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        modifyPart.setSelectedPart(selectedPart);

        //Load the stage
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Modify Part Form");
        stage.show();
    }

    /**
     * Deletes the selected Part.
     *
     * This method removes the selected Part from Inventory's list of Parts.
     * @param event The event that called the method
     */
    public void onDeletePart(ActionEvent event) {
        if (partsTable.getSelectionModel().isEmpty()) {  // No items were selected
            ErrorAlerts.selectionError("part");
            return;
        }

        if (!ErrorAlerts.deleteConfirmation("part"))
            return;

        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        Inventory.deletePart(selectedPart);
    }

    /**
     * Loads the Add Product Form.
     *
     * This method loads the form that allows the user to add new Products.
     * @param event The event that called the method
     */
    public void onAddProduct(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/AddProductForm.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Add Product Form");
        stage.show();
    }

    /**
     * Loads the Modify Product Form.
     *
     * This method loads the form that allows the user to modify an existing Product.
     * It passes the selected Product into the ModifyProductForm controller.
     *
     * <p> RUNTIME ERROR: When I created this method, the MainForm page crashed every time it tried to load. It had loaded
     * perfectly fine every time before then. Eventually I read through all the lines in the error message and found that
     * there was a nullPointerException involved with this method. When loading the main page, it couldn't seem to connect
     * the FXML with this method. That is when I realized the @FXML tag that I thought was extraneous was in fact needed
     * to connect the FXML and the controller method. I added the tag to the method and everything worked again. Later I
     * discovered that JavaDoc won't generate for methods with @FXML above them. But I discovered that I can take out the
     * tag and make the methods public, and they work. </p>
     * @param event The event that called the method
     */
    public void onModifyProduct(ActionEvent event) {
        if (productsTable.getSelectionModel().isEmpty()) {  // No items were selected
            ErrorAlerts.selectionError("product");
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyProductForm.fxml"));

        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ModifyProductForm modifyProduct = loader.getController();
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        modifyProduct.setSelectedProduct(selectedProduct);

        //Load the stage
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 1200, 700));
        stage.setTitle("Modify Product Form");
        stage.show();
    }

    /**
     * Deletes the selected Product.
     *
     * This method removes the selected Product from Inventory's list of Products.
     * A Product cannot be deleted if it contains associated Parts.
     * @param event The event that called the method
     */
    public void onDeleteProduct(ActionEvent event) {
        if (productsTable.getSelectionModel().isEmpty()) {  // No items were selected
            ErrorAlerts.selectionError("product");
            return;
        }

        //Confirm that user wants to delete product
        if (!ErrorAlerts.deleteConfirmation("product"))
            return;

        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();

        //Product cannot be deleted if it contains an associated part
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            ErrorAlerts.associatedPartDeleteError();
            return;
        }

        Inventory.deleteProduct(selectedProduct);
    }

    /**
     * Searches the list of Parts using the given partial Name or ID.
     *
     * This method searches the list of Parts in Inventory for those whose names contain the given String or whose
     * IDs match the given ID. It displays the entire list of Parts if an empty String is entered.
     * @param event The event that called the method
     */
    public void partsSearchHandler(ActionEvent event) {
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
     * Searches the list of Products using the given partial Name or ID.
     *
     * This method searches the list of Products in Inventory for those whose names contain the given String or whose
     * IDs match the given ID. It displays the entire list of Products if an empty String is entered.
     * @param event The event that called the method
     */
    public void productsSearchHandler(ActionEvent event) {
        String userSearch = productsSearchField.getText();
        ObservableList<Product> userList;

        // Search by partial or full name
        userList = Inventory.lookupProduct(userSearch);

        // If no matches found, check ID
        if (userList.size() == 0) {
            try {
                Product result = Inventory.lookupProduct(Integer.parseInt(userSearch));
                if (!Objects.isNull(result))
                    userList.add(result);
            }
            catch (NumberFormatException e) {
                // userSearch is not a valid integer
            }
        }

        if (userList.size() == 0) {  // No products found
            ErrorAlerts.searchNotFoundError("product");
            return;
        }

        productsTable.setItems(userList);
    }

    /**
     * Closes the application.
     *
     * This method exits the inventory management system.
     * @param event The event that called the method
     */
    public void onExit(ActionEvent event) {
        Stage stage = (Stage) ((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
}
