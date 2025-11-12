package udistrital.avanzada.pacman.cliente.Modelo;

/**
 * Clase JugadorVO
 * <p>
 * Representa un jugador dentro del sistema del juego Pac-Man.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public class JugadorVO {
    private String nombre;
    private String contrasena;

    public JugadorVO(String nombre, String contrasena) {
        this.nombre = nombre;
        this.contrasena = contrasena;
    }

    public String getNombre() {
        return nombre;
    }

    public String getContrasena() {
        return contrasena;
    }
}
