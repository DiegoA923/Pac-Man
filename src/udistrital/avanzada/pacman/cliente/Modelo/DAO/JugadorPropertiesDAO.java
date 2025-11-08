package udistrital.avanzada.pacman.cliente.Modelo.DAO;

import java.util.Properties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.ConexionProperties;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.IConexionProperties;

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
}
