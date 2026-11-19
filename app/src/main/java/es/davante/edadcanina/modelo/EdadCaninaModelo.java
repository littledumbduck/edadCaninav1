package es.davante.edadcanina.modelo;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import java.util.Objects;

import es.davante.edadcanina.BR;

/**
 * Estado y reglas de la app. No importa nada de android.* para poder probarlo
 * con JUnit sin emulador. Hereda de BaseObservable para poder avisar a la vista.
 */
public class EdadCaninaModelo extends BaseObservable {

    // Reglas de negocio. Públicas para que la vista pueda montar el mensaje de rango
    // sin repetir los números.
    public static final int FACTOR_CANINO = 7;
    public static final int EDAD_MINIMA = 1;
    public static final int EDAD_MAXIMA = 30;   // ningún perro supera esta edad

    private String edadTexto = "";
    private int edadHumana = 0;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    @Bindable
    public String getEdadTexto() {
        return edadTexto;
    }

    public void setEdadTexto(String nuevo) {
        if (nuevo == null) nuevo = "";
        // Con @={} la vista llama aquí y el aviso hace que la vista vuelva a escribir
        // el texto, lo que volvería a llamar aquí. Si no cambió, cortamos el bucle.
        if (nuevo.equals(edadTexto)) return;
        edadTexto = nuevo;
        notifyPropertyChanged(BR.edadTexto);
        // Regla de negocio: si la entrada cambia, el resultado anterior deja de valer
        setValidacion(Validacion.SIN_CALCULAR);
    }

    @Bindable
    public int getEdadHumana() {
        return edadHumana;
    }

    // Privado: solo calcular() puede cambiar el resultado
    private void setEdadHumana(int nueva) {
        if (nueva == edadHumana) return;   // sin cambio, sin aviso
        edadHumana = nueva;
        notifyPropertyChanged(BR.edadHumana);
    }

    @Bindable
    public Validacion getValidacion() {
        return validacion;
    }

    // Privado: solo calcular() y setEdadTexto() pueden cambiar la validación
    private void setValidacion(Validacion nueva) {
        if (Objects.equals(nueva, validacion)) return;
        validacion = nueva;
        notifyPropertyChanged(BR.validacion);
    }

    /** Valida la entrada y, si es correcta, calcula. Devuelve qué ha pasado, no un mensaje. */
    public Validacion calcular() {
        String texto = edadTexto.trim();

        if (texto.isEmpty()) {
            setValidacion(Validacion.VACIO);
            return validacion;
        }

        int edad;
        try {
            edad = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            setValidacion(Validacion.NO_NUMERO);
            return validacion;
        }

        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) {
            setValidacion(Validacion.FUERA_DE_RANGO);
            return validacion;
        }

        setEdadHumana(edad * FACTOR_CANINO);
        setValidacion(Validacion.OK);
        return validacion;
    }
}