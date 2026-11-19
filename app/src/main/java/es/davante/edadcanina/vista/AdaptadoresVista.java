package es.davante.edadcanina.vista;
import android.content.Context;
import android.widget.EditText;
import androidx.databinding.BindingAdapter;
import es.davante.edadcanina.R;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;
/**
 * Atributos XML propios para la vista. Está en el paquete 'vista' y no en 'modelo'
 * porque traduce y pinta: usa R.string (textos según el idioma) y EditText, que son
 * de Android. Así el modelo sigue sin depender de Android y se puede probar con JUnit.
 */
public final class AdaptadoresVista {
    // Clase de utilidades con métodos estáticos: no tiene sentido crear objetos de ella
    private AdaptadoresVista() { }
    // Crea el atributo app:errorValidacion. Debe ser public static para que DataBinding
    // pueda llamarlo sin crear un objeto; recibe la vista y el valor de @{modelo.validacion}
    @BindingAdapter("errorValidacion")
    public static void mostrarErrorValidacion(EditText campo, Validacion validacion) {
        String mensaje = mensajeDeError(campo.getContext(), validacion);
        campo.setError(mensaje); // null quita el error
        if (mensaje != null) {
            campo.requestFocus(); // antes en mostrarError()
        }
    }
    private static String mensajeDeError(Context contexto, Validacion validacion) {
        if (validacion == null) return null;
        switch (validacion) {
            case VACIO:
                return contexto.getString(R.string.error_vacio);
            case NO_NUMERO:
                return contexto.getString(R.string.error_no_numero);
            case FUERA_DE_RANGO:
                return contexto.getString(R.string.error_rango,
                        EdadCaninaModelo.EDAD_MINIMA, EdadCaninaModelo.EDAD_MAXIMA);
            default:
                return null; // SIN_CALCULAR u OK: sin error
        }
    }
}
