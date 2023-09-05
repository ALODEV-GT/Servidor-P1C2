package midik.ejecucion;

public class Variable {

    private String id;
    private TipoNativo tipoNativo;
    private Object valor;
    private Boolean reasignable;
    private Boolean isKeep;

    public Variable(String id, TipoNativo tipoNativo, Object valor, Boolean reasignable, Boolean isKeep) {
        this.id = id;
        this.tipoNativo = tipoNativo;
        this.valor = valor;
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
    
    public boolean hasTipoAsignado(){
        return this.tipoNativo != null;
    }
    
    

    @Override
    public String toString() {
        return "Variable{" + "id=" + id + ", tipoNativo=" + tipoNativo + ", valor=" + valor + ", reasignable=" + reasignable + ", isKeep=" + isKeep + '}';
    }

}
