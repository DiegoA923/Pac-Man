package udistrital.avanzada.pacman.cliente.Vista;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

/**
 * Clase PanelJuego
 * <p>
 * Vista principal del cliente Pac-Man. Permite mover al personaje con las
 * flechas del teclado y muestra los resultados enviados por el servidor.
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-06
 */
public class PanelJuego extends JPanel implements KeyListener {

    private JTextArea areaTexto;
    private JLabel lblInfo;
    private transient ActionListener movimientoListener; // Listener para comunicar al controlador

    /**
     * Constructor por defecto.
     */
    public PanelJuego() {
        // Diseño base
        setLayout(new BorderLayout(10, 10));
        setBackground(new Color(250, 250, 250));
        setFocusable(true);

        // --- Panel superior con texto informativo ---
        lblInfo = new JLabel("Usa las flechas del teclado para mover a Pac-Man", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblInfo.setForeground(new Color(44, 62, 80));
        lblInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // --- Área de resultados ---
        areaTexto = new JTextArea(10, 26);
        areaTexto.setEditable(false);
        areaTexto.setFont(new Font("Consolas", Font.PLAIN, 13));
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);
        Border borde = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        );
        areaTexto.setBorder(borde);

        JScrollPane scrollPane = new JScrollPane(areaTexto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Resultados del juego"));

        // --- Imagen o placeholder central (opcional) ---
        JLabel lblPacman = new JLabel("🟡", SwingConstants.CENTER);
        lblPacman.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 64));
        lblPacman.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        // --- Agregar componentes ---
        add(lblInfo, BorderLayout.NORTH);
        add(lblPacman, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // --- Escuchar teclas ---
        addKeyListener(this);
    }

    /**
     * Asigna el listener que recibirá los movimientos.
     */
    public void addMovimientoListener(ActionListener al) {
        this.movimientoListener = al;
    }

    /**
     * Muestra mensajes o resultados del juego.
     */
    public void mostrarResultado(String texto) {
        areaTexto.append("- " + texto + "\n");
    }

    /**
     * Limpia los textos.
     */
    public void resetText() {
        areaTexto.setText("");
    }

    // --- Eventos del teclado ---
    @Override
    public void keyPressed(KeyEvent e) {
        if (movimientoListener == null) {
            return;
        }

        String movimiento = null;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP ->
                movimiento = "ARRIBA";
            case KeyEvent.VK_DOWN ->
                movimiento = "ABAJO";
            case KeyEvent.VK_LEFT ->
                movimiento = "IZQUIERDA";
            case KeyEvent.VK_RIGHT ->
                movimiento = "DERECHA";
            case KeyEvent.VK_F ->
                movimiento = "F"; // finalizar si lo necesitas
        }

        if (movimiento != null) {
            movimientoListener.actionPerformed(
                    new ActionEvent(this, ActionEvent.ACTION_PERFORMED, movimiento)
            );
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Solicita el foco de teclado (importante al mostrar el panel).
     */
    @Override
    public void addNotify() {
        super.addNotify();
        requestFocusInWindow();
    }
}
