package midik.instrucciones;

import java.util.ArrayList;
import midik.ejecucion.Nativo;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.TipoNativo;

public class Mensaje extends Instruccion {

    private ArrayList<Instruccion> instrucciones;

    public Mensaje(String linea, Object instrucciones) {
        super(linea);
        this.instrucciones = (ArrayList<Instruccion>) instrucciones;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        for (Instruccion inst : instrucciones) {
            Object res = inst.ejecutar(entorno);
            if (res instanceof Nativo) {
                if (((Nativo) res).getTipo() == TipoNativo.ENTERO || ((Nativo) res).getTipo() == TipoNativo.BOOLEAN
                        || ((Nativo) res).getTipo() == TipoNativo.CADENA || ((Nativo) res).getTipo() == TipoNativo.DECIMAL
                        || ((Nativo) res).getTipo() == TipoNativo.CARACTER) {
                    System.out.println(((Nativo) res).getValor().toString());
                }
            }
        }
        return null;
    }
}
