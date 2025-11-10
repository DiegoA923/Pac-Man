package udistrital.avanzada.pacman.servidor.Control;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.swing.JFileChooser;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.ConexionProperties;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.IConexionProperties;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * GestorArchivoAleatorio
 * <p>
 * Clase encargada de manejar escritura y lectura del archivo aleatorio
 * donde se guardan los registros de juegos jugados
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-10
 */
public class GestorArchivoProperties {

    private IAleatorioDAO dao;

    public GestorArchivoProperties(IAleatorioDAO dao) {
        this.dao = dao;
    }

    /**
     * Carga directamente un archivo de propiedades sin abrir el JFileChooser.
     * 
     * @param archivo archivo de propiedades a cargar
     * @return true si se cargó correctamente, false si hubo un error
     */
    public void insertarJuego(String nombre, int puntaje, double tiempo) {
        dao.insertarJuego(nombre, puntaje, tiempo);
    }

    /**
     * Carga directamente un archivo de propiedades sin abrir el JFileChooser.
     * 
     * @param archivo archivo de propiedades a cargar
     * @return true si se cargó correctamente, false si hubo un error
     */
    public String[] obtenerMejorJugador() {
        return dao.getMejorJuego();
    }

    /**
     * Metodo para asignar la ruta del archivo aleatorio a la conexion
     * 
     * @param ruta
     * @return true si se puede conectar al archivo, false si no
     */
    public boolean setArchivoAleatorio(String ruta) {
        // Conexion asigna la ruta
        this.dao.setRutaArchivo(ruta);
        return this.dao.conexionValida();
    }
}