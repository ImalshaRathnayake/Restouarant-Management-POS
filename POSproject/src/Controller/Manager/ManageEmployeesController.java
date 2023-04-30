package Controller.Manager;

import DBConnection.DBConnection;
import Model.Manager.ManageEmployees;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class ManageEmployeesController implements Initializable {

//these are gui variables-----------------------------------------------------------------------------------------------
    @FXML
    private TableView<ManageEmployees> Employee_table;
    @FXML
    private Button btn_Add_employee;
    @FXML
    private TableColumn<ManageEmployees, String> col_Password;
    @FXML
    private TableColumn<ManageEmployees, String> col_address;
    @FXML
    private TableColumn<ManageEmployees, Date> col_dob;
    @FXML
    private TableColumn<ManageEmployees, Integer> col_eid;
    @FXML
    private TableColumn<ManageEmployees, String> col_email;
    @FXML
    private TableColumn<ManageEmployees, String> col_first_name;
    @FXML
    private TableColumn<ManageEmployees, String> col_last_name;
    @FXML
    private TableColumn<ManageEmployees, String> col_nic;
    @FXML
    private TableColumn<ManageEmployees, Integer> col_phone;
    @FXML
    private TableColumn<ManageEmployees, String> col_position;
    @FXML
    private TableColumn<ManageEmployees, String> col_userName;
    @FXML
    private Label lbl_Manage_Employees;
    @FXML
    private ScrollPane scrole_pane;


    //these are global variable witch help to me------------------------------------------------------------------------
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Parent root;
    Stage stage ;
    Scene scene;
    Stage primaryStage;

    //observe list for print item menues--------------------------------------------------------------------------------
    ObservableList<ManageEmployees> employee_list = FXCollections.observableArrayList();

    //the constructor without parameters--------------------------------------------------------------------------------
    public ManageEmployeesController() {

        con = DBConnection.conDB();
    }

    //the initialize method witch  call and load recipe list when this page open
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        load_data();
    }

    //the method witch load data  from the database---------------------------------------------------------------------
    public void load_data(){
        try {

            ps = con.prepareStatement("SELECT *FROM employee");
            rs =ps.executeQuery();
            while(rs.next()){

                int id= rs.getInt("Employee_ID");
                String first_name=rs.getString("Employee_First_Name");
                String last_name=rs.getString("Employee_Last_Name");
                int phone= rs.getInt("Employee_Phone");
                String address=rs.getString("Employee_Address");
                String position=rs.getString("Employee_Position");
                Date dob =rs.getDate("employee_dob");
                String nic= rs.getString("Employee_NIC_NO");
                String email=rs.getString("Employee_Email");
                String userName=rs.getString("Employee_UserName");
                String passWord=rs.getString("Employee_PassWord");

                employee_list.add(new ManageEmployees(id, first_name, last_name, phone, address, position, dob, nic, email, userName, passWord));
            }

            col_eid.setCellValueFactory(new PropertyValueFactory<>("eid"));
            col_first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
            col_last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
            col_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
            col_address.setCellValueFactory(new PropertyValueFactory<>("address"));
            col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
            col_dob.setCellValueFactory(new PropertyValueFactory<>("dob"));
            col_nic.setCellValueFactory(new PropertyValueFactory<>("nic"));
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            col_userName.setCellValueFactory(new PropertyValueFactory<>("userName"));
            col_Password.setCellValueFactory(new PropertyValueFactory<>("passWord"));

            Employee_table.setItems(employee_list);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

//the method witch load the add employee gui when clicked the add employee button---------------------------------------
    public void handle_addEmployee(MouseEvent mouseEvent) {
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/AddEmployee.fxml"));
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

    //here implement a code which,when I  click the table cell i can edit it and then it saved to mysql database
}

