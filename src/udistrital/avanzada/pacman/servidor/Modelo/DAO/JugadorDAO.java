package udistrital.avanzada.pacman.servidor.Modelo.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.IConexionBD;

/**
 * JugadorDAO
 * <p>
 * Implementación de la interfaz {@link IJugadorDAO} que gestiona las
 * operaciones de acceso a datos relacionadas con los jugadores en la base de
 * datos del sistema Pac-Man.
 * </p>
 *
 * <p>
 * Esta clase permite validar credenciales de inicio de sesión, registrar nuevos
 * jugadores y verificar su existencia en la base de datos. Utiliza la interfaz
 * {@link IConexionBD} para mantener la independencia entre la lógica de negocio
 * y los detalles técnicos de conexión.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-08
 */
public class JugadorDAO implements IJugadorDAO {

    private final IConexionBD conexion;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param conexion objeto que implementa {@link IConexionBD} y gestiona la
     * conexión con la base de datos
     */
    public JugadorDAO(IConexionBD conexion) {
        this.conexion = conexion;
    }

    /**
     * Valida las credenciales de inicio de sesión de un jugador.
     * <p>
     * Realiza una consulta SQL para verificar si existe un jugador con el
     * nombre de usuario y contraseña especificados.
     * </p>
     *
     * @param usuario nombre del jugador
     * @param password contraseña del jugador
     * @return {@code true} si las credenciales son correctas; {@code false} si
     * no coinciden
     */
    @Override
    public boolean validarJugador(String usuario, String password) {
        String sql = "SELECT COUNT(*) FROM jugadores WHERE usuario = ? AND password = ?";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, usuario);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al validar jugador: " + e.getMessage(), e);
        } finally {
            conexion.desconectar();
        }
        return false;
    }
    

    /**
     * Inserta un nuevo jugador en la base de datos.
     * <p>
     * Si el jugador ya existe (mismo nombre de usuario), no se realiza la
     * inserción.
     * </p>
     *
     * @param jugador objeto {@link JugadorVO} con la información del jugador
     * @return {@code true} si la inserción fue exitosa; {@code false} si ya
     * existe
     */
    @Override
    public boolean insertarJugador(JugadorVO jugador) {
        if (existeJugador(jugador.getNombreUsuario())) {
            return false;
        }

        String sql = "INSERT INTO jugadores (usuario, password) VALUES (?, ?)";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, jugador.getNombreUsuario());
            ps.setString(2, jugador.getPassword());
            ps.executeUpdate();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException("Error al insertar jugador: " + e.getMessage(), e);
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Verifica si un jugador ya existe en la base de datos.
     * <p>
     * Se realiza una consulta por nombre de usuario para determinar si el
     * jugador ya está registrado.
     * </p>
     *
     * @param nombreUsuario nombre de usuario del jugador
     * @return {@code true} si el jugador existe; {@code false} si no
     */
    @Override
    public boolean existeJugador(String nombreUsuario) {
        String sql = "SELECT COUNT(*) FROM jugadores WHERE usuario = ?";

        try (Connection con = conexion.getConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nombreUsuario);
            ResultSet rs = ps.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al verificar existencia de jugador: " + e.getMessage(), e);
        } finally {
            conexion.desconectar();
        }
        return false;
    }
}
