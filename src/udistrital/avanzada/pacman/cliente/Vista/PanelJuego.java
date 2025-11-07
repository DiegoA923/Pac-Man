package udistrital.avanzada.pacman.cliente.Vista;

import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author Mauricio
 */
/*

 */
public class PanelJuego extends JPanel {

    private JLabel lblMovimiento;
    private JTextField txtMovimiento;
    private JTextArea areaTexto;
    private JScrollPane scrollPane;

    public PanelJuego() {

        JPanel panelSuperior = new JPanel(new BorderLayout(5, 5));
        lblMovimiento = new JLabel("Ingrese movimiento(arriba, abajo, izquierda, derecha):");
        txtMovimiento = new JTextField();

        panelSuperior.add(lblMovimiento, BorderLayout.NORTH);
        panelSuperior.add(txtMovimiento, BorderLayout.CENTER);

        add(panelSuperior, BorderLayout.NORTH);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        areaTexto.setLineWrap(true);
        areaTexto.setWrapStyleWord(true);        

        scrollPane = new JScrollPane(areaTexto);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        add(scrollPane, BorderLayout.CENTER);

//        txtMovimiento.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String texto = txtMovimiento.getText().trim();
//                if (!texto.isEmpty()) {
//                    areaTexto.append(texto + "\n");
//                    txtMovimiento.setText("");
//                }
//            }
//        });
    }
    
    public void addActionListenerTxtMovimiento(ActionListener action) {
        txtMovimiento.addActionListener(action);
    }

    public void agregarTextoArea(String texto) {
        areaTexto.append(texto + "\n");
        txtMovimiento.setText("");
    }
    
    public String getTextMovimiento() {
        return txtMovimiento.getText().trim();
    }

}
