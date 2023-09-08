package midik.Singletons;

import java.util.ArrayList;
import midik.musica.CanalThread;

public class CentroCanalesThread {

    private static CentroCanalesThread instance;
    private ArrayList<CanalThread> canales = new ArrayList<>();

    public static CentroCanalesThread getInstance() {
        if (instance == null) {
            instance = new CentroCanalesThread();
        }
        return instance;
    }

    public void agregarCanalThread(CanalThread canal) {
        this.canales.add(canal);
    }

    public void clear() {
        this.canales = new ArrayList<>();
    }

    public ArrayList<CanalThread> getCanales() {
        return canales;
    }

    public void reproducirCanales() {
        for (CanalThread canal : this.canales) {
            canal.start();
        }
    }

}
