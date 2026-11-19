package es.davante.edadcanina.modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import modelo.EdadCaninaModelo;
import modelo.Validacion;

public class EdadCaninaModeloTest {
    @Test
    public void seis_da_42() {
        EdadCaninaModelo modelo = new EdadCaninaModelo();
        modelo.setEdadTexto("6");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(42, modelo.getEdadHumana());
    }
}
