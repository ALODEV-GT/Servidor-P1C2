package midik.instrucciones;

import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.miembroSi.Retornar;

public class RetornarI extends Instruccion {

    private boolean tieneValor;
    private Instruccion valor;

    public RetornarI(String linea, boolean tieneValor, Object valor) {
        super(linea);
        this.tieneValor = tieneValor;
        this.valor = (Instruccion) valor;
    }

    public boolean tieneValor() {
        return tieneValor;
    }

    public Object getValor() {
        return valor;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        if (tieneValor && this.valor != null) {
            Nativo val = (Nativo) this.valor.ejecutar(entorno);
            return new Retornar(this.tieneValor, val);
        } else {
            return new Retornar(this.tieneValor, null);
        }
    }

}
