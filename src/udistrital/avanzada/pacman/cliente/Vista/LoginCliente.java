package udistrital.avanzada.pacman.cliente.Vista;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.*;

/**
 * Clase LoginCliente.
 *
 * Panel visual que permite al usuario ingresar sus credenciales antes de
 * conectarse al servidor.
 *
 * @author Diego
 * @version 1.0
 * @since 2025-11-12
 */
public class LoginCliente extends JPanel {

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnConectar;

    public LoginCliente() {
        // Fondo general gris claro
        setBackground(new Color(240, 240, 240));
        setLayout(new GridBagLayout()); // Centra todo el contenido
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        // Panel interior (blanco)
        JPanel panelForm = new JPanel();
        panelForm.setLayout(new BoxLayout(panelForm, BoxLayout.Y_AXIS));
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(new CompoundBorder(
                new LineBorder(new Color(200, 200, 200), 1, true),
                new EmptyBorder(25, 30, 25, 30)
        ));
        panelForm.setMaximumSize(new Dimension(300, 250));

        // ---- Título ----
        JLabel lblTitulo = new JLabel("Inicio de Sesión");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(44, 62, 80));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // ---- Campo Usuario ----
        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblUsuario.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtUsuario = new JTextField(15);
        txtUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        txtUsuario.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210), 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));

        // ---- Campo Contraseña ----
        JLabel lblContrasena = new JLabel("Contraseña:");
        lblContrasena.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblContrasena.setAlignmentX(Component.CENTER_ALIGNMENT);

        txtContrasena = new JPasswordField(15);
        txtContrasena.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        txtContrasena.setBorder(BorderFactory.createCompoundBorder(
                new LineBorder(new Color(210, 210, 210), 1, true),
                new EmptyBorder(4, 8, 4, 8)
        ));

        // ---- Botón ----
        btnConectar = new JButton("Conectar");
        btnConectar.setActionCommand("login");
        btnConectar.setFocusPainted(false);
        btnConectar.setBackground(new Color(52, 152, 219));
        btnConectar.setForeground(Color.WHITE);
        btnConectar.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConectar.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        btnConectar.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnConectar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        btnConectar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConectar.setBackground(new Color(41, 128, 185));
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConectar.setBackground(new Color(52, 152, 219));
            }
        });

        // ---- Añadir componentes con espaciado ----
        panelForm.add(lblTitulo);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(lblUsuario);
        panelForm.add(txtUsuario);
        panelForm.add(Box.createVerticalStrut(10));
        panelForm.add(lblContrasena);
        panelForm.add(txtContrasena);
        panelForm.add(Box.createVerticalStrut(20));
        panelForm.add(btnConectar);

        add(panelForm, gbc);
    }

    public String getUsuario() {
        return txtUsuario.getText().trim();
    }

    public String getContrasena() {
        return new String(txtContrasena.getPassword());
    }

    public void setListener(ActionListener al) {
        btnConectar.addActionListener(al);
    }
}
