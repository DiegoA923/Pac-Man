package udistrital.avanzada.pacman.cliente.Control;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JFileChooser;
import udistrital.avanzada.pacman.cliente.Vista.PanelJuego;
import udistrital.avanzada.pacman.cliente.Vista.VentanaPrincipal;

/**
 * Clase ControlVentana.
 * <p>
 * Controlador que gestiona la navegación general de la aplicación.
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class ControlVentana implements ActionListener {

    private ControlPrincipal logica;
    private VentanaPrincipal ventanaPrincipal;
    private PanelJuego panelJuego;

    /**
     * Contructor
     *
     * @param logica quien maneja la logica principal
     */
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
                    logica.enviarMovimiento(texto);
                }
            }
        }
        );
        this.ventanaPrincipal = new VentanaPrincipal(panelJuego);
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
    public void mostrarMensajeInformativo(String titulo, String mensaje) {
        ventanaPrincipal.mostrarMensajeEmergente(titulo, mensaje);
    }

    /**
     * Metodo para mostrar panel para escoger archivo de configuracion
     */
    public void mostrarPanelArchivo() {
        ventanaPrincipal.mostrarPanel("archivo");
    }

    /**
     * Metodo para mostrar panel para jugar
     */
    public void mostrarPanelComando() {
        ventanaPrincipal.mostrarPanel("juego");
    }

    /**
     * Metodo para añadir resultado de juego y mostrarlo
     *
     * @param mensaje
     */
    public void agregarResultadoJuego(String mensaje) {
        panelJuego.mostrarResultado(mensaje);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "archivo":
                logica.precargaArchivo();
                break;
            default:
                break;
        }
    }
}
