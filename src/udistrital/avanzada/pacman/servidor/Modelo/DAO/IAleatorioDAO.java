package udistrital.avanzada.pacman.servidor.Modelo.DAO;


/**
 *
 * @author mauri
 */
public interface IAleatorioDAO {
    public void insertarJuego(String nombre, int puntos, double tiempo);
    public void setArchivoAleatorio(String ruta);
    public String[] getMejorJuego();
    public boolean conexionValida();
}
