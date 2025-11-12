package udistrital.avanzada.pacman.servidor.Control;

/**
 * Interfaz ConexionListener
 *
 * Para clases que requieran escuchar el cierre de una ventana
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-11
 */
public interface CerrarVentanaListener {

    /**
     * Metodo para notificar cierre de ventana
     */
    void notificar();
}
