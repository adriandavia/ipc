/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conecta4;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

/**
 *
 * @author Adrian
 */
public class FXMLConecta4DocumentController implements Initializable {
    private boolean juegaIzquierda = true;
    private Paint izqFichaPaint;
    private Paint derFichaPaint;
    private List<VBox> columnas = new ArrayList<>();
    @FXML
    private Button reiniciarJuego;
    @FXML
    private VBox vB0;
    @FXML
    private VBox vB1;
    @FXML
    private VBox vB2;
    @FXML
    private VBox vB3;
    @FXML
    private VBox vB4;
    @FXML
    private VBox vB5;
    @FXML
    private VBox vB6;
    private int fichasI, fichasD;
    private double deltax, deltay;
    @FXML
    private GridPane gridPanel;
    @FXML
    private Label labelGanador;

    enum Direccion {
        ARR, ABJ, IZQ, DER, DER_ARR, DER_ABJ, IZQ_ARR, IZQ_ABJ
    };

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // inicializar variables         derFichaPaint        izqFichaPaint
        izqFichaPaint = Color.PALEGREEN;
        derFichaPaint = Color.MISTYROSE;
        //-------------------------------------------------------------------------
        // anadimos los VBox al vector columnas, 0 es la columna mas a la izquierda
        columnas.add(vB0);columnas.add(vB1);columnas.add(vB2);columnas.add(vB3);
        columnas.add(vB4);columnas.add(vB5);columnas.add(vB6);

        fichasI = 0; fichasD = 0;
        //-------------------------------------------------------------------------
        // anadimos un filtro a las fichas para que no se muevan si no es su turno
        for (int i = 0; i < columnas.size(); i++) {
            ObservableList<Node> children = columnas.get(i).getChildren();
            for (Node node : children) {
                node.addEventFilter(MouseEvent.ANY, (e) -> {
                    Circle ficha = (Circle) e.getSource();
                    if (juegaIzquierda == true) {   //juega izquierda
                        if (ficha.getFill() != izqFichaPaint) {
                            e.consume();
                        }
                    } else //juega derecha
                    {
                        if (ficha.getFill() == izqFichaPaint) {
                            e.consume();
                        }
                    }
                });
            }
        }
    }
    
    @FXML
    private void reiniciarJuego(ActionEvent event) {
        for(int i = 0; i < columnas.size(); i++) {
            ObservableList<Node> children = columnas.get(i).getChildren();
            while (children.size() > 0){
                Circle ficha = (Circle) children.get(0);
                children.remove(0);
                if(ficha.getFill() == izqFichaPaint){
                    gridPanel.add(ficha, 0, 0);
                    fichasI++;
                } else {
                    gridPanel.add(ficha, 6, 0);
                    fichasD++;
                }
            }
        }
        labelGanador.setText("");
        gridPanel.setDisable(false);
        juegaIzquierda = true;
    }

    @FXML
    private void circuloR(MouseEvent event) {
        Circle c = (Circle) event.getSource();
        c.setTranslateX(0);
        c.setTranslateY(0);
        //-------------------------------------
        // si estamos sobre una columna, añadimos a esa columna
        int pos = calcularColumna(event.getSceneX());
            // borramos del grid y añadimos al vbox correspondiente
        VBox vBox = columnas.get(pos);
        vBox.getChildren().add(0, c);
            //cambiamos el jugador
        if(juegaIzquierda) {
            juegaIzquierda = false;
            fichasI--;
        } 
        else {
            juegaIzquierda = true;
            fichasD--;
        }
            //calculamos si es una jugadda ganadora
        if(calculaSolucion(c.getFill(), pos)) { 
            if(c.getFill() == izqFichaPaint){
                labelGanador.setTextFill(izqFichaPaint);
                labelGanador.setText("WINNER PLAYER 1");                
            } else {
                labelGanador.setTextFill(derFichaPaint);
                labelGanador.setText("WINNER PLAYER 2");
            }
            gridPanel.setDisable(true);
            labelGanador.setOpacity(100);
        } else if(fichasI == 0 && fichasD == 0){
            labelGanador.setTextFill(Color.DARKORCHID);
            labelGanador.setText("GAME OVER");
            gridPanel.setDisable(true);
            labelGanador.setOpacity(100);
            
        }
    }
    private int calcularColumna(double x){
        double scenaAncho = gridPanel.getScene().getWidth();
        double vBoxAncho = columnas.get(0).getWidth();
        double anchoTotal = (scenaAncho - vBoxAncho * 7)/2;
        int pos = (int) ((x-anchoTotal)/vBoxAncho);
        return pos;
    }
    
    @FXML
    private void circuloD(MouseEvent event) {
        Circle c = (Circle) event.getSource();
        c.setCursor(Cursor.OPEN_HAND);
        c.setTranslateX(event.getSceneX() - deltax);
        c.setTranslateY(event.getSceneY() - deltay);
    }

    @FXML
    private void circuloP(MouseEvent event) {
        deltax = event.getSceneX();
        deltay = event.getSceneY();
    } 

    //============================================================================================================
    // calcula si es soluciÃ³n a partir de la Ãºltima ficha de una columna dada col
    // busca la soluciÃ³n hacia abajo, en horizontal y en las dos oblicuas
    //utiliza una variable global columnas del tipo List<VBox> columnas; con los VBox ordenados, 0 a la izquierda,
    // y un enumerado para la direeciÃ³n: enum Direccion {ARR,ABJ,IZQ,DER,DER_ARR,DER_ABJ,IZQ_ARR,IZQ_ABJ};
    //============================================================================================================
    private boolean calculaSolucion(Paint fichaColor, int col) {
        // la y de la ficha siempre es la posiciÃ³n mas alta de la columna, 
        // que es inverso a la posiciÃ³ que ocupa en la lista de hijos, empieza en 0
        int y = columnas.get(col).getChildren().size() - 1;
        int res = 0;
        //=================================================================
        //calcula horizontal
        res = calculaIguales(fichaColor, col, y, Direccion.DER) + calculaIguales(fichaColor, col, y, Direccion.IZQ);
        if (res > 4) {
            return true;
        }
        //=================================================================
        //calcula vertical
        res = calculaIguales(fichaColor, col, y, Direccion.ABJ) + calculaIguales(fichaColor, col, y, Direccion.ARR);
        if (res > 4) {
            return true;
        }
        //=================================================================
        //calcula oblicuo izquierda arriba
       res = calculaIguales(fichaColor, col, y, Direccion.DER_ABJ) + calculaIguales(fichaColor, col, y, Direccion.IZQ_ARR) ;
        if (res > 4) {
            return true;
        }
        //=================================================================
        //calcula oblicuo derecha arriba
        res = calculaIguales(fichaColor, col, y, Direccion.DER_ARR) + calculaIguales(fichaColor, col, y, Direccion.IZQ_ABJ) ;
        if (res > 4) {
            return true;
        }
        return false;

    }
    
    //============================================================================================================
    // calcula el numero de fichas del color fichaColor desde una posicion dada (x,y), y hacia la dir indicada
    // incluye la ficha de la posiciÃ³n (x,y), la posiciÃ³n (0,0) es la de la ficha inferior a la izquierda
    //============================================================================================================
    private int calculaIguales(Paint fichaColor, int x, int y, Direccion dir) {
        Circle ficha;
        try {  // el elemento 0 del VBox es el que esta en la parte superior y para mi la y = 0 es la ficha que esta debajo
               ficha=(Circle) columnas.get(x).getChildren().get((columnas.get(x).getChildren().size()-y-1));
        } catch (Exception e) {
            return 0;
        }
        if (ficha.getFill() != fichaColor)
            return 0;
        else {
            switch (dir) {
                case ABJ:
                    y = y - 1;
                    break;
                case ARR:
                    y = y + 1;
                    break;
                case DER:
                    x = x + 1;
                    break;
                case IZQ:
                    x = x - 1;
                    break;
                case DER_ABJ:
                    x = x + 1;
                    y = y - 1;
                    break;
                case DER_ARR:
                    x = x + 1;
                    y = y + 1;
                    break;
                case IZQ_ARR:
                    x = x - 1;
                    y = y + 1;
                    break;
                case IZQ_ABJ:
                    x = x - 1;
                    y = y - 1;
                    break;
            }
            return 1 + calculaIguales(fichaColor, x, y, dir);
        }

    }

}
