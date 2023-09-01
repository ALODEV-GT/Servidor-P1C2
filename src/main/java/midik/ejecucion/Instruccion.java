package midik.ejecucion;

public abstract class Instruccion {

    private String linea;

    public Instruccion(String linea) {
        this.linea = linea;
    }

    public abstract Object ejecutar(Entorno entorno);

    public String getLinea() {
        return this.linea;
    }
}
