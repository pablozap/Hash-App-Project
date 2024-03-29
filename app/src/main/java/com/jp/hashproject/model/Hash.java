package com.jp.hashproject.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.net.InetAddress;
import java.io.File;
import java.io.FileInputStream;
import java.security.MessageDigest;
import java.util.Calendar;
import java.util.Date;

@Entity
public class Hash {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private final String nombreArchivo;
    private final String ubicacion;
    private final Date fechaHora;
    private final String origen;
    private final String hash;

    // Constructor
    public Hash(String ubicacion) {
        this.nombreArchivo = obtenerNombreArchivo(ubicacion);
        this.ubicacion = ubicacion;
        this.fechaHora = obtenerFechaHoraDelSistema();
        this.origen = obtenerOrigenDelSistema();
        this.hash = generateSHA256Hash(ubicacion);
    }

    // Método para obtener el nombre del archivo
    private String obtenerNombreArchivo(String rutaCompleta) {
        int indiceUltimaBarra = rutaCompleta.lastIndexOf("\\");
        if (indiceUltimaBarra != -1) {
            return rutaCompleta.substring(indiceUltimaBarra + 1);
        } else {
            return rutaCompleta;
        }
    }

    // Método para obtener la fecha y hora actual del sistema
    private Date obtenerFechaHoraDelSistema() {
        return Calendar.getInstance().getTime();
    }

    // Método para obtener la IP de la máquina
    private String obtenerOrigenDelSistema() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostAddress();
        } catch (Exception e) {
            // Si no se puede obtener la IP, usa un valor predeterminado
            return "UNKNOWN_IP";
        }
    }

    //Método para obtener el hash del archivo
    public static String generateSHA256Hash(String filePath) {
        try {
            File file = new File(filePath);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            fis.close();
            byte[] hashBytes = digest.digest();

            // Convert hash bytes to hexadecimal representation
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashBytes) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception error) {
            error.printStackTrace();
            return null;
        }
    }

    // Métodos getter
    public int getId() {
        return id;
    }

    public String getNombreArchivo() {
        return nombreArchivo;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public Date getFechaHora() {
        return fechaHora;
    }

    public String getOrigen() {
        return origen;
    }

    public String getHash() {
        return hash;
    }

    // Método toString para representación textual
    @Override
    public String toString() {
        return "Archivo{" +
                "id=" + id +
                ", nombreArchivo='" + nombreArchivo + '\'' +
                ", ubicacion='" + ubicacion + '\'' +
                ", fechaHora=" + fechaHora +
                ", origen='" + origen + '\'' +
                ", hash='" + hash + '\'' +
                '}';
    }
}