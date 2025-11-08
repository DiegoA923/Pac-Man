package udistrital.avanzada.pacman.servidor.Modelo.ModeloDAO;
import java.util.Properties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.ConexionProperties;
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
 * @since 2025-11-08
 */
public class PropertiesDAO {

    private IConexionProperties conexion;

    public PropertiesDAO() {
        this.conexion = new ConexionProperties();
    }  
    
    /**
     * Configurar ruta de archivo de origen
     * 
     * @param rutaArchivo 
     */
    public void setConfiguracionConexion(String rutaArchivo) {
        conexion.setRutaArchivo(rutaArchivo);
    }
    
    /**
     * Obtener una propiedad especifica del archivo
     * 
     * @param key
     * @return 
     */       
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
