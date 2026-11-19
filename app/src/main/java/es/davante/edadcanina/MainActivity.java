package es.davante.edadcanina;
import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import es.davante.edadcanina.databinding.ActivityMainBinding;
import modelo.EdadCaninaModelo;

public class MainActivity extends AppCompatActivity {
    private static final int FACTOR_CANINO = 7; // de momento siguen aquí (paso 3)
    private static final int EDAD_MINIMA = 1;
    private static final int EDAD_MAXIMA = 30;
    // Un único atributo sustituye a ageEdit y resultText: el binding ya conoce todas las vistas
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.btnCalc.setOnClickListener(view -> calcularEdad());
    }
    private void mostrarError(String mensaje) {
        binding.ageInput.setError(mensaje);
        binding.ageInput.requestFocus();
        binding.resultText.setText("");
    }

    private final EdadCaninaModelo modelo = new EdadCaninaModelo(); // atributo nuevo
    // (borra FACTOR_CANINO, EDAD_MINIMA y EDAD_MAXIMA de aquí: ya viven en el modelo)
    private void calcularEdad() {
        modelo.setEdadTexto(binding.ageInput.getText().toString());
        switch (modelo.calcular()) {
            case OK:
                binding.ageInput.setError(null);
                binding.resultText.setText(
                        getString(R.string.tu_edad_format, modelo.getEdadHumana()));
                break;
            case VACIO:
                mostrarError(getString(R.string.error_vacio));
                break;
            case NO_NUMERO:
                mostrarError(getString(R.string.error_no_numero));
                break;
            case FUERA_DE_RANGO:
                mostrarError(getString(R.string.error_rango,
                        EdadCaninaModelo.EDAD_MINIMA, EdadCaninaModelo.EDAD_MAXIMA));
                break;
            default:
                break;
        }
    }
}