package udistrital.avanzada.pacman.servidor.Control;

import java.io.IOException;
import java.net.*;

/**
 * Clase Servidor
 *
 * Maneja la logica para iniciar levantar el servidor y aceptar posibles
 * clientes
 * 
 * Hereda de Thread para evitar bloqueo de hilo principal
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-09
 */
public class Servidor extends Thread {
    private int puerto;
    private ServerSocket server;
    private ControlServidorHilo cs;
    
    public Servidor(ControlServidorHilo cs) {
        this.puerto = -1;
        this.cs = cs;
        server = null;
    }   

    public void config(int puerto) {
        this.puerto = puerto;
    }
    
    public boolean levantarServidor() {        
        try {     
            server = new ServerSocket(puerto);            
        } catch (IOException ex) {
            return false;
        }
        return true;
    }
        
    public void cerrarServidor() {                
        try {
            this.interrupt();
            if (server != null && estaConectado()) {
                server.close();
            }            
        } catch (IOException ex) {
            
        }
    }
    
    public boolean estaConectado () {
        return !server.isClosed();
    }

    @Override
    public void run() {
        try {    
            while (true) {
                Socket socket = server.accept();                                
                cs.agregarConexion(socket);
            }
        } catch (IOException e) {
            
        }
    }
}
