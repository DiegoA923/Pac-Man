package udistrital.avanzada.pacman.servidor.Modelo.Conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gestiona la conexión con la base de datos del servidor Pac-Man.
 * <p>
 * Utiliza un archivo .properties cargado a través de {@link IConexionProperties}
 * para obtener los datos de conexión (URL, usuario y contraseña).
 * </p>
 * 
 * @author Diego
 * @version 1.1
 * @since 2025-11-08
 */
public class ConexionBD implements IConexionBD {

    private final IConexionProperties conexionProps;
    private Connection conexion;

    public ConexionBD(IConexionProperties conexionProps) {
        this.conexionProps = conexionProps;
    }

    @Override
    public Connection getConexion() {
        if (conexion == null) {
            Properties props = conexionProps.conectar();

            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String pass = props.getProperty("db.password");

            try {
                conexion = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                throw new RuntimeException("Error al conectar a la base de datos: " + e.getMessage(), e);
            }
        }
        return conexion;
    }

    @Override
    public void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                
            }
            conexion = null;
        }
        conexionProps.desconectar();
    }
}
