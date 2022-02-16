/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controller;

import Entidades.Alarma;
import Util.FileImagUtil;
import controller.emergente.AlertController;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 * FXML Controller class
 *
 * @author yalle
 */
public class AlarmaController implements Initializable, Runnable {

    @FXML
    Label lblhora, lblminuto, lblsegundo;
    @FXML
    private TextField jtfhora;
    @FXML
    private TextField jtfminuto;
    @FXML
    private TextField jtfsegundo;
    @FXML
    private TextField jtfasunto;
    @FXML
    private Label lblnameMusica;
    @FXML
    private Label  lblFirst;
    @FXML
    private TableView<Alarma> tableAlarma;
    @FXML
    private TableColumn<Alarma, Date> columnHora;
    @FXML
    private TableColumn<Alarma, String> columnAsunto;
    @FXML
    private TableColumn<Alarma, String> columnNameAudio;
    @FXML
    AnchorPane ap;
    FileImagUtil oFileImagUtil = new FileImagUtil("user.home", "buscar audio .wap");
    File oAudio;
    Thread h1;
    ObservableList<Alarma> listAlarma = FXCollections.observableArrayList();
    Alarma oAlarmaFirst;
    TimeUnit time = TimeUnit.SECONDS;
    Boolean ping = true;
    AlertController oAlertController = new AlertController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        initTableView();
        updateListaAlarma();
        tableAlarma.setItems(listAlarma);
        h1 = new Thread(this);
        h1.start();
    }

    @Override
    public void run() {
        Thread ct = Thread.currentThread();
        while (ct == h1) {
            Platform.runLater(() -> {
                Date fechaHoraActual = actualizar();
                if (oAlarmaFirst != null) {
                    long segundos = time.convert(fechaHoraActual.getTime() - oAlarmaFirst.getHora().getTime(), TimeUnit.MILLISECONDS);
                    if (0 < segundos && segundos <= 30) {
                        if (ping) {
                            try {
                                alertar(reproducirAudio(oAlarmaFirst.getUrlAudio()));
                                updateListaAlarma();
                            } catch (LineUnavailableException | IOException | UnsupportedAudioFileException ex) {
                                System.out.println("error audio");
                            }
                        }
                        ping = false;
                    } else {
                        ping = true;
                    }
                }
            });
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
            }
        }
    }

    @FXML
    void aniadir() throws IOException {
        if (!jtfhora.getText().trim().isEmpty() && !jtfminuto.getText().trim().isEmpty()
                && !jtfsegundo.getText().trim().isEmpty() && !jtfasunto.getText().trim().isEmpty() && oAudio != null) {
            Date oDate = new Date();
            oDate.setDate(0);oDate.setMonth(0);oDate.setYear(y);
            oDate.setHours(Integer.parseInt(jtfhora.getText().trim()));
            oDate.setMinutes(Integer.parseInt(jtfminuto.getText().trim()));
            oDate.setSeconds(Integer.parseInt(jtfsegundo.getText().trim()));

            Alarma oalarma = new Alarma(
                    oDate,
                    jtfasunto.getText().trim());
            oalarma.setUrlAudio(oFileImagUtil.guardarAudio(oAudio));
            oalarma.setNameAudio(oAudio.getName());
            App.jpa.getTransaction().begin();
            App.jpa.persist(oalarma);
            App.jpa.getTransaction().commit();
            updateListaAlarma();
        }
    }

    @FXML
    void modificar() throws IOException {
        int index = selectItem();
        if (index != -1) {
            if (!jtfhora.getText().trim().isEmpty() && !jtfminuto.getText().trim().isEmpty()
                    && !jtfsegundo.getText().trim().isEmpty() && !jtfasunto.getText().trim().isEmpty() && oAudio != null) {
                Alarma oalarma = listAlarma.get(index);
                Date oDate = new Date();
                oDate.setDate(0);oDate.setMonth(0);oDate.setYear(y);
                oDate.setHours(Integer.parseInt(jtfhora.getText().trim()));
                oDate.setMinutes(Integer.parseInt(jtfminuto.getText().trim()));
                oDate.setSeconds(Integer.parseInt(jtfsegundo.getText().trim()));
                oalarma.setHora(oDate);
                oalarma.setAsunto(jtfasunto.getText().trim());
                oalarma.setUrlAudio(oFileImagUtil.guardarAudio(oAudio));
                oalarma.setNameAudio(oAudio.getName());
                App.jpa.getTransaction().begin();
                App.jpa.persist(oalarma);
                App.jpa.getTransaction().commit();
                listAlarma.set(index, oalarma);
                updateListaAlarma();
            }
        }
    }

    @FXML
    void eliminar() {
        int index = selectItem();
        if (index != -1) {
            Alarma oalarma = listAlarma.get(index);
            App.jpa.getTransaction().begin();
            App.jpa.remove(oalarma);
            App.jpa.getTransaction().commit();
            listAlarma.remove(index);
            updateListaAlarma();
        }
    }

    @FXML
    void test() {
 
    }

    @FXML
    void seleccionarAudio() throws IOException {
        oAudio = oFileImagUtil.buscarAudio();
        lblnameMusica.setText(oAudio.getName());
    }

    @FXML
    void minimizar() {
        Stage stage = (Stage) ap.getScene().getWindow();
        stage.setIconified(true);
    }
    SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
    void updateListaAlarma() {
        List<Alarma> olistAlarma = App.jpa.createQuery("select p from Alarma p ORDER BY idalarma ASC").getResultList();
        List<Alarma> olistAlarmaON = new ArrayList<Alarma>();
        Date oDateActual = new Date();
        oDateActual.setDate(0);oDateActual.setMonth(0);oDateActual.setYear(y);
        for (Alarma oAlarma : olistAlarma) {
            if (oDateActual.getTime() < oAlarma.getHora().getTime()) {
                olistAlarmaON.add(oAlarma);
            }
        }
        listAlarma.clear();
        for (Alarma oalarma : olistAlarma) {
            listAlarma.add(oalarma);
        }

        //ordenando menor (0) a mayor(size())
        if (olistAlarmaON.size() > 0) {
            Collections.sort(olistAlarmaON);
            oAlarmaFirst = olistAlarmaON.get(olistAlarmaON.size()-1);
            lblFirst.setText(oAlarmaFirst.getAsunto()+" "+format.format(oAlarmaFirst.getHora())); 
        }
        for (Alarma oAlarma : olistAlarmaON) {
            System.out.println("date: " + oAlarma.getHora().toString());
        }

    }

    int selectItem() {
        return listAlarma.indexOf(tableAlarma.getSelectionModel().getSelectedItem());
    }

    public Date actualizar() {
        String hora, minutos, segundos, ampm;
        Calendar calendario = new GregorianCalendar();
        Date fechaHoraActual = new Date();//Unico date sin modificar el dia,mes,año
        calendario.setTime(fechaHoraActual);
        ampm = calendario.get(Calendar.AM_PM) == Calendar.AM ? "AM" : "PM";
        if (ampm.equals("PM")) {
            int h = calendario.get(Calendar.HOUR_OF_DAY);
            hora = h > 9 ? "" + h : "0" + h;
        } else {
            int h = calendario.get(Calendar.HOUR_OF_DAY);
            hora = h > 9 ? "" + h : "0" + h;
        }
        int m = calendario.get(Calendar.MINUTE);
        minutos = m > 9 ? "" + m : "0" + m;
        int s = calendario.get(Calendar.SECOND);
        segundos = s > 9 ? "" + s : "0" + s;
        lblhora.setText(hora);
        lblminuto.setText(minutos);
        lblsegundo.setText(segundos);
        fechaHoraActual.setDate(0);fechaHoraActual.setMonth(0);fechaHoraActual.setYear(0);
        return fechaHoraActual;
    }

    Clip reproducirAudio(String nameAudio) throws LineUnavailableException, IOException, UnsupportedAudioFileException {
        File audio = new File(nameAudio);
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(audio);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.start();
        return clip;
    }

    void initTableView() {
        columnHora.setCellValueFactory(new PropertyValueFactory<Alarma, Date>("hora"));
        columnAsunto.setCellValueFactory(new PropertyValueFactory<Alarma, String>("asunto"));
        columnNameAudio.setCellValueFactory(new PropertyValueFactory<Alarma, String>("nameAudio"));
        
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        columnHora.setCellFactory(column -> {
            TableCell<Alarma, Date> cell = new TableCell<Alarma, Date>() {
                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText("");
                    } else {
                        setText(format.format(item));
                    }
                }
            };

            return cell;
        });

    }

    private void alertar(Clip clip) throws IOException, LineUnavailableException {
        mostrarAlert(clip);
    }

    @FXML
    void mostrarAlert(Clip oClip) throws IOException, LineUnavailableException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(AlertController.class.getResource("/fxml/Alert.fxml"));
        Scene scene = new Scene(loader.load());//instancia el controlador (!)
        Stage stage = new Stage();//creando la base vací
        scene.getStylesheets().add(AlertController.class.getResource("/css/bootstrap3.css").toExternalForm());;
        stage.setTitle("Alerta ");
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        AlertController oAlertController = (AlertController) loader.getController(); //esto depende de (1)
        oAlertController.recibeDatos(oClip);
        oAlertController.Asignar("successful");
        oAlertController.setMensaje(oAlarmaFirst.getAsunto());
        stage.show();
    }

    @FXML
    void cerrar() {
        ((Stage) ap.getScene().getWindow()).close();
        h1.stop();

    }
    int x = 0;
    int y;

    @FXML
    void mausePressed(MouseEvent event) {
        this.x = (int) event.getX();
        this.y = (int) event.getY();

    }

    @FXML
    void moverVentanada(MouseEvent event) {
        Point p = MouseInfo.getPointerInfo().getLocation();
    }

}
