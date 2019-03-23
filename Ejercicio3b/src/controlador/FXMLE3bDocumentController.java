/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author addagar
 */
public class FXMLE3bDocumentController implements Initializable {
    //Variable propias
    private ObservableList<Persona> datos = null;
    private Persona p;
    
    //Variables FXML
    @FXML private Button bAdd;
    @FXML private Button bDelete;
    @FXML private Button bUpdate;

    @FXML private TableView<Persona> tablaPersona;
    @FXML private TableColumn<Persona, String> columnaNombre;
    @FXML private TableColumn<Persona, String> columnaApellido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ArrayList<Persona> misdatos = new ArrayList<Persona>();
        misdatos.add(new Persona("Pepe", "García"));
        misdatos.add(new Persona("María", "Pérez"));
        datos = FXCollections.observableArrayList(misdatos);
        tablaPersona.setItems(datos); // vinculacion entre la vista y el modelo     
        //Hacer que se vean los datos.
        columnaNombre.setCellValueFactory(cellData -> cellData.getValue().NombreProperty());
        columnaApellido.setCellValueFactory(cellData -> cellData.getValue().ApellidosProperty());

        // oyente de foco para el TableView
        tablaPersona.focusedProperty().addListener(new ChangeListener<Boolean>(){	
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
                            Boolean newValue) {
                if (tablaPersona.getItems().isEmpty()) {
                    bDelete.setDisable(true);
                    bUpdate.setDisable(true);
                } else {
                    bDelete.setDisable(false);
                    bUpdate.setDisable(false);
                }
            }
        });
    }    

    @FXML
    private void añadir(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLAñadirDocument.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Añadir persona");
        stage.setScene(scene);
        ((FXMLAñadirDocumentController) loader.getController()).initializeListPersona(datos);
        //Para no poder abrir mil ventanas iguales
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();        
    }

    @FXML
    private void delete(ActionEvent event) {
        int i = tablaPersona.getSelectionModel().getSelectedIndex();
    	if (i>=0) datos.remove(i);
    }
    
    @FXML
    private void updateP(ActionEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/FXMLAñadirDocument.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setTitle("Modificar persona");
        stage.setScene(scene);
        if(!tablaPersona.getSelectionModel().isEmpty()) {
            //posicion y dato
            int i = tablaPersona.getSelectionModel().getSelectedIndex();
            if (i>=0) p = datos.get(i);
            ((FXMLAñadirDocumentController) loader.getController()).initializePersona(datos, p);

            //Para no poder abrir mil ventanas iguales
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        }
    }

    private void update(ActionEvent event) {
    }
    class PersonListCell extends ListCell<Persona> {

            @Override
            protected void updateItem(Persona item, boolean empty) {
                super.updateItem(item, empty); //To change body of generated methods, choose Tools | Templates.
                if (item == null || empty) setText(null);
                else setText(item.getNombre()+ " ," + item.getApellidos());
            }
        }
}
