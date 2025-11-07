package udistrital.avanzada.pacman.cliente.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import udistrital.avanzada.pacman.cliente.Vista.PanelJuego;
import udistrital.avanzada.pacman.cliente.Vista.VentanaPrincipal;

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
public class ControlVentana implements ActionListener, IVistaJuego {

    private ControlPrincipal logica;
    private VentanaPrincipal ventanaPrincipal;
    private PanelJuego panelJuego;

    public ControlVentana(ControlPrincipal logica) {
        this.logica = logica;
        this.panelJuego = new PanelJuego();
        panelJuego.addActionListenerTxtMovimiento(
            new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String texto = panelJuego.getTextMovimiento();
                    if (!texto.isEmpty()) {
                        //Llamar logica para enviar moviento
                    }
                }
            }
        );
        this.ventanaPrincipal = new VentanaPrincipal(panelJuego);
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
     * Muestra un mensaje informativo.
     */
    public void mostrarMensajeInformativo(String titulo, String mensaje) {
        ventanaPrincipal.mostrarMensajeEmergente(titulo, mensaje);
    }

    public void mostarVentana() {
        ventanaPrincipal.mostrarVentana(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
