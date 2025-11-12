package udistrital.avanzada.pacman.servidor.Control;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import javax.swing.SwingUtilities;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;
import udistrital.avanzada.pacman.servidor.Vista.PanelJuego;
import udistrital.avanzada.pacman.servidor.Vista.VentanaJuego;

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
public class ServidorHilo extends Thread implements CerrarVentanaListener {

    private Socket socket;
    private DataInputStream entrada;
    private DataOutputStream salida;
    private ProcesadorPeticiones procesador;
    private JugadorVO jugador;
    private int puntaje;
    private Long start;
    private Long end;
    private ControlJuego cJuego;

    /**
     * Constructor
     *
     * @param socket conexion socket
     * @param procesador clase que procesa los mensajes
     */
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
        if (cJuego != null) {
            SwingUtilities.invokeLater(() -> cJuego.cerrarVentana());
        }
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
        try {
            preguntarAutentificacion();
        } catch (Exception e) {

        }
        String opcion = "";
        while (!Thread.currentThread().isInterrupted()) {
            try {
                opcion = LeerMensajeString();
                switch (opcion) {
                    case "MOVER":
                        if (jugador == null || cJuego == null) {
                            return;
                        }
                        String movimiento = LeerMensajeString();
                        boolean acabo = false;
                        // para probar que se acaba pero acabo debe venir de 
                        // donde se calcule el puntaje del juego                        
                        int casillas = cJuego.moverPacman(movimiento);
                        String resultado = "";
                        if (casillas == 0) {
                            resultado = "No pudo avanzar a " + movimiento.toLowerCase();
                        } else if (casillas < 0) {
                            resultado = movimiento + " no es un movimiento valido";
                        } else {
                            resultado = "Se movio " + casillas + " hacia a " + movimiento.toLowerCase();
                        }

                        if (movimiento.equalsIgnoreCase("f") || cJuego.getFrutasActuales() == 0) {
                            acabo = true;
                        }

                        if (acabo) {
                            end = System.nanoTime();
                            double duracionSegundos = (end - start) / 1_000_000_000.0;
                            escribirMensajeString("FIN_JUEGO");
                            escribirMensajeString(
                                    jugador.getNombreUsuario()
                                    + " finalizo con "
                                    + cJuego.getPuntaje()
                                    + " puntos en "
                                    + duracionSegundos + "segundos");
                            procesador.terminarJuego(
                                    jugador.getNombreUsuario(),
                                    cJuego.getPuntaje(),
                                    duracionSegundos,
                                    this
                            );
                            SwingUtilities.invokeLater(() -> cJuego.cerrarVentana());
                        } else {
                            escribirMensajeString("RESULTADO_MOVIMIENTO");
                            escribirMensajeString(resultado);
                        }
                        break;
                    case "AUTENTIFICACION":
                        String usuario = LeerMensajeString();
                        String password = LeerMensajeString();

                        // Llamar al procesador para validar las credenciales
                        jugador = procesador.autentificarUsuario(usuario, password);

                        if (jugador == null) {
                            // Usuario inválido: enviar mensaje de error antes de cerrar la conexión
                            escribirMensajeString("RESULTADO_AUTENTIFICACION");
                            escribirMensajeString("error");

                            // Luego permitir que el procesador elimine la conexión y libere recursos
                            procesador.eliminarConexion(this);

                        } else {
                            // Usuario válido: enviar mensaje de éxito
                            escribirMensajeString("RESULTADO_AUTENTIFICACION");
                            escribirMensajeString("exito");

                            // Iniciar la ventana de juego y la lógica correspondiente
                            VentanaJuego ventana = new VentanaJuego(jugador.getNombreUsuario());
                            this.cJuego = new ControlJuego(ventana.getPanelJuego(), ventana, this);
                            cJuego.generarFrutasAleatorias();

                            // Iniciar el cronómetro de juego
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

    @Override
    public void notificar() {
        procesador.eliminarConexion(this);
    }
}
