package GUI;

import javafx.application.Application;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Point2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;



import static javafx.application.Application.launch;

public class main extends Application {
@Override
public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("GUI.fxml"));
        primaryStage.setTitle("Jumping machine builder");
        Scene scene =new Scene(root, 800, 400);
        //primaryStage.initStyle(StageStyle.TRANSPARENT);
        //primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
        }


//public void start(Stage primaryStage) throws Exception{
//    Parent root = FXMLLoader.load(getClass().getResource("test.fxml"));
//    primaryStage.setTitle("Jumping machine builder");
//    Scene scene =new Scene(root, 800, 400);
//    //primaryStage.initStyle(StageStyle.TRANSPARENT);
//    //primaryStage.setResizable(false);
//    primaryStage.setScene(scene);
//    primaryStage.show();
//}


public static void main(String[] args) {
        launch(args);
        }
}
