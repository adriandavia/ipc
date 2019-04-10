/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Persona;

/**
 * FXML Controller class
 *
 * @author Adrian
 */
public class FXMLFiltrarDocumentController implements Initializable {
    private ObservableList<Persona> datos, nuevo;
    @FXML
    private Button aceptar;
    @FXML
    private Button cancelar;
    @FXML
    private TextField nombre;
    @FXML
    private TextField apellido;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    public void initializeListPersona(ObservableList<Persona> datos, ObservableList<Persona> nuevo){
        this.datos = datos;
        this.nuevo = nuevo;
    }

    @FXML
    private void aceptar(ActionEvent event) {
        ObservableList<Persona> aux = FXCollections.observableArrayList();;
        if((!nombre.getText().isEmpty()) && (nombre.getText().trim().length()!=0)) {
            String buscar = nombre.getText().toLowerCase();
            for(int i = 0; i < datos.size(); i++){
                String dato = datos.get(i).getNombre().toLowerCase();
                if(buscar.equalsIgnoreCase(dato)) aux.add(datos.get(i));
                else if(dato.startsWith(buscar)) aux.add(datos.get(i));
            }
        }
        if((!apellido.getText().isEmpty()) && (apellido.getText().trim().length()!=0)) {
            String buscar = apellido.getText().toLowerCase();
            if(!aux.isEmpty()) {
                for(int i = 0; i < aux.size(); i++){
                    String dato = aux.get(i).getApellidos().toLowerCase();
                    if(!buscar.equalsIgnoreCase(dato)) aux.remove(i);
                    else if(!dato.startsWith(buscar)) aux.remove(i);
                }
            } else {
                for(int i = 0; i < datos.size(); i++){
                    String dato = datos.get(i).getApellidos().toLowerCase();
                    if(buscar.equalsIgnoreCase(dato)) aux.add(datos.get(i));
                    else if(dato.startsWith(buscar)) aux.add(datos.get(i));
                }
            }
        }
        for(int i = 0; i < aux.size(); i++){
            nuevo.add(aux.get(i));
        }
        ((Stage)nombre.getScene().getWindow()).close();
    }

    @FXML
    private void cancelar(ActionEvent event) {
        ((Stage)nombre.getScene().getWindow()).close();
    }
}
