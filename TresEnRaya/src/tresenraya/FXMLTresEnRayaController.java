/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tresenraya;

import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

/**
 *
 * @author addagar
 */
public class FXMLTresEnRayaController implements Initializable {
    
    private boolean esJudador1 = true;
    private int jugadas = 9;
    @FXML
    private GridPane gridJugar;
    @FXML
    private Label lG;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void hacerJugada(ActionEvent event) {
        Button botonPulsado = (Button)event.getSource();
        if("".equals(botonPulsado.getText())){
            if(esJudador1) {
                botonPulsado.setText("X");
                esJudador1 = false;
                if(ganador("X")) {
                    gridJugar.setDisable(true);
                    lG.setText("GAME OVER: WINNER PLAYER 1");
                }
            } else {
                botonPulsado.setText("O");
                esJudador1 = true;
                if(ganador("O")){
                    gridJugar.setDisable(true);
                    lG.setText("GAME OVER: WINNER PLAYER 2");
                }
            }
            jugadas--;
            if (jugadas == 0) {
                gridJugar.setDisable(true);
                lG.setText("GAME OVER");
            }
        } 
    }

    @FXML
    private void reiniciar(ActionEvent event) {
        for (Iterator iterator = gridJugar.getChildren().iterator(); iterator.hasNext();) {
            Button boton = (Button)iterator.next();
            boton.setText("");
        }
        gridJugar.setDisable(false);
        jugadas = 9;
        esJudador1 = true;
        lG.setText("");
    }
    
    private boolean ganador(String jugada){
        int cont1 = 0, cont2 = 0, cont3 = 0;
        //Linea recta:
        int hijos = gridJugar.getChildren().size();
        for(int i = 0; i < hijos; i++){
            String textB  = ((Button)gridJugar.getChildren().get(i)).getText();
            if(jugada.equals(textB) && (i >= 0 && i<3)){
                cont1++;
            } else if(jugada.equals(textB) && (i >= 3 && i<6)){
                cont2++;
            } else if(jugada.equals(textB) && (i >= 6 && i<9)){
                cont3++;
            }
        }
        if (cont1 == 3 || cont2 == 3 || cont3 == 3) { return true; }
        
        //Columna:
        cont1 = 0; cont2 = 0; cont3 = 0;
        for(int i = 0; i < hijos; i++){
            String textB  = ((Button)gridJugar.getChildren().get(i)).getText();
            if(jugada.equals(textB) && (i == 0 || i == 3 || i == 6)){
                cont1++;
            } else if(jugada.equals(textB) && (i == 1 || i == 4 || i == 7)){
                cont2++;
            } else if(jugada.equals(textB) && (i == 2 || i == 5 || i == 8)){
                cont3++;
            }
        }
        if (cont1 == 3 || cont2 == 3 || cont3 == 3) { return true; }
        
        //Diagonal:
        cont1 = 0; cont2 = 0;
        for(int i = 0; i < hijos; i++){
            String textB  = ((Button)gridJugar.getChildren().get(i)).getText();
            if(jugada.equals(textB) && (i == 0 || i == 4 || i == 8)){
                cont1++;
            } else if(jugada.equals(textB) && (i == 2 || i == 4 || i == 6)){
                cont2++;
            }
        }
        if (cont1 == 3 || cont2 == 3) { return true; }
        return false;
    }
}
