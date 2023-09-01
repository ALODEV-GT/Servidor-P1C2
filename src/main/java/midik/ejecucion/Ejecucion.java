package midik.ejecucion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import midik.arbol.NodoAST;
import midik.defvariables.ListaVars;
import midik.defvariables.TipoNativoVar;
import midik.instrucciones.AND;
import midik.instrucciones.DeclaracionVariable;
import midik.instrucciones.DiferenteQue;
import midik.instrucciones.Division;
import midik.instrucciones.IgualQue;
import midik.instrucciones.MayorIgual;
import midik.instrucciones.MayorQue;
import midik.instrucciones.MenorIgual;
import midik.instrucciones.MenorQue;
import midik.instrucciones.Mensaje;
import midik.instrucciones.Modulo;
import midik.instrucciones.Multiplicacion;
import midik.instrucciones.NAND;
import midik.instrucciones.NOR;
import midik.instrucciones.OR;
import midik.instrucciones.Potencia;
import midik.instrucciones.Resta;
import midik.instrucciones.Suma;
import midik.instrucciones.XOR;

public class Ejecucion {

    private NodoAST raiz;
    private Integer contador;

    public Ejecucion(NodoAST raiz) {
        this.raiz = raiz;
    }

    public void ejecutar() {
        Object instrucciones = this.recorrer(this.raiz);

        Entorno entornoGlobal = new Entorno(null);

        for (Instruccion instruccion : (ArrayList<Instruccion>) instrucciones) {
            instruccion.ejecutar(entornoGlobal);
        }
        
        //Impresion de variables en el entorno global
        Map<String, Variable> variables = entornoGlobal.getVariables();
        Iterator<String> iterator = variables.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            Variable valor = variables.get(key);
            System.out.println(valor);
        }
    }

    public Object recorrer(Object nodo) {
        //INICIO
        if (this.soyNodo("S", nodo)) {
            return this.recorrer(((NodoAST) nodo).getHijos().get(1));
        }

        //INSTRUCCIONES
        if (this.soyNodo("INSTRUCCIONES", nodo)) {
            ArrayList<Object> instrucciones = new ArrayList<>();
            for (Object hijo : ((NodoAST) nodo).getHijos()) {
                Object inst = this.recorrer(hijo);
                instrucciones.add(inst);
            }
            return instrucciones;
        }

        //DECLARACION_VARIABLE
        if (this.soyNodo("DECLARACION_VARIABLE", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            int numHijos = nodoA.getHijos().size();
            Object miembros = null;
            boolean hasKeep = false;
            switch (numHijos) {
                case 1:
                    miembros = this.recorrer(nodoA.getHijos().get(0));
                    break;
                case 2:
                    miembros = this.recorrer(nodoA.getHijos().get(1));
                    hasKeep = true;
                    break;
            }

            ArrayList<Object> listaMiembros = (ArrayList<Object>) miembros;
            String reasignable = (String) listaMiembros.get(0);
            TipoNativoVar tipo = (TipoNativoVar) listaMiembros.get(1);
            ListaVars listaVars = (ListaVars) listaMiembros.get(2);

            boolean isReasignable = reasignable.equals("Var");

            DeclaracionVariable dv = new DeclaracionVariable(nodoA.getLinea(), tipo, listaVars.getVariables(), listaVars.getExpresion(), isReasignable, hasKeep);
            return dv;
        }

        //DEF_VARIABLE
        if (this.soyNodo("DEF_VARIABLE", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> listaId = new ArrayList<>();
            int indiceUltimoHijo = nodoA.getHijos().size() - 1;

            if (((NodoAST) nodoA.getHijos().get(indiceUltimoHijo)).getEtiqueta().equals("EXP")) {
                for (int i = 0; i < indiceUltimoHijo; i++) {
                    listaId.add(nodoA.getHijos().get(i));
                }
                return new ListaVars(listaId, this.recorrer(nodoA.getHijos().get(indiceUltimoHijo)));
            } else {
                for (int i = 0; i <= indiceUltimoHijo; i++) {
                    listaId.add(nodoA.getHijos().get(i));
                }
                return new ListaVars(listaId, null);
            }
        }

        //VARIABLE
        if (this.soyNodo("VARIABLE", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> hijos = nodoA.getHijos();
            ArrayList<Object> lista = new ArrayList<>();
            Object tipo = this.recorrer(hijos.get(1));
            Object variables = this.recorrer(hijos.get(2));
            lista.add(hijos.get(0));
            lista.add(tipo);
            lista.add(variables);
            return lista;
        }

        //TIPOS_VARIABLE_NATIVA
        if (this.soyNodo("TIPOS_VARIABLE_NATIVA", nodo)) {
            return this.recorrer(((NodoAST) nodo).getHijos().get(0));
        }

        //MENSAJE
        if (this.soyNodo("MENSAJE", nodo)) {
            Object lista = this.recorrer(((NodoAST) nodo).getHijos().get(0));
            return new Mensaje("sus_linea", lista);

        }

        //LISTA_EXPRESIONES
        if (this.soyNodo("LISTA_EXPRESIONES", nodo)) {
            ArrayList<Object> lista = new ArrayList<>();
            for (Object hijo : ((NodoAST) nodo).getHijos()) {
                Object exp = this.recorrer(hijo);
                lista.add(exp);
            }
            return lista;
        }

        //EXP
        if (this.soyNodo("EXP", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            switch (nodoA.getHijos().size()) {
                case 1: {
                    Object exp = this.recorrer(nodoA.getHijos().get(0));
                    return exp;
                }
                case 3: {
                    //exp + exp
                    if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("+") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new Suma(linea, expIzq, expDer);

                        //exp - exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("-") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new Resta(linea, expIzq, expDer);

                        //exp * exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("*") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new Multiplicacion(linea, expIzq, expDer);

                        //exp / exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("/") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new Division(linea, expIzq, expDer);

                        //exp % exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("%") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new Modulo(linea, expIzq, expDer);

                        //exp ^ exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("^") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new Potencia(linea, expIzq, expDer);

                        //exp > exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals(">") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new MayorQue(linea, expIzq, expDer);

                        //exp < exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("<") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new MenorQue(linea, expIzq, expDer);

                        //exp >= exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals(">=") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new MayorIgual(linea, expIzq, expDer);

                        //exp <= exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("<=") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new MenorIgual(linea, expIzq, expDer);

                        //exp == exp
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("==") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new IgualQue(linea, expIzq, expDer);

                        //exp != exp  
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("!=") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new DiferenteQue(linea, expIzq, expDer);
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("&&") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new AND(linea, expIzq, expDer);
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("!&&") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new NAND(linea, expIzq, expDer);
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("||") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new OR(linea, expIzq, expDer);
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("!||") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new NOR(linea, expIzq, expDer);
                    } else if (this.soyNodo("EXP", nodoA.getHijos().get(0)) && nodoA.getHijos().get(1).equals("&|") && this.soyNodo("EXP", nodoA.getHijos().get(2))) {
                        Object expIzq = this.recorrer(nodoA.getHijos().get(0));
                        Object expDer = this.recorrer(nodoA.getHijos().get(2));
                        String linea = nodoA.getLinea();
                        return new XOR(linea, expIzq, expDer);
                    }
                }
            }
        }

        //ENTERO_VAL
        if (this.soyNodo("ENTERO_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.ENTERO);
        }

        //DOBLE_VAL
        if (this.soyNodo("DOBLE_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.DECIMAL);
        }

        //VERDADERO
        if (this.soyNodo("VERDADERO", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.BOOLEAN);
        }

        //FALSO
        if (this.soyNodo("FALSO", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.BOOLEAN);
        }

        //CADENA_VAL
        if (this.soyNodo("CADENA_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.CADENA);
        }

        //CARACTER_VAL
        if (this.soyNodo("CARACTER_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.CARACTER);
        }

        //------VARIABLES NATIVAS--------
        //ENTERO
        if (this.soyNodo("ENTERO", nodo)) {
            return new TipoNativoVar(TipoNativo.ENTERO);
        }

        //DOBLE
        if (this.soyNodo("DOBLE", nodo)) {
            return new TipoNativoVar(TipoNativo.DECIMAL);
        }

        //CARACTER
        if (this.soyNodo("CARACTER", nodo)) {
            return new TipoNativoVar(TipoNativo.CARACTER);
        }

        //CADENA
        if (this.soyNodo("CADENA", nodo)) {
            return new TipoNativoVar(TipoNativo.CADENA);
        }

        //BOOLEAN
        if (this.soyNodo("BOOLEAN", nodo)) {
            return new TipoNativoVar(TipoNativo.BOOLEAN);
        }

        return null;
    }

    private boolean soyNodo(String etiqueta, Object objeto) {
        if (objeto == null) {
            return false;
        }

        if (objeto instanceof NodoAST) {
            return ((NodoAST) objeto).getEtiqueta().equals(etiqueta);
        }
        return false;
    }

}
