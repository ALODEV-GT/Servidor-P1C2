package midik.Singletons;

public class Error {

    private String tipo;
    private String linea;
    private String descripcion;

    public Error(String tipo, String linea, String descripcion) {
        this.tipo = tipo;
        this.linea = linea;
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Error " + tipo + ": Linea: " + linea + ", " + descripcion;
    }

}
