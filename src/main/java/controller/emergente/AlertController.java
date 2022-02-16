/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller.emergente;

import Util.FileImagUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;

/**
 * FXML Controller class
 *
 * @author yalle
 */
public class AlertController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    HBox hboxAlert;
    @FXML
    Button jbtnOk;
    @FXML
    ImageView jimg;
    @FXML
    AnchorPane ap;
    @FXML
    Label lblmensaje;
    Clip oClip;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setMensaje(String mensaje) {
        lblmensaje.setText(mensaje);
    }

    public void Asignar(String alerta) {
        // #38940d verde
        // #db092f rojo
        // #e0d314 amarillo
        Image imag = null;
        if (alerta.equals("successful")) {
            jbtnOk.setStyle("-fx-background-color : #38940d");
            hboxAlert.setStyle("-fx-background-color : #38940d");
        } else if (alerta.equals("warning")) {
            jbtnOk.setStyle("-fx-background-color : #e0d314");
            hboxAlert.setStyle("-fx-background-color : #e0d314");
        } else {
            jbtnOk.setStyle("-fx-background-color : #db092f");
            hboxAlert.setStyle("-fx-background-color : #db092f");
        }
        imag = new Image(getClass().getResource("/imagenes/" + alerta + ".png").toExternalForm());
        jimg.setImage(imag);
    }

    public void recibeDatos(Clip clip) throws IOException, LineUnavailableException {
        this.oClip = AudioSystem.getClip();
        this.oClip = clip;
    }

    @FXML
    void cambiar() {
        Image imag = new Image(getClass().getResource("../imagenes/geminis1.jpg").toString());
        jimg.setImage(imag);
    }

    @FXML
    void cerrar(ActionEvent event) throws FileNotFoundException, IOException {
        ((Stage) ap.getScene().getWindow()).close();
        oClip.stop();
    }

}
