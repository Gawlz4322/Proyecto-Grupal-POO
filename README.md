# Control de Finanzas Personales (CFP)

Proyecto Grupal para la asignatura de **Programación Orientada a Objetos (POO)**.

Este software es una aplicación de escritorio en Java que permite llevar un registro de ingresos y gastos personales. El objetivo principal del proyecto fue aplicar los conceptos aprendidos en clases, como el patrón MVC, herencia, polimorfismo y manejo de archivos.

## Integrantes
- **Roberto Herrera:** Arquitectura MVC, refactorización y lógica del sistema.
- **Miguel Díaz:** Persistencia de datos (JSON) y seguridad (Hashing).
- **Benyasmín Sanhueza:** Desarrollo integral de la Interfaz Gráfica (Swing), Diseño UX/UI, Validaciones de sistema y QA (Aseguramiento de Calidad).

## Funcionalidades
La aplicación permite:
*   Iniciar sesión y registrar nuevos usuarios (las contraseñas se guardan encriptadas).
*   Ver el saldo actual en el menú principal.
*   Registrar un Ingreso o un Gasto con su categoría.
*   Ver un gráfico de torta con los gastos por categoría (usando la librería JFreeChart).
*   Revisar el historial de movimientos en una tabla.

## Aspectos Técnicos
El proyecto está construido en **Java 21** y utiliza **Maven** para manejar las dependencias.

### Librerías empleadas
*   `Gson`: Para guardar los datos en archivos JSON.
*   `FlatLaf`: Para darle un estilo oscuro (Dark Mode) a la interfaz Swing.
*   `JFreeChart`: Para generar los gráficos de gastos.
*   `FlatLaf Extras`: Para el manejo de iconos SVG.

### Estructura
El código sigue el patrón **Modelo-Vista-Controlador (MVC)**:
*   `Model`: Clases que representan los datos (`User`, `Transaction`, `FinanceSystem`).
*   `View`: Ventanas y paneles (`MainMenu`, `LoginWindow`).
*   `Controller`: Lógica que conecta la vista con el modelo (`AuthService`, `MenuController`).

## Ejecución
Para compilar y correr el proyecto, es necesario tener las librerías en la carpeta `lib`.

**Comandos para compilar y ejecutar (Windows):**

```cmd
dir /b /s src\main\java\*.java > sources.txt
javac -cp "lib/*" @sources.txt
java -cp "lib/*;src/main/java" Main
```
