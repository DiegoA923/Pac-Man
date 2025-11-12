package udistrital.avanzada.pacman.cliente.Control;

import java.io.IOException;

/**
 * Clase ControlCliente
 *
 * Controla la comunicacion entre el cliente del juego y el servidor
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-07
 */
public class ControlCliente {

    private Cliente cliente;
    private ClienteHilo hilo;

    /**
     * Constructor por defecto.
     */
    public ControlCliente() {
        this.cliente = new Cliente();
        this.hilo = null;
    }

    /**
     * Contructor
     *
     * @param ip direccion ip
     * @param puerto puerto de conexion
     * @param ml quien escucha los mensajes de entrada
     * @return
     */
    public boolean conectar(String ip, int puerto, MensajeListener ml) {
        //cerrar conexion anterior si esta activa
        if (cliente.estaConectado()) {
            cerrarConexion();
        }
        //configurar y tratar de conectar
        cliente.configSocket(ip, puerto);
        cliente.conectar();
        //Verificar conexion
        boolean conectado = cliente.estaConectado();
        //Crear hilo para escuchar mensajes del servidor
        if (conectado) {
            hilo = new ClienteHilo(cliente.getEntrada(), ml);
            hilo.start();
        }
        return conectado;
    }

    /**
     * Enviar mensaje especializado a servidor
     *
     * @param comado tipo de mensaje
     * @param mensaje informaicion a enviar
     * @throws IOException
     */
    public void enviarMensaje(String comado, String mensaje) throws IOException {
        cliente.enviarMensaje(comado, mensaje);
    }

    /**
     * Enviar mensaje a servidor
     *
     * @param mensaje
     * @throws IOException
     */
    public void enviarMensajeString(String mensaje) throws IOException {
        cliente.enviarMensajeString(mensaje);
    }

    /**
     * Cerrar conexion con servidor
     *
     * @return
     */
    public boolean cerrarConexion() {
        cliente.desconectar();
        if (hilo != null && hilo.isAlive()) {
            hilo.interrupt();
            hilo = null;
        }
        return cliente.estaConectado();
    }

    /**
     * Cierra conexiones y resetea la configuracion de conexion
     */
    public void reset() {
        cerrarConexion();
        cliente.resetConfig();
    }

    /**
     * Saber si cliente esta conectado al servidor
     *
     * @return true si hay conexion activa, false si no
     */
    public boolean estaConectado() {
        return cliente.estaConectado();
    }
}
