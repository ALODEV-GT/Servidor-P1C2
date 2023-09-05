package midik.instrucciones;

import java.util.ArrayList;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.miembroSi.Continuar;
import midik.miembroSi.Retornar;
import midik.miembroSi.Salir;

public class Para extends Instruccion {

    private Instruccion declaracion;
    private Instruccion asignacion;
    private Instruccion condicion;
    private Instruccion asignacionPara;
    private ArrayList<Instruccion> instrucciones;

    public Para(String linea, Object declaracion, Object asignacion, Object condicion, Object asignacionPara, Object instrucciones) {
        super(linea);
        this.declaracion = (Instruccion) declaracion;
        this.asignacion = (Instruccion) asignacion;
        this.condicion = (Instruccion) condicion;
        this.asignacionPara = (Instruccion) asignacionPara;
        this.instrucciones = (ArrayList<Instruccion>) instrucciones;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        Entorno entornoPara = new Entorno(entorno);

        //Si es una declaracion dentro del for
        if (this.declaracion != null) {
            this.declaracion.ejecutar(entornoPara);
            //Si es una asignacion dentro del for
        } else if (this.asignacion != null) {
            this.asignacion.ejecutar(entornoPara);
        }

        //Evaluacion de la condicion para la ejecucion del for
        while (((String) ((Nativo) this.condicion.ejecutar(entornoPara)).getValor()).equals("1")) {
            Entorno entornoIteraciones = new Entorno(entornoPara);

            //Ejecucion de las instrucciones
            for (Instruccion inst : this.instrucciones) {
                Object resp = inst.ejecutar(entornoIteraciones);

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
            this.asignacionPara.ejecutar(entornoPara);
        }
        return null;
    }

}
