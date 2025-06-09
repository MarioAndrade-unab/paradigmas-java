package gui;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;
import logica.Pregunta;

public class PanelPrueba extends JFrame {
    private List<Pregunta> preguntas;
    private int tiempoRestante;
    private int indicePregunta = 0;
    private JLabel lblEnunciado, lblTiempo;
    private JPanel panelOpciones;
    private JButton btnSiguiente, btnAnterior, btnEntregar;
    private Map<Pregunta, String> respuestasUsuario = new HashMap<>();
    private Timer temporizador;

    public PanelPrueba(List<Pregunta> preguntasSeleccionadas, int tiempoTotal) {
        this.preguntas = preguntasSeleccionadas;
        this.tiempoRestante = tiempoTotal * 60; // Convertir minutos a segundos

        setTitle("Prueba en Curso");
        setSize(500, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel superior para mostrar el tiempo restante
        lblTiempo = new JLabel("Tiempo restante: " + formatTiempo(tiempoRestante), SwingConstants.CENTER);
        add(lblTiempo, BorderLayout.NORTH);

        // Panel central para mostrar la pregunta y opciones
        JPanel panelCentral = new JPanel();
        panelCentral.setLayout(new BorderLayout());

        lblEnunciado = new JLabel("", SwingConstants.CENTER);
        panelCentral.add(lblEnunciado, BorderLayout.NORTH);

        panelOpciones = new JPanel();
        panelOpciones.setLayout(new GridLayout(0, 1)); // Organiza las opciones en filas
        panelCentral.add(panelOpciones, BorderLayout.CENTER);

        add(panelCentral, BorderLayout.CENTER);

        // Panel inferior con botones de navegación
        JPanel panelBotones = new JPanel();
        btnAnterior = new JButton("Anterior");
        btnSiguiente = new JButton("Siguiente");
        btnEntregar = new JButton("Entregar prueba");

        btnAnterior.addActionListener(e -> mostrarPregunta(indicePregunta - 1));
        btnSiguiente.addActionListener(e -> mostrarPregunta(indicePregunta + 1));
        btnEntregar.addActionListener(e -> entregarPrueba());

        panelBotones.add(btnAnterior);
        panelBotones.add(btnSiguiente);
        panelBotones.add(btnEntregar);
        add(panelBotones, BorderLayout.SOUTH);

        // Inicializar temporizador con formato `MM:SS`
        temporizador = new Timer();
        temporizador.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                tiempoRestante--;
                lblTiempo.setText("Tiempo restante: " + formatTiempo(tiempoRestante));
                if (tiempoRestante <= 0) {
                    temporizador.cancel();
                    entregarPrueba();
                }
            }
        }, 1000, 1000);

        // Mostrar la primera pregunta correctamente
        if (!preguntas.isEmpty()) {
            mostrarPregunta(0);
        }

        setVisible(true);
    }

    private void mostrarPregunta(int nuevoIndice) {
        if (nuevoIndice >= 0 && nuevoIndice < preguntas.size()) {
            indicePregunta = nuevoIndice;
            Pregunta pregunta = preguntas.get(indicePregunta);

            lblEnunciado.setText("Pregunta: " + pregunta.getEnunciado());

            panelOpciones.removeAll();
            ButtonGroup grupoOpciones = new ButtonGroup();
            String respuestaGuardada = respuestasUsuario.getOrDefault(pregunta, "");

            if (pregunta.getTipo().equalsIgnoreCase("Seleccion Multiple")) {
                for (String opcion : pregunta.getOpciones()) {
                    if (!opcion.isEmpty()) {
                        JRadioButton rb = new JRadioButton(opcion);
                        grupoOpciones.add(rb);
                        panelOpciones.add(rb);

                        // Mantener selección previa
                        if (opcion.equals(respuestaGuardada)) {
                            rb.setSelected(true);
                        }

                        rb.addActionListener(e -> respuestasUsuario.put(pregunta, opcion));
                    }
                }
            } else if (pregunta.getTipo().equalsIgnoreCase("Verdadero/Falso")) {
                JRadioButton rbVerdadero = new JRadioButton("Verdadero");
                JRadioButton rbFalso = new JRadioButton("Falso");
                grupoOpciones.add(rbVerdadero);
                grupoOpciones.add(rbFalso);
                panelOpciones.add(rbVerdadero);
                panelOpciones.add(rbFalso);

                if (respuestaGuardada.equals("Verdadero")) {
                    rbVerdadero.setSelected(true);
                } else if (respuestaGuardada.equals("Falso")) {
                    rbFalso.setSelected(true);
                }

                rbVerdadero.addActionListener(e -> respuestasUsuario.put(pregunta, "Verdadero"));
                rbFalso.addActionListener(e -> respuestasUsuario.put(pregunta, "Falso"));
            }

            panelOpciones.revalidate();
            panelOpciones.repaint();
        }
    }

    private void entregarPrueba() {
        temporizador.cancel();
        dispose();
        new PanelResultados(preguntas, respuestasUsuario);
    }

    private String formatTiempo(int tiempoEnSegundos) {
        int minutos = tiempoEnSegundos / 60;
        int segundos = tiempoEnSegundos % 60;
        return String.format("%02d:%02d", minutos, segundos);
    }
}