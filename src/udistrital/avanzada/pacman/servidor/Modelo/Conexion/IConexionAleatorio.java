package udistrital.avanzada.pacman.servidor.Modelo.Conexion;

import java.io.RandomAccessFile;

/**
 * Define el contrato para el manejo de la conexion al archivo de aleatorio
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public interface IConexionAleatorio {

    /**
     * Metodo para iniciar conexion a archivo aleatorio
     *
     * @return archivo aleatorio
     */
    public RandomAccessFile conectar();

    /**
     * Metodo para cerrar conexion a archivo aleatorio
     */

    public void desconectar();

    /**
     * Metodo para cambiar archivo aleatorio
     *
     * @param ruta direccion del archivo reemplazo
     */
    public void setRuta(String ruta);
}
