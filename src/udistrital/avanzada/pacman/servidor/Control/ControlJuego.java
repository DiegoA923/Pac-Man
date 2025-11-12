package udistrital.avanzada.pacman.servidor.Control;

import udistrital.avanzada.pacman.servidor.Modelo.FrutaTipo;
import udistrital.avanzada.pacman.servidor.Vista.PanelJuego;
import udistrital.avanzada.pacman.servidor.Vista.VentanaJuego;

import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * ControlJuego.
 *
 * <p>
 * Controlador responsable de la lógica del juego:
 * <ul>
 * <li>Genera frutas alineadas a la cuadrícula</li>
 * <li>Mueve al jugador (Pac-Man) celda por celda</li>
 * <li>Detecta colisiones y acumula puntaje</li>
 * <li>Mide el tiempo transcurrido</li>
 * <li>Comunica actualizaciones a la vista (PanelJuego y VentanaJuego)</li>
 * </ul>
 * </p>
 *
 * <p>
 * Esta clase no dibuja, solo calcula posiciones y comunica los resultados a la
 * vista.
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-11
 */
public class ControlJuego extends WindowAdapter {

    // Referencias a la vista
    private final PanelJuego panel;
    private final VentanaJuego ventana;
    
    // Configuración base
    private final Random random;
    private final int tamCelda;
    private final int ancho;
    private final int alto;

    // Estado del juego
    private int pacCol;   // columna de Pac-Man
    private int pacFila;  // fila de Pac-Man
    private int puntaje;
    private int segundos;

    // Elementos del juego
    private final List<FrutaTipo> frutas;
    private final List<Point> posiciones;

    // Constantes
    private static final int PASO_CASILLAS = 4;

    private Timer temporizador;
    
    private CerrarVentanaListener cerrarListener;

    /**
     * Crea un controlador asociado a la vista PanelJuego y a la VentanaJuego.
     *
     * @param panel vista donde se pintará el tablero
     * @param ventana vista principal para actualizar el tiempo y puntaje
     * @param clistener
     */
    public ControlJuego(PanelJuego panel, VentanaJuego ventana, CerrarVentanaListener clistener) {
        this.random = new Random();
        this.puntaje = 0;
        this.segundos = 0;
        this.frutas = new ArrayList<>();
        this.posiciones = new ArrayList<>();
        this.cerrarListener = clistener;
        this.panel = panel;
        this.ventana = ventana;
        this.ventana.addWindowsListener(this);

        this.tamCelda = PanelJuego.getTamCelda();
        this.ancho = PanelJuego.getAnchoTablero();
        this.alto = PanelJuego.getAltoTablero();

        // Ubicar Pac-Man en el centro del tablero (en celdas)
        int columnas = ancho / tamCelda;
        int filas = alto / tamCelda;
        this.pacCol = columnas / 2;
        this.pacFila = filas / 2;

        // Mostrar posición inicial en pantalla
        actualizarPosicionEnVista();

        // Iniciar conteo de tiempo
        iniciarTemporizador();
    }

    /**
     * Genera 4 frutas aleatorias en distintas celdas del tablero. Evita colocar
     * frutas en la celda central o duplicadas.
     */
    public void generarFrutasAleatorias() {
        frutas.clear();
        posiciones.clear();

        int columnas = ancho / tamCelda;
        int filas = alto / tamCelda;
        Set<Point> usadas = new HashSet<>();

        FrutaTipo[] tipos = FrutaTipo.values();

        while (frutas.size() < 4) {
            int col = random.nextInt(columnas);
            int fila = random.nextInt(filas);

            // Evitar centro
            if (col == pacCol && fila == pacFila) {
                continue;
            }

            Point celda = new Point(col, fila);
            if (!usadas.add(celda)) {
                continue; // evitar duplicados
            }
            // Elegir tipo de fruta aleatoria
            FrutaTipo elegido = tipos[random.nextInt(tipos.length)];
            frutas.add(elegido);

            // Coordenadas visuales centradas en la celda
            int offset = (tamCelda - 32) / 2;
            int px = col * tamCelda + offset - 4;
            int py = fila * tamCelda + offset - 3;
            posiciones.add(new Point(px, py));
        }

        // Notificar a la vista
        panel.setFrutas(List.copyOf(frutas), List.copyOf(posiciones));
    }

    /**
     * Mueve a Pac-Man PASO_CASILLAS celdas en la dirección indicada. Revisa
     * colisiones en cada celda intermedia.
     *
     * @param direccion "arriba", "abajo", "izquierda" o "derecha"
     * @return # casillas que se movio, -1 si el movimiento no es valido
     */
    public int moverPacman(String direccion) {
        direccion = direccion == null ? "derecha" : direccion.toLowerCase();
        int casillasMovidas = 0;
        for (int i = 0; i < PASO_CASILLAS; i++) {
            int nuevaCol = pacCol;
            int nuevaFila = pacFila;

            switch (direccion) {
                case "arriba" ->
                    nuevaFila = Math.max(0, pacFila - 1);
                case "abajo" ->
                    nuevaFila = Math.min((alto / tamCelda) - 1, pacFila + 1);
                case "izquierda" ->
                    nuevaCol = Math.max(0, pacCol - 1);
                case "derecha" ->
                    nuevaCol = Math.min((ancho / tamCelda) - 1, pacCol + 1);
                default ->
                    casillasMovidas = -1;
            }

            if (nuevaCol == pacCol && nuevaFila == pacFila) {
                break;
            }
            casillasMovidas += 1;
            // Actualizar posición lógica
            pacCol = nuevaCol;
            pacFila = nuevaFila;

            // Detectar si hay fruta en esta celda
            verificarColisionEnCelda(pacCol, pacFila);

            // Actualizar posición visual
            actualizarPosicionEnVista();
        }

        panel.setDireccion(direccion);
        return casillasMovidas;
    }

    /**
     * Detecta si Pac-Man colisiona con una fruta en la celda actual. Si la hay,
     * la elimina y suma los puntos correspondientes.
     *
     * @param col columna actual
     * @param fila fila actual
     */
    private void verificarColisionEnCelda(int col, int fila) {
        Rectangle celdaRect = new Rectangle(col * tamCelda, fila * tamCelda, tamCelda, tamCelda);

        for (int i = frutas.size() - 1; i >= 0; i--) {
            Point p = posiciones.get(i);
            int frutaCol = p.x / tamCelda;
            int frutaFila = p.y / tamCelda;
            Rectangle frutaRect = new Rectangle(frutaCol * tamCelda, frutaFila * tamCelda, tamCelda, tamCelda);

            if (celdaRect.intersects(frutaRect)) {
                puntaje += frutas.get(i).getPuntos();
                frutas.remove(i);
                posiciones.remove(i);
                ventana.actualizarPuntaje(puntaje);
            }
        }

        panel.setFrutas(List.copyOf(frutas), List.copyOf(posiciones));
    }

    /**
     * Inicia un temporizador que incrementa el tiempo cada segundo y lo
     * actualiza en la vista principal.
     */
    private void iniciarTemporizador() {
        temporizador = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                segundos++;
                ventana.actualizarTiempo(segundos);
            }
        });
        temporizador.start();
    }

    /**
     * Actualiza la posición visual de Pac-Man en el panel de juego.
     */
    private void actualizarPosicionEnVista() {
        int px = pacCol * tamCelda + (tamCelda - PanelJuego.getTamPacman()) / 2;
        int py = pacFila * tamCelda + (tamCelda - PanelJuego.getTamPacman()) / 2;
        panel.setPacmanPos(px, py);
    }

    public int getPuntaje() {
        return puntaje;
    }

    public List<FrutaTipo> getFrutas() {
        return List.copyOf(frutas);
    }

    public List<Point> getPosiciones() {
        return List.copyOf(posiciones);
    }
    
    public void cerrarVentana() {
        if (ventana != null) {            
            ventana.dispose();            
        }
        if (cerrarListener != null) {
            cerrarListener.notificar();
        }
    }
    
    public int getFrutasActuales() {
        return frutas.size();
    }
    
    /**
     * Genera 4 frutas aleatorias en distintas celdas del tablero. Evita colocar
     * frutas en la celda central o duplicadas.
     */
    public void generarFrutasAleatoriasPrueba() {
        frutas.clear();
        posiciones.clear();

        int columnas = ancho / tamCelda;
        int filas = alto / tamCelda;
        Set<Point> usadas = new HashSet<>();

        FrutaTipo[] tipos = FrutaTipo.values();
        int indice = 1;
        while (frutas.size() < 4) {
            int col = pacCol+indice;
            indice +=1;
            int fila = pacFila;

            // Evitar centro
            if (col == pacCol && fila == pacFila) {
                continue;
            }

            Point celda = new Point(col, fila);
            if (!usadas.add(celda)) {
                continue; // evitar duplicados
            }
            // Elegir tipo de fruta aleatoria
            FrutaTipo elegido = tipos[random.nextInt(tipos.length)];
            frutas.add(elegido);

            // Coordenadas visuales centradas en la celda
            int offset = (tamCelda - 32) / 2;
            int px = col * tamCelda + offset - 4;
            int py = fila * tamCelda + offset - 3;
            posiciones.add(new Point(px, py));
        }
    }
    
    @Override
    public void windowClosing(WindowEvent e) {
        cerrarVentana();
    }
}
