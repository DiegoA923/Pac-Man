package udistrital.avanzada.pacman.servidor.Modelo.DAO;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import udistrital.avanzada.pacman.servidor.Control.GestorArchivoProperties;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * Implementación de {@link IPropertiesDAO} que obtiene información del archivo
 * servidor.properties a través de {@link GestorArchivoProperties}.
 *
 * <p>
 * Separa la lógica de carga del archivo (control) del acceso a los datos
 * (modelo), asegurando bajo acoplamiento y claridad de responsabilidades.
 * </p>
 *
 * @author Diego
 * @version 1.1
 * @since 2025-11-08
 */
public class PropertiesDAO implements IPropertiesDAO {

    private final GestorArchivoProperties gestor;

    /**
     * Constructor principal.
     *
     * @param gestor instancia de {@link GestorArchivoProperties} ya cargada.
     */
    public PropertiesDAO(GestorArchivoProperties gestor) {
        this.gestor = gestor;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getProperty(String clave) {
        return gestor.getProperty(clave);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<JugadorVO> cargarJugadoresIniciales() {
        Properties props = gestor.getPropiedades();
        List<JugadorVO> jugadores = new ArrayList<>();

        try {
            int cantidad = Integer.parseInt(props.getProperty("jugadores.cantidad", "0"));

            for (int i = 1; i <= cantidad; i++) {
                String usuario = props.getProperty("jugador" + i + ".usuario");
                String password = props.getProperty("jugador" + i + ".password");

                if (usuario != null && password != null) {
                    jugadores.add(new JugadorVO(0, usuario, password));
                }
            }
        } catch (NumberFormatException e) {
            
        }

        return jugadores;
    }
}
