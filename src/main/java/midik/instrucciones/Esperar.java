package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.CentroCanales;
import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;
import midik.musica.Nota;

public class Esperar extends Instruccion {

    private ArrayList<Object> parametros;

    public Esperar(String linea, Object parametros) {
        super(linea);
        this.parametros = (ArrayList<Object>) parametros;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        //Verificar numero parametros
        if (this.parametros.size() != 2) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Debes ingresar (Tiempo, canal)"));
            return null;
        }

        for (int i = 0; i < this.parametros.size(); i++) {
            Nativo par = (Nativo) ((Instruccion) this.parametros.get(i)).ejecutar(entorno);
            if (par.getTipo() != TipoNativo.ENTERO) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Los parametros deben ser de tipo Entero."));
                return null;
            }
        }
        
        Long tiempo = (Integer.valueOf(((String) ((Nativo) (((Instruccion) this.parametros.get(0)).ejecutar(entorno))).getValor()))).longValue();
        Integer canal = Integer.valueOf(((String) ((Nativo) (((Instruccion) this.parametros.get(1)).ejecutar(entorno))).getValor()));

        CentroCanales.getInstance().agregarNota(new Nota("R", 0, tiempo, canal));

        return null;
    }
}
