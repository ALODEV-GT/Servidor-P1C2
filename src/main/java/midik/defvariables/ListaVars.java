package midik.defvariables;

import java.util.ArrayList;

public class ListaVars {

    private ArrayList<Object> variables;
    private Object expresion;

    public ListaVars(ArrayList<Object> variables, Object expresion) {
        this.variables = variables;
        this.expresion = expresion;
    }

    public ArrayList<Object> getVariables() {
        return variables;
    }

    public Object getExpresion() {
        return expresion;
    }

    public boolean hayExpresion() {
        return this.expresion != null;
    }

}
