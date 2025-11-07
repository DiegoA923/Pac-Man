package udistrital.avanzada.pacman.cliente.Modelo.DAO;

import java.util.Properties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.ConexionProperties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.IConexionProperties;

/**
 * Clase PropertiesDAO.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class PropertiesDAO implements IJugadorDAO {

    private IConexionProperties conexion;

    public PropertiesDAO() {
        this.conexion = new ConexionProperties();
    }        

    /**
     * {@inheritDoc}
     */
    @Override
    public String[] getJugador() {
        Properties props;
        String[] datos = new String[2];
        try {
            props = conexion.conectar();
            datos[0] = props.getProperty("usuario.nombre");
            datos[1] = props.getProperty("usuario.contrasena");
        } catch (RuntimeException e) {
            datos = null;
            throw new RuntimeException("Error de conexion a archivo: " + e.getMessage(), e);
        } finally {
            conexion.desconectar();
        }
        return datos;
    }
    
    public void setConfiguracionConexion(String rutaArchivo) {
        conexion.setRutaArchivo(rutaArchivo);
    }
        
    public String[] getServer() {
        Properties props;
        String[] datos = new String[2];
        try {
            props = conexion.conectar();
            datos[0] = props.getProperty("servidor.ip");
            datos[1] = props.getProperty("servidor.puerto");
        } catch (RuntimeException e) {
            throw new RuntimeException("Error de conexion a archivo: " + e.getMessage(), e);
        } finally {
            conexion.desconectar();
        }
        return datos;
    }
    
    public String getProperty (String key) {
        try {
            Properties props = conexion.conectar();
            return props.getProperty(key);
        } catch (Exception e) {
            return null;
        } finally {
            conexion.desconectar();
        }                
    }
}
