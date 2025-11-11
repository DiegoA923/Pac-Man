package udistrital.avanzada.pacman.servidor.Control;

import udistrital.avanzada.pacman.servidor.Modelo.DAO.IAleatorioDAO;

/**
 * GestorArchivoAleatorio
 * <p>
 * Clase encargada de manejar escritura y lectura del archivo aleatorio
 * donde se guardan los registros de juegos jugados
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-10
 */
public class GestorArchivoProperties {

    private IAleatorioDAO dao;

    public GestorArchivoProperties(IAleatorioDAO dao) {
        this.dao = dao;
    }

    public void insertarJuego(String nombre, int puntaje, double tiempo) {
        dao.insertarJuego(nombre, puntaje, tiempo);
    }

    public String[] obtenerMejorJugador() {
        return dao.getMejorJuego();
    }

    /**
     * Metodo para asignar la ruta del archivo aleatorio a la conexion
     * 
     * @param ruta
     * @return true si se puede conectar al archivo, false si no
     */
    public boolean setArchivoAleatorio(String ruta) {
        // Conexion asigna la ruta
        this.dao.setRutaArchivo(ruta);
        return this.dao.conexionValida();
    }
}