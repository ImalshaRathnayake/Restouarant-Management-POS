package Controller.Manager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

public class ManageReportsController {

    @FXML
    private TableView<?> Employee_table;

    @FXML
    private Button btn_Add_employee;

    @FXML
    private TableColumn<?, ?> col_Password;

    @FXML
    private TableColumn<?, ?> col_address;

    @FXML
    private TableColumn<?, ?> col_dob;

    @FXML
    private TableColumn<?, ?> col_eid;

    @FXML
    private TableColumn<?, ?> col_email;

    @FXML
    private TableColumn<?, ?> col_first_name;

    @FXML
    private TableColumn<?, ?> col_last_name;

    @FXML
    private TableColumn<?, ?> col_nic;

    @FXML
    private TableColumn<?, ?> col_phone;

    @FXML
    private TableColumn<?, ?> col_position;

    @FXML
    private TableColumn<?, ?> col_userName;

    @FXML
    private Label lbl_Manage_Employees;

    @FXML
    private ScrollPane scrole_pane;

    @FXML
    void handle_addEmployee(MouseEvent event) {

    }

}

