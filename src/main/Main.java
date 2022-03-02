// JavaDoc comments found in Inventory_C482/JavaDoc/index.html

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class for an inventory management system.
 *
 * This is the main class for an inventory management system designed to store Products and Parts.
 * <p> FUTURE ENHANCEMENT: Deleting Products with attached associated Parts is a long process. It would be nice to add an
 * option to automatically remove all associated Parts of a product before deleting. This could be an optional button
 * placed on the warning Alert dialogue window that pops up when a product with associated parts is deleted. </p>
 *
 * <p> JavaDoc comments found in Inventory_C482/JavaDoc/index.html </p>
 */
public class Main extends Application {

    /**
     * Launches the GUI.
     *
     * This method starts the program and launches the GUI.
     * @param stage The primary stage for the GUI
     * @throws Exception Exceptions that may occur in the program
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainForm.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1200, 700));
        stage.show();
    }

    /**
     * Main method for the Main class.
     *
     * This is the main method for the Main class and launches the program.
     * @param args Arguments for launching the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}
