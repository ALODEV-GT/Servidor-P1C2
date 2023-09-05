package midik.instrucciones;

import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.miembroSi.Salir;

public class SalirI extends Instruccion{

    public SalirI(String linea) {
        super(linea);
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        return new Salir();
    }

}
