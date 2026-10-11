# Boletín de Ejercicios Prácticos: Tema 3 - Gestión de Procesos en Java

**Asignatura:** Programación de Servicios y Procesos (PSP)  
**Curso:** 2.º Curso - Ciclo Formativo de Grado Superior  
**Entorno recomendado:** NetBeans 8.2 / JDK 8 (macOS / Windows / Linux)  

---

## 📋 Indicaciones Generales

* **Librerías permitidas:** Clases estándar del JDK de Java (`java.lang.ProcessBuilder`, `java.lang.Process`, `java.io.*`, `java.util.*`).
* **Tratamiento de excepciones:** Todo código que invoque la creación o gestión de procesos debe incluir el manejo adecuado de excepciones mediante bloques `try-catch` (especialmente `IOException` e `InterruptedException`).
* **Entorno de pruebas:** Si trabajas desde **macOS**, ten en cuenta las particularidades de las rutas de ejecutables (ej. `/System/Applications/...`) o comandos del sistema en comparación con **Windows**.

---

## 🧱 Bloque I: Creación y Lanzamiento de Procesos

### Ejercicio 1: El Lanzador Multiplataforma
* **Enunciado:** Desarrolla una aplicación en Java que detecte dinámicamente el sistema operativo en el que se está ejecutando. Si el sistema operativo es **macOS**, debe lanzar el editor de texto nativo `TextEdit` invocando directamente su ejecutable interno (`/System/Applications/TextEdit.app/Contents/MacOS/TextEdit`). Si se ejecuta en **Windows**, debe lanzar la aplicación `notepad.exe`.
* **Objetivo:** Aprender a crear procesos nativos del sistema operativo mediante `ProcessBuilder` y adaptar rutas/comandos según el entorno detectado.
* **Pistas y ayuda técnica:**
  * Utiliza `System.getProperty("os.name").toLowerCase()` para identificar el SO activo.
  * Recuerda instanciar la clase `ProcessBuilder` pasando el comando o la ruta absoluta del ejecutable.
  * Gestiona las excepciones de la familia `IOException` asociadas al lanzamiento de procesos.
* **Resultado esperado:** La consola debe imprimir el sistema operativo detectado y abrir la ventana del editor de texto correspondiente.

---

### Ejercicio 2: Lanzamiento de una Clase Java Externa con Argumentos
* **Enunciado:** Crea una clase llamada `Multiplicador` que reciba dos números enteros como argumentos en su método `main(String[] args)`, calcule su producto e imprima el resultado. A continuación, crea una segunda clase llamada `LanzadorMultiplicador` que invoque a `Multiplicador` para que se ejecute dentro de un proceso secundario independiente de la Máquina Virtual de Java (JVM), enviándole los valores `6` y `9`.
* **Objetivo:** Comprender cómo lanzar una clase Java compilada dentro de un proceso independiente pasando parámetros de entrada por linea de comandos.
* **Pistas y ayuda técnica:**
  * Obtén la ruta del ejecutable `java` del sistema mediante `System.getProperty("java.home") + File.separator + "bin" + File.separator + "java"`.
  * Obtén el classpath actual del proyecto mediante `System.getProperty("java.class.path")`.
  * Configura `ProcessBuilder` para ejecutar: `java -cp <classpath> Multiplicador 6 9`.
  * Para heredar automáticamente la consola del padre, puedes utilizar `pb.inheritIO()` antes de llamar a `.start()`.
* **Resultado esperado:** Impresión en la consola del resultado generado por el proceso hijo (`54`).

---

### Ejercicio 3: Listado de Directorio mediante `Redirect.INHERIT`
* **Enunciado:** Diseña un programa en Java que ejecute el comando del sistema operativo encargado de listar los ficheros del directorio actual (`ls -la` en macOS/Linux o `cmd.exe /c dir` en Windows). La salida estándar y la de errores del proceso hijo deben heredarse automáticamente en la consola del proceso padre sin necesidad de leer búferes manualmente.
* **Objetivo:** Dominar la redirección automática de flujos de salida con la constante `Redirect.INHERIT`.
* **Pistas y ayuda técnica:**
  * Utiliza los métodos `processBuilder.redirectOutput(Redirect.INHERIT)` y `processBuilder.redirectError(Redirect.INHERIT)`.
  * Haz que el proceso padre espere la finalización del proceso hijo invocando al método `.waitFor()`.
* **Resultado esperado:** Visualización directa del listado detallado de archivos y carpetas del directorio de trabajo en la consola de ejecución.

---

## ⏱️ Bloque II: Control del Ciclo de Vida y Temporización

### Ejercicio 4: El Guardián con Timeout (`destroy()` e `isAlive()`)
* **Enunciado:** Implementa un programa que inicie un proceso secundario de red de larga duración (por ejemplo, el comando `ping` con 10 paquetes). El proceso padre debe monitorear la ejecución del hijo durante un tiempo máximo de **3 segundos**. Si tras ese periodo el proceso hijo continúa activo, el programa padre debe forzar su destrucción inmediata e informar al usuario.
* **Objetivo:** Controlar el ciclo de vida de los procesos, verificar su estado de ejecución (`isAlive()`) y finalizar su ejecución por software con `destroy()`.
* **Pistas y ayuda técnica:**
  * Pausa el hilo principal del padre durante 3 segundos con `Thread.sleep(3000)`.
  * Evalúa el estado del proceso con `proceso.isAlive()`.
  * Si el proceso sigue activo (`true`), invoca `proceso.destroy()` para liberar los recursos asignados por el sistema operativo.
* **Resultado esperado:** Mensaje en consola confirmando que el proceso ha superado el tiempo límite y que ha sido destruido.

---

### Ejercicio 5: Validador de Códigos de Salida (`exitValue()`)
* **Enunciado:** Crea un programa que intente ejecutar un comando del sistema operativo sobre un directorio o archivo inexistente (por ejemplo, `ls /directorio_fantasma_123` en macOS/Linux). El programa debe capturar el valor numérico devuelto al finalizar el proceso e informar si la operación concluyó con éxito o si ocurrió un error.
* **Objetivo:** Analizar e interpretar los códigos de finalización de un proceso (`exitValue` / `waitFor`).
* **Pistas y ayuda técnica:**
  * Por convención en los sistemas operativos, un código de retorno `0` indica éxito, mientras que cualquier valor diferente de `0` representa un código de error.
  * Utiliza `int codigo = proceso.waitFor()` o `proceso.exitValue()` para recuperar la cifra de salida.
* **Resultado esperado:** Un mensaje por la consola de errores indicando el código numérico de fallo devuelto por el sistema operativo.

---

### Ejercicio 6: Cadena Secuencial de Procesos
* **Enunciado:** Construye un programa que coordine la ejecución en cadena de **tres procesos secuenciales**:
  1. **Proceso A:** Crea un archivo de texto vacío en disco (`resultado_cadena.txt`).
  2. **Proceso B:** Escribe una línea de texto dentro de dicho archivo.
  3. **Proceso C:** Lee y muestra el contenido final del archivo por consola.
  Cada proceso debe esperar obligatoriamente a que el proceso anterior haya concluido su tarea antes de iniciar la suya.
* **Objetivo:** Sincronizar el orden estricto de ejecución entre múltiples procesos independientes utilizando `.waitFor()`.
* **Pistas y ayuda técnica:**
  * Utiliza comandos nativos del shell (`touch`, `echo`, `cat` en macOS/Linux o comandos de `cmd.exe` en Windows).
  * Invoca `.waitFor()` inmediatamente después del `.start()` de cada proceso antes de instanciar el siguiente.
* **Resultado esperado:** Confirmación en pantalla del progreso paso a paso y visualización final del contenido escrito en el archivo.

---

## 💬 Bloque III: Comunicación e Interconexión de Flujos I/O

### Ejercicio 7: Capturador de la Salida Estándar (`InputStream`)
* **Enunciado:** Desarrolla una aplicación que invoque un comando del sistema operativo que devuelva información de texto (como `whoami` o `date`). El programa padre debe capturar la salida estándar generada por el proceso hijo a través de un canal de lectura e imprimirla en la consola de Java.
* **Objetivo:** Redirigir y leer el flujo `stdout` de un proceso hijo utilizando `InputStream`.
* **Pistas y ayuda técnica:**
  * El flujo de salida del proceso hijo se obtiene con `proceso.getInputStream()` desde la perspectiva del proceso padre.
  * Envuelve dicho flujo en un `InputStreamReader` y este a su vez en un `BufferedReader` para leer la respuesta línea a línea con `.readLine()`.
* **Resultado esperado:** Impresión en la consola de Java del texto emitido por el comando del sistema operativo.

---

### Ejercicio 8: Comunicación Bidireccional Padre-Hijo
* **Enunciado:** Diseña una clase llamada `TransformadorMayus` que lea una cadena de texto desde la entrada estándar (`System.in`) y la imprima convertida totalmente a mayúsculas. A continuación, crea la clase principal que lance `TransformadorMayus` en un proceso secundario, le envíe el texto `"programacion de servicios y procesos"` mediante su flujo de entrada y reciba la respuesta procesada de vuelta.
* **Objetivo:** Implementar una comunicación de dos vías enviando datos al `stdin` del hijo (`OutputStream`) y recibiendo la respuesta desde su `stdout` (`InputStream`).
* **Pistas y ayuda técnica:**
  * Para escribir hacia el proceso hijo, usa `PrintWriter escritor = new PrintWriter(proceso.getOutputStream(), true)`.
  * Para leer la respuesta enviada por el hijo, usa `BufferedReader lector = new BufferedReader(new InputStreamReader(proceso.getInputStream()))`.
* **Resultado esperado:** La consola del programa padre debe mostrar: `PROGRAMACION DE SERVICIOS Y PROCESOS`.

---

### Ejercicio 9: Gestor de Errores Estándar (`ErrorStream`)
* **Enunciado:** Crea una aplicación que ejecute un comando de consola incompleto o erróneo (por ejemplo, invocar el comando `ping` sin especificar ninguna dirección IP ni host). El programa debe capturar específicamente el canal de salida de errores (`stderr`) del proceso e imprimir el mensaje devuelto destacándolo en la consola de errores de Java (`System.err`).
* **Objetivo:** Diferenciar y gestionar adecuadamente el flujo de errores `ErrorStream` de un proceso.
* **Pistas y ayuda técnica:**
  * Accede al canal de errores del hijo mediante `proceso.getErrorStream()`.
  * Lee las líneas impresas por el proceso hijo e imprímelas utilizando `System.err.println()` en lugar de `System.out.println()`.
* **Resultado esperado:** Salida de error formateada en color rojo en la consola de NetBeans detallando la sintaxis incorrecta del comando.

---

## 🖥️ Bloque IV: Consola del SO, Sincronización y Retos Integradores

### Ejercicio 10: Invocación Interactiva del Shell (`bash`/`zsh`/`CMD`) con `flush()`
* **Enunciado:** Desarrolla un programa que abra una sesión interactiva en la consola del sistema operativo (`zsh` o `bash` en macOS/Linux, o `cmd.exe` en Windows). Envíale de forma secuencial las órdenes para consultar el directorio de trabajo (`pwd` o `cd`), obtener el nombre de usuario (`whoami`) y finalizar la sesión (`exit`). Asegúrate de vaciar el búfer de salida tras cada comando.
* **Objetivo:** Interactuar con el intérprete de comandos del sistema operativo y dominar la limpieza de búfer con `.flush()`.
* **Pistas y ayuda técnica:**
  * Instancia `ProcessBuilder` indicando `"zsh"` o `"cmd.exe"`.
  * Tras enviar cada instrucción con `writer.println("comando")`, es obligatorio invocar `writer.flush()` para forzar la transmisión del comando al proceso hijo.
* **Resultado esperado:** Muestra en consola de las respuestas consecutivas emitidas por el shell interactivo del sistema.

---

### Ejercicio 11: Escritura de Log en Zona Crítica (`synchronized`)
* **Enunciado:** Diseña una clase `GestorLog` que contenga un método encargado de escribir mensajes de auditoría en un archivo `registro_actividad.txt`. Lanza **3 hilos/procesos concurrentes** que intenten registrar eventos simultáneamente en dicho archivo. Protege la zona crítica mediante bloques o métodos sincronizados para garantizar la integridad del archivo.
* **Objetivo:** Identificar zonas críticas en aplicaciones concurrentes y aplicar mecanismos de exclusión mutua con la palabra reservada `synchronized`.
* **Pistas y ayuda técnica:**
  * Define el método de escritura utilizando el modificador `synchronized` (ejemplo: `public synchronized void escribirLog(...)`).
  * Utiliza `FileWriter(archivo, true)` para abrir el fichero en modo *append* (añadir al final) sin sobrescribir el contenido existente.
* **Resultado esperado:** Archivo `registro_actividad.txt` generado con todas las entradas de los distintos procesos correctamente ordenadas y sin líneas superpuestas ni corruptas.

---

### Ejercicio 12: Reto Integrador — "La Carrera de Procesos" (Simulación de Coches)
* **Enunciado:** Desarrolla un sistema completo de simulación de una carrera de automovilismo entre 4 escuderías (`Ferrari`, `RedBull`, `Mercedes`, `McLaren`) utilizando programación multiproceso:
  1. **Clase `Coche` (Proceso Hijo):** Recibe el nombre de la escudería por argumento. En un bucle, genera avances aleatorios (entre 1 y 10 metros), los acumula e imprime su progreso por su salida estándar (`stdout`). Cuando alcanza la meta (50 metros), imprime una marca especial de llegada (ej: `META:NombreCoche`) y finaliza.
  2. **Clase `GranPremio` (Proceso Padre):** Inicia un proceso independiente por cada coche. Mediante hilos de lectura en paralelo, intercepta la salida de cada vehículo y retransmite en tiempo real el progreso por pantalla. Cuando detecta la marca `META:`, anuncia al ganador y espera a que todos los procesos de coches terminen.
* **Objetivo:** Integrar todos los conceptos del tema: creación masiva de procesos, paso de parámetros, lectura continua de flujos `I/O` sin bloqueo y sincronización de fin de carrera.
* **Pistas y ayuda técnica:**
  * Utiliza un array de `Process` para almacenar las referencias de los 4 vehículos.
  * Para evitar que la lectura de un coche lento bloquee la pantalla de los demás, crea un hilo secundario en el padre (`new Thread(...)`) dedicado a leer el `InputStream` de cada coche de forma independiente.
* **Resultado esperado:** Transmisión en tiempo real en la consola mostrando el avance intercalado de los coches, el anuncio con trofeo del ganador al cruzar la meta y la liberación final de todos los procesos.
