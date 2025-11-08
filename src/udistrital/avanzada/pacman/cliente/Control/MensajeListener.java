package udistrital.avanzada.pacman.cliente.Control;

/**
 * Interfaz MensajeListener
 * <p>
 * Para ser implementada por aquellos que requieran escuchar los mensajes
 * enviados
 * </p>
 * 
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-09
 */
public interface MensajeListener {
    /**
     * Metodo para procesar un mensaje enviando datos completos
     * de tipo de accion requerida a ejecutar y mensaje con su descripcion o resultado
     * 
     * @param tipo que accion se requiere
     * @param mensaje resultado del servidor
     */
    void procesarMensaje(String tipo, String mensaje);
}
