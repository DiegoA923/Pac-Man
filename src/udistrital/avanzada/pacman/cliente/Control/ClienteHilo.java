package udistrital.avanzada.pacman.cliente.Control;

import java.io.*;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

/**
 * Clase ClienteHilo
 *
 * Maneja la captura de los mensajes que envia el servidor hereda de Thread para
 * no bloquear el hilo principal
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

    private String readMessage() throws IOException {
        try {
            int len = entrada.readInt();              // lee la longitud            
            if (len == 0) {
                return "";             // mensaje vacío
            }
            byte[] data = new byte[len];
            entrada.readFully(data);                  // lee exactamente len bytes
            return new String(data, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IOException();
        }

    }

    public void run() {
        String mensaje = "";
        String opcion = "";
        while (true) {
            try {
                // leer que quiere que hagamos el servidor
                opcion = readMessage();
                // leer mensaje con resultado o descripcion
                mensaje = readMessage();
                listener.procesarMensaje(opcion, mensaje);
            } catch (SocketException se) {
                //La conexion se interrumpio por algun error de conexion
                System.out.println("hola desconectado");
                listener.procesarMensaje(Comando.CONEXION_INTERRUMPIDA.toString(), "Conexion cerro inesperadamente");
                break;
            } catch (IOException e) {
                //Conexion fue cerrada controladamente
                System.out.println("hola desconectado co");
                listener.procesarMensaje(Comando.CERRAR_CONEXION.toString(), "Cerrada conexion");
                break;
            }
        }
        System.out.println("salio del bucle");
    }
}
