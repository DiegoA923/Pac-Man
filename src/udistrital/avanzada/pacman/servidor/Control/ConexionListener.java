package udistrital.avanzada.pacman.servidor.Control;

/**
 * Interfaz ConexionListener
 * 
 * Para clases que requieran escuchar el numero de conexiones
 * de clientes activos al servidor
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-09
 */
public interface ConexionListener {
    /**
     * Envia la cantidad de conexiones actuales activas
     * 
     * @param cantidad numero de conexiones activas
     */
    public void onConexion(int cantidad);
}
