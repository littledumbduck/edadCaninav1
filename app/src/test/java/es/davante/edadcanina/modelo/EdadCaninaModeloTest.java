package es.davante.edadcanina.modelo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import androidx.databinding.Observable;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import es.davante.edadcanina.BR;

/**
 * Pruebas del modelo. Se ejecutan en el ordenador, sin emulador, porque el modelo
 * no depende de Android: esa es la ventaja de haber sacado las reglas de la Activity.
 */
public class EdadCaninaModeloTest {

    private EdadCaninaModelo modelo;

    @Before
    public void setUp() {
        // Modelo nuevo en cada prueba para que ninguna dependa del estado de otra
        modelo = new EdadCaninaModelo();
    }

    @Test
    public void seis_da_42() {
        modelo.setEdadTexto("6");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(42, modelo.getEdadHumana());
    }

    @Test
    public void vacio_da_VACIO() {
        // Solo espacios también cuenta como vacío porque calcular() hace trim()
        modelo.setEdadTexto("   ");
        assertEquals(Validacion.VACIO, modelo.calcular());
    }

    @Test
    public void texto_da_NO_NUMERO() {
        // El teclado numérico no deja escribir letras, pero el modelo no debe fiarse de la vista
        modelo.setEdadTexto("abc");
        assertEquals(Validacion.NO_NUMERO, modelo.calcular());
    }

    @Test
    public void cero_y_31_dan_FUERA_DE_RANGO() {
        modelo.setEdadTexto("0");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
        modelo.setEdadTexto("31");
        assertEquals(Validacion.FUERA_DE_RANGO, modelo.calcular());
    }

    @Test
    public void limites_1_y_30_son_validos() {
        // Los extremos del rango: aquí se detectaría un < puesto donde iba un <=
        modelo.setEdadTexto("1");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(7, modelo.getEdadHumana());

        modelo.setEdadTexto("30");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(210, modelo.getEdadHumana());
    }

    @Test
    public void cambiar_el_texto_caduca_el_resultado() {
        // Regla de negocio: un resultado calculado con otra entrada ya no vale
        modelo.setEdadTexto("6");
        modelo.calcular();
        modelo.setEdadTexto("7");
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }

    @Test
    public void calcular_avisa_de_los_cambios() {
        // Sin estos avisos la pantalla no se repintaría: el binding solo escucha, no vigila
        List<Integer> avisos = new ArrayList<>();
        modelo.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                avisos.add(propertyId);
            }
        });

        modelo.setEdadTexto("6");
        modelo.calcular();

        assertTrue(avisos.contains(BR.edadTexto));
        assertTrue(avisos.contains(BR.edadHumana));
        assertTrue(avisos.contains(BR.validacion));
    }

    @Test
    public void reto_cero_a_la_izquierda_es_valido() {
        // Decisión del reto: "05" es un 5 escrito con un cero delante; no hay motivo para rechazarlo
        modelo.setEdadTexto("05");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(35, modelo.getEdadHumana());
    }

    @Test
    public void reto_signo_mas_es_valido() {
        // Decisión del reto: "+5" sigue siendo 5. El teclado numérico no deja escribirlo,
        // pero el modelo no depende de la vista, así que lo decide y lo prueba por su cuenta
        modelo.setEdadTexto("+5");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(35, modelo.getEdadHumana());
    }
}