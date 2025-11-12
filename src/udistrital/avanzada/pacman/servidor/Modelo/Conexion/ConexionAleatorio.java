package udistrital.avanzada.pacman.servidor.Modelo.Conexion;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * Clase ConexionAleatorio.
 * <p>
 * Encargada del manejo de la conexion al archivo aleatorio
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public class ConexionAleatorio implements IConexionAleatorio {
    
    private RandomAccessFile archivo;
    private String ruta;
    
    /**
     * Constructor por defecto.    
     */
    public ConexionAleatorio() { 
        this.ruta = "";
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public RandomAccessFile conectar() {
        try {
            archivo = new RandomAccessFile(ruta, "rw");
            return archivo;
        //Pasar excepciones a capa control
        } catch (FileNotFoundException ex) {
            throw new RuntimeException("Archivo no se puede abrir: " + ex.getMessage(), ex);
        }
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public void desconectar() {
        try {
            archivo.close();
            archivo = null;
        //Pasar excepciones a capa control
        } catch (IOException ex) {
            throw new RuntimeException("Archivo no se puede cerrar: " + ex.getMessage(), ex);
        } catch (NullPointerException ex) {
            throw new RuntimeException("Archivo es nulo: " + ex.getMessage(), ex);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRuta(String ruta) {
        this.ruta = ruta;
    }
}

