package midik.instrucciones;

import midik.Singletons.Errores;
import midik.ejecucion.Arreglo;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;
import midik.ejecucion.Variable;

public class Longitud extends Instruccion {

    private String id;
    private Instruccion expresion = null;

    public Longitud(String linea, String id) {
        super(linea);
        this.id = id;
    }

    public Longitud(String linea, Object expresion) {
        super(linea);
        this.expresion = (Instruccion) expresion;
    }

    @Override
    public Object ejecutar(Entorno entorno) {

        Variable var = null;
        Arreglo arr = null;

        if (expresion == null) {
            var = entorno.getVariable(id);
            arr = entorno.getArreglo(id);

            if (var == null && arr != null) {
                Double resultado = Math.pow(arr.getLongitud(), arr.getDimensiones());
                return new Nativo(this.getLinea(), resultado.intValue(), TipoNativo.ENTERO);
            } else if (var != null && arr == null) {
                Nativo val = (Nativo) ((Instruccion) var.getValor()).ejecutar(entorno);
                String cadena = (String) val.getValor();
                return new Nativo(this.getLinea(), cadena.length(), TipoNativo.ENTERO);
            } else if (var != null & arr != null) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Existe un arreglo y una variable con el mismo nombre " + id));
                return null;
            } else {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No existe ninguna variable con el id " + id));
                return null;
            }

        } else {
            Nativo nativo = (Nativo) ((Instruccion) this.expresion).ejecutar(entorno);
            if (nativo.getTipo() != TipoNativo.CADENA) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Solo puedes utilizar la funcion longitud en cadenas o arreglos. "));
                return null;
            } else {
                String cadena = (String) nativo.getValor();
                return new Nativo(this.getLinea(), cadena.length(), TipoNativo.ENTERO);
            }
        }
    }

}
