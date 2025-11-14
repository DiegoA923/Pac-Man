package udistrital.avanzada.pacman.servidor.Control;

import java.util.ArrayList;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.IJugadorDAO;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * Clase ControlJugador.
 * <p>
 * Clase que maneja los objetos tipo jugadores
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-06
 */
public class ControlJugador {

    private IJugadorDAO dao;
    private ArrayList<JugadorVO> jugadores;

    /**
     * Contructor
     *
     * @param dao el dao que sirve de puente con fuente de datos
     */
    public ControlJugador(IJugadorDAO dao) {
        this.dao = dao;
        this.jugadores = new ArrayList<>();
    }

    /**
     * Metodo para crear un objeto JugadorVO
     *
     * @param nombreUsuario
     * @param password
     * @return jugador creado
     */
    public JugadorVO crearJugador(String nombreUsuario, String password) {
        return new JugadorVO(0, nombreUsuario, password);
    }

    /**
     * Metodo para validar si usuario existe
     *
     * @param nombreUsuario
     * @param password
     * @return true si existe, si no false
     */
    public boolean validarJugador(String nombreUsuario, String password) {
        return dao.validarJugador(nombreUsuario, password);
    }

    /**
     * Metodo para insertar un jugador
     *
     * @param nombreUsuario
     * @param password
     * @return true si fue valido, de lo contrario false
     */
    public boolean insertarJugador(String nombreUsuario, String password) {
        if (dao.existeJugador(nombreUsuario)) {
            return false;
        }
        JugadorVO jugador = new JugadorVO(0, nombreUsuario, password);
        if (dao.insertarJugador(jugador)) {
            jugadores.add(jugador);
            return true;
        }
        return false;
    }

    /**
     * Metoodo para insertar un jugador
     *
     * @param jugador objeto JugadorVO
     * @return
     */
    public boolean insertarJugador(JugadorVO jugador) {
        if (dao.existeJugador(jugador.getNombreUsuario())) {
            return false;
        }
        if (dao.insertarJugador(jugador)) {
            jugadores.add(jugador);
            return true;
        }
        return false;
    }

}
