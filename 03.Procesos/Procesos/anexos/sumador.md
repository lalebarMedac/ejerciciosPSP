# Explicación línea a línea de `Sumador.java`

Este programa recibe dos números como argumentos, los suma y muestra el resultado. Está preparado para ejecutarse como proceso hijo de otro programa Java.

```java
public class Sumador {
```
- Declara una clase pública llamada `Sumador`.
- El archivo debe llamarse `Sumador.java`.

```java
public static void main(String[] args) {
```
- Es el punto de entrada del programa.
- `public`: permite que la JVM acceda al método.
- `static`: no necesitamos crear un objeto para ejecutarlo.
- `void`: no devuelve ningún valor.
- `String[] args`: array que contiene los argumentos recibidos desde la línea de comandos.

```java
if (args.length < 2) {
```
- Comprueba si se han recibido menos de dos argumentos.
- `args.length` indica cuántos argumentos se han recibido.
- Si faltan argumentos, no podemos realizar la suma.

```java
System.err.println("Se requieren 2 números como argumentos.");
```
- Muestra un mensaje de error.
- `System.err` representa la salida estándar de error.
- `println()` imprime el mensaje y añade un salto de línea.

```java
System.exit(1);
```
- Finaliza el proceso Java actual.
- El valor `1` indica convencionalmente que se ha producido un error.
- El valor `0` suele utilizarse para indicar una finalización correcta.

```java
}
```
- Cierra el bloque del `if`.

```java
int n1 = Integer.parseInt(args[0]);
```
- Declara la variable entera `n1`.
- `args[0]` obtiene el primer argumento.
- `Integer.parseInt()` convierte la cadena en un número entero.
- Por ejemplo, convierte `"15"` en `15`.

```java
int n2 = Integer.parseInt(args[1]);
```
- Declara la variable entera `n2`.
- Obtiene el segundo argumento y lo convierte en un número entero.

```java
int suma = n1 + n2;
```
- Suma los valores de `n1` y `n2`.
- Guarda el resultado en la variable `suma`.

```java
System.out.println("Suma realizada por el proceso hijo: " + suma);
```
- Muestra el resultado de la operación.
- El operador `+` concatena el texto con el valor numérico.

```java
}
}
```
- La primera llave cierra el método `main`.
- La segunda llave cierra la clase `Sumador`.

## Ejemplo de ejecución

```bash
java Sumador 15 25
```

Resultado:

```text
Suma realizada por el proceso hijo: 40
```

**Importante:** `args` contiene cadenas de texto, incluso cuando los argumentos representan números. Por eso es necesario utilizar `Integer.parseInt()`.