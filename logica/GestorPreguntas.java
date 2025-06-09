package logica;

import java.io.*;
import java.util.*;

public class GestorPreguntas {
    private List<Pregunta> preguntasDisponibles; // Lista completa de preguntas
    private List<Pregunta> preguntasSeleccionadas; // Lista de preguntas elegidas para la prueba

    public GestorPreguntas() {
        preguntasDisponibles = new ArrayList<>();
        preguntasSeleccionadas = new ArrayList<>();
    }

    public boolean validarArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Ignorar la primera l�nea si es un encabezado
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");

                // Validar que la l�nea tenga 6 columnas
                if (partes.length != 6) {
                    System.out.println("Error: La l�nea no tiene el formato correcto: " + linea);
                    return false;
                }

                String nivel = partes[1].replace("\"", "").trim();

                // Validar nivel taxon�mico
                try {
                    NivelTaxonomico.valueOf(nivel.toUpperCase());
                } catch (IllegalArgumentException e) {
                    System.out.println("Error: Nivel taxon�mico inv�lido en l�nea: " + linea);
                    return false;
                }

                String tipo = partes[2].replace("\"", "").trim();

                // Validar tipo de pregunta
                if (!tipo.equalsIgnoreCase("Verdadero/Falso") && !tipo.equalsIgnoreCase("Seleccion Multiple")) {
                    System.out.println("Error: Tipo de pregunta inv�lido en l�nea: " + linea);
                    return false;
                }

                // Validar opciones si es de selecci�n m�ltiple
                if (tipo.equalsIgnoreCase("Seleccion Multiple") && partes[3].trim().isEmpty()) {
                    System.out.println("Error: Pregunta de selecci�n m�ltiple sin opciones en l�nea: " + linea);
                    return false;
                }

                // Validar tiempo como n�mero
                try {
                    Integer.parseInt(partes[5].replace("\"", "").trim());
                } catch (NumberFormatException e) {
                    System.out.println("Error: Tiempo inv�lido en l�nea: " + linea);
                    return false;
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
            return false;
        }
        return true;
    }

    public void cargarPreguntasDesdeArchivo(String rutaArchivo) {
        if (!validarArchivo(rutaArchivo)) {
            System.out.println("El archivo tiene errores en su formato. No se pueden cargar las preguntas.");
            return;
        }

        preguntasDisponibles.clear();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Ignora la primera l�nea si es un encabezado
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length == 6) {
                    String enunciado = partes[0].replace("\"", "").trim();
                    String nivelStr = partes[1].replace("\"", "").trim();
                    String tipo = partes[2].replace("\"", "").trim();
                    String opcionesStr = partes[3].replace("\"", "").trim();
                    String respuestaCorrecta = partes[4].replace("\"", "").trim();
                    int tiempo = Integer.parseInt(partes[5].replace("\"", "").trim());

                    NivelTaxonomico nivel = NivelTaxonomico.valueOf(nivelStr.toUpperCase());
                    String[] opciones = opcionesStr.isEmpty() ? new String[0] : opcionesStr.split(",");

                    Pregunta pregunta = new Pregunta(enunciado, tipo, opciones, respuestaCorrecta, nivel, tiempo);
                    preguntasDisponibles.add(pregunta);
                } else {
                    System.out.println("Error en formato de l�nea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public List<Pregunta> getPreguntasDisponibles() {
        return preguntasDisponibles;
    }

    public void seleccionarPreguntas(List<Pregunta> seleccionadas) {
        preguntasSeleccionadas.clear();
        preguntasSeleccionadas.addAll(seleccionadas);
    }

    public List<Pregunta> getPreguntasSeleccionadas() {
        return preguntasSeleccionadas;
    }

    public int getTiempoTotal() {
        return preguntasSeleccionadas.stream().mapToInt(Pregunta::getTiempo).sum();
    }
}