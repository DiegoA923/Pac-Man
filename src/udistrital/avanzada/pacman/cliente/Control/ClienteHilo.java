package udistrital.avanzada.pacman.cliente.Control;

import java.io.*;
import java.net.SocketException;

/**
 * Clase ClienteHilo
 * 
 * Maneja la captura de los mensajes que envia el servidor
 * hereda de Thread para no bloquear el hilo principal
 * 
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-07
 */
class ClienteHilo extends Thread {

    private DataInputStream entrada;
    private MensajeListener listener;

    public ClienteHilo(DataInputStream entrada, MensajeListener listener) {
        this.entrada = entrada;
        this.listener = listener;
    }

    public void run() {
        String mensaje = "";
        String opcion = "";
        while (true) {
            try {
                // leer que quiere que hagamos el servidor
                opcion = entrada.readUTF();
                // leer mensaje con resultado o descripcion
                mensaje = entrada.readUTF();
                listener.procesarMensaje(opcion, mensaje);
            } catch (SocketException se) {
                //La conexion se interrumpio por algun error de conexion
                listener.procesarMensaje(Comando.CONEXION_INTERRUMPIDA.name(), "Conexion cerro inesperadamente");
                break;
            } catch (IOException e) {
                //Conexion fue cerrada controladamente
                listener.procesarMensaje(Comando.CERRAR_CONEXION.name(), "Cerra conexion");
                break;
            }
        }
    }
}
