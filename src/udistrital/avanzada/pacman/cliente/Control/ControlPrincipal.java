package udistrital.avanzada.pacman.cliente.Control;

import java.io.File;
import java.io.IOException;
import javax.swing.SwingUtilities;
import udistrital.avanzada.pacman.cliente.Modelo.Conexion.ConexionProperties;
import udistrital.avanzada.pacman.cliente.Modelo.DAO.JugadorPropertiesDAO;
import udistrital.avanzada.pacman.cliente.Modelo.DAO.PropertiesDAO;
import udistrital.avanzada.pacman.cliente.Modelo.DAO.IPropertiesDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Punto central de inicialización de la aplicación. Inicializa controladores y
 * se encarga de delegar segun se requiera.
 * </p>
 *
 * @author Mauricio
 * @version 1.0
 * @since 2025-11-05
 */
public class ControlPrincipal implements MensajeListener {

    private ControlVentana cVentana;
    private ControlJugador cJugador;
    private IPropertiesDAO propsDAO;
    private ControlCliente cCliente;

    /**
     * Constructor por defecto.
     */
    public ControlPrincipal() {
        this.cVentana = new ControlVentana(this);
        this.cJugador = new ControlJugador();
        this.propsDAO = new PropertiesDAO(new ConexionProperties());
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
            int puerto = -1;
            try {
                puerto = Integer.parseInt(propsDAO.get("servidor.puerto"));
            } catch (Exception e) {
            }
            String ip = propsDAO.get("servidor.ip");
            //Propiedades no validas retornar
            if (ip == null || puerto < 0) {
                cCliente.reset();
                cVentana.mostrarMensajeInformativo("Info", "Archivo no tiene configuración de conexión válida");
                return;
            }

            //No se pudo conectar al servidor 
            if (!cCliente.conectar(ip, puerto, this)) {
                cVentana.mostrarMensajeInformativo("Info", "No se pudo realizar la conexion al servidor");
                return;
            }

            SwingUtilities.invokeLater(() -> cVentana.mostrarPanelLogin());

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
     * Envía las credenciales de inicio de sesión al servidor.
     * <p>
     * El servidor espera recibir el comando <b>AUTENTIFICACION</b>, seguido de
     * dos mensajes consecutivos: el nombre de usuario y la contraseña. Este
     * método cumple con ese protocolo de comunicación.
     * </p>
     *
     * @param usuario nombre de usuario del cliente
     * @param password contraseña del usuario
     */
    public void enviarLogin(String usuario, String password) {
        try {
            // Enviar comando principal para indicar autenticación
            cCliente.enviarMensajeString("AUTENTIFICACION");

            // Enviar usuario y contraseña en mensajes separados
            cCliente.enviarMensajeString(usuario);
            cCliente.enviarMensajeString(password);

        } catch (IOException e) {
            cVentana.mostrarMensajeInformativo("Error",
                    "No se pudo enviar la autenticación al servidor.\n" + e.getMessage());
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
                    SwingUtilities.invokeLater(() -> cVentana.agregarResultadoJuego(mensaje));
                    break;
                //Fin juego
                case Comando.FIN_JUEGO:
                    SwingUtilities.invokeLater(() -> cVentana.agregarResultadoJuego(mensaje));
                    cCliente.cerrarConexion();
                    break;

                //Resultado autentificacion    
                case Comando.RESULTADO_AUTENTIFICACION:
                    if (mensaje.equalsIgnoreCase("exito")) {
                        // Autenticación exitosa
                        SwingUtilities.invokeLater(() -> {
                            cVentana.mostrarMensajeInformativo("Inicio de sesión",
                                    "¡Inicio de sesión exitoso! Bienvenido al juego.");
                            cVentana.mostrarPanelComando();
                        });
                    } else {
                        // Autenticación fallida: informar al usuario y cerrar recursos
                        SwingUtilities.invokeLater(() -> {
                            cVentana.mostrarMensajeInformativo("Error de autenticación",
                                    "Usuario o contraseña incorrectos.");
                        });

                        // Cierra los streams y sockets del cliente
                        cCliente.cerrarConexion();
                    }
                    break;

                //cerrar conexion si no se ha cerrado correctamente    
                case Comando.CERRAR_CONEXION:
                    SwingUtilities.invokeLater(() -> cVentana.agregarResultadoJuego(mensaje));
                    cCliente.cerrarConexion();
                    break;
                //Si se cerro la conexion por algun error    
                case Comando.CONEXION_INTERRUMPIDA:
                    SwingUtilities.invokeLater(() -> cVentana.mostrarMensajeInformativo("Info", mensaje));
                    //asegura cierre
                    cCliente.cerrarConexion();
                    break;
            }
        } catch (Exception e) {
            //comado no existe en las constantes
            return;
        }

    }

}
