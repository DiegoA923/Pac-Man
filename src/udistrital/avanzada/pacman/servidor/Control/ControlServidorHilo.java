package udistrital.avanzada.pacman.servidor.Control;

import java.net.Socket;
import java.util.ArrayList;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * Clase ControlServidorHilo
 *
 * Maneja los hilos de clientes que se conecten al servidor y procesa sus
 * peticiones
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-09
 */
public class ControlServidorHilo implements ProcesadorPeticiones {

    private ArrayList<ServidorHilo> hilos;
    private ConexionListener cListener;
    private GestorArchivoAleatorio gAleatorio;
    private ControlJugadores cJugadores;

    public ControlServidorHilo(ConexionListener listener, GestorArchivoAleatorio gAleatorio, ControlJugadores cJugadores) {
        this.cListener = listener;
        this.hilos = new ArrayList<>();
        this.gAleatorio = gAleatorio;
        this.cJugadores = cJugadores;
    }

    /**
     * Agregar un hilo de conexion a la lista de hilos
     *
     * @param so conexion a agregar
     */
    public synchronized void agregarConexion(Socket so) {
        //Inyectamos el procesador de peticiones del cliente
        ServidorHilo hilo = new ServidorHilo(so, this);
        hilos.add(hilo);
        hilo.start();
        cListener.onConexion(hilos.size());
    }

    /**
     * Cerrar todas las conexiones existentes
     */
    public void cerrarConexiones() {
        for (ServidorHilo hilo : hilos) {
            eliminarConexion(hilo);
        }
    }

    /**
     * {@inheritDoc}
     *  Metodo sincronizado para que lo usen los hilos
     */
    @Override
    public synchronized void eliminarConexion(ServidorHilo so) {
        so.parar();
        hilos.remove(so);
        cListener.onConexion(hilos.size());
    }

    /**
     * {@inheritDoc}
     * Metodo sincronizado para que lo usen los hilos
     */
    @Override
    public synchronized JugadorVO autentificarUsuario(String name, String pass) {
        //peticion a DB para saber si el usuario exite  
        //retornar null si no exite en BD
        try {
            if (cJugadores.validarJugador(name, pass)) {
                return cJugadores.crearJugador(name, pass);
            }
        } catch (Exception e) {
            return null;
        }   
        return null;
    }

    /**
     * {@inheritDoc}
     *  Metodo sincronizado para que lo usen los hilos
     */
    @Override
    public synchronized void terminarJuego(String nombre, int puntaje, double tiempoTotal, ServidorHilo hilo) {
        gAleatorio.insertarJuego(nombre, puntaje, tiempoTotal);
        //Guardar en el el archivo serializado        
    }
}
