package midik.musica;

import java.io.Serializable;

public class Nota implements Serializable{

    private String nota;
    private int octava;
    private int repMidi;
    private Long tiempo;
    private int canal;

    public Nota(String nota, int octava, Long tiempo, int canal) {
        this.nota = nota;
        this.octava = octava;
        this.tiempo = tiempo;
        this.canal = canal;
        this.repMidi = this.getNotaToMidi();
    }

    public int getRepMidi() {
        return repMidi;
    }

    public Long getTiempo() {
        return tiempo;
    }

    public int getCanal() {
        return canal;
    }

    //Crear funcion para represetar nota en midi
    private int getNotaToMidi() {
        switch (this.nota) {
            case "Do":
                return 12 + (octava * 12);
            case "Do#":
                return 13 + (octava * 12);
            case "Re":
                return 14 + (octava * 12);
            case "Re#":
                return 15 + (octava * 12);
            case "Mi":
                return 16 + (octava * 12);
            case "Fa":
                return 17 + (octava * 12);
            case "Fa#":
                return 18 + (octava * 12);
            case "Sol":
                return 19 + (octava * 12);
            case "Sol#":
                return 20 + (octava * 12);
            case "La":
                return 21 + (octava * 12);
            case "La#":
                return 22 + (octava * 12);
            case "Si":
                return 23 + (octava * 12);
            default:
                return 0;
        }

    }

}
