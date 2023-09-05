package midik.instrucciones;

import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;
import midik.ejecucion.Variable;

public class MasMas extends Instruccion {

    private String id;

    public MasMas(String linea, String id) {
        super(linea);
        this.id = id;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        //Validacion de variable
        Variable variable = entorno.getVariable(this.id);

        if (variable == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "No se encontro la variable con el id: " + this.id));
            return null;
        }

        Nativo valor = (Nativo) variable.getValor();

        if (valor.getTipo() != TipoNativo.ENTERO && valor.getTipo() != TipoNativo.DECIMAL) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Para usar el operador ++ la variable debe ser de tipo numero"));
            return null;
        }

        if (valor.getValor() == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Para usar el operador ++ la variable debe tener un valor inicial"));
            return null;
        }

        if (valor.getTipo() == TipoNativo.ENTERO) {
            valor.setValor((Integer.valueOf((String) valor.getValor()) + 1) + "");
            return valor;
        } else {
            valor.setValor((Double.valueOf((String) valor.getValor()) + 1) + "");
            return valor;
        }

    }

}
