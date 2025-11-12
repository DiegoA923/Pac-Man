package udistrital.avanzada.pacman.cliente.Modelo.DAO;

/**
 * Interfaz IPropertiesDAO
 *
 * Interfaz que define las operaciones para acceder a propiedades de un archivo
 * de propiedades
 *
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-11
 *
 */
public interface IPropertiesDAO {

    /**
     * Configurar ruta de archivo de origen
     *
     * @param rutaArchivo
     */
    public void setConfiguracionConexion(String rutaArchivo);

    /**
     * Obtener una propiedad especifica del archivo
     *
     * @param key
     * @return
     */
    public String get(String key);
}
