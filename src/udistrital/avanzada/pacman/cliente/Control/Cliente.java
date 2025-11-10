package udistrital.avanzada.pacman.cliente.Control;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

/**
 * Clase Cliente
 *
 * Maneja la conexion al servidor con sockets
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-07
 */
public class Cliente {

    private String ip;
    private int puerto;
    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;

    public Cliente() {
        this.ip = null;
        this.puerto = -1;
        this.socket = null;
        this.entrada = null;
        this.salida = null;
    }

    /**
     * Configurar ip y puerto de servidor a conectarse
     *
     * @param ip
     * @param puerto
     */
    public void configSocket(String ip, int puerto) {
        this.ip = ip;
        this.puerto = puerto;
    }

    /**
     * Conectar al servidor por sockets
     *
     * @return true si se pudo conectar si no false
     */
    public boolean conectar() {
        try {
            socket = new Socket(ip, puerto);
            entrada = new DataInputStream(socket.getInputStream());
            salida = new DataOutputStream(socket.getOutputStream());
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * Enviar un mensaje especializado
     *
     * @param comando tipo de mensaje
     * @param mensaje informacion a enviar
     * @throws IOException
     */
    public void enviarMensaje(String comando, String mensaje) throws IOException {
        if (socket != null && !socket.isClosed()) {
            sendString(comando);
            sendString(mensaje);
        }
    }

    /**
     * Enviar un mensaje UTF al servidor
     *
     * @param mensaje informacin a enviar
     * @throws IOException
     */
    public void enviarMensajeString(String mensaje) throws IOException {
        if (socket != null && !socket.isClosed()) {
            sendString(mensaje);
        }
    }
    /**
     * Metodo para enviar mensaje personalilzado
     *
     * @param msg informacin a enviar
     * @throws IOException
     */
    public void sendString(String msg) throws IOException {
        try {
            byte[] data = msg != null ? msg.getBytes(StandardCharsets.UTF_8) : new byte[0];
            salida.writeInt(data.length);  // primero la longitud
            if (data.length > 0) {
                salida.write(data);        // luego los bytes
            }
            salida.flush();               // asegura que se envíe al socket
        } catch (Exception e) {
            throw new IOException();
        }
        
    }

    /**
     * Cerrar conexion de servidor y cerrar canales de comunicacion
     *
     * @return true si se cerro correctamente, false si no se pudo cerrar
     */
    public boolean desconectar() {
        try {
            if (entrada != null) {
                entrada.close();
            }
            if (salida != null) {
                salida.close();
            }
            if (!socket.isClosed()) {
                socket.close();
            }
        } catch (IOException exc) {
            return false;
        }
        return true;
    }

    /**
     * Saber si hay conexion al servidor
     *
     * @return true si hay conexion activa, false si no
     */
    public boolean estaConectado() {
        if (socket != null) {
            return !socket.isClosed();
        }
        return false;
    }

    /**
     * Resetear ip y puerto
     */
    public void resetConfig() {
        this.ip = null;
        this.puerto = -1;
    }

    /**
     * Obtener entrada del servidor
     *
     * @return
     */
    public DataInputStream getEntrada() {
        return entrada;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }
}
