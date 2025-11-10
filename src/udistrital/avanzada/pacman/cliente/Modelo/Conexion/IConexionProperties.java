package udistrital.avanzada.pacman.cliente.Modelo.Conexion;

import java.util.Properties;

/**
 * Clase IConexionProperties.
 * <p>
 * Define el contrato para las clases encargadas de gestionar la lectura 
 * del archivo de propiedades.
 * Garantiza un acceso centralizado y controlado al recurso del archivo properties.
 * </p>
 *
 * @author Mauricio
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
