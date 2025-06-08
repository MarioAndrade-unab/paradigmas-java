package gui;

import javax.swing.*;
import java.awt.*;
import logica.GestorPreguntas;

public class PanelConfiguracion extends JFrame {
    private GestorPreguntas gestorPreguntas;
    
    public PanelConfiguracion(GestorPreguntas gestor) {
        this.gestorPreguntas = gestor;

        setTitle("Configuración de la Prueba");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Mostrar cantidad de preguntas disponibles
        JLabel lblCantidadPreguntas = new JLabel("Preguntas cargadas: " + gestorPreguntas.getPreguntas().size());

        // Campo para ingresar tiempo límite
        JLabel lblTiempo = new JLabel("Tiempo de prueba (min): ");
        JTextField txtTiempo = new JTextField(5);

        JButton btnIniciarPrueba = new JButton("Iniciar Prueba");

        btnIniciarPrueba.addActionListener(e -> iniciarPrueba(Integer.parseInt(txtTiempo.getText())));

        add(lblCantidadPreguntas);
        add(lblTiempo);
        add(txtTiempo);
        add(btnIniciarPrueba);

        setVisible(true);
    }

    private void iniciarPrueba(int tiempo) {
        dispose();
        new PanelPrueba(gestorPreguntas, tiempo);
    }
}
