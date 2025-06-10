package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import logica.Pregunta;
import logica.NivelTaxonomico;

public class PanelResultados extends JFrame {
    private List<Pregunta> preguntas;
    private Map<Pregunta, String> respuestasUsuario;

    public PanelResultados(List<Pregunta> preguntas, Map<Pregunta, String> respuestasUsuario) {
        this.preguntas = preguntas;
        this.respuestasUsuario = respuestasUsuario;

        setTitle("Resultados de la Prueba");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel lblTitulo = new JLabel("Resultados de la Prueba", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblTitulo, BorderLayout.NORTH);

        calcularResultados();
        agregarBotones();

        setVisible(true);
    }

    private void calcularResultados() {
        // Mapas para respuestas correctas por nivel taxonomico
        Map<NivelTaxonomico, Integer> respuestasCorrectasPorNivel = new HashMap<>();
        Map<NivelTaxonomico, Integer> totalPreguntasPorNivel = new HashMap<>();

        // Mapas para respuestas correctas por tipo de pregunta
        Map<String, Integer> respuestasCorrectasPorTipo = new HashMap<>();
        Map<String, Integer> totalPreguntasPorTipo = new HashMap<>();
        
        // Conteo general
        int totalCorrectas = 0;
        int totalPreguntas = preguntas.size();

        for (Pregunta pregunta : preguntas) {
            NivelTaxonomico nivel = pregunta.getNivel();
            String tipoPregunta = pregunta.getTipo();

            totalPreguntasPorNivel.put(nivel, totalPreguntasPorNivel.getOrDefault(nivel, 0) + 1);
            totalPreguntasPorTipo.put(tipoPregunta, totalPreguntasPorTipo.getOrDefault(tipoPregunta, 0) + 1);

            String respuestaUsuario = respuestasUsuario.get(pregunta);
            if (respuestaUsuario != null && respuestaUsuario.equals(pregunta.getRespuestaCorrecta())) {
                totalCorrectas++;
                respuestasCorrectasPorNivel.put(nivel, respuestasCorrectasPorNivel.getOrDefault(nivel, 0) + 1);
                respuestasCorrectasPorTipo.put(tipoPregunta, respuestasCorrectasPorTipo.getOrDefault(tipoPregunta, 0) + 1);
            }
        }
        
        // Resultados generales
        double porcentajeTotal = (totalCorrectas / (double) totalPreguntas) * 100;
        String resumenGeneral = String.format("<html><div style='font-size:14pt'><b>Resumen General:</b><br>"
            + "%.2f%% (%d de %d correctas)</div></html>", porcentajeTotal, totalCorrectas, totalPreguntas);

        // Construccion del desglose por nivel taxonomico
        StringBuilder resultadosNivel = new StringBuilder("<html><b>Resultados por Nivel Taxonomico:</b><br>");
        for (NivelTaxonomico nivel : totalPreguntasPorNivel.keySet()) {
            int total = totalPreguntasPorNivel.get(nivel);
            int correctas = respuestasCorrectasPorNivel.getOrDefault(nivel, 0);
            double porcentaje = (correctas / (double) total) * 100;
            resultadosNivel.append(String.format("%s: %.2f%% (%d de %d correctas)<br>", nivel, porcentaje, correctas, total));
        }
        resultadosNivel.append("</html>");

        // Construccion del desglose por tipo de pregunta
        StringBuilder resultadosTipo = new StringBuilder("<html><b>Resultados por Tipo de Pregunta:</b><br>");
        for (String tipo : totalPreguntasPorTipo.keySet()) {
            int total = totalPreguntasPorTipo.get(tipo);
            int correctas = respuestasCorrectasPorTipo.getOrDefault(tipo, 0);
            double porcentaje = (correctas / (double) total) * 100;
            resultadosTipo.append(String.format("%s: %.2f%% (%d de %d correctas)<br>", tipo, porcentaje, correctas, total));
        }
        resultadosTipo.append("</html>");

        // Mostrar en la interfaz
        JLabel lblResultadosGeneral = new JLabel(resumenGeneral, SwingConstants.CENTER);
        JLabel lblResultadosNivel = new JLabel(resultadosNivel.toString());
        JLabel lblResultadosTipo = new JLabel(resultadosTipo.toString());

        JPanel panelResultados = new JPanel();
        panelResultados.setLayout(new GridLayout(2, 1));
        panelResultados.add(lblResultadosGeneral);
        panelResultados.add(lblResultadosNivel);
        panelResultados.add(lblResultadosTipo);

        add(panelResultados, BorderLayout.CENTER);
    }

    private void agregarBotones() {
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new FlowLayout());

        JButton btnRevisarRespuestas = new JButton("Revisar respuestas");
        JButton btnSalir = new JButton("Salir");

        btnRevisarRespuestas.addActionListener(e -> revisarRespuestas());
        btnSalir.addActionListener(e -> System.exit(0)); // Salir del programa

        panelBotones.add(btnRevisarRespuestas);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);
    }

    private void revisarRespuestas() {
        dispose(); // Cierra `PanelResultados`
        new PanelRevisar(preguntas, respuestasUsuario, this); // Ahora pasa el mapa de respuestas
    }
}
