package midik.Singletons;

import java.util.ArrayList;

public class Consola {

    private static Consola instance;
    private ArrayList<String> lista = new ArrayList<>();

    public static Consola getInstance() {
        if (instance == null) {
            instance = new Consola();
        }
        return instance;
    }

    public void push(String mensaje) {
        this.lista.add(mensaje);
    }

    public void clear() {
        this.lista = new ArrayList<>();
    }

    public ArrayList<String> getMensajes() {
        return this.lista;
    }
}
