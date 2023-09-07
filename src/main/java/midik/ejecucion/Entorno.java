package midik.ejecucion;

import java.util.HashMap;
import java.util.Map;

public class Entorno {

    private Map<String, Variable> variables;
    private Map<String, Funcion> funciones;
    private Map<String, Arreglo> arreglos;
    private Entorno padre;

    public Entorno(Entorno padre) {
        this.padre = padre;
        this.variables = new HashMap();
        this.funciones = new HashMap();
        this.arreglos = new HashMap();
    }

    public void setVariable(Variable variable) {
        this.variables.put(variable.getId(), variable);
    }

    public Variable getVariable(String id) {
        Variable var = this.variables.get(id);
        if (var == null && this.padre != null) {
            return this.padre.getVariable(id);
        } else {
            return var;
        }
    }

    public void setArreglo(Arreglo arreglo) {
        this.arreglos.put(arreglo.getId(), arreglo);
    }

    public Arreglo getArreglo(String id) {
        Arreglo arr = this.arreglos.get(id);
        if (arr == null && this.padre != null) {
            return this.padre.getArreglo(id);
        } else {
            return arr;
        }
    }

    public void setFuncion(Funcion funcion) {
        this.funciones.put(funcion.getId(), funcion);
    }

    public Funcion getFuncion(String id) {
        Funcion fun = this.funciones.get(id);
        if (fun == null && this.padre != null) {
            return this.padre.getFuncion(id);
        } else {
            return fun;
        }
    }

    public Map<String, Variable> getVariables() {
        return variables;
    }

    public Map<String, Funcion> getFunciones() {
        return funciones;
    }

    public Map<String, Arreglo> getArreglos() {
        return arreglos;
    }

    public void setVariables(Map<String, Variable> variables) {
        this.variables = variables;
    }

}
