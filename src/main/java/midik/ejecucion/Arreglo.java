package midik.ejecucion;

import java.util.ArrayList;

public class Arreglo {

    private String id;
    private TipoNativo tipoNativo;
    private ArrayList<Object> valor;
    private int longitud;
    private int dimensiones;
    private Boolean reasignable;
    private Boolean isKeep;

    public Arreglo(String id, TipoNativo tipoNativo, Object valor, int longitud, int dimensiones, Boolean reasignable, Boolean isKeep) {
        this.id = id;
        this.tipoNativo = tipoNativo;
        this.valor = (ArrayList<Object>) valor;
        this.longitud = longitud;
        this.dimensiones = dimensiones;
        this.reasignable = reasignable;
        this.isKeep = isKeep;
    }

    public Object getElementoArreglo(ArrayList<Integer> indices) {
        ArrayList<Object> principal = (ArrayList<Object>) (this.valor).get(0);
        return this.encontrarElemento(principal, indices, 0);
    }

    private Object encontrarElemento(ArrayList<Object> arreglo, ArrayList<Integer> indices, int indice) {
        if (indice < indices.size() - 1) {
            return encontrarElemento((ArrayList<Object>) arreglo.get(indices.get(indice)), indices, indice + 1);
        } else {
            return arreglo.get(indices.get(indice));
        }
    }

    public String getId() {
        return id;
    }

    public TipoNativo getTipoAsignado() {
        return tipoNativo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor, ArrayList<Integer> indices) {
        ArrayList<Object> principal = (ArrayList<Object>) (this.valor).get(0);
        this.cambiarElemento(principal, indices, 0, valor);
    }

    private void cambiarElemento(ArrayList<Object> arreglo, ArrayList<Integer> indices, int indice, Object valor) {
        if (indice < indices.size() - 1) {
            cambiarElemento((ArrayList<Object>) arreglo.get(indices.get(indice)), indices, indice + 1, valor);
        } else {
            arreglo.set(indices.get(indice), valor);
        }
    }

    public boolean hasTipoAsignado() {
        return this.tipoNativo != null;
    }

    @Override
    public String toString() {
        return "Arreglo{" + "id=" + id + ", tipoNativo=" + tipoNativo + ", valor=" + valor + ", longitud=" + longitud + ", dimensiones=" + dimensiones + ", reasignable=" + reasignable + ", isKeep=" + isKeep + '}';
    }

    public int getLongitud() {
        return longitud;
    }

    public int getDimensiones() {
        return dimensiones;
    }

}
