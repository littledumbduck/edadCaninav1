package es.davante.edadcanina;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import es.davante.edadcanina.controlador.EdadCaninaControlador;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

public class MainActivity extends AppCompatActivity {
    // Claves constantes: un error tipográfico en una clave escrita dos veces no se detecta
    private static final String CLAVE_TEXTO = "edad_texto";
    private static final String CLAVE_CALCULADO = "calculado";
    private EdadCaninaModelo modelo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        modelo = new EdadCaninaModelo();
        if (savedInstanceState != null) {
            restaurarEstado(savedInstanceState);
        }
        binding.setModelo(modelo);
        binding.setControlador(new EdadCaninaControlador(modelo));
    }
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(CLAVE_TEXTO, modelo.getEdadTexto());
        outState.putBoolean(CLAVE_CALCULADO, modelo.getValidacion() != Validacion.SIN_CALCULAR);
    }
    private void restaurarEstado(Bundle estado) {
        modelo.setEdadTexto(estado.getString(CLAVE_TEXTO, ""));
        if (estado.getBoolean(CLAVE_CALCULADO, false)) {
            modelo.calcular();
        }
    }
}