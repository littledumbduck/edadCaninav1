package es.davante.edadcanina.vista;
import android.content.Context;
import android.widget.EditText;
import androidx.databinding.BindingAdapter;
import es.davante.edadcanina.R;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;
public final class AdaptadoresVista {
    private AdaptadoresVista() { }
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
