package udistrital.avanzada.pacman.servidor.Control;

import java.util.ArrayList;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.IJugadorDAO;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 *
 * @author Mauricio
 */
public class ControlJugadores {

    private IJugadorDAO dao;
    private ArrayList<JugadorVO> jugadores;

    public ControlJugadores(IJugadorDAO dao) {
        this.dao = dao;
    }
    
    public JugadorVO crearJugador(String nombreUsuario, String password) {
        return new JugadorVO(0, nombreUsuario, password);
    }

    public boolean validarJugador(String nombreUsuario, String password) {
        return dao.validarJugador(nombreUsuario, password);
    }

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
