package logica;

public class Pregunta {
    private String enunciado;
    private String tipo;
    private String[] opciones; // Para selección múltiple
    private String respuestaCorrecta;

    public Pregunta(String enunciado, String tipo, String[] opciones, String respuestaCorrecta) {
        this.enunciado = enunciado;
        this.tipo = tipo;
        this.opciones = opciones;
        this.respuestaCorrecta = respuestaCorrecta;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public String getTipo() {
        return tipo;
    }

    public String[] getOpciones() {
        return opciones;
    }

    public String getRespuestaCorrecta() {
        return respuestaCorrecta;
    }
}