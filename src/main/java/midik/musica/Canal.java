package midik.musica;

import java.util.ArrayList;

public class Canal {

    private int numCanal;
    private ArrayList<Nota> notas = new ArrayList<>();

    public Canal(int numCanal) {
        this.numCanal = numCanal;
    }

    public void agregarNota(Nota nota) {
        this.notas.add(nota);
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

}
