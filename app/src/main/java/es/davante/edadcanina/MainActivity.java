package es.davante.edadcanina;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import es.davante.edadcanina.databinding.ActivityMainBinding;
import es.davante.edadcanina.modelo.EdadCaninaModelo;

public class MainActivity extends AppCompatActivity {

    // El binding sustituye a todos los findViewById: conoce todas las vistas con id
    private ActivityMainBinding binding;

    // Las reglas y los datos viven en el modelo; la Activity solo pinta
    private final EdadCaninaModelo modelo = new EdadCaninaModelo();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setModelo(modelo);
        binding.btnCalc.setOnClickListener(view -> modelo.calcular());
    }
}