/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import javafx.stage.FileChooser;

/**
 *
 * @author yalle
 */
public class FileImagUtil {

    FileChooser filechooserAudio = new FileChooser();
    byte[] imagen = new byte[1024 * 100000];
    String URLdestino = "audio/";

    String initialDirectory;
    String title;

    public FileImagUtil(String id, String title) {
        this.initialDirectory = id;
        this.title = title;

    }

    public File buscarAudio() throws FileNotFoundException, IOException {
        filechooserAudio.setInitialDirectory(new File(System.getProperty(initialDirectory)));//para iniciar la carpeta
        filechooserAudio.getExtensionFilters().clear();
        filechooserAudio.getExtensionFilters().add(new FileChooser.ExtensionFilter("musicas", "*.wav*"));
        filechooserAudio.setTitle(title);
        File fileAudio = filechooserAudio.showOpenDialog(null);
        FileInputStream FileEntrada = new FileInputStream(fileAudio);
        FileEntrada.read(imagen);
        try {
            if (FileEntrada != null) {
                FileEntrada.close();
            }
        } catch (IOException E) {
            System.out.println("problema cerrando File entrada");
        }
        return fileAudio;
    }

    public String guardarAudio(File file) throws FileNotFoundException, IOException {
        File fileAudioSalida = new File(URLdestino + file.getName());
        FileOutputStream FileSalida = new FileOutputStream(fileAudioSalida);
        FileSalida.write(imagen);
        try {
            if (FileSalida != null) {
                FileSalida.close();
            }
        } catch (IOException E) {
            System.out.println("problema cerrando File salida");
        }
        return fileAudioSalida.getAbsolutePath();
    }

}
