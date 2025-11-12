package udistrital.avanzada.pacman.servidor.Control;

import udistrital.avanzada.pacman.servidor.Modelo.DAO.IAleatorioDAO;

/**
 * GestorArchivoAleatorio
 * <p>
 * Clase encargada de manejar escritura y lectura del archivo aleatorio donde se
 * guardan los registros de juegos jugados
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-10
 */
public class GestorArchivoAleatorio {

    private IAleatorioDAO dao;

    /**
     * Constructor
     *
     * @param dao clase puente hacia la fuente de datos
     */
    public GestorArchivoAleatorio(IAleatorioDAO dao) {
        this.dao = dao;
    }

    /**
     * Metodo para insertar un juego en el archivo aleatorio
     *
     * @param nombre jugador
     * @param puntaje puntos totales
     * @param tiempo tiempo tardado
     */
    public void insertarJuego(String nombre, int puntaje, double tiempo) {
        dao.insertarJuego(nombre, puntaje, tiempo);
    }

    /**
     * Metodo para obtener datos del mejor juego
     *
     * @return array de string, incice 0 nombre, indice 1 putnos, indice 2
     * tiempo
     */
    public String[] getMejorJuego() {
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
        this.dao.setArchivoAleatorio(ruta);
        return this.dao.conexionValida();
    }
}
