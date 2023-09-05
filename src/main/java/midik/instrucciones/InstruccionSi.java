package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;
import midik.miembroSi.Continuar;
import midik.miembroSi.Retornar;
import midik.miembroSi.Salir;
import midik.miembroSi.Si;

public class InstruccionSi extends Instruccion {

    private ArrayList<Si> listaSis;

    public InstruccionSi(String linea, Object listaSis) {
        super(linea);
        this.listaSis = (ArrayList<Si>) listaSis;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        for (Si si : this.listaSis) {
            Instruccion condicion = si.getCondicion();
            ArrayList<Instruccion> instrucciones = si.getInstrucciones();

            Nativo cond = (Nativo) condicion.ejecutar(entorno);
            //Si la condicion es booleana
            if (cond.getTipo() == TipoNativo.BOOLEAN) {
                //Si la condicion es veradera
                if (((String) cond.getValor()).equals("1")) {
                    //Entorno generado por el si
                    Entorno entornoSi = new Entorno(entorno);
                    //Ejecucion de las instrucciones
                    for (Instruccion instruccion : instrucciones) {
                        Object resp = instruccion.ejecutar(entornoSi);
                        //Validacion sentencia Saltar, Contiruar, Retorno
                        if (resp instanceof Salir || resp instanceof Continuar || resp instanceof Retornar) {
                            return resp;
                        }
                    }

                    return null;
                }

            } else {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La condicion debe ser una expresion booleana."));
            }
        }
        return null;
    }

}
