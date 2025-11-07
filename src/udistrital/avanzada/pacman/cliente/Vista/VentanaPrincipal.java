package udistrital.avanzada.pacman.cliente.Vista;

import java.awt.*;
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
    
    private JLabel lblArchivoPropEscogido;
    private JLabel lblTitulo;
    private JLabel lblMensaje;
    private JButton btnEscogerArchivo;
    private JButton btnSalir;  
    private CardLayout cardLayout;
    private JPanel contenedor;        

    public VentanaPrincipal(PanelJuego panelJuego) throws HeadlessException {
        super("Cliente Pac-Man");

        // Configuración base de la ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        cardLayout = new CardLayout();
        contenedor = new JPanel(cardLayout);
        
        contenedor.add(panelJuego, "juego");
                
        add(contenedor, BorderLayout.CENTER);
        setVisible(false);
    }    
    
    /**
     * Muestra una ventana de selección de archivos configurada
     * para escoger archivos .properties de configuración del torneo.
     *
     * @param descripcion descripción del tipo de archivo (por ejemplo: "Archivo de configuración")
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
    
    public void mostrarVentana(boolean mostrar) {
        this.setVisible(mostrar);
    }
}
