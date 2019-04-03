/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejercicioa4a;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

/**
 *
 * @author Adrian
 */
public class FXMLEjercicio4aDocumentController implements Initializable {
    
    @FXML
    private Label labelEstado;
    @FXML
    private Button amazon;
    @FXML
    private Button ebay;
    @FXML
    private Menu comprarEn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void salir(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmación");
        alert.setHeaderText("Vas a salir del programa");
        alert.setContentText("¿Seguro que quieres salir?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            System.exit(0);
        }
    }

    @FXML
    private void comprar(ActionEvent event) {
        if(((CheckMenuItem) comprarEn.getItems().get(0)).isSelected()) {
            if(((Button) event.getSource()).equals(amazon)){
                dialogoCompra("Amazon", false);
            } else if(((Button) event.getSource()).equals(ebay)){
                dialogoCompra("Ebay", true);
            }
        } else if(((CheckMenuItem) comprarEn.getItems().get(1)).isSelected()){
            if(((Button) event.getSource()).equals(amazon)){
                dialogoCompra("Amazon", true);
            } else if(((Button) event.getSource()).equals(ebay)){
                dialogoCompra("Ebay", false);
            }
        }
    }
    
    private void dialogoCompra(String tienda, boolean error){
        Alert alert = new Alert(AlertType.CONFIRMATION);
        if(!error){
            alert.setTitle("Confirmación");
            alert.setHeaderText("Compra realizada correctamente");
            alert.setContentText("Ha comprado en " + tienda);
        } else {
            alert.setTitle("Error en la selección");
            alert.setHeaderText("No se puede comprar en " + tienda);
            alert.setContentText("Por favor, cambie la selección actual en el menú "
                + "de Opciones");  
        }
        ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
        Optional<ButtonType> result = alert.showAndWait();
    }
    
    @FXML
    private void bloggear(ActionEvent event) {
        List<String> choices = new ArrayList<>();
        choices.add("El blog de Athos");
        choices.add("El blog de Porthos");
        choices.add("El blog de Aramis");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("El blog de Athos",choices);
        dialog.setTitle("Selección de blog");
        dialog.setHeaderText("¿Qué blog quieres visitar?");
        dialog.setContentText("Elige: ");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(blog-> labelEstado.setText("Visitando " + blog));
    }

    @FXML
    private void mandarMensajeFacebook(ActionEvent event) {
        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Introduce tu nombre");
        dialog.setHeaderText("¿Con qué usuario quieres escribir en Facebook?");
        dialog.setContentText("Introduce tu nombre: ");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
                if(!name.equals("")){
                    labelEstado.setText("Mensaje enviado como " + name);
                }
        });
    }

}
