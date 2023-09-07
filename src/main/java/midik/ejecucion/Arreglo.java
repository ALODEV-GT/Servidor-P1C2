package midik.ejecucion;

public class Arreglo {

    private String id;
    private TipoNativo tipoNativo;
    private Object valor;
    private int longitud;
    private int dimensiones;
    private Boolean reasignable;
    private Boolean isKeep;

    public Arreglo(String id, TipoNativo tipoNativo, Object valor, int longitud, int dimensiones, Boolean reasignable, Boolean isKeep) {
        this.id = id;
        this.tipoNativo = tipoNativo;
        this.valor = valor;
        this.longitud = longitud;
        this.dimensiones = dimensiones;
        this.reasignable = reasignable;
        this.isKeep = isKeep;
    }

    public String getId() {
        return id;
    }

    public TipoNativo getTipoAsignado() {
        return tipoNativo;
    }

    public Object getValor() {
        return valor;
    }

    public void setValor(Object valor) {
        this.valor = valor;
    }

    public boolean hasTipoAsignado() {
        return this.tipoNativo != null;
    }

    @Override
    public String toString() {
        return "Arreglo{" + "id=" + id + ", tipoNativo=" + tipoNativo + ", valor=" + valor + ", longitud=" + longitud + ", dimensiones=" + dimensiones + ", reasignable=" + reasignable + ", isKeep=" + isKeep + '}';
    }

}
