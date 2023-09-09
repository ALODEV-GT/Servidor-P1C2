package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Funcion;
import midik.ejecucion.Instruccion;
import midik.ejecucion.TipoNativo;

public class Principal extends Instruccion {

    private ArrayList<Instruccion> instrucciones;

    public Principal(String linea, Object instrucciones) {
        super(linea);
        this.instrucciones = (ArrayList<Instruccion>) instrucciones;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        Funcion fun = entorno.getFuncion("Principal");
        if (!(fun == null)) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Ya existe una funcion con el nombre " + "Principal"));
        } else {
            entorno.setFuncion(new Funcion("Principal", instrucciones, TipoNativo.CARACTER, null));
        }
        return null;
    }

}
