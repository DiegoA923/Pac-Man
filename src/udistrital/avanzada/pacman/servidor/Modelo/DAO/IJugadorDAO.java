package udistrital.avanzada.pacman.servidor.Modelo.DAO;

import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * IJugadorDAO
 * <p>
 * Interfaz que define las operaciones básicas de acceso a datos (DAO)
 * relacionadas con los jugadores del sistema Pac-Man.
 * </p>
 *
 * <p>
 * Permite realizar operaciones de autenticación y gestión de información del
 * jugador en la base de datos. Las implementaciones de esta interfaz deben
 * garantizar la separación entre la lógica de negocio y la persistencia de
 * datos.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-08
 */
public interface IJugadorDAO {

    /**
     * Valida las credenciales de inicio de sesión de un jugador.
     * <p>
     * Este método comprueba si existe un registro en la base de datos con el
     * nombre de usuario y contraseña especificados.
     * </p>
     *
     * @param nombreUsuario nombre del jugador
     * @param password contraseña del jugador
     * @return {@code true} si las credenciales son válidas; {@code false} en
     * caso contrario
     */
    boolean validarJugador(String nombreUsuario, String password);

    /**
     * Inserta un nuevo jugador en la base de datos.
     * <p>
     * Este método se usa para registrar un nuevo jugador si aún no existe en el
     * sistema.
     * </p>
     *
     * @param jugador objeto {@link JugadorVO} con la información del jugador
     * @return {@code true} si la inserción fue exitosa; {@code false} si el
     * jugador ya existe
     */
    boolean insertarJugador(JugadorVO jugador);

    /**
     * Verifica si un jugador ya existe en la base de datos.
     *
     * @param nombreUsuario nombre de usuario del jugador
     * @return {@code true} si el jugador ya existe; {@code false} si no
     */
    boolean existeJugador(String nombreUsuario);
}
