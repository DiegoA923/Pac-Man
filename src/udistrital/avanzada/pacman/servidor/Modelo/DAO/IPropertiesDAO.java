package udistrital.avanzada.pacman.servidor.Modelo.DAO;

import java.util.List;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;

/**
 * Define el contrato de acceso a los datos del archivo servidor.properties.
 * <p>
 * Permite abstraer la obtención de propiedades de configuración generales 
 * y de la lista inicial de jugadores a registrar en la base de datos.
 * </p>
 * 
 * @author Diego
 * @version 1.0
 * @since 2025-11-08
 */
public interface IPropertiesDAO {

    /**
     * Obtiene el valor de una propiedad específica.
     * 
     * @param clave clave de la propiedad dentro del archivo.
     * @return valor asociado o {@code null} si no existe.
     */
    String getProperty(String clave);

    /**
     * Carga la lista de jugadores iniciales definidos en el archivo 
     * servidor.properties.
     * 
     * @return lista de objetos {@link JugadorVO}.
     */
    List<JugadorVO> cargarJugadoresIniciales();
}
