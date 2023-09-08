package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.ejecucion.Arreglo;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class AccesoArreglo extends Instruccion {

    private String id;
    private ArrayList<Object> indices;
    private Entorno entorno;

    public AccesoArreglo(String linea, Object id, Object indices) {
        super(linea);
        this.id = (String) id;
        this.indices = (ArrayList<Object>) indices;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        this.entorno = entorno;
        //Recuperar arreglo
        Arreglo arr = entorno.getArreglo(id);
        if (arr == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No existe ningun arreglo con el id  " + id));
            return null;
        }

        //Validar dimensiones
        if (this.indices.size() != arr.getDimensiones()) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "El arreglo es de   " + arr.getDimensiones() + " dimensiones."));
            return null;
        }

        int longitud = arr.getLongitud();
        for (Object h : this.indices) {
            Nativo exp = (Nativo) ((Instruccion) h).ejecutar(entorno);
            //Validar que los indices sean de tipo entero
            if (exp.getTipo() != TipoNativo.ENTERO) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Los indices deben ser de tipo entero."));
                return null;
            } else {
                //Validar que los indices se encuentran en la longitud de los arreglos
                Integer valor = Integer.valueOf((String) exp.getValor());
                if (valor > longitud - 1) {
                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Indice fuera del tamaño del arreglo."));
                    return null;
                }
            }

        }

        //Recuperar valor en el arreglo
        ArrayList<Integer> indicesVal = this.getIndicesInteger();
        Object valor = arr.getElementoArreglo(indicesVal);
        return valor;
    }

    private ArrayList<Integer> getIndicesInteger() {
        ArrayList<Integer> indicesInt = new ArrayList<>();
        for (Object h : this.indices) {
            Nativo exp = (Nativo) ((Instruccion) h).ejecutar(entorno);
            Integer valor = Integer.valueOf((String) exp.getValor());
            indicesInt.add(valor);
        }
        return indicesInt;
    }

}
