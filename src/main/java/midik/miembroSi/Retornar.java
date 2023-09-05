package midik.miembroSi;

import midik.ejecucion.Instruccion;

public class Retornar {

    private boolean tieneValor;
    private Instruccion valor;

    public Retornar(boolean tieneValor, Instruccion valor) {
        this.tieneValor = tieneValor;
        this.valor = valor;
    }

    public boolean tieneValor() {
        return tieneValor;
    }

    public Instruccion getValor() {
        return valor;
    }

}
