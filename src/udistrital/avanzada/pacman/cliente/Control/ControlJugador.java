package udistrital.avanzada.pacman.cliente.Control;

import udistrital.avanzada.pacman.cliente.Modelo.DAO.IJugadorDAO;
import udistrital.avanzada.pacman.cliente.Modelo.JugadorVO;

/**
 * Clase ControlJugador.
 * <p>
 * Clase que maneja los datos del jugador cliente
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-06
 */
public class ControlJugador {

    private JugadorVO jugador;
    private IJugadorDAO jugadorDAO;

    /**
     * Constructor por defecto.
     */
    public ControlJugador() {
        this.jugador = null;
    }

    /**
     * Metodo para cargar jugador desde el DAO
     *
     * @return true si usuario valido encontrado sino false
     */
    public boolean cargarJugador() throws RuntimeException {
        if (jugadorDAO == null) {
            return false;
        }
        jugador = jugadorDAO.getJugador();
        if (jugador.getNombre() == null || jugador.getContrasena() == null) {
            return false;
        }
        return true;
    }

    /**
     * Metodo para obtener datos de jugador
     *
     * @return array de string, indice 0 nombre, indice 1 contraseña
     */
    public String[] getDatosJugador() {
        jugador = jugadorDAO.getJugador();
        String[] datos = {"", ""};
        if (jugador != null) {
            datos[0] = jugador.getNombre();
            datos[1] = jugador.getContrasena();
        }
        return datos;
    }

    /**
     * asignar el dao
     *
     * @param jugadorDAO
     */
    public void setJugadorDAO(IJugadorDAO jugadorDAO) {
        this.jugadorDAO = jugadorDAO;
    }

    /**
     * resetear el jugador
     */
    public void resetJugador() {
        this.jugador = null;
    }
}
