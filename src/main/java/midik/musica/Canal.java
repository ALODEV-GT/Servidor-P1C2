package midik.musica;

import java.io.Serializable;
import java.util.ArrayList;

public class Canal implements Serializable {

    private int numCanal;
    private ArrayList<Nota> notas = new ArrayList<>();

    public Canal(int numCanal) {
        this.numCanal = numCanal;
    }

    public void agregarNota(Nota nota) {
        if (!estaEnLaListaNegra(nota)) {
            this.notas.add(nota);
        }
    }

    public int[] getNotas() {
        int[] midis = new int[this.notas.size()];
        for (int i = 0; i < midis.length; i++) {
            midis[i] = this.notas.get(i).getRepMidi();
        }
        return midis;
    }

    public long[] getDuraciones() {
        long[] tiempos = new long[this.notas.size()];
        for (int i = 0; i < tiempos.length; i++) {
            tiempos[i] = this.notas.get(i).getTiempo();
        }
        return tiempos;
    }

    public long getTiempoTotal() {
        long tiempos =0;
        for (int i = 0; i < this.notas.size(); i++) {
            tiempos += this.notas.get(i).getTiempo();
        }
        return tiempos;
    }

    public boolean estaEnLaListaNegra(Nota nota) {
        int midi = nota.getRepMidi();
        if (midi == 0) {
            return false;
        } else {
            return midi < 21 || midi > 108;
        }
    }

}
