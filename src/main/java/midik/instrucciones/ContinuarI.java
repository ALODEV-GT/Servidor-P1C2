package midik.instrucciones;

import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.miembroSi.Continuar;

public class ContinuarI extends Instruccion{

    public ContinuarI(String linea) {
        super(linea);
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        return new Continuar();
    }

}
