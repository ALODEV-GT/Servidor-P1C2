package midik.musica;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import midik.Singletons.CentroCanalesThread;

public class Reproductor {

    private Map<Integer, Canal> canales;
    private ArrayList<CanalThread> canalesPista = new ArrayList<>();

    public Reproductor(Map<Integer, Canal> canales) {
        this.canales = canales;
        this.construirPista();
    }

    private void construirPista() {
        Iterator<Integer> interatorC = this.canales.keySet().iterator();
        while (interatorC.hasNext()) {
            Integer key = interatorC.next();
            Canal valor = this.canales.get(key);
            CanalThread nuevoCanal = new CanalThread();
            nuevoCanal.agregarMusica(valor.getNotas(), valor.getDuraciones());
            canalesPista.add(nuevoCanal);
        }
    }

    public void reproducir() {
        for (CanalThread canal : this.canalesPista) {
            canal.start();
        }
    }
}
