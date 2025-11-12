package udistrital.avanzada.pacman.servidor.Control;

import udistrital.avanzada.pacman.servidor.Vista.*;

/**
 * Clase de prueba para validar el funcionamiento del tablero.
 * <p>
 * Permite mover a Pac-Man con las flechas y comprobar que se alinea con la
 * cuadrícula, que las frutas se generan correctamente y que el puntaje aumenta
 * al recogerlas.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-11
 */
public class TestJuego {

    public static void main(String[] args) {
        VentanaJuego ventana = new VentanaJuego("Diego");
        ControlJuego control = new ControlJuego(ventana.getPanelJuego(), ventana, null);
        control.generarFrutasAleatorias();

        control.generarFrutasAleatorias();

        ventana.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP ->
                        control.moverPacman("arriba");
                    case java.awt.event.KeyEvent.VK_DOWN ->
                        control.moverPacman("abajo");
                    case java.awt.event.KeyEvent.VK_LEFT ->
                        control.moverPacman("izquierda");
                    case java.awt.event.KeyEvent.VK_RIGHT ->
                        control.moverPacman("derecha");
                }
                ventana.actualizarPuntaje(control.getPuntaje());
            }
        });
        ventana.setFocusable(true);
        ventana.requestFocus();
    }
}
