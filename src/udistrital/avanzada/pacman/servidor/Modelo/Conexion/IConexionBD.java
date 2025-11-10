package udistrital.avanzada.pacman.servidor.Modelo.Conexion;

import java.sql.Connection;

/**
 * IConexionBD
 * <p>
 * Interfaz que define las operaciones básicas para gestionar la conexión con la
 * base de datos en el sistema Pac-Man.
 * </p>
 *
 * <p>
 * Esta interfaz establece el contrato que deben cumplir las clases que
 * implementen la lógica de conexión a la base de datos, asegurando la
 * independencia entre la lógica del juego y los detalles técnicos de conexión.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-08
 */
public interface IConexionBD {

    /**
     * Obtiene una conexión activa a la base de datos.
     * <p>
     * Si no existe una conexión abierta, debe establecer una nueva utilizando
     * los parámetros definidos en el archivo de propiedades (como URL, usuario
     * y contraseña).
     * </p>
     *
     * @return un objeto {@link Connection} activo y válido
     * @throws RuntimeException si ocurre un error al intentar conectarse a la
     * base de datos
     */
    Connection getConexion();

    /**
     * Cierra la conexión actual con la base de datos, si existe.
     * <p>
     * Este método debe liberar todos los recursos asociados a la conexión.
     * </p>
     */
    void desconectar();
}
