package midik.instrucciones;

import midik.Singletons.Errores;
import midik.ejecucion.Nativo;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.TipoNativo;

public class Suma extends Instruccion {

    private Instruccion expIzq;
    private Instruccion expDer;

    public Suma(String linea, Object expIzq, Object expDer) {
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
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No se puede sumar null"));
            return null;
        }

        if (TipoNativo.BOOLEAN == ((Nativo) exp1).getTipo()) {
            //BOOLEAN + BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //BOOLEAN + DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //BOOLEAN + CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //BOOLEAN + ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //BOOLEAN + CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }
        }

        if (TipoNativo.ENTERO == ((Nativo) exp1).getTipo()) {

            //ENTERO + BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //ENTERO + DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Double suma = Integer.valueOf((String) ((Nativo) exp1).getValor()) + Double.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), suma.toString(), TipoNativo.DECIMAL);
            }

            //ENTERO + CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //ENTERO + ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Integer suma = Integer.valueOf((String) ((Nativo) exp1).getValor()) + Integer.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), suma.toString(), TipoNativo.ENTERO);
            }

            //ENTERO + CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }
        }
        
        if (TipoNativo.DECIMAL == ((Nativo) exp1).getTipo()) {

            //DECIMAL + BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //DECIMAL + DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                Double suma = Double.valueOf((String) ((Nativo) exp1).getValor()) + Double.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), suma.toString(), TipoNativo.DECIMAL);
            }

            //DECIMAL + CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //DECIMAL + ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                Double suma = Double.valueOf((String) ((Nativo) exp1).getValor()) + Integer.valueOf((String) ((Nativo) exp2).getValor());
                return new Nativo(super.getLinea(), suma.toString(), TipoNativo.DECIMAL);
            }

            //DECIMAL + CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }
        }
        
        if (TipoNativo.CADENA == ((Nativo) exp1).getTipo()) {

            //CADENA + BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CADENA + DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CADENA + CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CADENA + ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CADENA + CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }
        }
        
        if (TipoNativo.CARACTER == ((Nativo) exp1).getTipo()) {

            //CARACTER + BOOLEAN
            if (TipoNativo.BOOLEAN == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CARACTER + DECIMAL
            if (TipoNativo.DECIMAL == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CARACTER + CADENA
            if (TipoNativo.CADENA == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CARACTER + ENTERO
            if (TipoNativo.ENTERO == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }

            //CARACTER + CARACTER
            if (TipoNativo.CARACTER == ((Nativo) exp2).getTipo()) {
                String suma = (String) ((Nativo) exp1).getValor() + (String) ((Nativo) exp2).getValor();
                return new Nativo(super.getLinea(), suma, TipoNativo.CADENA);
            }
        }

        return valor;
    }

}
