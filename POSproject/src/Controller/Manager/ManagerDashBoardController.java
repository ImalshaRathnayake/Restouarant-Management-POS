package Controller.Manager;
import Controller.MainLoginController;
import DBConnection.DBConnection;

import Model.MainLogin;
import Model.Manager.ManagerDashboard;
import Model.Manager.ManagerDashboardCart;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManagerDashBoardController implements Initializable {

    //fxml variables----------------------------------------------------------------------------------------------------
    @FXML
    private TextField pay_amount;
    @FXML
    private TextField txt_discount;
    @FXML
    private Button btn_logout;
    @FXML
    private Button btn_manage_customers;
    @FXML
    private Button btn_manage_employees;
    @FXML
    private Button btn_orders;
    @FXML
    private Button btn_place_order;
    @FXML
    private Button btn_reports;
    @FXML
    private Button btn_supplier_orders;
    @FXML
    private Button btn_clear;
    @FXML
    private Button btn_pay;
    @FXML
    private TableColumn<ManagerDashboard, String> col_name;
    @FXML
    private TableColumn<ManagerDashboard, Double> col_price;
    @FXML
    private TableColumn col_edit;
    @FXML
    private TableView<ManagerDashboard> recipe_table;
    @FXML
    private TableView<ManagerDashboardCart> cart_table;
    @FXML
    private TableColumn<ManagerDashboardCart, String> cart_name;
    @FXML
    private TableColumn<ManagerDashboardCart, String> cart_price;
    @FXML
    private TableColumn<ManagerDashboardCart, String> cart_qty;
    @FXML
    private TableColumn cart_remove;
    @FXML
    private Label due_amount;
    @FXML
    private Label grand_total;
    @FXML
    private Label lbl_DATE;
    @FXML
    private Label lbl_INVOICE_NUMBER;
    @FXML
    private Label lbl_discount;
    @FXML
    private Label lbl_dueAmount;
    @FXML
    private Label lbl_grand_Total;
    @FXML
    private Label lbl_invoice_Number_DISPLAAY;
    @FXML
    private Label lbl_manager;
    @FXML
    private Label lbl_manager_name;
    @FXML
    private Label lbl_payAmount;
    @FXML
    private Label lbl_place_order;
    @FXML
    private Label lbl_place_order1;
    @FXML
    private Label lbl_total_amount;
    @FXML
    private Pane pane_bill;
    @FXML
    private AnchorPane anchorPane_loadPage;
    @FXML
    private AnchorPane anchorPane_nevigation;
    @FXML
    private AnchorPane anchorPane_placeoeder;
    @FXML
    private ImageView photo_of_manager;
    @FXML
    private ScrollPane scrole_pane_mains;
    @FXML
    private Label total_amount;

    //variables which want to develop methods---------------------------------------------------------------------------
    Connection con;
    Parent root;
    Stage stage ;
    Scene scene;
    Stage primaryStage;
    PreparedStatement preparedStatement;
    HBox container;
    String quantity;
    static double sum=0.0;
    PreparedStatement ps;
    ResultSet rs;
    HBox cart_container;
    static int invoiceNo=0;

    //observable list used to write data in the table-------------------------------------------------------------------
    ObservableList<ManagerDashboard> list = FXCollections.observableArrayList();//used as food list
    ObservableList<ManagerDashboardCart> cart_list = FXCollections.observableArrayList();//used as cart list


    //default controller that connect database--------------------------------------------------------------------------
    public ManagerDashBoardController() {con = DBConnection.conDB();}


    //initialize method which run method when open fxml page -----------------------------------------------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initClock();//view current date and time
        load_data();//load food list

    }

    //method which view date and time in navigation bar-----------------------------------------------------------------
    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            lbl_DATE.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }

    //handle logout button -> when clicked logout page will go to login page--------------------------------------------
    public void handleLogOut(javafx.event.ActionEvent actionEvent) {
        try {
            root = FXMLLoader.load(getClass().getClassLoader().getResource("View/MainLogin.fxml"));
            stage =(Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            stage.close();
            primaryStage = new Stage();
            scene = new Scene(root);
            primaryStage.initStyle(StageStyle.UNDECORATED );
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

 //get the details from the database and set to the table---------------------------------------------------------------
    public void load_data(){
        try {
            ps = con.prepareStatement("SELECT *FROM recipe");
            rs =ps.executeQuery();
            while(rs.next()){
                String food=rs.getString("Recipe_Name");
                Double price=rs.getDouble("Recepe_Price");
                list.add(new ManagerDashboard(food,price));
            }
            col_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
            col_price.setCellValueFactory(new PropertyValueFactory<>("food_price"));
            food_list();//call the food list method
            recipe_table.setItems(list);
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }


//the method which add each raw details to cart when I clicked add button-----------------------------------------------
    public void food_list(){
        Callback<TableColumn<ManagerDashboard,String>,TableCell<ManagerDashboard,String>> cellFactory = (param) ->{
            final TableCell<ManagerDashboard,String>cell = new TableCell<ManagerDashboard,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else{
                        final Button plus_btn = new Button("+");
                        final Label qnty_lbl = new Label("1");
                        final Button Minus_btn = new Button("-");
                        final Button addtoCartButton = new Button("Add to Cart");

                        plus_btn.setOnAction(event ->{//plus button action event
                            ManagerDashboard object = getTableView().getItems().get(getIndex());
                            int quantity = Integer.parseInt(qnty_lbl.getText());
                            qnty_lbl.setText(String.valueOf(quantity+1));
                        });
                        qnty_lbl.getStyleClass().add("1");//set quantity label

                        Minus_btn.setOnAction(event ->{//minus button action event
                            ManagerDashboard object = getTableView().getItems().get(getIndex());
                            int quantity = Integer.parseInt(qnty_lbl.getText());
                            qnty_lbl.setText(String.valueOf(quantity-1));
                            if (quantity==0){qnty_lbl.setText(String.valueOf(0));}
                        });

                        container = new HBox(50,plus_btn,qnty_lbl, Minus_btn, addtoCartButton);
                        setGraphic(container);//set HBox for plus button,minus button,quantity label,

                        quantity=qnty_lbl.getText();//get quantity label amount and assign it in to quantity attribute

                        addtoCartButton.setOnAction(event ->{//add to cart button action event
                            ManagerDashboard object = getTableRow().getItem();

                            String name=object.getFood_name();
                            String qty =qnty_lbl.getText();
                            String price= String.valueOf(object.getFood_price()*Double.parseDouble(qty));

                            cart_list.add(new ManagerDashboardCart(name,qty,price));

                            cart_name.setCellValueFactory(new PropertyValueFactory<>("food_name"));
                            cart_qty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
                            cart_price.setCellValueFactory(new PropertyValueFactory<>("price"));
                            cart_table.setItems(cart_list);
                            qnty_lbl.setText("1");

                            sum=sum+Double.parseDouble(price);
                            grand_total.setText(String.valueOf(sum));
                            total_amount.setText(String.valueOf(sum));

                        });
                        removebtn();
                    }
                }
            };
            return cell;
        };
        col_edit.setCellFactory(cellFactory);
        sum=0.0;

    }


    //method which calculate discount in cart---------------------------------------------------------------------------
    public void calDiscount(KeyEvent actionEvent) {
        double discount = (Double.parseDouble(txt_discount.getText())/100)*sum;
        total_amount.setText(String.valueOf(sum-discount));

        pay_amount.setOnKeyTyped(event -> {
            double amount = Double.parseDouble(pay_amount.getText());
            double dueAmount=amount-Double.parseDouble(total_amount.getText());
            due_amount.setText(String.valueOf(dueAmount));
            calDiscount(event);
        });
    }


    //method which run when clicked remove button in cart table---------------------------------------------------------
    public void removebtn(){
        Callback<TableColumn<ManagerDashboardCart,String>,TableCell<ManagerDashboardCart,String>> cellFactory = (param) ->{
            final TableCell<ManagerDashboardCart,String>cell = new TableCell<ManagerDashboardCart,String>(){
                @Override
                public void updateItem(String item,boolean empty){
                    super.updateItem(item,empty);
                    if(empty){
                        setGraphic(null);
                        setText(null);
                    }
                    else{
                        final Button removeButton = new Button("Remove");

                        cart_container = new HBox(removeButton);
                        setGraphic(cart_container);//set HBox for plus button,minus button,quantity label,

                        removeButton.setOnMouseClicked((MouseEvent event) ->{//remove button action event
                            ManagerDashboardCart object =getTableRow().getItem();
                            cart_table.getItems().removeAll(object);
                            sum=sum-Double.parseDouble(object.getPrice());
                            grand_total.setText(String.valueOf(sum));
                            total_amount.setText(String.valueOf(sum));
                            txt_discount.setText("0");
                            due_amount.setText("0.00");
                            pay_amount.setText("0.00");
                        });

                    }
                }
            };
            return cell;
        };
        cart_remove.setCellFactory(cellFactory);
    }


    //method for place order button in navigation bar-------------------------------------------------------------------
    public void handle_Place_Order(MouseEvent mouseEvent) {
        anchorPane_loadPage.getChildren().setAll(anchorPane_placeoeder);

    }

    //method for manage employee button in navigation bar---------------------------------------------------------------
    public void handle_manage_employees(MouseEvent mouseEvent)  {

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/ManageEmployees.fxml"));
            anchorPane_loadPage.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //method for manage customer button in navigation bar---------------------------------------------------------------
    public void handle_manage_customer(MouseEvent mouseEvent)  {

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/ManageCustomers.fxml"));
            anchorPane_loadPage.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    //method for manage supplier orders button in navigation bar---------------------------------------------------------------
    public void handle_supplier_orders(MouseEvent mouseEvent) {

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/ManageSupplierOrders.fxml"));
            anchorPane_loadPage.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //method for manage order button in navigation bar---------------------------------------------------------------
    public void handle_orders(MouseEvent mouseEvent) {

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/ManageOrders.fxml"));
            anchorPane_loadPage.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method for manage reports button in navigation bar---------------------------------------------------------------
    public void handle_reports(MouseEvent mouseEvent) {

        AnchorPane pane = null;
        try {
            pane = FXMLLoader.load(getClass().getClassLoader().getResource("View/Manager/ManageReports.fxml"));
            anchorPane_loadPage.getChildren().setAll(pane);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //method for pay the bill-------------------------------------------------------------------------------------------
    public void handle_pay(MouseEvent mouseEvent) {
        grand_total.setText("0.00");
        txt_discount.setText(" ");
        total_amount.setText("0.00");
        due_amount.setText("0.00");
        cart_table.getItems().clear();
        //here implement a method witch after click the pay button the print the bill and store it
    }

    //method for handle clear the cart list------------------------------------------------------------------------------
    public void handle_clear(MouseEvent mouseEvent) {
        grand_total.setText("0.00");
        txt_discount.setText(" ");
        total_amount.setText("0.00");
        due_amount.setText("0.00");
        cart_table.getItems().clear();
    }

    // plz implement method for printing invoice number and manager name

    public void display_name() throws SQLException {
        String sql="SELECT Employee_Last_Name FROM `employee` WHERE Employee_PassWord=?";
        preparedStatement = con.prepareStatement(sql);


    }

    public void display_invoice_number(){

    }

}