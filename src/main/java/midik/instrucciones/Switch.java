package midik.instrucciones;

import java.util.ArrayList;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.miembroSi.Caso;
import midik.miembroSi.Continuar;
import midik.miembroSi.Retornar;
import midik.miembroSi.Salir;

public class Switch extends Instruccion {

    private Instruccion exp;
    private ArrayList<Caso> listaCasos;

    public Switch(String linea, Object exp, Object listaCasos) {
        super(linea);
        this.exp = (Instruccion) exp;
        this.listaCasos = (ArrayList<Caso>) listaCasos;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        Nativo valor = (Nativo) this.exp.ejecutar(entorno);
        for (Caso cs : this.listaCasos) {
            //Si es un case con expresion
            if (cs.getExp() != null) {
                //Evaluar la condicion del case
                Nativo valorCase = (Nativo) cs.getExp().ejecutar(entorno);
                //Si no hacen mach se salta
                if (!(valor.getTipo() == valorCase.getTipo() && ((String) valor.getValor()).equals(((String) valorCase.getValor())))) {
                    continue;
                }
            }

            //Si los valores son iguales se ejecuta las instrucciones
            Entorno entornoSwitch = new Entorno(entorno);
            for (Instruccion inst : cs.getInstrucciones()) {
                Object resp = inst.ejecutar(entornoSwitch);
                //Validacion de instrucciones cambio de flujo
                if (resp instanceof Salir) {
                    return null;
                }
                if (resp instanceof Retornar || resp instanceof Continuar) {
                    return resp;
                }
            }

            //Si se ejecuta un defaul ya no debe iterar
            if (cs.isIsDefault()) {
                return null;
            }

        }
        return null;
    }

}
