package midik.miembroSi;

import java.util.ArrayList;
import midik.ejecucion.Instruccion;

public class Caso {

    private Instruccion exp;
    private ArrayList<Instruccion> instrucciones;
    private boolean isDefault;

    public Caso(Object exp, Object instrucciones, boolean isDefault) {
        this.exp = (Instruccion) exp;
        this.instrucciones = (ArrayList<Instruccion>) instrucciones;
        this.isDefault = isDefault;
    }

    public boolean isIsDefault() {
        return isDefault;
    }

    public Instruccion getExp() {
        return exp;
    }

    public ArrayList<Instruccion> getInstrucciones() {
        return instrucciones;
    }
    
    

}
