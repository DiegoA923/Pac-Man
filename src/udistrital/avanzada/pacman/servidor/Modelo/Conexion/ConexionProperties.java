package udistrital.avanzada.pacman.servidor.Modelo.Conexion;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Implementación de {@link IConexionProperties}.
 * <p>
 * Se encarga de abrir, leer y cerrar el archivo .properties indicado. No tiene
 * interacción con el usuario ni rutas predefinidas.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-08
 */
public class ConexionProperties implements IConexionProperties {

    private String rutaArchivo;
    private FileInputStream fis;
    
    public ConexionProperties() {
        this.rutaArchivo = null;
        this.fis = null;
    }

    public ConexionProperties(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public Properties conectar() {
        Properties props = new Properties();
        try {
            fis = new FileInputStream(rutaArchivo);
            props.load(fis);
        } catch (IOException e) {
            desconectar();
            throw new RuntimeException("Error al cargar el archivo de propiedades: " + e.getMessage(), e);
        }
        return props;
    }

    @Override
    public void desconectar() {
        try {
            if (fis != null) {
                fis.close();
            }
        } catch (IOException e) {
            
        }
    }

    @Override
    public String getRutaArchivo() {
        return rutaArchivo;
    }

    @Override
    public void setRutaArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }
}
