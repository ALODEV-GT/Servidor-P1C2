package midik.instrucciones;

import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Variable;

public class Id extends Instruccion {

    private String id;

    public Id(String linea, String id) {
        super(linea);
        this.id = id;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        Variable var = entorno.getVariable(this.id);
        if (var != null) {
            return var.getValor();
        } else {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "No se encontro ninguna variable con el id: " + this.id));
            return null;
        }
    }

}
