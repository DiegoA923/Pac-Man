package udistrital.avanzada.pacman.servidor.Modelo;

/**
 *
 * @author Mauricio
 */
public class JuegoVO {
    private String nombre;
    private int puntaje;
    private double tiempo;

    public JuegoVO(String nombre, int puntaje, double tiempo) {
        this.nombre = nombre;
        this.puntaje = puntaje;
        this.tiempo = tiempo;
    }        

    public String getNombre() {
        return nombre;
    }

    public int getPuntaje() {
        return puntaje;
    }

    public double getTiempo() {
        return tiempo;
    }
    
}
