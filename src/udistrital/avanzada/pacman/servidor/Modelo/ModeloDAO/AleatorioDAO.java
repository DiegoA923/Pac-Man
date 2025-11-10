package udistrital.avanzada.pacman.servidor.Modelo.ModeloDAO;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import udistrital.avanzada.pacman.servidor.Modelo.JuegoVO;
import udistrital.avanzada.pacman.servidor.Modelo.ModeloConexion.IConexionAleatorio;

/**
 * Clase DAO
 *
 * Gestiona las operaciones de crear y listar las mascotas en un archivo
 * aleatorio
 *
 * @author Mauricio
 * @since 2025-11-09
 */
public class AleatorioDAO implements IAleatorioDAO {

    private IConexionAleatorio conexion;
    private static final int NOMBRE_LEN = 25; // 25 caracteres fijos
    private static final int REGISTRO_SIZE = 2 * NOMBRE_LEN + 4 + 8; // tamaño de un registro

    public AleatorioDAO(IConexionAleatorio conexion) {
        this.conexion = conexion;
    }

    /**
     * Metodo para escribir un juego
     *
     */
    @Override
    public void insertarJuego(String nombre, int puntos, double tiempo) {
        try {
            RandomAccessFile raf = conexion.conectar();
            //Ir al final del archivo
            raf.seek(raf.length());
            //Escribir
            escribirCampo(raf, nombre, NOMBRE_LEN);
            raf.writeInt(puntos);
            raf.writeDouble(tiempo);
        } catch (IOException ex) {
            throw new RuntimeException("Error al escribir: " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }
    }

    /**
     * Metodo auxiliar para escribir un campo String en el archivo aleatorio
     *
     * @param raf RandomAccessFile de donde se escribe
     * @param texto Texto a escribir
     * @param longitud Limite de caracteres permitidos
     */
    private void escribirCampo(RandomAccessFile raf, String texto, int longitud) {
        // Limitamos la cantidad de caracteres para escribir correctamente
        String valor = String.format("%-" + longitud + "s", texto);
        try {
            raf.writeChars(valor);
        } catch (IOException ex) {
            throw new RuntimeException("Error al escribir: " + ex.getMessage(), ex);
        }
    }

    private String leerCadena(RandomAccessFile archivo, int tamaño) throws IOException {
        char[] buffer = new char[tamaño];
        for (int i = 0; i < NOMBRE_LEN; i++) {
            buffer[i] = archivo.readChar();
        }
        return new String(buffer);
    }

    /**
     * Metodo para asignar la ruta del archivo aleatorio a la conexion
     *
     * @param ruta
     */
    @Override
    public void setArchivoAleatorio(String ruta) {
        // Conexion asigna la ruta
        this.conexion.setRuta(ruta);
    }

    @Override
    public ArrayList<JuegoVO> getJuegos() {
        RandomAccessFile raf = conexion.conectar();
        int cantidadReg = 0;
        try {
            cantidadReg = (int) raf.length() / REGISTRO_SIZE;
        } catch (IOException ex) {

        }
        ArrayList<JuegoVO> juegos = new ArrayList<>();
        try {
            //Nos posicionamos en el primer registro
            raf.seek(0);
            //Leemos cada uno y lo agregamos a la lista            
            while (raf.getFilePointer() < raf.length()) {
                String nombre = leerCadena(raf, NOMBRE_LEN).trim();
                int puntaje = raf.readInt();
                double tiempo = raf.readDouble();
                juegos.add(new JuegoVO(nombre, puntaje, tiempo));
            }
            return juegos;
        } catch (IOException ex) {
            throw new RuntimeException("Error al leer " + ex.getMessage(), ex);
        } finally {
            conexion.desconectar();
        }
    }

    @Override
    public String[] getMejorJuego() {
        try {
            ArrayList<JuegoVO> juegos = getJuegos();
            if (juegos == null || juegos.isEmpty()) {
                return new String[0];
            }
            JuegoVO mejor = juegos.get(0);
            for (JuegoVO juego : juegos) {
                if (juego.getPuntaje()/juego.getTiempo() > mejor.getPuntaje()/mejor.getTiempo()) {
                    mejor = juego;
                }
            }
            String[] datos = new String[3];
            datos[0] = mejor.getNombre();
            datos[1] = String.valueOf(mejor.getPuntaje());
            datos[2] = String.valueOf(mejor.getTiempo());
            return datos;
        } catch (Exception e) {
            return null;
        }

    }
}
