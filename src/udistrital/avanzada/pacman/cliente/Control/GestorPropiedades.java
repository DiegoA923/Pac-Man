package udistrital.avanzada.pacman.cliente.Control;

import udistrital.avanzada.pacman.cliente.Modelo.DAO.PropertiesDAO;

/**
 *
 * @author Mauricio
 */
public class GestorPropiedades {
    PropertiesDAO propeidadesDAO;
    
    public String get(String key) {
        return propeidadesDAO.getProperty(key);
    }
}
