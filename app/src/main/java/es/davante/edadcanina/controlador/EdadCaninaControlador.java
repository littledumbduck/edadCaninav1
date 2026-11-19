package es.davante.edadcanina.controlador;

import es.davante.edadcanina.modelo.EdadCaninaModelo;

/**
 * Convierte cada acción del usuario en una orden al modelo. De esta forma,
 * podemos separar el código y poder modificarlo más rápido y mejor.
 */

public class EdadCaninaControlador {

    private final EdadCaninaModelo modelo;

    public EdadCaninaControlador(EdadCaninaModelo modelo) {
        this.modelo = modelo;
    }

    public void onCalcularClick() {
        modelo.calcular();
    }

    public void onLimpiarClick() {
        modelo.limpiar();
    }
}