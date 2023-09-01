package midik.arbol;

import java.util.ArrayList;

public class NodoAST {

    private String etiqueta;
    private ArrayList<Object> hijos = new ArrayList<>();
    private String linea;

    public void agregarHijo(Object hijo) {
        this.hijos.add(hijo);
    }

    public NodoAST(String etiqueta, ArrayList<Object> hijos, String linea) {
        this.etiqueta = etiqueta;
        this.hijos = hijos;
        this.linea = linea;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    public String getLinea() {
        return linea;
    }

    public void setLinea(String linea) {
        this.linea = linea;
    }

    public ArrayList<Object> getHijos() {
        return this.hijos;
    }

}
