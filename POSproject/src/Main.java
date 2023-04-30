import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


//main class------------------------------------------------------------------------------------------------------------
public class Main extends Application{

    //global variables--------------------------------------------------------------------------------------------------
    private double xOffset = 0;
    private double yOffset = 0; 

    //start method which load sign in page------------------------------------------------------------------------------
    @Override
    public void  start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/MainLogin.fxml"));
        Parent root = loader.load();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        
        root.setOnMousePressed(event -> {
           xOffset = event.getSceneX();
           yOffset = event.getSceneY();
        });

        root.setOnMouseDragged(event -> {
           primaryStage.setX(event.getScreenX() - xOffset);

           primaryStage.setY(event.getScreenY() - yOffset);
       });            
    }

    //main method-------------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        launch(args);
    }
}
