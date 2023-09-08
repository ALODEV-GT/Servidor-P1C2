package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class Reproducir extends Instruccion {

    private ArrayList<Object> parametros;

    public Reproducir(String linea, Object parametros) {
        super(linea);
        this.parametros = (ArrayList<Object>) parametros;
    }

    @Override
    public Object ejecutar(Entorno entorno) {

        //Verificar numero parametros
        if (this.parametros.size() != 4) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Debes ingresar (Nota, octava, tiempo, canal)"));
            return null;
        }

        //Verificacion de tipos
        Nativo nota = (Nativo) ((Instruccion) this.parametros.get(0)).ejecutar(entorno);
        if (nota.getTipo() != TipoNativo.CADENA) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "La nota debe ser de tipo Cadena."));
            return null;
        } else {
            //Validar que sea una nota correcta
            if (this.perteneceAlConjuntoNotas(nota)) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No es una nota valida."));
                return null;
            }
        }

        for (int i = 1; i < this.parametros.size(); i++) {
            Nativo par = (Nativo) ((Instruccion) this.parametros.get(i)).ejecutar(entorno);
            if (par.getTipo() != TipoNativo.ENTERO) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Los parametros octava, tiempo y canal deben ser de tipo Entero."));
                return null;
            }
        }
        
        

        return null;
    }

    private boolean perteneceAlConjuntoNotas(Nativo nota) {
        boolean pertenece = false;
        String[] conjuntoNotas = {"Do", "Do#", "Re", "Re#", "Mi", "Fa", "Fa#", "Sol", "Sol#", "La", "La#", "Si"};
        String notaS = (String) nota.getValor();
        for (String n : conjuntoNotas) {
            if (n.equals(notaS)) {
                pertenece = true;
                break;
            }
        }

        return pertenece;
    }

}
