package midik.instrucciones;

import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class OR extends Instruccion {

    private Instruccion expIzq;
    private Instruccion expDer;

    public OR(String linea, Object expIzq, Object expDer) {
        super(linea);
        this.expIzq = (Instruccion) expIzq;
        this.expDer = (Instruccion) expDer;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        Object exp1 = this.expIzq.ejecutar(entorno);
        Object exp2 = this.expDer.ejecutar(entorno);

        String valor = null;

        if (exp1 == null || exp2 == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre null"));
            return null;
        }

        if (TipoNativo.BOOLEAN == ((Nativo) exp1).getTipo()) {
            //BOOLEAN and BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Boolean resultado = this.getValorBoolean(exp1) || (this.getValorBoolean(exp2));
                return new Nativo(super.getLinea(), resultado.toString(), TipoNativo.BOOLEAN);
            }

            //BOOLEAN and DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre booleano y decimal."));
            }

            //BOOLEAN and CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre booleano y cadena"));
            }

            //BOOLEAN and ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre booleano y entero"));
            }

            //BOOLEAN and CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre booleano y caracter"));
            }
        }

        if (TipoNativo.ENTERO == ((Nativo) exp1).getTipo()) {

            //ENTERO and BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre entero y booleano"));
            }

            //ENTERO and DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre entero y decimal"));

            }

            //ENTERO and CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre entero y cadena"));
            }

            //ENTERO and ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre entero y entero"));

            }

            //ENTERO and CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre entero y caracter"));
            }
        }

        if (TipoNativo.DECIMAL == ((Nativo) exp1).getTipo()) {

            //DECIMAL and BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre decimal y boolean"));
            }

            //DECIMAL and DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre decimal y decimal"));
            }

            //DECIMAL and CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre decimal y cadena"));
            }

            //DECIMAL and ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre decimal y entero"));
            }

            //DECIMAL and CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre decimal y caracter"));
            }
        }

        if (TipoNativo.CADENA == ((Nativo) exp1).getTipo()) {

            //CADENA and BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre cadena y boolean"));
            }

            //CADENA and DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre cadena y decimal"));
            }

            //CADENA and CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre cadena y cadena"));
            }

            //CADENA and ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre cadena y entero"));
            }

            //CADENA and CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre cadena y caracter"));
            }
        }

        if (TipoNativo.CARACTER == ((Nativo) exp1).getTipo()) {

            //CARACTER and BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre caracter y boolean"));
            }

            //CARACTER and DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre caracter y decimal"));
            }

            //CARACTER and CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre caracter y cadena"));
            }

            //CARACTER and ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre caracter y entero"));
            }

            //CARACTER and CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (and) no permitida entre caracter y caracter"));
            }
        }

        return valor;
    }

    private boolean getValorBoolean(Object exp) {
        String valor = (String) ((Nativo) exp).getValor();
        if (valor.equals("verdadero") || valor.equals("true") || valor.equals("1")) {
            return true;
        } else {
            return false;
        }
    }

}
