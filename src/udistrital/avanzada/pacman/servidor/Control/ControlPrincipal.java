package udistrital.avanzada.pacman.servidor.Control;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TimerTask;
import java.util.Timer;
import udistrital.avanzada.pacman.cliente.Modelo.DAO.PropertiesDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public class ControlPrincipal {

    //private PropertiesDAO propsDAO;
    private ControlVentana cVentana;
    private DataOutputStream out;

    public ControlPrincipal() {
        //this.propsDAO = new PropertiesDAO();
        this.cVentana = new ControlVentana(this);        
    }

    public void preCarga() {
        File archivoPropiedades = cVentana.obtenerArchivoPropiedades(
                "specs/data",
                "Seleccione el archivo de propiedades con la configuración"
        );
        //No escogio archivo retornar
        if (archivoPropiedades == null) {
            return;
        }
        //Obtener ruta de archivo
        String ruta = archivoPropiedades.getAbsolutePath();
        //Asignar ruta a DAOs props y DAO jugador
//        propsDAO.setConfiguracionConexion(ruta);
//
//        String urlDB = propsDAO.get("db.url");
//        String userDB = propsDAO.get("db.user");
//        String passwordDB = propsDAO.get("db.password");
//
//        int puertoServer = -1;
//        try {
//            puertoServer = Integer.parseInt(propsDAO.get("server.puerto"));
//        } catch (Exception e) {
//        }
//        if (urlDB == null || userDB == null || passwordDB == null) {
//            cVentana.mostrarMensajeEmergente("Info", "Archivo no tiene configuracion de Base de datos");
//            return;
//        }
//        if (puertoServer < 0) {
//            cVentana.mostrarMensajeEmergente("Info", "Archivo no tiene configuracion de servidor");
//            return;
//        }
//        //Configurar Sevidor
//
//        //Check si el servidor
//        boolean servidorLevantado = true;
//
//        if (!servidorLevantado) {
//            cVentana.mostrarMensajeEmergente("Info", "No se pudo levantar el servidor");
//            return;
//        }
//
//        //Configurar base de datos
//        //Check si se esta conectado a la DB
//        boolean conectadoBD = true;
//        if (!conectadoBD) {
//            //cerrar servidor levantado
//            cVentana.mostrarMensajeEmergente("Info", "No se pudo conectar al Base de Datos");
//            return;
//        }
//
//        int nJugadores = -1;
//        try {
//            nJugadores = Integer.parseInt(propsDAO.get("cantidadUsuarios"));
//        } catch (Exception e) {
//        }
//        if (nJugadores > 0) {
//            for (int i = 1; i <= nJugadores; i++) {
//                String nombre = propsDAO.get("usuario" + i + ".name");
//                String contrasena = propsDAO.get("usuario" + i + ".contrasena");
//                if (nombre != null && contrasena != null) {
//                    // Llamar al controlador jugador para crear en DB                    
//                }
//            }
//        }

    }

}
