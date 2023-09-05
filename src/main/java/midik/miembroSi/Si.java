package midik.miembroSi;

import java.util.ArrayList;
import midik.ejecucion.Instruccion;

public class Si {

    private Instruccion condicion;
    private ArrayList<Instruccion> instrucciones;

    public Si(Object condicion, Object instrucciones) {
        this.condicion = (Instruccion) condicion;
        this.instrucciones = (ArrayList<Instruccion>) instrucciones;
    }

    public Instruccion getCondicion() {
        return condicion;
    }

    public ArrayList<Instruccion> getInstrucciones() {
        return instrucciones;
    }
    
    

}
