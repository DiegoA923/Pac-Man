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
 * GestorArchivoProperties
 * <p>
 * Clase encargada de manejar la carga y lectura del archivo
 * <b>servidor.properties</b>. Permite seleccionar el archivo mediante
 * un JFileChooser y delega la lectura de datos al componente
 * {@link ConexionProperties}.
 * </p>
 *
 * <p>
 * También ofrece métodos para interpretar configuraciones específicas,
 * como la lista de jugadores iniciales definida en el archivo.
 * </p>
 *
 * @author Diego
 * @version 1.2
 * @since 2025-11-08
 */
public class GestorArchivoProperties {

    private IConexionProperties conexionProperties;
    private Properties propiedades;
    private File archivoSeleccionado;

    /**
     * Carga el archivo de propiedades, permitiendo al usuario seleccionarlo con JFileChooser.
     * 
     * @return true si se cargó correctamente; false si el usuario canceló o hubo un error.
     */
    public boolean cargar() {
        try {
            JFileChooser chooser = new JFileChooser(new File("specs/data"));
            chooser.setDialogTitle("Seleccione el archivo servidor.properties");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

            int opcion = chooser.showOpenDialog(null);
            if (opcion != JFileChooser.APPROVE_OPTION) {
                System.err.println("No se seleccionó ningún archivo.");
                return false;
            }

            archivoSeleccionado = chooser.getSelectedFile();            

            conexionProperties = new ConexionProperties(archivoSeleccionado.getAbsolutePath());
            propiedades = conexionProperties.conectar();

            return true;

        } catch (Exception e) {
            
            return false;
        }
    }

    /**
     * Carga directamente un archivo de propiedades sin abrir el JFileChooser.
     * 
     * @param archivo archivo de propiedades a cargar
     * @return true si se cargó correctamente, false si hubo un error
     */
    public boolean cargar(File archivo) {
        try {
            archivoSeleccionado = archivo;
            conexionProperties = new ConexionProperties(archivo.getAbsolutePath());
            propiedades = conexionProperties.conectar();
            return true;
        } catch (Exception e) {
            
            return false;
        }
    }

    /**
     * Devuelve el valor de una propiedad específica del archivo.
     *
     * @param clave clave de la propiedad
     * @return valor asociado, o null si no existe
     */
    public String getProperty(String clave) {
        return propiedades != null ? propiedades.getProperty(clave) : null;
    }

    /**
     * Cierra el archivo de propiedades cargado.
     */
    public void cerrarArchivo() {
        if (conexionProperties != null) {
            conexionProperties.desconectar();
        }
    }

    /**
     * Carga la lista de jugadores iniciales desde el archivo properties.
     *
     * @return lista de objetos {@link JugadorVO}
     */
    public List<JugadorVO> cargarJugadoresIniciales() {
        List<JugadorVO> jugadores = new ArrayList<>();
        if (propiedades == null) {           
            return jugadores;
        }

        try {
            int cantidad = Integer.parseInt(propiedades.getProperty("jugadores.cantidad", "0"));
            for (int i = 1; i <= cantidad; i++) {
                String usuario = propiedades.getProperty("jugador" + i + ".usuario");
                String password = propiedades.getProperty("jugador" + i + ".password");
                if (usuario != null && password != null) {
                    jugadores.add(new JugadorVO(0, usuario, password));
                }
            }
        } catch (NumberFormatException e) {
            
        }

        return jugadores;
    }

    /**
     * Retorna todas las propiedades cargadas.
     *
     * @return objeto {@link Properties} con la configuración cargada
     */
    public Properties getPropiedades() {
        return propiedades;
    }

    /**
     * Retorna el archivo actualmente cargado.
     *
     * @return archivo de propiedades
     */
    public File getArchivoSeleccionado() {
        return archivoSeleccionado;
    }
}
