package es.davante.edadcanina.modelo;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class EdadCaninaModeloTest {

    private EdadCaninaModelo modelo;

    @Before
    public void setUp() {
        modelo = new EdadCaninaModelo();   // modelo nuevo en cada prueba
    }

    @Test
    public void seis_da_42() {
        modelo.setEdadTexto("6");
        assertEquals(Validacion.OK, modelo.calcular());
        assertEquals(42, modelo.getEdadHumana());
    }

    @Test
    public void vacio_da_VACIO() {
        modelo.setEdadTexto("   ");
        assertEquals(Validacion.VACIO, modelo.calcular());
    }

    @Test
    public void texto_da_NO_NUMERO() {
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
    public void cambiar_el_texto_caduca_el_resultado() {
        modelo.setEdadTexto("6");
        modelo.calcular();
        modelo.setEdadTexto("7");
        assertEquals(Validacion.SIN_CALCULAR, modelo.getValidacion());
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