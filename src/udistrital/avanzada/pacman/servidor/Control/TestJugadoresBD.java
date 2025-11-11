package udistrital.avanzada.pacman.servidor.Control;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;
import javax.swing.SwingUtilities;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.ConexionBD;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.ConexionProperties;
import udistrital.avanzada.pacman.servidor.Modelo.Conexion.IConexionProperties;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.IPropertiesDAO;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.JugadorDAO;
import udistrital.avanzada.pacman.servidor.Modelo.DAO.PropertiesDAO;
import udistrital.avanzada.pacman.servidor.Modelo.JugadorVO;
import udistrital.avanzada.pacman.servidor.Vista.VentanaJuego;

/**
 * Clase de prueba para validar conexión, inserción y autenticación de jugadores.
 * Si el jugador se valida correctamente, se abre la ventana del juego Pac-Man.
 * 
 * @author Diego
 * @version 2.0
 * @since 2025-11-11
 */
public class TestJugadoresBD {

    public static void main(String[] args) {
        System.out.println("Prueba de carga, inserción y validación de jugadores \n");

        // 1️⃣ Cargar archivo de propiedades
        GestorArchivoProperties gestor = new GestorArchivoProperties();
        if (!gestor.cargar()) {
            System.err.println("No se pudo cargar el archivo de propiedades.");
            return;
        }

        // 2️⃣ Crear DAO para leer jugadores iniciales
        IPropertiesDAO propertiesDAO = new PropertiesDAO(gestor);
        List<JugadorVO> jugadores = propertiesDAO.cargarJugadoresIniciales();

        if (jugadores.isEmpty()) {
            System.out.println("No se encontraron jugadores en el archivo.");
            return;
        }

        // 3️⃣ Conectarse a la base de datos
        IConexionProperties conexionProps = new ConexionProperties(
                gestor.getArchivoSeleccionado().getAbsolutePath());
        ConexionBD conexionBD = new ConexionBD(conexionProps);
        JugadorDAO jugadorDAO = new JugadorDAO(conexionBD);

        try (Connection conn = conexionBD.getConexion()) {
            if (conn == null || conn.isClosed()) {
                System.err.println("No se pudo establecer conexión con la base de datos.");
                return;
            }

            System.out.println("Conexión exitosa con la base de datos.\n");

            // 4️⃣ Insertar jugadores iniciales si no existen
            System.out.println("--- Inserción de jugadores ---");
            for (JugadorVO jugador : jugadores) {
                if (jugadorDAO.existeJugador(jugador.getNombreUsuario())) {
                    System.out.println(jugador.getNombreUsuario() + " ya existe en la base de datos.");
                } else {
                    if (jugadorDAO.insertarJugador(jugador)) {
                        System.out.println("➕ " + jugador.getNombreUsuario() + " insertado correctamente.");
                    } else {
                        System.out.println("No se pudo insertar " + jugador.getNombreUsuario());
                    }
                }
            }

            // 5️⃣ Validar inicio de sesión
            System.out.println("\n--- Inicio de sesión ---");
            Scanner sc = new Scanner(System.in);

            System.out.print("Ingrese su usuario: ");
            String usuario = sc.nextLine().trim();

            System.out.print("Ingrese su contraseña: ");
            String password = sc.nextLine().trim();

            boolean valido = jugadorDAO.validarJugador(usuario, password);
            if (valido) {
                System.out.println("Inicio de sesión exitoso. ¡Bienvenido, " + usuario + "!");

                // 🪟 Mostrar ventana del juego en el hilo gráfico de Swing
                SwingUtilities.invokeLater(() -> {
                    new VentanaJuego(usuario);
                });

            } else {
                System.out.println("Usuario o contraseña incorrectos.");
            }

            sc.close();

        } catch (Exception e) {
            System.err.println("Error en la prueba: " + e.getMessage());
            e.printStackTrace();
        } finally {
            conexionBD.desconectar();
        }

        System.out.println("\n=== Fin de la prueba ===");
    }
}
