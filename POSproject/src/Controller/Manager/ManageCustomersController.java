package Controller.Manager;

import DBConnection.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ManageCustomersController {

    @FXML
    private TableView<?> Employee_table;

    @FXML
    private Button btn_Add_Customer;

    @FXML
    private TableColumn<?, ?> col_address;

    @FXML
    private TableColumn<?, ?> col_cid;

    @FXML
    private TableColumn<?, ?> col_name;

    @FXML
    private TableColumn<?, ?> col_phone;

    @FXML
    private Label lbl_Manage_Customer;

    @FXML
    private ScrollPane scrole_pane;


    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Parent root;
    Stage stage ;
    Scene scene;
    Stage primaryStage;

    public ManageCustomersController() {
        con = DBConnection.conDB();
    }


    public void handle_addCustomer(MouseEvent mouseEvent) {

        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/AddCustomer.fxml"));
            stage =(Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            primaryStage = new Stage();
            primaryStage.setAlwaysOnTop(true);
            scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED );
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}

