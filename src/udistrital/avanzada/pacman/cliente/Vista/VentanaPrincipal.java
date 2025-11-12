package udistrital.avanzada.pacman.cliente.Vista;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

/**
 * Clase VentanaPrincipal.
 * <p>
 * Esta clase representa la vista base del sistema y recibe su panel principal
 * (PanelJuego) mediante inyección de dependencias.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public class VentanaPrincipal extends JFrame {

    private JLabel lblMensaje;
    private JButton btnEscogerArchivo;
    private CardLayout cardLayout;
    private JPanel contenedor;

    /**
     * Constructor
     *
     * @param panelJuego panel de interaccion de juego
     * @throws HeadlessException
     */
    public VentanaPrincipal(PanelJuego panelJuego) throws HeadlessException {
        super("Cliente Pac-Man");
        // Configuración base de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        cardLayout = new CardLayout();

        JPanel escogerArchivo = new JPanel();
        escogerArchivo.setLayout(new BoxLayout(escogerArchivo, BoxLayout.Y_AXIS));

        escogerArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);
        escogerArchivo.setAlignmentY(Component.CENTER_ALIGNMENT);

        lblMensaje = new JLabel("<html><div style='text-align: center; width: 300px;'>"
                + "Seleccione el archivo de propiedades con la configuración para iniciar"
                + "</div></html>");
        lblMensaje.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnEscogerArchivo = new JButton("Seleccionar Archivo");
        btnEscogerArchivo.setActionCommand("archivo");
        btnEscogerArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);

        escogerArchivo.add(Box.createVerticalGlue());
        escogerArchivo.add(lblMensaje);
        escogerArchivo.add(Box.createVerticalStrut(10));
        escogerArchivo.add(btnEscogerArchivo);
        escogerArchivo.add(Box.createVerticalGlue());

        contenedor = new JPanel(cardLayout);
        contenedor.add(escogerArchivo, "archivo");
        contenedor.add(panelJuego, "juego");

        add(contenedor, BorderLayout.CENTER);
        setVisible(true);

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

    public void setBtnsListener(ActionListener al) {
        btnEscogerArchivo.addActionListener(al);
    }

    /**
     * Método para mostrar un panel específico de la aplicación.
     *
     * @param nombre Identificador del panel a mostrar
     */
    public void mostrarPanel(String nombre) {
        cardLayout.show(contenedor, nombre);
    }
}
