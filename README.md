# 🐶 EdadCanina · v1

Aplicación Android (Java) que calcula la edad "humana" de un perro multiplicando su edad por 7.

Esta es la **versión 1**: funciona, pero no comprueba lo que escribe el usuario. Tu tarea es convertirla en la **versión 2** (rama `v2-validacion`), que valida la entrada y controla los errores sin que la app se cierre.

---

## Objetivos

Al terminar sabrás:

- Detectar por qué una app se cierra (`NumberFormatException`) y evitarlo con `try/catch`.
- Validar datos de entrada: campo vacío, texto no numérico y rango de valores.
- Mostrar errores al usuario con `EditText.setError()`.
- Sustituir "números mágicos" por constantes.
- Llevar los textos a `strings.xml` y usar formatos con varios parámetros.
- Trabajar en una rama de Git distinta de `main`.

---

## Paso 0 · Prepara el proyecto

1. Haz un **fork** de este repositorio (botón *Fork*) y clona tu fork:
   ```bash
   git clone https://github.com/TU_USUARIO/edadCaninav1.git
   ```
2. Ábrelo en Android Studio y ejecútalo en el emulador.

## Paso 1 · Rompe la app (a propósito)

Antes de arreglar nada, comprueba qué falla. Prueba estas entradas y anota qué pasa:

| Entrada | ¿Qué ocurre? |
|---|---|
| *(campo vacío)* | Se cierra: `NumberFormatException` |
| `abc` | Se cierra: `NumberFormatException` |
| `3.5` | Se cierra: `NumberFormatException` |
| `0` | Muestra "tendría 0 años" |
| `-4` | Muestra "tendría -28 años" |
| `99999999999` | Se cierra: `NumberFormatException` (no cabe en un `int`) |
| `400000000` | No se cierra y muestra `-1494967296` (desbordamiento) |

> *Resultados previstos según el código de la v1; confirmar en el emulador.*

> 💡 Cuando la app se cierre, abre el **Logcat** y busca la excepción. ¿Qué método la lanza? ¿Por qué el último caso no cierra la app pero da un resultado absurdo?
>
> ✏️ **Respuesta:** la lanza `Integer.parseInt()`, al convertir el texto en número. El último caso no cierra la app porque 400000000 sí cabe en un `int`, así que `parseInt` no falla. Pero al multiplicar por 7 el resultado (2800000000) supera el máximo de un `int` (2147483647) y Java da un valor incorrecto sin lanzar ninguna excepción.

## Paso 2 · Crea la rama de trabajo

```bash
git checkout -b v2-validacion
```

Todo lo que hagas a partir de aquí irá en esta rama; `main` se queda como está.

## Paso 3 · Corrige los fallos que ya trae la v1

Antes de añadir nada nuevo, limpia lo que hay:

1. **`activity_main.xml`**: busca la etiqueta de cierre `</TextView>>` y quita el `>` que sobra.
2. **`strings.xml`**: en `tu_edad_format` hay una falta de ortografía ("fuero"). Corrígela.
3. **Texto escrito "a fuego"**: el `android:hint="Ejemplo: 6"` del `EditText` está directamente en el layout. Llévalo a `strings.xml` con el nombre `hint_edad` y úsalo con `@string/hint_edad`.
   > 💡 Android Studio marca este aviso en amarillo (*Hardcoded string*). Prueba `Alt + Enter` encima.
4. **`MainActivity.java`**: hay tres `import` que no se usan (`Insets`, `ViewCompat`, `WindowInsetsCompat`). Elimínalos (`Ctrl + Alt + O`).

## Paso 4 · Añade los mensajes de error

En `res/values/strings.xml` añade tres textos nuevos:

```xml
<string name="error_vacio">Introduce la edad de tu perro</string>
<string name="error_no_numero">Introduce un número entero válido</string>
<string name="error_rango">La edad debe estar entre %1$d y %2$d años</string>
```

> ❓ ¿Qué significan `%1$d` y `%2$d`? ¿En qué orden se rellenan?
>
> ✏️ **Respuesta:** son marcadores de formato para números enteros. El número indica la posición del argumento: `%1$d` toma el primer valor que se pasa a `getString` (`EDAD_MINIMA`) y `%2$d` el segundo (`EDAD_MAXIMA`). Como usan posición, el orden de los valores se fija por el número y no por el orden en que aparecen en el texto.

## Paso 5 · Restringe la entrada desde el layout

En el `EditText` (`@+id/age_input`) añade:

```xml
android:inputType="number"
android:maxLength="2"
android:gravity="center"
android:importantForAutofill="no"
```

| Atributo | Para qué sirve |
|---|---|
| `inputType="number"` | Muestra el teclado numérico: sin letras, sin signo menos ni decimales. |
| `maxLength="2"` | No deja escribir más de 2 cifras. |
| `gravity="center"` | Centra el número en el campo. |
| `importantForAutofill="no"` | Evita que el sistema ofrezca autocompletar. |

> ⚠️ Esto **no sustituye** a la validación en Java: el campo puede quedarse vacío y nunca conviene fiarse solo de la interfaz. Por eso seguimos con el código.

## Paso 6 · Sustituye los números mágicos por constantes

Al principio de la clase `MainActivity` declara:

```java
private static final int FACTOR_CANINO = 7;
private static final int EDAD_MINIMA = 1;
private static final int EDAD_MAXIMA = 30; // ningún perro supera esta edad
```

y usa `FACTOR_CANINO` en lugar del `7`.

## Paso 7 · Convierte las vistas en atributos de la clase

Ahora `ageEdit` y `resultText` son variables locales de `onCreate()`. Como vas a usarlas desde otros métodos, decláralas como atributos:

```java
private EditText ageEdit;
private TextView resultText;
```

y en `onCreate()` asígnalas **sin volver a escribir el tipo**:

```java
ageEdit = findViewById(R.id.age_input);
resultText = findViewById(R.id.result_text);
```

> ❓ ¿Qué pasaría si dejaras `EditText ageEdit = findViewById(...)` dentro de `onCreate()`?
>
> ✏️ **Respuesta:** esa línea crea una variable local que solo existe dentro de `onCreate()`. El atributo de la clase se queda en `null`, y al pulsar el botón `calcularEdad()` lanza `NullPointerException` al usarlo.

## Paso 8 · Saca la lógica del botón a un método

El `setOnClickListener` debe quedar así de corto:

```java
button1.setOnClickListener(view -> calcularEdad());
```

Toda la lógica pasa a un método nuevo: `private void calcularEdad()`.

## Paso 9 · Crea un método para mostrar errores

```java
private void mostrarError(String mensaje) {
    // 1. Mostrar el mensaje en el EditText (setError)
    // 2. Poner el foco en el EditText (requestFocus)
    // 3. Borrar el resultado anterior del TextView
}
```

## Paso 10 · Valida la entrada en `calcularEdad()`

Implementa el método siguiendo este orden. En cuanto algo falle, llama a `mostrarError(...)` y sal con `return`:

1. **Leer** el texto del `EditText` quitando espacios (`trim()`).
2. **¿Está vacío?** → `error_vacio`. Recuerda que `Integer.parseInt("")` lanza `NumberFormatException`.
3. **Convertir a `int`** dentro de un `try/catch (NumberFormatException e)` → si falla, `error_no_numero`.
4. **¿Está en rango?** Si `age < EDAD_MINIMA || age > EDAD_MAXIMA` → `error_rango`. Para rellenar `%1$d` y `%2$d`:
   ```java
   getString(R.string.error_rango, EDAD_MINIMA, EDAD_MAXIMA)
   ```
5. **Todo correcto**: quita el error del campo (`setError(null)`), calcula `age * FACTOR_CANINO` y muestra el resultado con `tu_edad_format`.

> ❓ ¿Por qué limitar la edad a 30 evita también el desbordamiento que viste en el Paso 1?
>
> ✏️ **Respuesta:** con edades entre 1 y 30, el mayor resultado es 30 × 7 = 210, muy lejos del máximo de un `int` (2147483647). Como la comprobación de rango se hace antes de multiplicar, la operación nunca desborda.

## Paso 11 · Vuelve a probar

Comprueba que **la app nunca se cierra**:

| Entrada | Resultado esperado |
|---|---|
| *(vacío)* | Error: "Introduce la edad de tu perro" |
| `0` | Error: "La edad debe estar entre 1 y 30 años" |
| `31` | Error de rango |
| `1` | "Si tu perro fuera humano tendría 7 años" |
| `30` | "Si tu perro fuera humano tendría 210 años" |

> 💡 Con el teclado numérico no puedes escribir `abc` ni `-4`. ¿Cómo comprobarías que el `try/catch` funciona? (Pista: quita `inputType` un momento.)
>
> ✏️ **Respuesta:** quitando temporalmente `android:inputType="number"` se puede escribir texto. Al introducir `abc`, `parseInt` lanza `NumberFormatException`, el `catch` lo captura y se muestra `error_no_numero` sin que la app se cierre.

## Paso 12 · Guarda y sube tu trabajo

```bash
git add .
git commit -m "Validación de la entrada y control de errores aritméticos"
git push -u origin v2-validacion
```

Comprueba en GitHub que tu fork tiene ahora dos ramas: `main` y `v2-validacion`.

---

## ✅ Lista de comprobación

- [x] La app no se cierra con ninguna entrada. *(comprobar en el emulador)*
- [x] Todos los textos están en `strings.xml` (ningún aviso *Hardcoded string*).
- [x] No quedan números mágicos en `MainActivity`.
- [x] `onCreate()` solo prepara la pantalla; la lógica está en `calcularEdad()`.
- [x] El trabajo está en la rama `v2-validacion`, no en `main`.

---

# 🐶 EdadCaninaMVC · v3 (rama `v3-mvc`)

La v3 hace exactamente lo mismo que la v2, pero cada responsabilidad está en su pieza gracias a **DataBinding** y al patrón **MVC**. `MainActivity` ya no tiene ningún `findViewById`, `setText` ni `setOnClickListener`.

## Estructura

| Pieza | Dónde | Qué hace |
|---|---|---|
| Modelo | `modelo/EdadCaninaModelo`, `modelo/Validacion` | Guarda la entrada y el resultado, valida, calcula y avisa con `notifyPropertyChanged`. No importa nada de `android.*` |
| Vista | `activity_main.xml`, `vista/AdaptadoresVista` | Se pinta a partir del modelo con `@{}` y `@={}`; el error se muestra con el atributo propio `app:errorValidacion` |
| Controlador | `controlador/EdadCaninaControlador` | Convierte los clics de Calcular y Limpiar en órdenes al modelo |
| Activity | `MainActivity` | Crea las tres piezas, las conecta y guarda el estado al girar |

## Pasos y etiquetas

| Etiqueta | Paso |
|---|---|
| `paso-1` / `paso-2` | Activar DataBinding y envolver el layout en `<layout>` (mismo commit) |
| `paso-3` | Modelo `EdadCaninaModelo` y enum `Validacion`, con pruebas JUnit |
| `paso-4` | Modelo observable y enlace de datos |
| `paso-5` | Error con `@BindingAdapter` propio |
| `paso-6` | Controlador y botón Limpiar |
| `paso-7` | Conservar el estado al girar |

## Pruebas

En `app/src/test`, sin emulador: 9 del modelo (caso correcto, vacío, no numérico, fuera de rango, límites 1 y 30, caducidad del resultado, avisos del modelo y los dos casos del reto) y 1 del controlador.

## Reto realizado: casos raros

**¿Qué hace la app con `05` o con `+5`?** He decidido que los dos son **válidos**: `05` es un 5 con un cero delante y `+5` es un 5 con signo positivo, así que los dos deben dar 35 años.

**Cómo lo he resuelto:** `Integer.parseInt` ya acepta los dos formatos, así que el modelo no necesitaba cambios. Lo que he hecho es dejar la decisión fijada con dos pruebas (`reto_cero_a_la_izquierda_es_valido` y `reto_signo_mas_es_valido`). Así, si alguien cambia la validación en el futuro, las pruebas avisarán de que el comportamiento cambia.

En la app, `+5` no se puede escribir porque el `EditText` tiene `inputType="number"`. Aun así lo pruebo en el modelo porque el modelo no debe fiarse de la vista: es independiente de la pantalla y tiene que decidir por su cuenta qué entrada es válida.

## Explícalo con tus palabras

**1. ¿Qué diferencia hay entre `@{...}` y `@={...}`?**

`@{...}` es un enlace en un solo sentido, del modelo a la vista: la vista lee el dato y se repinta cuando el modelo avisa. Lo uso en el resultado (`@{@string/tu_edad_format(modelo.edadHumana)}`) y en su visibilidad. `@={...}` es un enlace en los dos sentidos: además de leer del modelo, lo que escribe el usuario llega solo al modelo, porque el binding llama a `setEdadTexto()` cada vez que cambia el texto. Lo uso en el `EditText` de la edad, y por eso ya no hace falta `getText()`. Por ese viaje de ida y vuelta, `setEdadTexto()` comprueba si el texto ha cambiado antes de avisar; si no, se formaría un bucle.

**2. ¿Qué ocurre, paso a paso, desde que pulsas Calcular hasta que aparece «42»?**

1. Al escribir «6», el enlace `@={modelo.edadTexto}` llama a `modelo.setEdadTexto("6")`.
2. Al pulsar Calcular, la expresión `@{() -> controlador.onCalcularClick()}` del botón llama al controlador.
3. El controlador solo da la orden: `modelo.calcular()`.
4. El modelo valida (no está vacío, es un número, está entre 1 y 30) y calcula 6 × 7 = 42. Lo guarda con `setEdadHumana(42)` y `setValidacion(OK)`, y cada setter avisa con `notifyPropertyChanged(BR.edadHumana)` y `notifyPropertyChanged(BR.validacion)`.
5. El binding, que se suscribió al modelo con `binding.setModelo(modelo)`, recibe los avisos y vuelve a evaluar solo las expresiones que usan esas propiedades.
6. El `TextView` del resultado recibe el texto «…tendría 42 años» y pasa a `VISIBLE` porque la validación es `OK`. El adaptador `errorValidacion` recibe `OK` y quita el error del campo.

**3. ¿Qué cambiarías para que el factor fuera 6 en vez de 7, y por qué solo tocas un fichero?**

Solo cambiaría la constante `FACTOR_CANINO = 7` por `6` en `EdadCaninaModelo.java`. Es una regla de negocio, y en MVC las reglas viven únicamente en el modelo: la vista solo muestra `edadHumana` sin saber cómo se calcula, el controlador solo da la orden de calcular y la Activity solo conecta las piezas. Como ningún otro fichero repite ese 7, no hay que tocar nada más. En la v2, en cambio, el cálculo estaba mezclado en `MainActivity` con el código que pinta la pantalla.