package udistrital.avanzada.pacman.cliente.Control;


/**
 * Enum Comando
 * 
 * Contiene las acciones que se pueden enviar y recibir
 * del servidor
 * 
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-07
 */
public enum Comando {
    RESULTADO_MOVIMIENTO,
    FIN_JUEGO,
    AUTENTIFICACION,
    RESULTADO_AUTENTIFICACION,
    CERRAR_CONEXION,
    CONEXION_INTERRUMPIDA,
    MOVER,        
}