package udistrital.avanzada.pacman.servidor.Control;

import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * Interfaz ProcesadorPeticiones
 * 
 * Para las clases que necesiten procesar los mensajes del clientes que se
 * conecten al servidor
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-09
 */
public interface ProcesadorPeticiones {
    /**
     * Verificar que existe usuario
     * 
     * @param name usuario
     * @param pass contrasena
     * @return 
     */
    public JugadorVO autentificarUsuario(String name, String pass);
    
    /**
     * Manejar la terminacion del juego de un cliente en especifico
     * 
     * @param nombre del cliente
     * @param puntaje puntos totales obtenidos
     * @param tiempoTotal cuanto tardo en completar el juego
     * @param hilo cliente en especifico
     */
    public void terminarJuego(String nombre, int puntaje, double tiempoTotal, ServidorHilo hilo);
    
    /**
     * Manejar la eliminiacion de un hilo en especifico
     * 
     * @param so cliente en especifico
     */
    public void eliminarConexion(ServidorHilo so);
}
