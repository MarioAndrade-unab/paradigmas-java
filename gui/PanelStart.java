package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import logica.GestorPreguntas;
import logica.Pregunta;

public class PanelStart extends JFrame {
    private GestorPreguntas gestorPreguntas;
    private Map<Pregunta, JCheckBox> seleccionadas;
    private JLabel lblSeleccionadas, lblTiempoTotal;

    public PanelStart(GestorPreguntas gestor) {
        //System.out.println("PanelStart se está ejecutando..."); // Depuración

        this.gestorPreguntas = gestor;
        seleccionadas = new HashMap<>();

        setTitle("PRUEBA");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior con total de preguntas y tiempo acumulado
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new GridLayout(2, 1));

        lblSeleccionadas = new JLabel("Preguntas seleccionadas: 0", SwingConstants.CENTER);
        lblTiempoTotal = new JLabel("Tiempo total de prueba: 0 min", SwingConstants.CENTER);
        
        panelSuperior.add(lblSeleccionadas);
        panelSuperior.add(lblTiempoTotal);
        add(panelSuperior, BorderLayout.NORTH);

        // Panel con lista de preguntas disponibles
        JPanel panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new GridLayout(0, 1));

        for (Pregunta pregunta : gestorPreguntas.getPreguntasDisponibles()) {
            JCheckBox checkBox = new JCheckBox(pregunta.getEnunciado() + " (Tiempo: " + pregunta.getTiempo() + " minutos)");
            seleccionadas.put(pregunta, checkBox);
            panelPreguntas.add(checkBox);

            // Escucha eventos para actualizar contador dinámico
            checkBox.addActionListener(e -> actualizarResumenSeleccion());
        }

        JButton btnConfirmar = new JButton("Confirmar selección");
        btnConfirmar.addActionListener(e -> confirmarSeleccion());

        add(new JScrollPane(panelPreguntas), BorderLayout.CENTER);
        add(btnConfirmar, BorderLayout.SOUTH);

        setVisible(true); // Asegurar que la ventana aparece
    }

    private void actualizarResumenSeleccion() {
        int totalSeleccionadas = 0;
        int tiempoTotal = 0;

        for (Map.Entry<Pregunta, JCheckBox> entry : seleccionadas.entrySet()) {
            if (entry.getValue().isSelected()) {
                totalSeleccionadas++;
                tiempoTotal += entry.getKey().getTiempo();
            }
        }

        lblSeleccionadas.setText("Preguntas seleccionadas: " + totalSeleccionadas);
        lblTiempoTotal.setText("Tiempo total de prueba: " + tiempoTotal + " min");
    }

    private void confirmarSeleccion() {
        List<Pregunta> preguntasSeleccionadas = new ArrayList<>();
        int tiempoTotal = 0;

        for (Map.Entry<Pregunta, JCheckBox> entry : seleccionadas.entrySet()) {
            if (entry.getValue().isSelected()) {
                preguntasSeleccionadas.add(entry.getKey());
                tiempoTotal += entry.getKey().getTiempo();
            }
        }

        if (preguntasSeleccionadas.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos una pregunta para la prueba.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        gestorPreguntas.seleccionarPreguntas(preguntasSeleccionadas);
        
        dispose(); // Cierra `PanelStart` tras confirmar la selección
        
        new PanelPrueba(preguntasSeleccionadas, tiempoTotal);
    }
}