package midik.instrucciones;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Funcion;
import midik.ejecucion.Instruccion;
import midik.ejecucion.TipoNativo;
import midik.ejecucion.Variable;

public class DeclaracionFuncion extends Instruccion {

    private String id;
    private ArrayList<Instruccion> instrucciones;
    private TipoNativo tipoRetorno;
    private ArrayList<Variable> parametros;

    public DeclaracionFuncion(String linea, Object id, Object instrucciones, Object tipoRetorno, Object parametros) {
        super(linea);
        this.id = (String) id;
        this.instrucciones = (ArrayList<Instruccion>) instrucciones;
        this.tipoRetorno = (TipoNativo) tipoRetorno;
        this.parametros = (ArrayList<Variable>) parametros;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        Funcion fun = entorno.getFuncion(id);
        if (fun == null) {
            //Validacion nombre parametros unicos
            if (parametros != null) {
                boolean unicos = this.verificarNombresUnicos(parametros);
                if (!unicos) {
                    Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Verifica que el nombre de los parametros sea diferente en la declaracion de la funcion " + id));
                    return null;
                }
            }
            entorno.setFuncion(new Funcion(id, instrucciones, tipoRetorno, parametros));
        } else {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Ya existe una funcion con el nombre " + id));
        }
        return null;
    }

    private boolean verificarNombresUnicos(ArrayList<Variable> parametros) {
        Set<String> nombresUnicos = new HashSet<>();
        for (Variable var : parametros) {
            if (nombresUnicos.contains(var.getId())) {
                return false;
            }
            nombresUnicos.add(var.getId());
        }
        return true;
    }

}
