package Controller.Manager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class AddCustomerController {

    @FXML
    private TextField Customer_Email;

    @FXML
    private TextField Customer_ID;

    @FXML
    private TextField Customer_Name;

    @FXML
    private TextField Customer_Phone;

    @FXML
    private Button btnClose;

    @FXML
    private Button btn_sumbit;

    @FXML
    private Label lbl_addCustomer;

    @FXML
    private Label lbl_text_error;

    @FXML
    void handleCloseButtonAction(ActionEvent event) {

    }

    public void handleSubmit(MouseEvent mouseEvent) {
    }

    public void handle_close(MouseEvent mouseEvent) {
    }
}
