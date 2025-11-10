package udistrital.avanzada.pacman.servidor.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ControlVentana implements ActionListener {

    private ControlPrincipal logica;
    private VentanaPrincipal ventanaPrincipal;

    public ControlVentana(ControlPrincipal logica) {
        this.logica = logica;
        this.ventanaPrincipal = new VentanaPrincipal();
        ventanaPrincipal.setBtnsListener(this);
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

    /**
     * Metodo para habilitar o deshabilitar el boton de salir
     *
     * @param mostrar
     */
    public void mostrarBtnSalir(boolean mostrar) {
        ventanaPrincipal.setEnableBtnSalir(mostrar);
    }

    /**
     * Metodo para habilitar o deshabilitar el boton de salir
     *
     * @param mostrar
     */
    public void mostrarVentana(boolean mostrar) {
        ventanaPrincipal.mostrarVentana(mostrar);
    }

    /**
     * Metodo para ocultar la visibilidad de la ventana principal
     */
    public void ocultarVentana() {
        ventanaPrincipal.mostrarVentana(false);
    }

    /**
     * Metodo para mostrar un mensaje en al ventana
     *
     * @param msg
     */
    public void mostrarMensajelbl(String msg) {
        ventanaPrincipal.setTextMensajeLbl(msg);
    }

    /**
     * Metodo para salir de la app
     */
    public void salir() {
        //llamar a control principal para que acabe procesos
        logica.salir();
        //Cerra ventana y app
        ventanaPrincipal.setVisible(false);
        ventanaPrincipal.dispose();
        System.exit(0);
    }

    /**
     * Centralizar todos los eventos de la ventana principal
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "archivo":
                logica.preCarga();
                break;
            case "salir":
                salir();
            default:
                break;
        }
    }
}
