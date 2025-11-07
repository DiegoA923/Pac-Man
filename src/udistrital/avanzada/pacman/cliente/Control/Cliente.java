package udistrital.avanzada.pacman.cliente.Control;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *
 * @author Mauricio
 */
public class Cliente {

    private String ip;
    private int puerto;
    private ControlJugador cJugador;
    private Socket socket;
    private ObjectInputStream entrada;
    private ObjectOutputStream salida;
    private IVistaJuego vistaJuego;

    public Cliente(ControlJugador cJugador, IVistaJuego vistaJuego) {
        this.ip = null;
        this.puerto = -1;
        this.socket = null;
        this.entrada = null;
        this.salida = null;
        this.cJugador = cJugador;
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
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    public String getIp() {
        return ip;
    }

    public int getPuerto() {
        return puerto;
    }

    public void resetConfig() {
        this.ip = null;
        this.puerto = -1;
    }

}
