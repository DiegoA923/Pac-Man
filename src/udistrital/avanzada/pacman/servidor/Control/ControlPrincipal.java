package udistrital.avanzada.pacman.servidor.Control;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import jdk.jshell.spi.ExecutionControl.RunException;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.ConexionAleatorio;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.ConexionBD;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.ConexionProperties;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.AleatorioDAO;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.JugadorDAO;

/**
 * Clase ControlPrincipal.
 * <p>
 * Descripción:
 * </p>
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-05
 */
public class ControlPrincipal implements ConexionListener {

    //private PropertiesDAO propsDAO;
    private ControlVentana cVentana;
    private ControlServidorHilo cServidorHilo;
    private Servidor servidor;
    private GestorArchivoAleatorio gAleatorio;
    private GestorArchivoProperties gPropiedades;

    /**
     * Constructor por defecto.
     */
    public ControlPrincipal() {
        //this.propsDAO = new PropertiesDAO();
        this.cVentana = new ControlVentana(this);
        this.gPropiedades = new GestorArchivoProperties();
        preCarga();
    }

    /**
     * Metodo para la comprobacion y configuracion de los controladores
     */
    public void preCarga() {
        File archivoPropiedades = cVentana.obtenerArchivoPropiedades(
                "specs/data",
                "Seleccione el archivo de propiedades con la configuración"
        );
        //No escogio archivo o no carga retornar
        if (archivoPropiedades == null || !gPropiedades.cargar(archivoPropiedades)) {
            return;
        }

        //Configurar Gestor de archivo aleatorio
        ConexionAleatorio conexionAleatorio = new ConexionAleatorio();
        //ruta donde se guarda debe ser escogida por usuario
        String carpeta = "specs/data/";
        conexionAleatorio.setRuta(carpeta + "juegos.txt");
        AleatorioDAO aleatorioDAO = new AleatorioDAO(conexionAleatorio);
        //Si no exite la carpeta donde esta el archivo aleatorio retornar
        if (!aleatorioDAO.conexionValida()) {
            cVentana.mostrarMensajeConsola("Carpeta del archivo aleatorio no existe");
            return;
        }
        this.gAleatorio = new GestorArchivoAleatorio(aleatorioDAO);

        //Configurar conexion a base de datos y controladores que la utilizen
        ConexionProperties conexionProps = new ConexionProperties(archivoPropiedades.getAbsolutePath());
        //pasamos por inyeccion la conexion de propiedades 
        ConexionBD conexionBD = new ConexionBD(conexionProps);

        //Comprobar conexion a base de datos si no hay retornar
        try (Connection conn = conexionBD.getConexion()) {
            if (conn == null || conn.isClosed()) {
                cVentana.mostrarMensajeEmergente("Info","No se pudo establecer conexion con la base de datos.");
                cVentana.mostrarMensajeConsola("No se pudo establecer conexion con la base de datos.");
                return;
            }
        } catch (Exception e) {
            cVentana.mostrarMensajeEmergente("Info","Error en la conexion a base de datos");
            cVentana.mostrarMensajeConsola("Error en la conexion a base de datos: " + e.getMessage());
            return;
        } finally {
            conexionBD.desconectar();
        }
        //Configurara conexion a controlador que la usa
        JugadorDAO jugadorDAO = new JugadorDAO(conexionBD);
        ControlJugador cJugadores = new ControlJugador(jugadorDAO);

        //Insertar jugadores que hayan en el archivo de propiedades
        try {
            int nJugadores = Integer.parseInt(gPropiedades.getProperty("jugadores.cantidad"));
            if (nJugadores > 0) {
                for (int i = 1; i <= nJugadores; i++) {
                    String usuario = gPropiedades.getProperty("jugador" + i + ".usuario");
                    String password = gPropiedades.getProperty("jugador" + i + ".password");
                    if (usuario != null && password != null) {
                        cJugadores.insertarJugador(usuario, password);
                    }
                }
            }
        } catch (Exception e) {
        }

        //Levantar servidor de escucha
        //Configurar controlador de hilos
        this.cServidorHilo = new ControlServidorHilo(this, gAleatorio, cJugadores);
        this.servidor = new Servidor(cServidorHilo);
        int puerto = -1;
        try {
            puerto = Integer.parseInt(gPropiedades.getProperty("server.port"));
        } catch (Exception e) {
        }

        servidor.config(puerto);
        if (!servidor.levantarServidor()) {
            cVentana.mostrarMensajeEmergente("Info", "Puerto " + puerto + " ya usado");
            return;
        }
        cVentana.mostrarMensajeConsola("escuchando en puerto: " + puerto);
        servidor.start();
        //Despues de asegurar la conexion a BD y levantamiento del servidor mostrar ventana
        //Se hace necesario para poder cerrar el servidor cuando esta activo por primera vez y nadie
        //se va a conectar
        //Otro modo es por medio de consola preguntar si acabar servidor
        cVentana.mostrarVentana(true);
        cVentana.mostrarBtnSalir(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onConexion(int cantidad) {
        if (cantidad == 0) {
            //mostrar ventana cuando hay 0 conectados al servidor
            cVentana.mostrarVentana(true);
        } else {
            //ocultar ventana cuando hay mas de 1 persona conectada al servidor
            cVentana.mostrarVentana(false);
        }
        cVentana.mostrarMensajelbl("Conectados: " + cantidad);
    }

    /**
     * Metodo para salir de la aplicacion de manera controlada
     */
    public void salir() {
        //Mostrar Mejor Jugador ejemplo                
        String[] mejor = gAleatorio.getMejorJuego();
        if (mejor == null) {
            cVentana.mostrarMensajeEmergente("INFO", "Error al conectar archivo aleatorio");
        } else if (mejor.length == 0) {
            cVentana.mostrarMensajeEmergente("Ganador", "No hay registros aun");
        } else {
            cVentana.mostrarMensajeEmergente(
                    "Ganador",
                    "El mejor es " + mejor[0] + " con " + mejor[1] + " puntos en " + mejor[2] + " segundos"
            );
            cVentana.mostrarMensajeConsola("El mejor es nombre");
        }

        //Cerrar conexiones activas
        servidor.cerrarServidor();
        //Cerrar servidor levantado
        cServidorHilo.cerrarConexiones();
    }
}
