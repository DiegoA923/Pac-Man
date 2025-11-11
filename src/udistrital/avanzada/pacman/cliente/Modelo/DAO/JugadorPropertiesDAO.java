package udistrital.avanzada.pacman.cliente.Modelo.DAO;

import java.util.Properties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.ConexionProperties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.IConexionProperties;
import udistrital.avanzada.pacman.cliente.Modelo.JugadorVO;

/**
 * Clase JugadorPropertiesDAO.
 * <p>
 * DAO para la consulta de jugador desde un archivo de propiedades
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-07
 */
public class JugadorPropertiesDAO implements IJugadorDAO {

    private IConexionProperties conexion;

    public JugadorPropertiesDAO() {
        this.conexion = new ConexionProperties();
    }        

    /**
     * {@inheritDoc}
     */
    @Override
    public JugadorVO getJugador() {
        Properties props;
        try {
            props = conexion.conectar();
            String nombre = props.getProperty("usuario.nombre");
            String contrasena = props.getProperty("usuario.contrasena");
            return new JugadorVO(nombre, contrasena);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error de conexion a archivo: " + e.getMessage(), e);            
        } finally {
            conexion.desconectar();
        }
    }
    
    public void setConfiguracionConexion(String rutaArchivo) {
        conexion.setRutaArchivo(rutaArchivo);
    }   
}
