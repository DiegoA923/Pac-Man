package udistrital.avanzada.pacman.cliente.Vista;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 * Clase PanelJuego
 *
 * Maneja la vista del juego para que el usuario pueda jugar
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-06
 */
public class PanelJuego extends JPanel {

    private JLabel lblMovimiento;
    private JTextField txtMovimiento;
    private JTextArea areaTexto;
    private JScrollPane scrollPane;

    public PanelJuego() {

        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        lblMovimiento = new JLabel("Ingrese movimiento(arriba, abajo, izquierda, derecha):");
        txtMovimiento = new JTextField();

        panelSuperior.add(lblMovimiento, BorderLayout.NORTH);
        panelSuperior.add(txtMovimiento, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        areaTexto = new JTextArea(10, 26);
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);

        scrollPane = new JScrollPane(areaTexto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Metodo para añadir acción custom al cuandro de texto
     *
     * @param action
     */
    public void addActionListenerTxtMovimiento(ActionListener action) {
        txtMovimiento.addActionListener(action);
    }

    /**
     * Añadir resultado de juego y mostrarlo
     *
     * @param texto
     */
    public void mostrarResultado(String texto) {
        areaTexto.append("- "+texto + "\n");
        txtMovimiento.setText("");
    }

    /**
     * Limpiar input y resultados
     */
    public void resetText() {
        areaTexto.setText("");
        txtMovimiento.setText("");
    }
    
    /**
     * Obterner texto del campo texto moviento
     * 
     * @return 
     */
    public String getTextMovimiento() {
        return txtMovimiento.getText().trim();
    }

}
