package midik.instrucciones;

import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class DiferenteQue extends Instruccion {

    private Instruccion expIzq;
    private Instruccion expDer;

    public DiferenteQue(String linea, Object expIzq, Object expDer) {
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
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre null"));
            return null;
        }

        if (TipoNativo.BOOLEAN == ((Nativo) exp1).getTipo()) {
            //BOOLEAN != BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                boolean resultado = this.getValorBoolean(exp1).equals(this.getValorBoolean(exp2));
                return new Nativo(super.getLinea(), Boolean.toString((!resultado)), TipoNativo.BOOLEAN);
            }

            //BOOLEAN != DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre booleano y decimal."));
            }

            //BOOLEAN != CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre booleano y cadena"));
            }

            //BOOLEAN != ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre booleano y entero"));
            }

            //BOOLEAN != CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre booleano y caracter"));
            }
        }

        if (TipoNativo.ENTERO == ((Nativo) exp1).getTipo()) {

            //ENTERO != BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre entero y booleano"));
            }

            //ENTERO != DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre entero y decimal"));
            }

            //ENTERO != CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre entero y cadena"));
            }

            //ENTERO != ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Boolean resultado = ((String) ((Nativo) exp1).getValor()).equals((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(),  Boolean.toString((!resultado)), TipoNativo.BOOLEAN);
            }

            //ENTERO != CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre entero y caracter"));
            }
        }

        if (TipoNativo.DECIMAL == ((Nativo) exp1).getTipo()) {

            //DECIMAL != BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre decimal y boolean"));
            }

            //DECIMAL != DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Boolean resultado = ((String) ((Nativo) exp1).getValor()).equals((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(),  Boolean.toString((!resultado)), TipoNativo.BOOLEAN);
            }

            //DECIMAL != CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre decimal y cadena"));
            }

            //DECIMAL != ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre decimal y entero"));
            }

            //DECIMAL != CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre decimal y caracter"));
            }
        }

        if (TipoNativo.CADENA == ((Nativo) exp1).getTipo()) {

            //CADENA != BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre cadena y boolean"));
            }

            //CADENA != DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre cadena y decimal"));
            }

            //CADENA != CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Boolean resultado = ((String) ((Nativo) exp1).getValor()).equals((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(),  Boolean.toString((!resultado)), TipoNativo.BOOLEAN);
            }

            //CADENA != ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre cadena y entero"));
            }

            //CADENA != CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre cadena y caracter"));
            }
        }

        if (TipoNativo.CARACTER == ((Nativo) exp1).getTipo()) {

            //CARACTER != BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre caracter y boolean"));
            }

            //CARACTER != DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre caracter y decimal"));
            }

            //CARACTER != CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre caracter y cadena"));
            }

            //CARACTER != ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Operacion (==) no permitida entre caracter y entero"));
            }

            //CARACTER != CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Boolean resultado = ((String) ((Nativo) exp1).getValor()).equals((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(),  Boolean.toString((!resultado)), TipoNativo.BOOLEAN);
            }
        }

        return valor;
    }

    private String getValorBoolean(Object exp) {
        String valor = (String) ((Nativo) exp).getValor();
        if (valor.equals("verdadero") || valor.equals("true") || valor.equals("1")) {
            return "1";
        } else {
            return "0";
        }
    }
}
