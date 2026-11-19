package es.davante.edadcanina.controlador;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import es.davante.edadcanina.modelo.EdadCaninaModelo;
import es.davante.edadcanina.modelo.Validacion;

public class EdadCaninaControladorTest {

    @Test
    public void calcular_y_limpiar_ordenan_al_modelo() {
        EdadCaninaModelo modelo = new EdadCaninaModelo();
        EdadCaninaControlador controlador = new EdadCaninaControlador(modelo);

        modelo.setEdadTexto("6");
        controlador.onCalcularClick();             // simula el clic en Calcular
        assertEquals(Validacion.OK, modelo.getValidacion());
        assertEquals(42, modelo.getEdadHumana());

        controlador.onLimpiarClick();              // simula el clic en Limpiar
        assertEquals("", modelo.getEdadTexto());
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
    }
}