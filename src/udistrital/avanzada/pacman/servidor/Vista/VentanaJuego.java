package udistrital.avanzada.pacman.servidor.Vista;

import javax.swing.*;
import java.awt.*;

/**
 * VentanaJuego.
 * <p>
 * Contiene la interfaz principal del jugador: encabezado (HUD) y tablero.
 * </p>
 *
 * @author Diego
 * @version 2.0
 * @since 2025-11-11
 */
public class VentanaJuego extends JFrame {

    private final JLabel lblJugador;
    private final JLabel lblPuntaje;
    private final JLabel lblTiempo;
    private final PanelJuego panelJuego;

    
    // Crea la ventana principal.
    public VentanaJuego(String nombreJugador) {
        super("Pac-Man - Jugador: " + nombreJugador);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(820, 728);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());

        // Panel Superior
        JPanel panelSuperior = new JPanel(new GridLayout(1, 3));
        panelSuperior.setBackground(new Color(25, 25, 25));
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        lblJugador = crearEtiqueta("Jugador: " + nombreJugador, Color.CYAN);
        lblPuntaje = crearEtiqueta("Puntuación: 0", Color.GREEN);
        lblTiempo = crearEtiqueta("Tiempo: 0 s", Color.YELLOW);

        panelSuperior.add(lblJugador);
        panelSuperior.add(lblPuntaje);
        panelSuperior.add(lblTiempo);

        // Panel de juego
        panelJuego = new PanelJuego();
        add(panelSuperior, BorderLayout.NORTH);
        add(panelJuego, BorderLayout.CENTER);

        setVisible(true);
    }

    private JLabel crearEtiqueta(String texto, Color color) {
        JLabel lbl = new JLabel(texto, SwingConstants.CENTER);
        lbl.setForeground(color);
        lbl.setFont(new Font("Consolas", Font.BOLD, 18));
        return lbl;
    }

    // Actualizaciones externas
    public void actualizarPuntaje(int puntaje) {
        lblPuntaje.setText("⭐ Puntuación: " + puntaje);
    }

    public void actualizarTiempo(int segundos) {
        lblTiempo.setText("⏱ Tiempo: " + segundos + " s");
    }

    public PanelJuego getPanelJuego() {
        return panelJuego;
    }
}
