package midik.ejecucion;

import java.util.ArrayList;

public class Funcion {

    private String id;
    private ArrayList<Instruccion> instrucciones;
    private TipoNativo tipoNativo;
    private ArrayList<Variable> listaParametros;

    public Funcion(String id, ArrayList<Instruccion> instrucciones, TipoNativo tipoNativo, ArrayList<Variable> listaParametros) {
        this.id = id;
        this.instrucciones = instrucciones;
        this.tipoNativo = tipoNativo;
        this.listaParametros = listaParametros;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Instruccion> getInstrucciones() {
        return instrucciones;
    }

    public TipoNativo getTipoReturn() {
        return tipoNativo;
    }

    public ArrayList<Variable> getListaParametros() {
        return listaParametros;
    }

    public int getParametrosSize() {
        if (this.listaParametros != null) {
            return this.listaParametros.size();
        } else {
            return 0;
        }
    }

    public boolean hasRetorno() {
        return this.tipoNativo != null;
    }

    public boolean hasParametros() {
        return this.listaParametros != null;
    }

    @Override
    public String toString() {
        return "Funcion{" + "id=" + id + ", instrucciones=" + instrucciones + ", tipoNativo=" + tipoNativo + ", listaParametros=" + listaParametros + '}';
    }

}
