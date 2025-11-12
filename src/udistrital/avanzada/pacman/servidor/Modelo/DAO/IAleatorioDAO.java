package udistrital.avanzada.pacman.servidor.Modelo.DAO;

/**
 * Interfaz IAleatorioDAO
 *
 * Define contrato de acceso de datos a archivo de acceso aleatorio
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-09
 */
public interface IAleatorioDAO {

    /**
     * Insertar un registro de juego
     *
     * @param nombre del jugador
     * @param puntos total puntos
     * @param tiempo tiempo que tardo
     */
    public void insertarJuego(String nombre, int puntos, double tiempo);

    /**
     * Metodo para asignar la ruta del archivo aleatorio a la conexion
     *
     * @param ruta
     */
    public void setArchivoAleatorio(String ruta);

    /**
     * Metodo para obtener el mejor juego registrado
     *
     * @return array de string, incice 0 nombre, indice 1 putnos, indice 2
     * tiempo
     */
    public String[] getMejorJuego();

    /**
     * Metodo para saber si la conexion al archivo es correcta
     *
     * @return true si se pudo contectar, false si no
     */
    public boolean conexionValida();
}
