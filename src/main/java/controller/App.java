package controller;

import Util.JPAUtil;
import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;
import javax.persistence.EntityManager;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    public static EntityManager jpa = JPAUtil.getEntityManagerFactory().createEntityManager();
    private double x = 0;
    private double y = 0;

    @Override
    public void start(Stage stage) throws IOException {
        File carpetaAudio = new File("audio");
        if (!carpetaAudio.exists()) {
            carpetaAudio.mkdirs();

        }
        Parent root = loadFXML("Alarma");
        scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/css/bootstrap3.css").toExternalForm());
        root.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                x = event.getX();
                y = event.getY();
            }
        });
        root.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX()-x);
                stage.setY(event.getScreenY()-y);
            }
        });
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image(getClass().getResource("/imagenes/warning.png").toExternalForm()));
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
