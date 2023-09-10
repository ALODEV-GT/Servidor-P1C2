package midik.musica;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Map;

public class Pista implements Serializable {

    private String nombre;
    private Map<Integer, Canal> canales;
    private String codigoFuente;

    public Pista(String nombre, Map<Integer, Canal> canales, String codigoFuente) {
        this.nombre = nombre;
        this.canales = canales;
        this.codigoFuente = codigoFuente;
    }

    public String getNombre() {
        return nombre;
    }

    public String getCodigoFuente() {
        return codigoFuente;
    }

    public Map<Integer, Canal> getCanales() {
        return canales;
    }

    public String getDuracion() {
        Iterator<Integer> interatorC = canales.keySet().iterator();
        long tiempoG = 0;
        while (interatorC.hasNext()) {
            Integer key = interatorC.next();
            Canal valor = canales.get(key);
            long aux = valor.getTiempoTotal();
            if (aux > tiempoG) {
                tiempoG = aux;
            }
        }
        return this.convertirMillis(tiempoG);
    }

    public String convertirMillis(long millis) {
        if (millis < 1000) {
            return millis + "ms";
        } else if (millis < 60_000) {
            double segundos = (double) millis / 1000;
            return String.format("%.1fs", segundos);
        } else if (millis < 3_600_000) {
            long minutos = millis / 60_000;
            long segundosRestantes = (millis % 60_000) / 1000;
            return minutos + "m " + segundosRestantes + "s";
        } else {
            long horas = millis / 3_600_000;
            long minutosRestantes = (millis % 3_600_000) / 60_000;
            long segundosRestantes = ((millis % 3_600_000) % 60_000) / 1000;
            return horas + "h " + minutosRestantes + "m " + segundosRestantes + "s";
        }
    }

}
