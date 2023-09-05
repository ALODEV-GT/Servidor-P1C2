package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.ejecucion.Entorno;
import midik.ejecucion.Funcion;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.Variable;
import midik.miembroSi.Continuar;
import midik.miembroSi.Retornar;
import midik.miembroSi.Salir;

public class LlamadaFuncion extends Instruccion {

    private String id;
    private ArrayList<Instruccion> listaParametros;

    public LlamadaFuncion(String linea, String id, Object listaParametros) {
        super(linea);
        this.id = id;
        this.listaParametros = (ArrayList<Instruccion>) listaParametros;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        Entorno entornoAux = new Entorno(null);
        Entorno entornoLocal = new Entorno(entorno);

        Funcion funcion = entorno.getFuncion(id);

        if (funcion == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "No existe ninguna funcion con el nombre  " + id));
            return null;
        }

        //Si hay parametros
        if (this.listaParametros != null) {
            //Si la funcion no tiene parametros
            if (funcion.getListaParametros() == null) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La funcion  " + id + " no recibe parametros"));
                return null;
            }

            //Comprobacion misma cantidad de parametros
            if (this.listaParametros.size() != funcion.getListaParametros().size()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La cantidad de parametros no coincide en la funcion   " + id));
                return null;
            }

            //Declaracion de parametros
            for (int i = 0; i < this.listaParametros.size(); i++) {
                Instruccion exp = this.listaParametros.get(i);
                Variable variable = funcion.getListaParametros().get(i);

                Nativo valor = (Nativo) exp.ejecutar(entornoLocal);

                //Validacion de tipo a asignar
                if (valor != null && variable.hasTipoAsignado() && variable.getTipoAsignado() != valor.getTipo()) {
                    Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "El parametro   " + variable.getId() + " de la funcion " + this.id + " no es del tipo enviado en la llamada de la funcion"));
                    return null;
                }

                variable.setValor(valor);
                entornoAux.setVariable(variable);
            }
        } //Si la llamada de la funcion no trae parametros
        else {
            //Es un erro si la funcion tiene parametros
            if (funcion.hasParametros()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La funcion " + this.id + " debe recibir " + funcion.getParametrosSize() + " parametros"));
                return null;
            }
        }

        entornoLocal.setVariables(entornoAux.getVariables());
        
        midik.ejecucion.EntornoAux.getInstance().inicioEjecucionFuncio();

        //Ejecucion de las instrucciones
        for (Instruccion instruccion : funcion.getInstrucciones()) {
            Object resp = instruccion.ejecutar(entornoLocal);

            //Validacion retorno
            if (resp instanceof Retornar) {
                //Validacion retorno en funcion
                if (funcion.hasRetorno() && ((Retornar) resp).tieneValor()) {
                    //Validacion del tipo de retorno
                    Instruccion val = ((Retornar) resp).getValor();
                    if (val != null && ((Nativo) val).getTipo() != funcion.getTipoReturn()) {
                        Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La funcion " + this.id + " esta retornando un tipo distinto al declarado"));
                        midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
                        return null;
                    }
                    midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
                    return val;
                }

                //Si la funcion tiene return pero el return no tiene valor
                if (funcion.hasRetorno() && !(((Retornar) resp).tieneValor())) {
                    Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La funcion " + this.id + " debe retornar un valor"));
                    midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
                    return null;
                }

                //Si la funcion no debe tener return y el return trae un valor
                if (!funcion.hasRetorno() && ((Retornar) resp).tieneValor()) {
                    Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La funcion " + this.id + "no debe retornar un valor"));
                    midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
                    return null;
                }

                //Si solo es un return
                midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
                return null;
            }

            //Validacion Salir y Continuar
            if (resp instanceof Salir || resp instanceof Continuar) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La instruccion Salir/Continuar solo puede ser usados detro de ciclos"));
                midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
                return null;
            }
        }

        //Validacion si la funcion debia retornar algo
        if (funcion.hasRetorno()) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "La funcion " + this.id + " debe retonar un valor"));
            midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
            return null;
        }

        midik.ejecucion.EntornoAux.getInstance().finEjecucionFuncion();
        return null;
    }

}
