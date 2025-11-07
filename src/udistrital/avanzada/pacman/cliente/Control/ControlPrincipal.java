package udistrital.avanzada.pacman.cliente.Control;

import java.io.File;
import udistrital.avanzada.pacman.cliente.Modelo.DAO.PropertiesDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class ControlPrincipal {
    private ControlVentana cVentana;
    private ControlJugador cJugador;
    private PropertiesDAO propsDAO;
    private Cliente cliente;

    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);
        this.cJugador = new ControlJugador();
        this.propsDAO = new PropertiesDAO();
        this.cliente = new Cliente(cJugador, cVentana);
        precargaArchivo();
    }    
    
    public void precargaArchivo() {
        File archivoPropiedades = cVentana.obtenerArchivoPropiedades(
            "specs/data",
            "Seleccione el archivo de propiedades con la configuración"
        );
        if (archivoPropiedades == null) { 
            return;
        }
        String ruta = archivoPropiedades.getAbsolutePath();                
        propsDAO.setConfiguracionConexion(ruta);
        cJugador.setJugadorDAO(propsDAO);        
        try {
            boolean usuarioValido = cJugador.cargarJugador();            
            String[] server = propsDAO.getServer();
            try {
                cliente.configSocket(server[0], Integer.parseInt(server[1]));
            } catch (Exception e) {
            }
            
            //Propiedades no existen retornar
            if(!usuarioValido || cliente.getIp() == null ||  cliente.getPuerto() < 0) {
                cJugador.resetJugador();
                cliente.resetConfig();
                cVentana.mostrarMensajeInformativo("Info", "Archivo no tiene configuracion requerida");
                return;
            }
            if(!cliente.conectar()){
                cVentana.mostrarMensajeInformativo("Info", "No se pudo realizar la conexion al servidor");
            };
        } catch (Exception e) {
            cJugador.resetJugador();
            cliente.resetConfig();
        }
    }
    
    public void conectarAServer() {        
        cliente.conectar();
    }
}
