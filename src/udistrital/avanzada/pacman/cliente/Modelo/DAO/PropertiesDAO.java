package udistrital.avanzada.pacman.cliente.Modelo.DAO;

import java.util.Properties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.IConexionProperties;

/**
 * Clase PropertiesDAO.
 * <p>
 * DAO encargado de gestionar el acceso el archivo Properties con la configuracion
 * necesario para la app
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class PropertiesDAO implements IPropertiesDAO {

    private IConexionProperties conexion;

    public PropertiesDAO(IConexionProperties conexion) {
        this.conexion = conexion;
    }  
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void setConfiguracionConexion(String rutaArchivo) {
        conexion.setRutaArchivo(rutaArchivo);
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public String get (String key) {
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
