# Explicación línea a línea de `App.java`

Este programa actúa como **proceso padre**: localiza Java, prepara el comando, inicia el programa `Sumador` como proceso hijo y espera a que termine.

## Importaciones

```java
import java.io.File;
import java.io.IOException;
```

- `File`: permite utilizar `File.separator` para construir rutas compatibles con el sistema operativo.
- `IOException`: representa errores de entrada/salida, como un fallo al iniciar el proceso.

## Declaración de la clase

```java
public class App {
    public static void main(String[] args) throws Exception {
```

- `public class App`: declara la clase principal.
- `main()`: punto de entrada del programa.
- `String[] args`: permite recibir argumentos desde la línea de comandos.
- `throws Exception`: declara que el método puede propagar excepciones. En este ejemplo es demasiado general, ya que las excepciones relevantes se capturan más adelante.

## Bloque de control de errores

```java
try {
```

Permite ejecutar código que podría producir excepciones y gestionarlas mediante `catch`.

## Obtener el directorio de Java

```java
String javaHome = System.getProperty("java.home");
```

- Declara la variable `javaHome`.
- `System.getProperty("java.home")` obtiene la ruta del entorno Java que está ejecutando el programa.
- Permite descubrir dónde está instalado Java sin escribir su ruta manualmente.

## Construir la ruta del ejecutable

```java
String javaBin = javaHome + File.separator
        + "bin" + File.separator + "java";
```

- `javaHome`: directorio de instalación de Java.
- `File.separator`: separador de directorios del sistema operativo.
- `"bin"`: directorio donde suelen encontrarse los ejecutables.
- `"java"`: nombre del ejecutable en macOS y Linux.

Construye la ruta completa del ejecutable que se utilizará para iniciar el proceso hijo. En Windows habría que adaptar el nombre del ejecutable a `java.exe`.

## Obtener el classpath

```java
String classpath = System.getProperty("java.class.path");
```

- Obtiene el classpath de la ejecución actual.
- El classpath indica dónde buscar clases y archivos JAR.
- Permite que el proceso hijo encuentre la clase `Sumador`, siempre que su ubicación esté incluida.

## Obtener el nombre de la clase hija

```java
String className = Sumador.class.getCanonicalName();
```

- Obtiene el nombre canónico de la clase `Sumador`.
- En este ejemplo devuelve `"Sumador"`.
- Evita tener que escribir manualmente el nombre de la clase.

## Configurar el proceso hijo

```java
ProcessBuilder builder = new ProcessBuilder(
    javaBin, "-cp", classpath, className, "15", "25"
);
```

Crea un objeto `ProcessBuilder` y configura el comando que ejecutará el proceso hijo.

| Argumento | Función |
|---|---|
| `javaBin` | Ejecutable de Java que se iniciará. |
| `"-cp"` | Indica que se especificará el classpath. |
| `classpath` | Rutas donde Java buscará las clases. |
| `className` | Clase que se ejecutará: `Sumador`. |
| `"15"` | Primer argumento que recibirá `Sumador`. |
| `"25"` | Segundo argumento que recibirá `Sumador`. |

Equivale aproximadamente a ejecutar:

```bash
java -cp <classpath> Sumador 15 25
```

Los argumentos se pasan individualmente, sin necesidad de construir una única cadena de comandos.

## Compartir la entrada y salida

```java
builder.inheritIO();
```

Hace que el proceso hijo herede la entrada estándar, la salida estándar y la salida de error del padre.

Así, los mensajes de `Sumador` aparecen en la misma consola que los mensajes de `App`.

## Mostrar el inicio

```java
System.out.println("PADRE: Lanzando el proceso hijo 'Sumador'...");
```

Muestra un mensaje informativo. No inicia el proceso; únicamente anuncia lo que va a ocurrir.

## Iniciar el proceso

```java
Process proceso = builder.start();
```

- `builder.start()`: inicia el proceso hijo.
- `Process`: representa el proceso iniciado.
- `proceso`: variable que permite interactuar con él.

**Esta es la línea que realmente lanza el programa externo.**

## Esperar a que termine

```java
proceso.waitFor();
```

- Suspende la ejecución del hilo actual hasta que el proceso hijo termina.
- Evita que el padre continúe antes de que finalice el hijo.
- Devuelve el código de salida del proceso, aunque en este ejemplo no se utiliza.

## Mostrar la finalización

```java
System.out.println("PADRE: Proceso hijo finalizado.");
```

Muestra un mensaje después de que el proceso hijo haya terminado.

No garantiza por sí mismo que el hijo haya finalizado correctamente; para comprobarlo habría que analizar el código de salida.

## Capturar errores

```java
} catch (IOException | InterruptedException e) {
    System.err.println("Error en la ejecución: " + e.getMessage());
}
```

- `IOException`: captura errores de entrada/salida.
- `InterruptedException`: captura una interrupción mientras el padre espera.
- `|`: permite capturar cualquiera de las dos excepciones.
- `e.getMessage()`: obtiene el mensaje descriptivo del error.
- `System.err.println()`: muestra el error por la salida estándar de error.

En código de producción conviene restaurar el estado de interrupción del hilo cuando se captura `InterruptedException`.

## Cierre de bloques

```java
    }
}
```

Cierra el bloque `try-catch`, el método `main` y la clase `App`.

## Ejemplo de ejecución

```text
PADRE: Lanzando el proceso hijo 'Sumador'...
Suma realizada por el proceso hijo: 40
PADRE: Proceso hijo finalizado.
```

## Resumen del funcionamiento

1. El padre obtiene la ubicación de Java.
2. Construye la ruta del ejecutable y obtiene el classpath.
3. Configura el comando con los argumentos `15` y `25`.
4. Inicia `Sumador` mediante `ProcessBuilder.start()`.
5. El hijo suma los números y muestra `40`.
6. El padre espera a que termine y muestra el mensaje final.

**Concepto clave:** `App` y `Sumador` son procesos independientes. `App` no llama a un método de `Sumador`, sino que inicia otra ejecución de Java y le pasa los argumentos mediante la línea de comandos.