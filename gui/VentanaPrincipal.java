package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import logica.GestorPreguntas;

public class VentanaPrincipal extends JFrame {
    private GestorPreguntas gestorPreguntas;
    private JLabel lblArchivo;
    private String rutaArchivo;

    public VentanaPrincipal() {
        gestorPreguntas = new GestorPreguntas();

        setTitle("Carga de Preguntas");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        lblArchivo = new JLabel("Archivo no seleccionado");
        JButton btnSeleccionar = new JButton("Seleccionar Archivo");
        JButton btnContinuar = new JButton("Continuar");

        btnSeleccionar.addActionListener(this::seleccionarArchivo);
        btnContinuar.addActionListener(this::iniciarPanelStart);

        add(btnSeleccionar);
        add(lblArchivo);
        add(btnContinuar);
    }

    private void seleccionarArchivo(ActionEvent e) {
        JFileChooser fileChooser = new JFileChooser();
        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            rutaArchivo = fileChooser.getSelectedFile().getAbsolutePath();
            lblArchivo.setText("Seleccionado: " + fileChooser.getSelectedFile().getName());

            if (!gestorPreguntas.validarArchivo(rutaArchivo)) {
                JOptionPane.showMessageDialog(this, "El archivo tiene errores en su formato. Verifique y vuelva a intentarlo.", "Error de archivo", JOptionPane.ERROR_MESSAGE);
                rutaArchivo = null;
            } else {
                gestorPreguntas.cargarPreguntasDesdeArchivo(rutaArchivo);
            }
        }
    }

    private void iniciarPanelStart(ActionEvent e) {
        if (rutaArchivo == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un archivo válido antes de continuar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dispose();
        new PanelStart(gestorPreguntas); // Ahora `PanelStart` maneja la selección de preguntas
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal().setVisible(true));
    }
}