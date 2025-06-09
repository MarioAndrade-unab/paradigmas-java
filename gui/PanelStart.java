package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import logica.GestorPreguntas;
import logica.Pregunta;

public class PanelStart extends JFrame {
    private GestorPreguntas gestorPreguntas;
    private Map<Pregunta, JCheckBox> seleccionadas;

    public PanelStart(GestorPreguntas gestor) {
        this.gestorPreguntas = gestor;
        seleccionadas = new HashMap<>();

        setTitle("PRUEBA");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel para mostrar lista de preguntas
        JPanel panelPreguntas = new JPanel();
        panelPreguntas.setLayout(new GridLayout(0, 1));

        for (Pregunta pregunta : gestorPreguntas.getPreguntasDisponibles()) {
            JCheckBox checkBox = new JCheckBox(pregunta.getEnunciado() + " (Tiempo: " + pregunta.getTiempo() + " minutos)");
            seleccionadas.put(pregunta, checkBox);
            panelPreguntas.add(checkBox);
        }

        JButton btnConfirmar = new JButton("Confirmar selección");
        btnConfirmar.addActionListener(e -> confirmarSeleccion());

        add(new JScrollPane(panelPreguntas), BorderLayout.CENTER);
        add(btnConfirmar, BorderLayout.SOUTH);

        setVisible(true);
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
        dispose();
        new PanelPrueba(preguntasSeleccionadas, tiempoTotal);
    }
}