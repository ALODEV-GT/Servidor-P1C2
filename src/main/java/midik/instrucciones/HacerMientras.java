package midik.instrucciones;

import java.util.ArrayList;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.miembroSi.Continuar;
import midik.miembroSi.Retornar;
import midik.miembroSi.Salir;

public class HacerMientras extends Instruccion {

    private ArrayList<Instruccion> instrucciones;
    private Instruccion condicion;

    public HacerMientras(String linea, Object instrucciones, Object condicion) {
        super(linea);
        this.instrucciones = (ArrayList<Instruccion>) instrucciones;
        this.condicion = (Instruccion) condicion;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        do {
            //Creacion del entorno generado por el Mientras
            Entorno entornoMientras = new Entorno(entorno);

            //Ejecucion de las instrucciones
            for (Instruccion inst : this.instrucciones) {
                Object resp = inst.ejecutar(entornoMientras);

                //Validacion instruccion Retornar
                if (resp instanceof Retornar) {
                    return resp;
                }

                //Validacion instruccion Saltar
                if (resp instanceof Salir) {
                    return null;
                }

                //Validacion instruccion Continue
                if (resp instanceof Continuar) {
                    break;
                }
            }

        } while (((String) ((Nativo) this.condicion.ejecutar(entorno)).getValor()).equals("1"));
        return null;
    }

}
