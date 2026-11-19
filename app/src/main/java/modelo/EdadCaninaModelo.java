package modelo;

public class EdadCaninaModelo {
    public static final int FACTOR_CANINO = 7;
    public static final int EDAD_MINIMA = 1;
    public static final int EDAD_MAXIMA = 30;

    private String edadTexto = "";
    private int edadHumana = 0;
    private Validacion validacion = Validacion.SIN_CALCULAR;

    public String getEdadTexto() { return edadTexto; }
    public void setEdadTexto(String nuevo) {
        if (nuevo == null) nuevo = "";
        edadTexto = nuevo;
        // Regla de negocio: si la entrada cambia, el resultado anterior deja de valer
        validacion = Validacion.SIN_CALCULAR;
    }

    public int getEdadHumana() { return edadHumana; }
    public Validacion getValidacion() { return validacion; }


    public Validacion calcular() {
        String texto = edadTexto.trim(); // v2 L36
        if (texto.isEmpty()) { // v2 L38-L41
            validacion = Validacion.VACIO;
            return validacion;
        }
        int edad;
        try { // v2 L43-L49
            edad = Integer.parseInt(texto);
        } catch (NumberFormatException e) {
            validacion = Validacion.NO_NUMERO;
            return validacion;
        }
        if (edad < EDAD_MINIMA || edad > EDAD_MAXIMA) { // v2 L51-L54
            validacion = Validacion.FUERA_DE_RANGO;
            return validacion;
        }
        edadHumana = edad * FACTOR_CANINO; // v2 L57
        validacion = Validacion.OK;
        return validacion;
    }



}
