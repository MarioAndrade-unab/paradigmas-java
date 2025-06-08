# Sistema de Pruebas en Java Swing

##  Descripción
Este programa permite la gestión de pruebas con preguntas de **Selección Múltiple** y **Verdadero/Falso** en Java Swing. 
Los usuarios pueden cargar un archivo de preguntas, configurar la prueba, realizarla y ver los resultados detallados.

##  Instrucciones de Ejecución
1. **Clonar o descargar el código** en tu equipo.
2. **Abrir el proyecto en Eclipse**.
3. **Ejecutar `VentanaPrincipal.java`** para iniciar la prueba.
4. **Seleccionar un archivo de preguntas** en formato válido.
5. **Configurar tiempo de prueba** y comenzar.
6. **Responder preguntas y ver resultados**.

##  Formato del Archivo de Preguntas
Las preguntas deben estar en **formato CSV** con el siguiente esquema:

```
Enunciado;Tipo;Opciones;RespuestaCorrecta
"¿Cuál es la capital de Francia?";"Selección Múltiple";"París,Londres,Berlín";"París"
"El agua hierve a 100°C";"Verdadero/Falso";"";"Verdadero"
```

Si el formato es incorrecto, el programa mostrará un mensaje de error.

##  Estructura del Código

```
paradigma-java/
src/
│── logica/     # Maneja lógica de la aplicación backend
│   ├── Pregunta.java
│   ├── GestorPreguntas.java
│   ├── Prueba.java
│── gui/    # Gestiona la interfaz gráfica frontend
│   ├── VentanaPrincipal.java
│   ├── PanelConfiguracion.java
│   ├── PanelPrueba.java
│   ├── PanelResultados.java
│   ├── PanelRevisar.java
│── README.md    # Documentación del proyecto
```

##  Tecnologías Utilizadas
- **Java 17**  
- **Swing (para la GUI)**  
- **Eclipse IDE**  
---

 