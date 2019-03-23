/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
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
 * @author addagar
 */
public class FXMLAñadirDocumentController implements Initializable {

    private ObservableList<Persona> datosPersona;
    private boolean modify = false;
    private Persona persona;
    @FXML
    private Button bCancel;
    @FXML
    private TextField textFieldfxID;
    @FXML
    private TextField textFieldApellidofxID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    public void initializeListPersona(ObservableList<Persona> dat) {
        datosPersona = dat;
        // añade a la colección si los campos no son vacíos y no contienen únicamente blancos
    }
    
    public void initializePersona(ObservableList<Persona> dat, Persona p) {
        datosPersona = dat;
        persona = p;
        textFieldfxID.setText(p.getNombre());
        textFieldApellidofxID.setText(p.getApellidos());
        //Si hemos llegado desde el boton modificar
        modify = true;
    }
    
    @FXML
    private void cancelar(ActionEvent event) {
        //Cerrar ventana -> Se puede hacer con cualquier cosa grafica
        ((Stage)bCancel.getScene().getWindow()).close();
    }

    @FXML
    private void añadir(ActionEvent event) {
        //Para distinguir entre modificado o añadido directamente
        if(modify){
            if ((!textFieldfxID.getText().isEmpty())
                    && (textFieldfxID.getText().trim().length()!=0)
                    && (!textFieldApellidofxID.getText().isEmpty())
                    && (textFieldApellidofxID.getText().trim().length()!=0))
            {
                int pos = datosPersona.indexOf(persona);
                persona.setApellidos(textFieldApellidofxID.getText());
                persona.setNombre(textFieldfxID.getText());
                datosPersona.set(pos, persona);
                textFieldfxID.clear();
                textFieldApellidofxID.clear();
                textFieldfxID.requestFocus();  //cambio del foco al textfield.
            }
        } else {
            if ((!textFieldfxID.getText().isEmpty())
                    && (textFieldfxID.getText().trim().length()!=0)
                    && (!textFieldApellidofxID.getText().isEmpty())
                    && (textFieldApellidofxID.getText().trim().length()!=0))
             { 
               datosPersona.add(new Persona(textFieldfxID.getText(),textFieldApellidofxID.getText()));
               textFieldfxID.clear();
               textFieldApellidofxID.clear();
               textFieldfxID.requestFocus();  //cambio del foco al textfield.
            }
        }
        ((Stage)bCancel.getScene().getWindow()).close();
    }
}