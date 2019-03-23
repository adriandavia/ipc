/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2.pkg1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author addagar
 */
public class Practica2Ej1 extends Application {
    private double defaultX, defaultY;
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLActividad1Document.fxml"));
        
        Scene scene = new Scene(root);
        stage.initStyle(StageStyle.UNDECORATED);
        
        //Cerrar la ventana si le dame a la tecla scape
        scene.setOnKeyPressed(eventKey -> {
            if(eventKey.getCode().equals(KeyCode.ESCAPE))stage.close();
        });
        
        //Mover pantalla
        scene.setOnMouseClicked(mouseC -> {
            defaultX = mouseC.getSceneX();
            defaultY = mouseC.getSceneY();
        });
        scene.setOnMouseDragged(mouseD -> {
            scene.setCursor(Cursor.OPEN_HAND); //Tipo de cursor
            stage.setX(mouseD.getScreenX() - defaultX);
            stage.setY(mouseD.getScreenY() - defaultY);
        });
        scene.setOnMouseReleased(mouseR -> {
            scene.setCursor(Cursor.DEFAULT); //Tipo de cursor 
        });
        //Fin mover pantalla
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
