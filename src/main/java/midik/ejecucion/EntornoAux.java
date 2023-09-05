package midik.ejecucion;

import java.util.ArrayList;

public class EntornoAux {

    private static EntornoAux instance;
    private ArrayList<Object> lista;

    public EntornoAux() {
        this.lista = new ArrayList<>();
    }

    public static EntornoAux getInstance() {
        if (instance == null) {
            instance = new EntornoAux();
        }
        return instance;
    }

    public boolean estoyEjecutandoFuncion() {
        return this.lista.size() > 0;
    }

    public void inicioEjecucionFuncio() {
        this.lista.add(true);
    }

    public void finEjecucionFuncion() {
        if (!lista.isEmpty()) {
            int indiceUltimoElemento = lista.size() - 1;
            lista.remove(indiceUltimoElemento);
        }
    }

}
