package udistrital.avanzada.pacman.servidor.Modelo;

import java.io.Serializable;

/**
 * JugadorVO
 * <p>
 * Representa un jugador dentro del sistema del juego Pac-Man.
 * </p>
 *
 * <p>
 * Esta clase actúa como un objeto de valor (VO) dentro del patrón DAO,
 * encapsulando los datos básicos del jugador, como su identificador, nombre de
 * usuario y contraseña. Se utiliza principalmente para la comunicación entre la
 * capa de datos y la lógica del servidor.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-08
 */
public class JugadorVO implements Serializable {

    private int id;
    private String usuario;
    private String password;

    /**
     * Constructor vacío de la clase {@code JugadorVO}.
     * <p>
     * Permite crear una instancia sin inicializar los atributos, útil cuando
     * los valores se asignan posteriormente mediante los métodos setter, como
     * durante una consulta o validación de credenciales.
     * </p>
     */
    public JugadorVO() {
    }

    /**
     * Constructor principal de la clase {@code JugadorVO}.
     *
     * @param id identificador único del jugador
     * @param usuario nombre de usuario del jugador
     * @param password contraseña asociada al jugador
     */
    public JugadorVO(int id, String usuario, String password) {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
    }

    /**
     * Obtiene el identificador único del jugador.
     *
     * @return identificador del jugador
     */
    public int getId() {
        return id;
    }

    /**
     * Asigna un nuevo identificador al jugador.
     *
     * @param id identificador único a asignar
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de usuario del jugador.
     *
     * @return nombre de usuario
     */
    public String getNombreUsuario() {
        return usuario;
    }

    /**
     * Asigna un nombre de usuario al jugador.
     *
     * @param usuario nombre de usuario a asignar
     */
    public void setNombreUsuario(String usuario) {
        this.usuario = usuario;
    }

    /**
     * Obtiene la contraseña del jugador.
     *
     * @return contraseña del jugador
     */
    public String getPassword() {
        return password;
    }

    /**
     * Asigna una nueva contraseña al jugador.
     *
     * @param password contraseña a asignar
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Verifica si el jugador tiene los datos mínimos requeridos.
     * <p>
     * Este método evalúa si los campos de nombre de usuario y contraseña están
     * completos, sin validar aún su existencia en la base de datos.
     * </p>
     *
     * @return {@code true} si ambos campos son válidos; {@code false} en caso
     * contrario
     */
    public boolean tieneDatosCompletos() {
        return usuario != null && !usuario.isBlank()
                && password != null && !password.isBlank();
    }

    @Override
    public String toString() {
        return "JugadorVO{" + "id=" + id + ", Usuario ='" + usuario + '\'' + '}';
    }
}
