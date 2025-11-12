package udistrital.avanzada.pacman.servidor.Control;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * Clase ServidorHilo
 *
 * Maneja toda la logica de su propio juego como la conexion y comunicacion
 * hacia el cliente que se conecta al servidor
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-09
 */
public class ServidorHilo extends Thread {

    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private ProcesadorPeticiones procesador;
    private JugadorVO jugador;
    private int puntaje;
    private Long start;
    private Long end;

    public ServidorHilo(Socket socket, ProcesadorPeticiones procesador) {
        this.procesador = procesador;
        try {
            this.socket = socket;
            this.entrada = new DataInputStream(socket.getInputStream());
            this.salida = new DataOutputStream(socket.getOutputStream());
        } catch (IOException ex) {
        }
    }

    /**
     * Solicita la autentificacion del cliente para saber si iniciar el juego o
     * terminar la conexion
     */
    public void preguntarAutentificacion() throws IOException {        
        escribirMensajeString("AUTENTIFICACION");
        escribirMensajeString("Ingrese usuario y contraseña");                  
    }

    /**
     * Metodo custom para la escritura de un mensaje tipo String hacia el
     * cliente
     *
     * @param msg contenido a enviar
     * @throws IOException
     */
    public void escribirMensajeString(String msg) throws IOException {
        try {
            byte[] data = msg != null ? msg.getBytes("UTF-8") : new byte[0];
            salida.writeInt(data.length);
            if (data.length > 0) {
                salida.write(data);
            }
            salida.flush();
        } catch (Exception e) {
            throw new IOException();
        }
    }

    /**
     * Metodo custom para la lecturaa de un mensaje tipo String desde el cliente
     *
     * @return contenido de mensaje recibido
     * @throws IOException
     */
    public String LeerMensajeString() throws IOException {
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

    /**
     * Metodo para parar cerrar Streams y socket
     */
    public void parar() {
        //Enviar mensaje a cliente para cerrar controladamente
        try {
            escribirMensajeString("CERRAR_CONEXION");
            escribirMensajeString("");
        } catch (IOException ex) {

        }
        //Cerrar recursos
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

        }
        this.interrupt();
    }

    @Override
    public void run() {      
        System.out.println("pedir datos");
        try {
            preguntarAutentificacion();
        } catch (Exception e) {
            
        }        
        String opcion = "";
        while (!Thread.currentThread().isInterrupted()) {
            try {
                opcion = LeerMensajeString();
                System.out.println("comando");
                System.out.println(opcion);
                switch (opcion) {
                    case "MOVER":
                        String movimiento = LeerMensajeString();
                        boolean acabo = false;
                        // para probar que se acaba pero acabo debe venir de 
                        // donde se calcule el puntaje del juego
                        if (movimiento.equalsIgnoreCase("f")) {
                            acabo = true;
                        }
                        if (acabo) {
                            end = System.nanoTime();
                            double duracionSegundos = (end - start) / 1_000_000_000.0;
                            int puntaje = new Random().nextInt(100);
                            String nombre = "nombre" + new Random().nextInt(100);
                            escribirMensajeString("FIN_JUEGO");
                            escribirMensajeString(
                                    nombre
                                    + " finalizo con "
                                    + puntaje
                                    + " puntos en "
                                    + duracionSegundos + "segundos");
                            procesador.terminarJuego(nombre, puntaje, duracionSegundos, this);
                        } else {
                            escribirMensajeString("RESULTADO_MOVIMIENTO");
                            escribirMensajeString("Movido a la " + movimiento);
                        }
                        break;
                    case "AUTENTIFICACION":                        
                        String usuario = LeerMensajeString();
                        String password = LeerMensajeString();                        
                        System.out.println(usuario + " " + password);
                        //Llamar a procesador para saber si cliente existe
                        jugador = procesador.autentificarUsuario(usuario, password);
                        // si la respuesta es null entonces jugador no existe en BD
                        if (jugador == null) {
                            //El procesador se encarge de la eliminacion de esta conexion
                            procesador.eliminarConexion(this);
                        } else {
                            //Enviar mensaje de exito para que pueda iniciar juego
                            escribirMensajeString("RESULTADO_AUTENTIFICACION");
                            escribirMensajeString("exito");
                            //Iniciar conometro de juego
                            start = System.nanoTime();
                        }
                        break;
                    default:
                }
            } catch (IOException e) {
                //asegurar que se borrer la conexion
                procesador.eliminarConexion(this);
                break;
            }
        }
    }
}
