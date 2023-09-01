package midik.ejecucion;

import java.text.DecimalFormat;

public class Nativo extends Instruccion {

    private DecimalFormat df = new DecimalFormat("#.######");

    private Object valor;
    private TipoNativo tipo;

    public Nativo(String linea, Object valor, TipoNativo tipo) {
        super(linea);
        this.tipo = tipo;
        if (this.tipo == TipoNativo.BOOLEAN) {
            if (valor.equals("verdadero") || valor.equals("true") || valor.equals("1")) {
                this.valor = "1";
            } else {
                this.valor = "0";
            }
        } else if (this.tipo == TipoNativo.CARACTER) {
            this.valor = this.limpiarCaracter((String) valor);
        } else if (this.tipo == TipoNativo.CADENA) {
            this.valor = this.limpiarCadena((String) valor);
        } else {
            this.valor = valor;
        }
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        return this;
    }

    public Object getValor() {
        if (this.tipo == TipoNativo.DECIMAL) {
            String fn = this.df.format(Double.parseDouble(this.valor.toString()));
            this.valor = fn;
        }
        return valor;
    }

    public TipoNativo getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "Nativo{valor=" + valor + ", tipo=" + tipo + '}';
    }

    public void setTipo(TipoNativo tipo) {
        this.tipo = tipo;
    }

    private String limpiarCadena(String cadena) {
        cadena = cadena.trim();
        if (cadena.startsWith("\"")) {
            cadena = cadena.substring(1);
        }
        if (cadena.endsWith("\"")) {
            cadena = cadena.substring(0, cadena.length() - 1);
        }

        return cadena;
    }

    private String limpiarCaracter(String cadena) {
        cadena = cadena.trim();
        if (cadena.startsWith("'")) {
            cadena = cadena.substring(1);
        }
        if (cadena.endsWith("'")) {
            cadena = cadena.substring(0, cadena.length() - 1);
        }

        return cadena;
    }

}
