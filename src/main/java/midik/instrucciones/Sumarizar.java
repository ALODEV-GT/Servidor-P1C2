package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.ejecucion.Arreglo;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class Sumarizar extends Instruccion {
    
    private Entorno entorno;
    private String id;
    
    public Sumarizar(String linea, Object id) {
        super(linea);
        this.id = (String) id;
    }
    
    @Override
    public Object ejecutar(Entorno entorno) {
        this.entorno = entorno;
        //Verificar si existe un arreglo con el id
        Arreglo arr = entorno.getArreglo(id);
        if (arr == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No existe ningun arreglo con el id " + this.id));
            return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
        }

        //Verificar que sea de una dimension
        if (arr.getDimensiones() != 1) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Solo se pueden ordenar arreglos de una dimension."));
            return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
        }

        //Verificar que todos los datos sean enteros
        ArrayList<Object> arreglo = (ArrayList<Object>) ((ArrayList<Object>) arr.getValor()).get(0);
        for (Object h : arreglo) {
            if (h instanceof ArrayList) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Hay valores nulos en el arreglo"));
                return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
            }
        }
        
        Object resultado = this.sumarizar(arreglo);
        Nativo resultNativo = (Nativo) resultado;
        resultNativo.setTipo(TipoNativo.CADENA);
        return resultNativo;
    }
    
    private Object sumarizar(ArrayList<Object> arreglo) {
        Object aux = arreglo.get(0);
        for (int i = 0; i < arreglo.size() - 1; i++) {
            aux = new Suma(this.getLinea(), aux, arreglo.get(i + 1));
        }
        
        return ((Instruccion) aux).ejecutar(entorno);
    }
    
}
