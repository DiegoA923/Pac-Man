package udistrital.avanzada.pacman.cliente.Modelo.Conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Clase ConexionProperties.
 * <p>
 * Implementa la interfaz {@link IConexionProperties} para gestionar 
 * la conexión y lectura del archivo de propiedades con los datos de configuracion
 * </p>
 * <p>
 * Esta clase se encarga únicamente de la lectura del archivo,
 * delegando el procesamiento de los datos a la capa DAO o Control.
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class ConexionProperties implements IConexionProperties {

    /** Ruta del archivo de propiedades */
    private String rutaArchivo;
    private FileInputStream fis;

    /**
     * Constructor por defecto.    
     */
    public ConexionProperties() {
        this.rutaArchivo = null;
        this.fis = null;
    }

    /**
     * Constructor con ruta personalizada.
     *
     * @param rutaArchivo ruta del archivo de propiedades
     */
    public ConexionProperties(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Properties conectar() {        
        Properties properties = new Properties();
        try {
            fis = new FileInputStream(rutaArchivo);
            properties.load(fis);           
        } catch (IOException e) {             
            desconectar();
            throw new RuntimeException("Error al cargar el archivo de propiedades: " + e.getMessage(), e);
        }
        return properties;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void desconectar() {
        try {
            fis.close();
        } catch (IOException | NullPointerException e) {
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
