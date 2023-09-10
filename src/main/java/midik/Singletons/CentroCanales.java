package midik.Singletons;

import java.util.HashMap;
import java.util.Map;
import midik.musica.Canal;
import midik.musica.Nota;
import midik.musica.Pista;

public class CentroCanales {

    private static CentroCanales instance;
    private Map<Integer, Canal> canales = new HashMap<>();

    public static CentroCanales getInstance() {
        if (instance == null) {
            instance = new CentroCanales();
        }
        return instance;
    }

    public void agregarNota(Nota nota) {
        Canal can = this.canales.get(nota.getCanal());
        if (can != null) {
            //Agregar al canal
            can.agregarNota(nota);
        } else {
            //Crear el canal y agregar
            this.canales.put(nota.getCanal(), new Canal(nota.getCanal()));
            Canal canal = this.canales.get(nota.getCanal());
            canal.agregarNota(nota);
        }
    }

    public void clear() {
        this.canales = new HashMap<>();
    }

    public Map<Integer, Canal> getCanales() {
        return this.canales;
    }
    
    public void guardarPista(String nombre, String codigoFuente) {
        Pista pista = new Pista(nombre, this.canales, codigoFuente);
        Biblioteca.getInstance().guardarPista(pista);
    }
    
    public void editarPista(String nombre, String codigoFuente, int indexPista) {
        Pista pista = new Pista(nombre, this.canales, codigoFuente);
        Biblioteca.getInstance().editarPista(pista, indexPista);
    }

}
