package midik.instrucciones;

import midik.Singletons.Errores;
import midik.Singletons.Error;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class Resta extends Instruccion {

    private Instruccion expIzq;
    private Instruccion expDer;

    public Resta(String linea, Object expIzq, Object expDer) {
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
            Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar null"));
            return null;
        }

        if (TipoNativo.BOOLEAN == ((Nativo) exp1).getTipo()) {
            //BOOLEAN - BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar booleano y booleano"));
            }

            //BOOLEAN - DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar booleano y decimal."));
            }

            //BOOLEAN - CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar booleano y cadena"));
            }

            //BOOLEAN - ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar booleano y entero"));
            }

            //BOOLEAN - CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar booleano y caracter"));
            }
        }

        if (TipoNativo.ENTERO == ((Nativo) exp1).getTipo()) {

            //ENTERO - BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar entero y booleano"));
            }

            //ENTERO - DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Double resta = Integer.valueOf((String) ((Nativo) exp1).getValor()) - Double.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), resta.toString(), TipoNativo.DECIMAL);
            }

            //ENTERO - CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar entero y cadena"));
            }

            //ENTERO - ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Integer resta = Integer.valueOf((String) ((Nativo) exp1).getValor()) - Integer.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), resta.toString(), TipoNativo.ENTERO);
            }

            //ENTERO - CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar entero y caracter"));
            }
        }

        if (TipoNativo.DECIMAL == ((Nativo) exp1).getTipo()) {

            //DECIMAL - BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar decimal y boolean"));
            }

            //DECIMAL - DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Double resta = Double.valueOf((String) ((Nativo) exp1).getValor()) - Double.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), resta.toString(), TipoNativo.DECIMAL);
            }

            //DECIMAL - CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar decimal y cadena"));
            }

            //DECIMAL - ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Double resta = Double.valueOf((String) ((Nativo) exp1).getValor()) - Integer.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), resta.toString(), TipoNativo.DECIMAL);
            }

            //DECIMAL - CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar decimal y caracter"));
            }
        }

        if (TipoNativo.CADENA == ((Nativo) exp1).getTipo()) {

            //CADENA - BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
               Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar cadena y boolean"));
            }

            //CADENA - DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
               Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar cadena y decimal"));
            }

            //CADENA - CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar cadena y cadena"));
            }

            //CADENA - ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar cadena y entero"));
            }

            //CADENA - CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar cadena y caracter"));
            }
        }

        if (TipoNativo.CARACTER == ((Nativo) exp1).getTipo()) {

            //CARACTER - BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar caracter y boolean"));
            }

            //CARACTER - DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar caracter y decimal"));
            }

            //CARACTER - CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar caracter y cadena"));
            }

            //CARACTER - ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar caracter y entero"));
            }

            //CARACTER - CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
               Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No se puede restar caracter y caracter"));
            }
        }

        return valor;
    }

}
