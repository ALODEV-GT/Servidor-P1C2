package midik.musica;

import java.io.Serializable;
import java.util.ArrayList;

public class ListaReproduccion implements Serializable {

    private String nombreLista;
    private ArrayList<Pista> pistas = new ArrayList<>();

    public ListaReproduccion(String nombreLista) {
        this.nombreLista = nombreLista;
    }

    public void agregarPista(Pista pista) {
        this.pistas.add(pista);
    }

    public Pista getPista(int indexPista) {
        return this.pistas.get(indexPista);
    }

    public void eliminarPista(int indexPista) {
        this.pistas.remove(indexPista);
    }

    public String getNombreLista() {
        return nombreLista;
    }

    public ArrayList<Pista> getPistas() {
        return pistas;
    }

}
