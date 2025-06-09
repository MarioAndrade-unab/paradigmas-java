# Sistema de Pruebas en Java Swing

##  Descripción
Este programa permite la gestión de pruebas con preguntas de **Selección Múltiple** y **Verdadero/Falso** en Java Swing. 
Los usuarios pueden cargar un archivo de preguntas, elegir las preguntas, realizarla y ver los resultados detallados.

##  Instrucciones de Ejecución
1. **Clonar o descargar el código** en tu equipo.
2. **Abrir el proyecto en Eclipse**.
3. **Ejecutar `VentanaPrincipal.java`** para iniciar la prueba.
4. **Seleccionar un archivo de preguntas** en formato válido.
5. **Seleccionar Preguntas** y comenzar.
6. **Responder preguntas y ver resultados**.

##  Características principales  
- **Carga de preguntas desde CSV** con formato estándar.
- **Selección de preguntas antes de iniciar la prueba.**
- **Tiempo total de la prueba calculado dinámicamente** en función de las preguntas seleccionadas.
- **Cuenta regresiva en formato `MM:SS`, mostrando minutos y segundos.**
- **Persistencia de respuestas al cambiar entre preguntas**, permitiendo corregir antes de finalizar.  



##  Formato del Archivo de Preguntas
Las preguntas deben estar en **formato CSV** con el siguiente esquema:

```
Enunciado;Nivel;Tipo;Opciones;RespuestaCorrecta;Tiempo
"¿Cuál es la capital de Francia?";"RECORDAR";"Seleccion Multiple";"Paris,Londres,Berlin";"Paris";2

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
│   ├── PruebaListener.java
│   ├── NivelTaxonomico.java
│── gui/    # Gestiona la interfaz gráfica frontend
│   ├── VentanaPrincipal.java
│   ├── PanelStart.java
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

 
