/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vehicle;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.*;

/**
 *
 * @author Harrishan
*/
public class Main extends Application{
    
    @Override
    public void start(Stage stage) throws Exception {
        Pane page  = (Pane) FXMLLoader.load(Main.class.getResource("Vehicle.fxml"));
        Scene scene = new Scene(page);
   
        stage.setScene(scene);
        stage.show();
    }
    
    public static void main(String[] args){
        launch(args);
    }
 
}
