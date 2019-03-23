/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica2.pkg1;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Circle;

/**
 *
 * @author addagar
 */
public class FXMLActividad1DocumentController implements Initializable {
    
    @FXML
    private GridPane gridPane;
    @FXML
    private Circle circulo;
    
    private double defaultXC, defaultYC;
    
    private Integer row, column, sizeRow, sizeColumn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        sizeRow = gridPane.getRowConstraints().size(); //Numero de filas
        row = 2;
        sizeColumn = gridPane.getColumnConstraints().size(); //Numero de columnas
        column = 2;
    }    
    @FXML
    private void moverCirculo(KeyEvent event) {
        switch (event.getCode()) {
            case LEFT:
                column = ((column - 1)+sizeColumn)%sizeColumn; //Para ir desde 0-sizeColumn
                gridPane.setColumnIndex(circulo, column);
                break;
            case RIGHT:
                column = ((column + 1)+sizeColumn)%sizeColumn; //Para ir desde 0-sizeColumn
                gridPane.setColumnIndex(circulo, column);
                break;
            case UP:
                row = ((row - 1)+sizeRow)%sizeRow; //Para ir desde 0-sizeColumn
                gridPane.setRowIndex(circulo, row);
                break;
            case DOWN:
                row = ((row + 1)+sizeRow)%sizeRow; //Para ir desde 0-sizeColumn
                gridPane.setRowIndex(circulo, row);
                break;
            default:
                break;
        }
    }
    //Mover circulo con raton
    @FXML
    private void circuloR(MouseEvent event) {
        circulo.setCursor(Cursor.DEFAULT);
    }

    @FXML
    private void circuloD(MouseEvent event) {
        circulo.setCursor(Cursor.OPEN_HAND);
        gridPane.setConstraints(circulo, calcularPosColumn(event.getScreenY()), 
                calcularPosRow(event.getScreenX()));
        event.consume();
    }
    
    private int calcularPosColumn(double Y){return (int)(Y - defaultYC) % (600/sizeColumn);}
    private int calcularPosRow(double X){return (int)(X - defaultXC) % (400/sizeRow);}
    @FXML
    private void circuloC(MouseEvent event) {
        defaultXC = event.getSceneX();
        defaultYC = event.getSceneY();
    }
}
