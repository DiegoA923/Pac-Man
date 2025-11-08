package udistrital.avanzada.pacman.servidor.Control;

import java.io.File;
import javax.swing.JFileChooser;
import udistrital.avanzada.pacman.servidor.Vista.VentanaPrincipal;

/**
 * Clase ControlVentana.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class ControlVentana {

    private ControlPrincipal logica;
    private VentanaPrincipal ventanaPrincipal;

    public ControlVentana(ControlPrincipal logica) {
        this.logica = logica;
        this.ventanaPrincipal = new VentanaPrincipal();
    }

    /**
     * Diálogo de selección de archivo de propiedades.
     *
     * @param ruta
     * @param mensaje
     * @return File si fue escogido el archivo, null si no escogio nada
     */
    public File obtenerArchivoPropiedades(String ruta, String mensaje) {
        JFileChooser chooser = ventanaPrincipal.getFileChoser(
                mensaje, "properties", JFileChooser.FILES_ONLY, ruta
        );
        int seleccion = chooser.showOpenDialog(null);
        return (seleccion == JFileChooser.APPROVE_OPTION) ? chooser.getSelectedFile() : null;
    }
    
    /**
     * Muestra un mensaje informativo a usuario en una ventana emergente que
     * bloquea a la pricipal
     *
     * @param titulo
     * @param mensaje
     */
    public void mostrarMensajeEmergente(String titulo, String mensaje) {
        ventanaPrincipal.mostrarMensajeEmergente(titulo, mensaje);
    }

    /**
     * Mostrar mensaje en consola
     *
     * @param mensaje
     */
    public void mostrarMensajeConsola(String mensaje) {
        ventanaPrincipal.mostrarMensajeConsola(mensaje);
    }

}
