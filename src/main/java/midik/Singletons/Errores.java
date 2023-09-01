package midik.Singletons;

import java.util.ArrayList;

public class Errores {

    private static Errores instance;
    private ArrayList<Error> lista = new ArrayList<>();

    public static Errores getInstance() {
        if (instance == null) {
            instance = new Errores();
        }
        return instance;
    }

    public void push(Error error) {
        this.lista.add(error);
    }

    public void clear() {
        this.lista = new ArrayList<>();
    }

    public boolean hasErrors() {
        return this.lista.size() > 0;
    }

    public ArrayList<Error> getErrors() {
        return this.lista;
    }
}
