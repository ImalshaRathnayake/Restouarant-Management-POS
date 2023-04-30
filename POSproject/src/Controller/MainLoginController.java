package Controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import DBConnection.DBConnection;
import Model.MainLogin;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;

public class MainLoginController implements Initializable {

    @FXML
    private ChoiceBox<String> PositionOfEmployment;
    @FXML
    private Button btnClose;
    @FXML
    private Button btnLogin;
    @FXML
    private PasswordField passWord;
    @FXML
    private Label txtError;
    @FXML
    private TextField userName;

//global variables
    Connection con;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Parent root;
    Stage stage ;
    Scene scene;
    Stage primaryStage;

//constructor
    public MainLoginController() {
        con = DBConnection.conDB();
    }

//mouse events
    @FXML
    void handleClose(MouseEvent event) {
        System.exit(0);
    }

//action events
    @FXML
    public void handleCloseButtonAction(javafx.event.ActionEvent actionEvent)
    {
        Stage stage = (Stage) btnClose.getScene().getWindow();
        stage.close();
    }

    @FXML
    public
    void handleLoginButtonAction(javafx.event.ActionEvent actionEvent)
    {
        String position = PositionOfEmployment.getValue().trim();
        String uName = userName.getText().trim();
        String pass = passWord.getText().trim();

        if( position.isEmpty() || uName.isEmpty() || pass.isEmpty() )
        {
            txtError.setVisible(true);
            txtError.setText("PassWord or UserName is empty");
        }

        else
        {
            //query
            String sql = "SELECT * FROM employee WHERE Employee_Position = ? AND Employee_UserName = ? AND Employee_PassWord = ?";

            try {
                preparedStatement = con.prepareStatement(sql);

                preparedStatement.setString(1, position);
                preparedStatement.setString(2, uName);
                preparedStatement.setString(3, pass);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next())
                {
                    txtError.setVisible(true);
                    txtError.setText("Login Success ");

                    try {
                        if (position.equals("MANAGER"))
                        {
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/ManagerDashboard.fxml"));
                            stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                            stage.close();
                            primaryStage = new Stage();
                            scene = new Scene(root);
                            primaryStage.setScene(scene);
                            primaryStage.setTitle("Manager Dashboard");
                            primaryStage.initStyle(StageStyle.DECORATED );
                            primaryStage.setResizable(false);
                            primaryStage.show();
                        }

                        else if (position.equals("RECEPTIONIST"))
                        {
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Receptionist/ReceptionistDashboard.fxml"));
                            stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                            stage.close();
                            primaryStage = new Stage();
                            scene = new Scene(root);
                            primaryStage.setScene(scene);
                            primaryStage.setTitle("Receptionist Dashboard");
                            stage.initStyle(StageStyle.DECORATED);
                            primaryStage.show();
                        }
                        else if (position.equals("CHEF"))
                        {
                            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/Chef/ChefDashboard.fxml"));
                            stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
                            stage.close();
                            primaryStage = new Stage();
                            scene = new Scene(root);
                            primaryStage.setScene(scene);
                            primaryStage.setTitle("Chef Dashboard");
                            primaryStage.initStyle(StageStyle.DECORATED);
                            primaryStage.show();
                        }
                    }
                    catch (IOException ex)
                    {
                        System.err.println(ex.getMessage());
                    }
                }
                else
                {
                    txtError.setVisible(true);
                    txtError.setText("Enter correct  Position , UserName and Password ");
                }
            }
            catch (SQLException ex)
            {
                System.err.println(ex.getMessage());
            }
        }
    }
    
//initialize method
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        MainLogin employeePositionObj = new MainLogin();
        PositionOfEmployment.getItems().addAll(employeePositionObj.getPosition());
        PositionOfEmployment.getSelectionModel().select(0);
        txtError.setVisible(false);
    }
}