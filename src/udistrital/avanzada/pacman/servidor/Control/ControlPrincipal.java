package udistrital.avanzada.pacman.servidor.Control;

import java.io.File;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.ConexionAleatorio;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.AleatorioDAO;

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

    public ControlPrincipal() {
        //this.propsDAO = new PropertiesDAO();
        this.cVentana = new ControlVentana(this); 
        this.gAleatorio = new GestorArchivoAleatorio(new AleatorioDAO(new ConexionAleatorio()));
        this.cServidorHilo = new ControlServidorHilo(this, gAleatorio);
        this.servidor = new  Servidor(cServidorHilo);   
        preCarga();
    }

    public void preCarga() {
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
        //ruta debe venir de archivo propiedades
        gAleatorio.setArchivoAleatorio("specs/data/juegos.txt");
        
        
        int puerto = 5000;
        servidor.config(puerto);
        if (!servidor.levantarServidor()) {
            cVentana.mostrarMensajeEmergente("Info", "Puerto "+puerto+" ya usado");
            return;
        }
        cVentana.mostrarMensajeConsola("escuchando en puerto: "+ puerto);
        servidor.start();
        
        
                
        //Despues de asegurar la conexion a BD y levantamiento del servidor mostrar ventana
        //Se hace necesario para poder cerrar el servidor cuando esta activo por primera vez y nadie
        //se va a conectar
        //Otro modo es por medio de consola preguntar si acabar servidor
        cVentana.mostrarVentana(true);
        cVentana.mostrarBtnSalir(true);
        //-------------------------
        //Asignar ruta a DAOs props y DAO jugador
//        propsDAO.setConfiguracionConexion(ruta);
//
//        String urlDB = propsDAO.get("db.url");
//        String userDB = propsDAO.get("db.user");
//        String passwordDB = propsDAO.get("db.password");
//
//        int puertoServer = -1;
//        try {
//            puertoServer = Integer.parseInt(propsDAO.get("server.puerto"));
//        } catch (Exception e) {
//        }
//        if (urlDB == null || userDB == null || passwordDB == null) {
//            cVentana.mostrarMensajeEmergente("Info", "Archivo no tiene configuracion de Base de datos");
//            return;
//        }
//        if (puertoServer < 0) {
//            cVentana.mostrarMensajeEmergente("Info", "Archivo no tiene configuracion de servidor");
//            return;
//        }
//        //Configurar Sevidor
//
//        //Check si el servidor
//        boolean servidorLevantado = true;
//
//        if (!servidorLevantado) {
//            cVentana.mostrarMensajeEmergente("Info", "No se pudo levantar el servidor");
//            return;
//        }
//
//        //Configurar base de datos
//        //Check si se esta conectado a la DB
//        boolean conectadoBD = true;
//        if (!conectadoBD) {
//            //cerrar servidor levantado
//            cVentana.mostrarMensajeEmergente("Info", "No se pudo conectar al Base de Datos");
//            return;
//        }
//
//        int nJugadores = -1;
//        try {
//            nJugadores = Integer.parseInt(propsDAO.get("cantidadUsuarios"));
//        } catch (Exception e) {
//        }
//        if (nJugadores > 0) {
//            for (int i = 1; i <= nJugadores; i++) {
//                String nombre = propsDAO.get("usuario" + i + ".name");
//                String contrasena = propsDAO.get("usuario" + i + ".contrasena");
//                if (nombre != null && contrasena != null) {
//                    // Llamar al controlador jugador para crear en DB                    
//                }
//            }
//        }

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
       cVentana.mostrarMensajelbl("Conectados: "+cantidad);
    }
    
    /**
     * Metodo para salir de la aplicacion de manera controlada
     */
    public void salir() {    
        //Mostrar Mejor Jugador ejemplo                
        String[] mejor = gAleatorio.getMejorJuego();
        if (mejor == null) {
            cVentana.mostrarMensajeEmergente("INFO","Error al conectar archivo aleatorio");
        } else if (mejor.length == 0) {
            cVentana.mostrarMensajeEmergente("Ganador","No hay registros aun");            
        } else {
            cVentana.mostrarMensajeEmergente(
                    "Ganador",
                    "El mejor es "+mejor[0]+" con "+ mejor[1]+" puntos en "+mejor[2]+" segundos"
            );
            cVentana.mostrarMensajeConsola("El mejor es nombre");
        }
                
        //Cerrar conexiones activas
        servidor.cerrarServidor();
        //Cerrar servidor levantado
        cServidorHilo.cerrarConexiones();               
    }
}
