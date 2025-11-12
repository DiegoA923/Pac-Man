package udistrital.avanzada.pacman.cliente.Modelo.DAO;

import udistrital.avanzada.pacman.cliente.Modelo.JugadorVO;

/**
 * Interfaz IJugadorDAO
 * 
 * Interfaz que define las operaciones para acceder a jugadores de un medio externo
 * 
 * Proporciona métodos para cargar jugador
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 * 
 */
public interface IJugadorDAO {
    /**
     * Obtener datos de jugador
     * @return Array de string. Pocision 0 nombre, posicion 1 contraseña
     */
    public JugadorVO getJugador();
}
