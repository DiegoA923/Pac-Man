package udistrital.avanzada.pacman.servidor.Modelo;

import java.awt.Color;

/**
 * Enum FrutaTipo.
 * <p>
 * Representa los diferentes ítems o frutas del juego Pac-Man, cada uno con su
 * respectivo ícono visual, color y valor en puntos.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-11
 */
public enum FrutaTipo {

    
    // Cereza: valor de 100 puntos.
     
    CEREZA("🍒", new Color(255, 0, 0), 100),
    
    // Fresa: valor de 300 puntos.
    
    FRESA("🍓", new Color(255, 80, 120), 300),
    
    // Naranja: valor de 500 puntos.
     
    NARANJA("🍊", new Color(255, 140, 0), 500),
    
    // Manzana: valor de 700 puntos.
     
    MANZANA("🍎", new Color(220, 20, 60), 700),
    
    // Melón: valor de 1000 puntos.
     
    MELON("🍉", new Color(0, 200, 100), 1000),
    
    // Galaxian: valor de 2000 puntos.
    
    GALAXIAN("🛸", new Color(100, 180, 255), 2000),
    
    // Campana: valor de 3000 puntos.
    
    CAMPANA("🔔", new Color(255, 255, 0), 3000),
    
    // Llave: valor de 5000 puntos.
    
    LLAVE("🔑", new Color(255, 215, 0), 5000);

    private final String icono;
    private final Color color;
    private final int puntos;

    /**
     * Constructor del tipo de fruta.
     *
     * @param icono emoji representativo del ítem
     * @param color color principal de la fruta (para resaltarla visualmente)
     * @param puntos valor en puntos que otorga al jugador
     */
    FrutaTipo(String icono, Color color, int puntos) {
        this.icono = icono;
        this.color = color;
        this.puntos = puntos;
    }

    /**
     * @return el emoji que representa la fruta en pantalla
     */
    public String getIcono() {
        return icono;
    }

    /**
     * @return el color que se usará al dibujar la fruta
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return el valor en puntos que otorga esta fruta
     */
    public int getPuntos() {
        return puntos;
    }
}
