package udistrital.avanzada.pacman.servidor.Vista;

/**
 * Clase VentanaJuego.
 * <p>
 * Descripción: ventana que tiene cada jugador con su propio juego
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
import javax.swing.*;
import java.awt.*;

public class VentanaJuego extends JFrame {
    
    private final int FILAS = 11;
    private final int COLUMNAS = 11;
    
    public VentanaJuego() {
        super("Ventana Juego");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 500);        
        setLocationRelativeTo(null);
        setResizable(false);
        // Panel principal con GridLayout
        JPanel panelTablero = new JPanel();
        panelTablero.setLayout(new GridLayout(FILAS, COLUMNAS));
        
        // Crear celdas
        for (int fila = 0; fila < FILAS; fila++) {
            for (int col = 0; col < COLUMNAS; col++) {
                JPanel celda = new JPanel();
                celda.setBorder(BorderFactory.createLineBorder(Color.BLACK));

                // Alternar colores (opcional)
                celda.setBackground(Color.WHITE);                

                panelTablero.add(celda);
            }
        }

        add(panelTablero);
        setVisible(true);
    }
}
