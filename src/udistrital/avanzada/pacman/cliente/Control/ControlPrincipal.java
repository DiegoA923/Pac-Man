package udistrital.avanzada.pacman.cliente.Control;

import java.io.File;
import java.io.IOException;
import udistrital.avanzada.pacman.cliente.Modelo.DAO.JugadorPropertiesDAO;
import udistrital.avanzada.pacman.cliente.Modelo.DAO.PropertiesDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Inicializa controladores y se encarga de delegar acciones a los controladores
 * segun se requiera. Punto centra de la aplicacion
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class ControlPrincipal implements MensajeListener {

    private ControlVentana cVentana;
    private ControlJugador cJugador;
    private PropertiesDAO propsDAO;
    private ControlCliente cCliente;

    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);
        this.cJugador = new ControlJugador();
        this.propsDAO = new PropertiesDAO();
        this.cCliente = new ControlCliente();
    }

    /**
     * Metodo para precargar datos desde el archivo de propiedades y verificar
     * que sean validos para el funcionamiento correcto de al app
     */
    public void precargaArchivo() {
        File archivoPropiedades = cVentana.obtenerArchivoPropiedades(
                "specs/data",
                "Seleccione el archivo de propiedades con la configuración"
        );
        //No escogio archivo retornar
        if (archivoPropiedades == null) {
            return;
        }
        //Obtener ruta de archivo
        String ruta = archivoPropiedades.getAbsolutePath();
        //Asignar ruta a DAOs props y DAO jugador
        propsDAO.setConfiguracionConexion(ruta);        
        JugadorPropertiesDAO jpd = new JugadorPropertiesDAO();        
        jpd.setConfiguracionConexion(ruta);
        //Configurar DAO de jugador
        cJugador.setJugadorDAO(jpd);
        try {
            boolean usuarioValido = cJugador.cargarJugador();
            int puerto = -1;
            try {
                puerto = Integer.parseInt(propsDAO.get("servidor.puerto"));
            } catch (Exception e) {
            }
            String ip = propsDAO.get("servidor.ip");
            //Propiedades no validas retornar
            if (!usuarioValido || ip == null || puerto < 0) {
                cJugador.resetJugador();
                cCliente.reset();
                cVentana.mostrarMensajeInformativo("Info", "Archivo no tiene configuracion requerida");
                return;
            }
            //No se pudo conectar al servidor 
            if (!cCliente.conectar(ip, puerto, this)) {
                cVentana.mostrarMensajeInformativo("Info", "No se pudo realizar la conexion al servidor");
                return;
            }
            
        } catch (Exception e) {
            // en caso de error volver a estado inicial controladores
            cJugador.resetJugador();
            cCliente.reset();
        }
    }

    /**
     * Metodo para enviar un movimiento al servidor
     *
     * @param mensaje
     */
    public void enviarMovimiento(String mensaje) {
        try {
            cCliente.enviarMensaje(Comando.MOVER.name(), mensaje);
        } catch (IOException ex) {

        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public synchronized void procesarMensaje(String tipo, String mensaje) {
        try {
            Comando cmd = Comando.valueOf(tipo.toUpperCase());
            switch (cmd) {
                //Resultado de movimiento
                case Comando.RESULTADO_MOVIMIENTO:
                    cVentana.agregarResultadoJuego(mensaje);
                    break;
                //Fin juego
                case Comando.FIN_JUEGO:
                    cVentana.agregarResultadoJuego(mensaje);
                    cCliente.cerrarConexion();
                    break;

                //Enviar datos de cliente para autentificar si es solicitado por el servidor
                case Comando.AUTENTIFICACION:
                    String[] datosJugador = cJugador.getDatosJugador();
                    cCliente.enviarMensajeString(datosJugador[0]);
                    cCliente.enviarMensajeString(datosJugador[1]);
                    break;
                //Resultado autentificacion    
                case Comando.RESULTADO_AUTENTIFICACION:
                    if (mensaje.equalsIgnoreCase("exito")) {
                        //Si autentificacion exitosa mostrar ventana para jugar    
                        cVentana.mostrarPanelComando();
                    } else {
                        //Si no mostra que hubo error
                        cVentana.mostrarMensajeInformativo("Info", "Autentificacion fallida");
                        cCliente.cerrarConexion();
                    }
                    break;
                //cerrar conexion si no se ha cerrado correctamente    
                case Comando.CERRAR_CONEXION:
                    cVentana.mostrarMensajeInformativo("Info", mensaje);
                    cCliente.cerrarConexion();
                    break;
                //Si se cerro la conexion por algun error    
                case Comando.CONEXION_INTERRUMPIDA:
                    cVentana.mostrarMensajeInformativo("Info", mensaje);
                    cCliente.cerrarConexion();
                    break;
            }
        } catch (Exception e) {
            //comado no existe en las constantes
            return;
        }

    }

}
