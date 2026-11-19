package es.davante.edadcanina.modelo;

public enum Validacion {
    SIN_CALCULAR,    // aún no se ha calculado, o la entrada cambió después
    OK,
    VACIO,
    NO_NUMERO,
    FUERA_DE_RANGO
}