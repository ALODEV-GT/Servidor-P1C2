package midik.Singletons;

import archivos.ManejoArchivos;
import java.util.ArrayList;
import midik.musica.ListaReproduccion;
import midik.musica.Pista;

public class Biblioteca {

    private static Biblioteca instance;
    private ArrayList<Pista> pistas = new ArrayList<>();
    private ArrayList<ListaReproduccion> listasReproduccion = new ArrayList<>();

    private Biblioteca() {
        ManejoArchivos ma = new ManejoArchivos();
        this.pistas = ma.readPistasActivacion("pistas.bin");
        this.listasReproduccion = ma.readListasActivacion("listas.bin");
    }

    public static Biblioteca getInstance() {
        if (instance == null) {
            instance = new Biblioteca();
        }
        return instance;
    }

    public ArrayList<Pista> getPistas() {
        return pistas;
    }

    public ArrayList<ListaReproduccion> getListasReproduccion() {
        return listasReproduccion;
    }

    public ListaReproduccion getListaReproduccion(int indiceLR) {
        return listasReproduccion.get(indiceLR);
    }

    public void guardarPista(Pista nuevaPista) {
        this.pistas.add(nuevaPista);
        ManejoArchivos ma = new ManejoArchivos();
        ma.guardarPistasActivacion("pistas.bin", this.pistas);
    }

    public void editarPista(Pista nuevaPista, int indexPista) {
        this.pistas.set(indexPista, nuevaPista);
        ManejoArchivos ma = new ManejoArchivos();
        ma.guardarPistasActivacion("pistas.bin", this.pistas);
    }

    public void eliminarPista(int indicePista) {
        this.pistas.remove(indicePista);
        ManejoArchivos ma = new ManejoArchivos();
        ma.guardarPistasActivacion("pistas.bin", this.pistas);
    }

    public void guardarlistaReproduccion(ListaReproduccion nuevaLR) {
        this.listasReproduccion.add(nuevaLR);
        ManejoArchivos ma = new ManejoArchivos();
        ma.guardarListasActivacion("listas.bin", this.listasReproduccion);
    }

    public void eliminarListaReproduccion(int indexLR) {
        this.listasReproduccion.remove(indexLR);
        ManejoArchivos ma = new ManejoArchivos();
        ma.guardarListasActivacion("listas.bin", this.listasReproduccion);
    }

    public void agregarPistaALista(Pista pista, int indexLR) {
        ListaReproduccion lista = this.listasReproduccion.get(indexLR);
        lista.agregarPista(pista);
        ManejoArchivos ma = new ManejoArchivos();
        ma.guardarListasActivacion("listas.bin", this.listasReproduccion);
    }

    public void eliminarPistaDeLista(int indexLR, int indexPista) {
        ListaReproduccion lista = this.listasReproduccion.get(indexLR);
        lista.eliminarPista(indexPista);
        ManejoArchivos ma = new ManejoArchivos();
        ma.guardarListasActivacion("listas.bin", this.listasReproduccion);
    }

    public Pista getPistaLista(int indexLR, int indexPista) {
        ListaReproduccion lista = this.listasReproduccion.get(indexLR);
        return lista.getPista(indexPista);
    }

}
