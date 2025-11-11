package udistrital.avanzada.pacman.servidor.Vista;

import udistrital.avanzada.pacman.servidor.Modelo.FrutaTipo;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.util.List;

/**
 * PanelJuego.
 *
 * <p>
 * Panel visual que dibuja el tablero (cuadrícula), las frutas y a Pac-Man.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-11
 */
public class PanelJuego extends JPanel {

    // Constantes del tablero
    private static final int TAM_CELDA = 40;      // px por celda
    private static final int ANCHO = 800;         // ancho total tablero (px)
    private static final int ALTO = 640;          // alto total tablero (px)
    private static final int TAM_PACMAN = 36;     // tamaño dibujo Pac-Man (px)
    private static final int FRUTA_FONT_SIZE = 28;// tamaño del icono fruta al dibujar

    // Estado de dibujo (valores en píxeles)
    private int pacX, pacY;                         // esquina superior izquierda del sprite Pac-Man
    private String direccion = "derecha";           // orientación visual de Pac-Man
    private List<FrutaTipo> frutas;                 // tipos de frutas a dibujar
    private java.util.List<Point> posicionesFrutas; // posiciones (px) donde dibujar los iconos

    /**
     * Constructor: configura tamaño preferido y sitúa a Pac-Man centrado en la
     * celda central del tablero.
     */
    public PanelJuego() {
        setPreferredSize(new Dimension(ANCHO, ALTO));
        setBackground(Color.BLACK);
        setDoubleBuffered(true);

        // Inicializar Pac-Man centrado en la celda central
        int centroCol = (ANCHO / TAM_CELDA) / 2;
        int centroFila = (ALTO / TAM_CELDA) / 2;
        pacX = centroCol * TAM_CELDA + (TAM_CELDA - TAM_PACMAN) / 2;
        pacY = centroFila * TAM_CELDA + (TAM_CELDA - TAM_PACMAN) / 2;
    }

    /**
     * Actualiza las frutas que deben dibujarse en pantalla.
     *
     * @param frutas lista de tipos de frutas (modelo)
     * @param posiciones lista de posiciones (esquinas superiores en px) donde
     * dibujar cada fruta
     */
    public void setFrutas(List<FrutaTipo> frutas, java.util.List<Point> posiciones) {
        this.frutas = frutas;
        this.posicionesFrutas = posiciones;
        repaint();
    }

    /**
     * Actualiza la orientación visual de Pac-Man (rotacion al moverse).
     *
     * @param direccion "arriba","abajo","izquierda" o "derecha"
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion == null ? "derecha" : direccion.toLowerCase();
        repaint();
    }

    /**
     * Establece la posición en píxeles (esquina superior izquierda del sprite)
     * donde se debe dibujar Pac-Man.
     *
     * @param px coordenada X en píxeles
     * @param py coordenada Y en píxeles
     */
    public void setPacmanPos(int px, int py) {
        this.pacX = px;
        this.pacY = py;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 1) Fondo con gradiente
        GradientPaint fondo = new GradientPaint(0, 0, new Color(12, 12, 40), 0, ALTO, Color.BLACK);
        g2.setPaint(fondo);
        g2.fillRect(0, 0, ANCHO, ALTO);

        // 2) Cuadrícula (referencia visual)
        g2.setColor(new Color(40, 40, 60));
        for (int x = 0; x <= ANCHO; x += TAM_CELDA) {
            g2.drawLine(x, 0, x, ALTO);
        }
        for (int y = 0; y <= ALTO; y += TAM_CELDA) {
            g2.drawLine(0, y, ANCHO, y);
        }

        // 3) Frutas (si hay)
        if (frutas != null && posicionesFrutas != null) {
            drawFrutas(g2);
        }

        // 4) Pac-Man (encima de las frutas)
        drawPacman(g2);
    }

    
    // Dibuja a Pac-Man orientado.
    
    private void drawPacman(Graphics2D g2) {
        // Determinar ángulo boca según dirección
        double startAngle = switch (direccion) {
            case "arriba" ->
                120;
            case "abajo" ->
                300;
            case "izquierda" ->
                210;
            default ->
                30; // derecha
        };

        // Cuerpo
        g2.setColor(Color.YELLOW);
        g2.fill(new Arc2D.Double(pacX, pacY, TAM_PACMAN, TAM_PACMAN, startAngle, 300, Arc2D.PIE));
    }

    /**
     * Dibuja los iconos de las frutas centrados en sus celdas.
     */
    private void drawFrutas(Graphics2D g2) {
        g2.setFont(new Font("Segoe UI Emoji", Font.PLAIN, FRUTA_FONT_SIZE));
        for (int i = 0; i < frutas.size(); i++) {
            FrutaTipo f = frutas.get(i);
            Point p = posicionesFrutas.get(i);

            // sombra
            g2.setColor(new Color(f.getColor().getRed(), f.getColor().getGreen(), f.getColor().getBlue(), 120));
            g2.drawString(f.getIcono(), p.x + 2, p.y + FRUTA_FONT_SIZE + 2);

            // ícono
            g2.setColor(f.getColor());
            g2.drawString(f.getIcono(), p.x, p.y + FRUTA_FONT_SIZE);
        }
    }

    public static int getTamCelda() {
        return TAM_CELDA;
    }

    public static int getAnchoTablero() {
        return ANCHO;
    }

    public static int getAltoTablero() {
        return ALTO;
    }

    public static int getTamPacman() {
        return TAM_PACMAN;
    }

    public static int getFrutaFontSize() {
        return FRUTA_FONT_SIZE;
    }

    public int getPacX() {
        return pacX;
    }

    public int getPacY() {
        return pacY;
    }
}
