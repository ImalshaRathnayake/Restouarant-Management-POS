package Controller.Manager;

import DBConnection.DBConnection;
import Model.Manager.AddEmployee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AddEmployeeController implements Initializable {
    //gui items variables-----------------------------------------------------------------------------------------------

    @FXML
    private ChoiceBox<String> EmployeeType;
    @FXML
    private TextField Employee_ID;
    @FXML
    private TextField Employee_Address;
    @FXML
    private TextField Employee_Email;
    @FXML
    private TextField Employee_First_Name;
    @FXML
    private TextField Employee_Last_Name;
    @FXML
    private TextField Employee_NIC_NO;
    @FXML
    private TextField Employee_PassWord;
    @FXML
    private TextField Employee_Phone;
    @FXML
    private TextField Employee_UserName;
    @FXML
    private TextField Re_Enter_PassWord;
    @FXML
    private Button btnClose;
    @FXML
    private Button btn_sumbit;
    @FXML
    private DatePicker employee_dob;
    @FXML
    private Label lbl_addEmployee;
    @FXML
    private Label lbl_text_error;

    //variable which I want---------------------------------------------------------------------------------------------
    PreparedStatement preparedStatement;
    Connection con;

    //ManageEmployeeController object-----------------------------------------------------------------------------------
    ManageEmployeesController object = new ManageEmployeesController();

    //constructor with no parameter-------------------------------------------------------------------------------------
    public AddEmployeeController() {
        con = DBConnection.conDB();
    }

    //the initialize method---------------------------------------------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        AddEmployee employee = new AddEmployee();
        EmployeeType.getItems().addAll(employee.getPosition());
        EmployeeType.getSelectionModel().select(0);
        lbl_text_error.setVisible(false);
    }

    //close button method-----------------------------------------------------------------------------------------------
    @FXML
    void handleClose(MouseEvent event) {
        System.exit(0);
    }
    @FXML
    void handleCloseButtonAction(ActionEvent event) {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    //submit method which update the database adding new values---------------------------------------------------------
    public void handle_submit(MouseEvent mouseEvent) {
        String id = String.valueOf(Employee_ID.getText());
         String position = String.valueOf(EmployeeType.getValue());
         String first_name = Employee_First_Name.getText().trim();
         String last_name = Employee_Last_Name.getText().trim();
         String phone = Employee_Phone.getText().trim();
         String address = Employee_Address.getText().trim();
         LocalDate dob = employee_dob.getValue();
         String nic = Employee_NIC_NO.getText().trim();
         String email = Employee_Email.getText().trim();
         String userName = Employee_UserName.getText().trim();
         String passWord = Employee_PassWord.getText().trim();
        String re_Enter_PassWord = Re_Enter_PassWord.getText().trim();

        if( id.isEmpty() || position.isEmpty() || first_name.isEmpty() || last_name.isEmpty() || phone.isEmpty() || address.isEmpty() || dob==null || nic.isEmpty() || email.isEmpty() || userName.isEmpty() || passWord.isEmpty() || re_Enter_PassWord.isEmpty() ) {

            lbl_text_error.setVisible(true);
            lbl_text_error.setText("Please fill all the text fields");
        }
        else {
            checkData(id,position,first_name,last_name,phone,address,dob,nic,email,userName,passWord,re_Enter_PassWord);
            //query
            String sql = "INSERT INTO employee(Employee_ID, Employee_First_Name, Employee_Last_Name, Employee_Phone, Employee_Address, Employee_Position, employee_dob, Employee_NIC_NO, Employee_Email, Employee_UserName, Employee_PassWord) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            try {
                preparedStatement = con.prepareStatement(sql);

                preparedStatement.setString(1, id);
                preparedStatement.setString(2, first_name);
                preparedStatement.setString(3, last_name);
                preparedStatement.setString(4, phone);
                preparedStatement.setString(5, address);
                preparedStatement.setString(6, position);
                preparedStatement.setString(7, String.valueOf(dob));
                preparedStatement.setString(8, nic);
                preparedStatement.setString(9, email);
                preparedStatement.setString(10, userName);
                preparedStatement.setString(11, passWord);

                preparedStatement.executeUpdate();

                lbl_text_error.setText("Added successfully!");
                lbl_text_error.setVisible(true);

            }
            catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    //check data is constrain------------------------(update this ,not working)-----------------------------------------
    public void checkData(String id,String position,String first_name,String last_name,String phone,String address,LocalDate dob,
                          String nic,String email,String userName,String passWord ,String re_Enter_PassWord){
        if (id.length()>12 ){
            lbl_text_error.setText("Enter id number up to 12 digits");
            lbl_text_error.setVisible(true);
        }
        if (phone.length()!=10){
            lbl_text_error.setText("Enter 10 digits contact number");
            lbl_text_error.setVisible(true);
        }
        if (nic.length()!=10 ){
            lbl_text_error.setText("Enter 10 digits NIC number");
            lbl_text_error.setVisible(true);
        }
        if (userName.length()>8){
            lbl_text_error.setText("Enter 8 digits or above user Name");
            lbl_text_error.setVisible(true);
        }
        if(passWord!=re_Enter_PassWord || passWord.length()>8){
            lbl_text_error.setText("Re enter 8 digits or above  password ");
            lbl_text_error.setVisible(true);
        }
    }

    //there I want to add a clear button--------------------------------------------------------------------------------
}
