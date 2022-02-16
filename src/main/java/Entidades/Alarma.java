/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entidades;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author yalle
 */
@Entity
public class Alarma implements Comparable<Alarma>{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idalarma;
    
    @Column(name = "hora", nullable = false)
    private Date hora;

    
    @Column(name = "asunto", nullable = false)
    private String asunto;
    
    @Column(name = "nameAudio", nullable = true)
    private String nameAudio;
    
    @Column(name = "urlAudio", nullable = true)
    private String urlAudio;

    public Alarma(Date hora, String asunto) {
        this.hora = hora;
        this.asunto = asunto;
    }

    public Alarma() {
    }

    public int getIdalarma() {
        return idalarma;
    }

    public void setIdalarma(int idenfermedad) {
        this.idalarma = idenfermedad;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getNameAudio() {
        return nameAudio;
    }

    public void setNameAudio(String nameAudio) {
        this.nameAudio = nameAudio;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getUrlAudio() {
        return urlAudio;
    }

    public void setUrlAudio(String urlAudio) {
        this.urlAudio = urlAudio;
    }
    
    
    
    @Override
    public int compareTo(Alarma o) {
        return (this.hora.getTime()+"").compareTo(o.getHora().getTime()+"");
    } 
    
    
    
}
