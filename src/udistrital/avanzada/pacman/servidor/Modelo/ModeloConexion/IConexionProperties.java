package udistrital.avanzada.pacman.servidor.Modelo.ModeloConexion;

import java.util.Properties;

/**
 * Clase IConexionProperties.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public interface IConexionProperties {
/**
     * Establecer conexion
     *
     * @return Properties
     */
    public Properties conectar();

    /**
     * Cerrar la conexion
     */
    public void desconectar();

    /**
     * Establece la ruta del archivo de propiedades.
     *
     * @param rutaArchivo nueva ruta del archivo
     */
    public void setRutaArchivo(String rutaArchivo);
    
    /**
     * Obtener ruta configurada
     * @return 
     */
    public String getRutaArchivo();
}
