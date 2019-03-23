/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package listviewlenguajes;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author Juan
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private ListView<String> listView;
    
     @FXML
    private Button botonAdd;

    @FXML
    private Button botonBorrar;
    
     @FXML
    private TextField textoEntrada;

    @FXML
    void BotonAdd(ActionEvent event) {
        String s = textoEntrada.getText();
        s=s.trim(); // quita los blancos
        if (s.length()==0) { textoEntrada.setText("");return;} // el string contiene únicamente blancos
        datos.add(textoEntrada.getText());
        textoEntrada.setText("");

    }

    @FXML
    void botonBorrar(ActionEvent event) {
      int i = listView.getSelectionModel().getSelectedIndex(); // elemento seleccionado de la la lista
      if (i>-1) datos.remove(i); // lo borrra de la lista observable, no hace falta la comparación i>-1
      listView.getSelectionModel().clearSelection(); // no aparece ninguno seleccionado
    }
    
    private ObservableList<String> datos = null;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
     
        ArrayList<String>  misdatos = new ArrayList<String>();
        misdatos.add("Java"); misdatos.add("JavaFX"); misdatos.add("C++"); 
        misdatos.add("Python"); misdatos.add("Javascript"); misdatos.add("C#");

        datos = FXCollections.observableArrayList(misdatos);
        listView.setItems(datos);
        botonAdd.setDisable(true);
        botonBorrar.setDisable(true);
        
        ChangeListener<Number> oyenteCambio = new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (newValue.intValue()==-1) // nada seleccionado
                botonBorrar.setDisable(true);
                else botonBorrar.setDisable(false); // 1 elemento de la lista seleccionado
            }
        };
        listView.getSelectionModel().selectedIndexProperty().addListener(oyenteCambio);
        
        textoEntrada.textProperty().addListener(  (o, oldVal, newVal) -> {
                 if (newVal.isEmpty()) 
                 botonAdd.setDisable(true);
                 else 
                botonAdd.setDisable(false);
          });

    }    
    
}
