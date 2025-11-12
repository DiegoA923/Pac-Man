package udistrital.avanzada.pacman.servidor.Vista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Clase VentanaPrincipal.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public class VentanaPrincipal extends JFrame {

    private final JLabel lblMensaje;
    private final JButton btnSalir;
    private final JPanel Contenedor;

    /**
     * Constructor por defecto.
     */
    public VentanaPrincipal() {
        super("Servidor Pac-Man");
        // Configuración base de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        Contenedor = new JPanel();
        Contenedor.setLayout(new BoxLayout(Contenedor, BoxLayout.Y_AXIS));

        Contenedor.setAlignmentX(Component.CENTER_ALIGNMENT);
        Contenedor.setAlignmentY(Component.CENTER_ALIGNMENT);

        lblMensaje = new JLabel("Conectados: 0");
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnSalir = new JButton("Salir");
        btnSalir.setActionCommand("salir");
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);

        Contenedor.add(Box.createVerticalGlue());
        Contenedor.add(lblMensaje);
        Contenedor.add(Box.createVerticalStrut(10));
        Contenedor.add(btnSalir);
        Contenedor.add(Box.createVerticalGlue());

        add(Contenedor, BorderLayout.CENTER);
        setVisible(false);
    }

    /**
     * Muestra una ventana de selección de archivos configurada para escoger
     * archivos .properties de configuración del torneo.
     *
     * @param descripcion descripción del tipo de archivo (por ejemplo: "Archivo
     * de configuración")
     * @param extension extensión aceptada sin punto (por ejemplo: "properties")
     * @param modoSeleccion modo de selección (ver {@link JFileChooser})
     * @param rutaPredeterminada ruta inicial del explorador
     * @return instancia configurada de {@link JFileChooser}
     */
    public JFileChooser getFileChoser(String descripcion, String extension, int modoSeleccion, String rutaPredeterminada) {
        JFileChooser fileChooser = new JFileChooser();
        File carpetaInicial = new File(rutaPredeterminada);
        fileChooser.setCurrentDirectory(carpetaInicial);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter(descripcion, extension);
        fileChooser.setFileFilter(filtro);
        fileChooser.setFileSelectionMode(modoSeleccion);
        return fileChooser;
    }

    /**
     * Muestra un mensaje emergente informativo.
     *
     * @param titulo de la ventana emergente
     * @param mensaje texto a mostrar
     */
    public void mostrarMensajeEmergente(String titulo, String mensaje) {
        JOptionPane.showMessageDialog(
                this,
                mensaje,
                titulo,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Muestra mensaje en consola.
     *
     * @param mensaje texto a mostrar
     */
    public void mostrarMensajeConsola(String mensaje) {
        System.out.println(mensaje);
    }

    /**
     * Metodo para mostrar la ventana principal
     *
     * @param bandera
     */
    public void mostrarVentana(boolean bandera) {
        setVisible(bandera);
    }

    /**
     * Configurar action listener de los botones
     *
     * @param al objeto que escuchara
     */
    public void setBtnsListener(ActionListener al) {
        btnSalir.addActionListener(al);
    }

    /**
     * habilitar/deshabilitar boton salir
     *
     * @param bandera
     */
    public void setEnableBtnSalir(boolean bandera) {
        btnSalir.setEnabled(bandera);
    }

    /**
     * Cambiar texto de mensaje en la ventana
     *
     * @param text
     */
    public void setTextMensajeLbl(String text) {
        lblMensaje.setText(text);
    }
}
