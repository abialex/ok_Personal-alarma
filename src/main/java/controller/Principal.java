/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package controller;

import ch.qos.logback.classic.spi.Configurator;
import javax.swing.JOptionPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author yalle
 */
public class Principal {

    /**
     * @param args the command line arguments
     */
    private static final Logger looger = LogManager.getLogger(Principal.class);

    public static void main(String[] args) {
  
        looger.debug("sadsd");
        looger.trace("asdasd");
        
    }
    
}
