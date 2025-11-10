package udistrital.avanzada.pacman.servidor.Modelo.Conexion;

import java.util.Properties;

/**
 * Define el contrato para el manejo de archivos de configuración (.properties).
 * <p>
 * Esta interfaz abstrae las operaciones básicas de lectura, cierre y 
 * administración de la ruta del archivo de propiedades, garantizando 
 * independencia entre las capas del sistema.
 * </p>
 * 
 * @author Diego
 * @version 1.0
 * @since 2025-11-08
 */
public interface IConexionProperties {

    /**
     * Carga las propiedades desde el archivo configurado.
     * 
     * @return un objeto {@link Properties} con las claves y valores cargados.
     */
    Properties conectar();

    /**
     * Cierra el flujo de lectura del archivo de propiedades.
     */
    void desconectar();

    /**
     * Establece la ruta del archivo .properties.
     * 
     * @param rutaArchivo ruta absoluta o relativa del archivo.
     */
    void setRutaArchivo(String rutaArchivo);

    /**
     * Retorna la ruta configurada del archivo .properties.
     * 
     * @return la ruta del archivo de configuración.
     */
    String getRutaArchivo();
}

