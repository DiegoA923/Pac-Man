package udistrital.avanzada.pacman.servidor.Modelo.Conexion;

import java.io.RandomAccessFile;

/**
 * Clase IConexionAleatorio.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public interface IConexionAleatorio {
    public RandomAccessFile conectar();
    public void desconectar();
    public void setRuta(String ruta);
}
