package logica;

import java.io.*;
import java.util.*;

public class GestorPreguntas {
    private List<Pregunta> preguntas;

    public GestorPreguntas() {
        preguntas = new ArrayList<>();
    }

    public boolean validarArchivo(String rutaArchivo) {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
            String linea;
            boolean primeraLinea = true;

            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Ignora la primera línea si es un encabezado
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");

                // Validar que la línea tenga 4 columnas
                if (partes.length != 4) {
                    System.out.println("Error: La línea no tiene el formato correcto: " + linea);
                    return false;
                }

                String tipo = partes[1].replace("\"", "").trim();

                // Validar tipo de pregunta
                if (!tipo.equalsIgnoreCase("Verdadero/Falso") && !tipo.equalsIgnoreCase("Seleccion Multiple")) {
                    System.out.println("Error: Tipo de pregunta inválido en línea: " + linea);
                    return false;
                }

                // Validar opciones si es de selección múltiple
                if (tipo.equalsIgnoreCase("Seleccion Multiple") && partes[2].trim().isEmpty()) {
                    System.out.println("Error: Pregunta de selección múltiple sin opciones en línea: " + linea);
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

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(rutaArchivo), "UTF-8"))) {
            String linea;
            boolean primeraLinea = true;
            
            while ((linea = br.readLine()) != null) {
                if (primeraLinea) { // Ignora la primera línea si es un encabezado
                    primeraLinea = false;
                    continue;
                }

                String[] partes = linea.split(";");
                if (partes.length >= 4) {
                    String enunciado = partes[0].replace("\"", "").trim();
                    String tipo = partes[1].replace("\"", "").trim();
                    String opcionesStr = partes[2].replace("\"", "").trim();
                    String respuestaCorrecta = partes[3].replace("\"", "").trim();

                    String[] opciones = opcionesStr.isEmpty() ? new String[0] : opcionesStr.split(",");

                    Pregunta pregunta = new Pregunta(enunciado, tipo, opciones, respuestaCorrecta);
                    preguntas.add(pregunta);
                } else {
                    System.out.println("Error en formato de línea: " + linea);
                }
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public List<Pregunta> getPreguntas() {
        return preguntas;
    }
}