package midik.ejecucion;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import midik.Singletons.CentroCanales;
import midik.Singletons.CentroCanalesThread;
import midik.arbol.NodoAST;
import midik.defvariables.ListaVars;
import midik.defvariables.TipoIgual;
import midik.defvariables.TipoIgualVar;
import midik.defvariables.TipoNativoVar;
import midik.instrucciones.AND;
import midik.instrucciones.AccesoArreglo;
import midik.instrucciones.Asignacion;
import midik.instrucciones.ContinuarI;
import midik.instrucciones.DeclaracionArreglo;
import midik.instrucciones.DeclaracionFuncion;
import midik.instrucciones.DeclaracionVariable;
import midik.instrucciones.DiferenteQue;
import midik.instrucciones.Division;
import midik.instrucciones.HacerMientras;
import midik.instrucciones.Id;
import midik.instrucciones.IgualQue;
import midik.instrucciones.InstruccionSi;
import midik.instrucciones.LlamadaFuncion;
import midik.instrucciones.MasMas;
import midik.instrucciones.MayorIgual;
import midik.instrucciones.MayorQue;
import midik.instrucciones.MenorIgual;
import midik.instrucciones.MenorQue;
import midik.instrucciones.MenosMenos;
import midik.instrucciones.Mensaje;
import midik.instrucciones.Mientras;
import midik.instrucciones.Modulo;
import midik.instrucciones.Multiplicacion;
import midik.instrucciones.NAND;
import midik.instrucciones.NOR;
import midik.instrucciones.OR;
import midik.instrucciones.Para;
import midik.instrucciones.Potencia;
import midik.instrucciones.Reproducir;
import midik.instrucciones.Resta;
import midik.instrucciones.RetornarI;
import midik.instrucciones.SalirI;
import midik.instrucciones.Suma;
import midik.instrucciones.Switch;
import midik.instrucciones.XOR;
import midik.miembroSi.Caso;
import midik.miembroSi.Si;
import midik.musica.Canal;
import midik.musica.CanalThread;

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

        //Impresion de Funciones en el entorno global
        Map<String, Funcion> funciones = entornoGlobal.getFunciones();
        Iterator<String> iteratorF = funciones.keySet().iterator();
        while (iteratorF.hasNext()) {
            String key = iteratorF.next();
            Funcion valor = funciones.get(key);
            System.out.println(valor);
        }

        //Impresion de Arreglos en el entorno global
        Map<String, Arreglo> arreglos = entornoGlobal.getArreglos();
        Iterator<String> iteratorA = arreglos.keySet().iterator();
        while (iteratorA.hasNext()) {
            String key = iteratorA.next();
            Arreglo valor = arreglos.get(key);
            System.out.println(valor);
        }

        //Ejecucion de musica
        
        Map<Integer, Canal> canales = CentroCanales.getInstance().getCanales();
        Iterator<Integer> interatorC = canales.keySet().iterator();
        while (interatorC.hasNext()) {
            Integer key = interatorC.next();
            Canal valor = canales.get(key);
            CanalThread nuevoCanal = new CanalThread();
            nuevoCanal.agregarMusica(valor.getNotas(), valor.getDuraciones());
            CentroCanalesThread.getInstance().agregarCanalThread(nuevoCanal);
        }
        CentroCanalesThread.getInstance().reproducirCanales();
        
        
        
        
       /* int[] notes = {60, 62, 64, 65, 67, 69, 71, 72};  // Números MIDI para notas
        long[] durations = {1000, 1000, 1000, 1000, 1000, 1000, 1000, 1000};  // Duraciones en milisegundos para cada nota

        CanalThread nuevoCanal = new CanalThread();
        nuevoCanal.agregarMusica(notes, durations);
        CentroCanalesThread.getInstance().agregarCanalThread(nuevoCanal);
        CentroCanalesThread.getInstance().reproducirCanales();*/

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

        //REPRODUCIR
        if (this.soyNodo("REPRODUCIR", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object parametros = this.recorrer(nodoA.getHijos().get(0));
            return new Reproducir(nodoA.getLinea(), parametros);
        }

        //REPRODUCIR_EXP
        if (this.soyNodo("REPRODUCIR_EXP", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object parametros = this.recorrer(nodoA.getHijos().get(0));
            return new Reproducir(nodoA.getLinea(), parametros);
        }

        //ACCESO_ARREGLO
        if (this.soyNodo("ACCESO_ARREGLO", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            String id = (String) nodoA.getHijos().get(0);
            Object indices = this.recorrer(nodoA.getHijos().get(1));
            return new AccesoArreglo(nodoA.getLinea(), id, indices);
        }

        //LISTA_CORCHETES_ASIGNACION
        if (this.soyNodo("LISTA_CORCHETES_ASIGNACION", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> lista = new ArrayList<>();

            for (Object h : nodoA.getHijos()) {
                Object resp = this.recorrer(h);
                lista.add(resp);
            }
            return lista;
        }

        //LLAMADA_FUNCION
        if (this.soyNodo("LLAMADA_FUNCION", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            String id = (String) nodoA.getHijos().get(0);
            switch (nodoA.getHijos().size()) {
                case 1:
                    return new LlamadaFuncion(nodoA.getLinea(), id, null);
                case 2:
                    Object listaExpresiones = this.recorrer(nodoA.getHijos().get(1));
                    return new LlamadaFuncion(nodoA.getLinea(), id, listaExpresiones);
            }
        }

        //LLAMADA_FUNCION_EXP
        if (this.soyNodo("LLAMADA_FUNCION_EXP", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            String id = (String) nodoA.getHijos().get(0);
            switch (nodoA.getHijos().size()) {
                case 1:
                    return new LlamadaFuncion(nodoA.getLinea(), id, null);
                case 2:
                    Object listaExpresiones = this.recorrer(nodoA.getHijos().get(1));
                    return new LlamadaFuncion(nodoA.getLinea(), id, listaExpresiones);
            }
        }

        //DEF_ARREGLO
        if (this.soyNodo("DEF_ARREGLO", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            int hijosSize = nodoA.getHijos().size();
            ArrayList<String> ids = new ArrayList<>();
            ArrayList<Object> miembros = new ArrayList<>();
            for (int i = 0; i < hijosSize - 2; i++) {
                ids.add((String) nodoA.getHijos().get(i));
            }
            Object longitudes = this.recorrer(nodoA.getHijos().get(hijosSize - 2));
            Object valores = this.recorrer(nodoA.getHijos().get(hijosSize - 1));
            miembros.add(ids);
            miembros.add(longitudes);
            miembros.add(valores);
            return miembros;
        }

        //LISTA_DEF_ARREGLO
        if (this.soyNodo("LISTA_DEF_ARREGLO", nodo)) {
            int hola = 1;
            System.out.println("");
        }

        //LISTA_CORCHETES
        if (this.soyNodo("LISTA_CORCHETES", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> longitudes = new ArrayList<>();
            for (Object h : nodoA.getHijos()) {
                longitudes.add(this.recorrer(h));
            }
            return longitudes;
        }

        //ARREGLO_LENGHT
        if (this.soyNodo("ARREGLO_LENGHT", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            switch (nodoA.getHijos().size()) {
                case 0: //Sin definir la longitud []
                    return null;
                case 1:
                    return this.recorrer(nodoA.getHijos().get(0));
            }
        }

        //DEF_ARREGLO_VALORES
        if (this.soyNodo("DEF_ARREGLO_VALORES", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            switch (nodoA.getHijos().size()) {
                case 0:
                    return null;
                case 1:
                    return this.recorrer(nodoA.getHijos().get(0));
            }

        }
        //LISTA_LLAVES
        if (this.soyNodo("LISTA_LLAVES", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> lista = new ArrayList<>();
            for (Object h : nodoA.getHijos()) {
                lista.add(this.recorrer(h));
            }
            return lista;
        }

        //VALORES_ARREGLO
        if (this.soyNodo("VALORES_ARREGLO", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> lista = new ArrayList<>();
            switch (nodoA.getHijos().size()) {
                case 0: //Arreglo vacio {}
                    return lista;
                case 1: //Arreglo {LISTA_EXPRESIONES}
                    return this.recorrer(nodoA.getHijos().get(0));
            }
        }

        //DECLARACION_FUNCION
        if (this.soyNodo("DECLARACION_FUNCION", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            switch (nodoA.getHijos().size()) {
                case 4: {
                    TipoNativoVar tipo = (TipoNativoVar) this.recorrer(nodoA.getHijos().get(1));
                    String id = (String) nodoA.getHijos().get(2);
                    ArrayList<Object> miembros = (ArrayList<Object>) this.recorrer(nodoA.getHijos().get(3));
                    switch (miembros.size()) {
                        case 1: {
                            Object instrucciones = miembros.get(0);
                            return new DeclaracionFuncion(nodoA.getLinea(), id, instrucciones, tipo.getTipo(), null);
                        }
                        case 2: {
                            Object parametros = miembros.get(0);
                            Object instrucciones = miembros.get(1);
                            return new DeclaracionFuncion(nodoA.getLinea(), id, instrucciones, tipo.getTipo(), parametros);
                        }
                    }
                }
                case 3: {
                    TipoNativoVar tipo = (TipoNativoVar) this.recorrer(nodoA.getHijos().get(0));
                    String id = (String) nodoA.getHijos().get(1);
                    ArrayList<Object> miembros = (ArrayList<Object>) this.recorrer(nodoA.getHijos().get(2));
                    switch (miembros.size()) {
                        case 1: {
                            Object instrucciones = miembros.get(0);
                            return new DeclaracionFuncion(nodoA.getLinea(), id, instrucciones, tipo.getTipo(), null);
                        }
                        case 2: {
                            Object parametros = miembros.get(0);
                            Object instrucciones = miembros.get(1);
                            return new DeclaracionFuncion(nodoA.getLinea(), id, instrucciones, tipo.getTipo(), parametros);
                        }
                    }
                }
            }
        }
        //S1
        if (this.soyNodo("S1", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> miembros = new ArrayList<>();
            switch (nodoA.getHijos().size()) {
                case 1: {
                    Object instrucciones = this.recorrer(nodoA.getHijos().get(0));
                    miembros.add(instrucciones);
                    return miembros;
                }
                case 2: {
                    Object parametros = this.recorrer(nodoA.getHijos().get(0));
                    Object instrucciones = this.recorrer(nodoA.getHijos().get(1));
                    miembros.add(parametros);
                    miembros.add(instrucciones);
                    return miembros;
                }
            }
        }

        //S2
        if (this.soyNodo("S2", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            return this.recorrer(nodoA.getHijos().get(0));
        }

        //LISTA_PARAMETROS
        if (this.soyNodo("LISTA_PARAMETROS", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> parametros = new ArrayList<>();
            for (Object h : nodoA.getHijos()) {
                Object parametro = this.recorrer(h);
                parametros.add(parametro);
            }
            return parametros;
        }

        //PARAMETRO
        if (this.soyNodo("PARAMETRO", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            TipoNativoVar tipo = (TipoNativoVar) this.recorrer(nodoA.getHijos().get(0));
            String id = (String) nodoA.getHijos().get(1);
            return new Variable(id, tipo.getTipo(), null, Boolean.TRUE, Boolean.FALSE);
        }

        //SALIR
        if (this.soyNodo("SALIR", nodo)) {
            return new SalirI(((NodoAST) nodo).getLinea());
        }
        //RETORNAR
        if (this.soyNodo("RETORNAR", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            switch (nodoA.getHijos().size()) {
                case 1:
                    Object exp = this.recorrer(nodoA.getHijos().get(0));
                    return new RetornarI(nodoA.getLinea(), true, exp);
                case 0:
                    return new RetornarI(nodoA.getLinea(), false, null);
            }

        }

        //CONTINUAR
        if (this.soyNodo("CONTINUAR", nodo)) {
            return new ContinuarI(((NodoAST) nodo).getLinea());
        }

        //HACER_MIENTRAS
        if (this.soyNodo("HACER_MIENTRAS", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object condicion = this.recorrer(nodoA.getHijos().get(1));
            Object instrucciones = this.recorrer(nodoA.getHijos().get(0));
            return new HacerMientras(nodoA.getLinea(), instrucciones, condicion);
        }

        //MIENTRAS
        if (this.soyNodo("MIENTRAS", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object condicion = this.recorrer(nodoA.getHijos().get(0));
            Object instrucciones = this.recorrer(nodoA.getHijos().get(1));
            return new Mientras(nodoA.getLinea(), condicion, instrucciones);
        }

        //PARA
        if (this.soyNodo("PARA", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object condicion = this.recorrer(nodoA.getHijos().get(1));
            Object asginacionPara = this.recorrer(nodoA.getHijos().get(2));
            Object instrucciones = this.recorrer(nodoA.getHijos().get(3));

            switch (nodoA.getHijos().size()) {
                case 4: //DECLARACION_VARIABLE
                    Object declaracion = this.recorrer(nodoA.getHijos().get(0));
                    return new Para(nodoA.getLinea(), declaracion, null, condicion, asginacionPara, instrucciones);
                case 5: //ASIGNACION
                    Object asignacion = this.recorrer(nodoA.getHijos().get(0));
                    return new Para(nodoA.getLinea(), null, asignacion, condicion, asginacionPara, instrucciones);
            }
        }

        //ASIGNACION
        if (this.soyNodo("ASIGNACION", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            String id = (String) nodoA.getHijos().get(0);

            switch (nodoA.getHijos().size()) {
                case 4: {
                    Object indices = this.recorrer(nodoA.getHijos().get(1));
                    TipoIgualVar tipoIgual = (TipoIgualVar) this.recorrer(nodoA.getHijos().get(2));
                    Instruccion expresion = (Instruccion) this.recorrer(nodoA.getHijos().get(3));
                    return new Asignacion(nodoA.getLinea(), id, indices, tipoIgual.getTipoIgual(), expresion);
                }
                case 3: {
                    TipoIgualVar tipoIgual = (TipoIgualVar) this.recorrer(nodoA.getHijos().get(1));
                    Instruccion expresion = (Instruccion) this.recorrer(nodoA.getHijos().get(2));
                    return new Asignacion(nodoA.getLinea(), id, tipoIgual.getTipoIgual(), expresion);
                }
                case 2: {
                    if (((String) nodoA.getHijos().get(1)).equals("++")) {
                        Instruccion expresionPP = new MasMas(nodoA.getLinea(), id);
                        return new Asignacion(nodoA.getLinea(), id, TipoIgual.ASIGNACION, expresionPP);

                    }
                    if (((String) nodoA.getHijos().get(1)).equals("--")) {
                        Instruccion expresionMM = new MenosMenos(nodoA.getLinea(), id);
                        return new Asignacion(nodoA.getLinea(), id, TipoIgual.ASIGNACION, expresionMM);
                    }
                }
            }
        }

        //ASIGNACION_PARA
        if (this.soyNodo("ASIGNACION_PARA", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            String id = (String) nodoA.getHijos().get(0);
            switch (nodoA.getHijos().size()) {
                case 2:
                    if (((String) nodoA.getHijos().get(1)).equals("++")) {
                        return new MasMas(nodoA.getLinea(), id);
                    }
                    if (((String) nodoA.getHijos().get(1)).equals("--")) {
                        return new MenosMenos(nodoA.getLinea(), id);
                    }

                case 3:
                    TipoIgualVar tipoIgual = (TipoIgualVar) this.recorrer(nodoA.getHijos().get(1));
                    Instruccion expresion = (Instruccion) this.recorrer(nodoA.getHijos().get(2));
                    return new Asignacion(nodoA.getLinea(), id, tipoIgual.getTipoIgual(), expresion);
            }
        }

        //SWITCH
        if (this.soyNodo("SWITCH", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object exp = this.recorrer(nodoA.getHijos().get(0));
            Object listaCasos = this.recorrer(nodoA.getHijos().get(1));
            return new Switch(nodoA.getLinea(), exp, listaCasos);
        }

        //CASO
        if (this.soyNodo("CASO", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object exp = this.recorrer(nodoA.getHijos().get(0));
            Object instrucciones = this.recorrer(nodoA.getHijos().get(1));
            return new Caso(exp, instrucciones, false);
        }

        //LISTA_CASOS
        if (this.soyNodo("LISTA_CASOS", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> lista = new ArrayList<>();

            for (Object h : nodoA.getHijos()) {
                Object resp = this.recorrer(h);
                if (resp instanceof Caso) {
                    lista.add(resp);
                }
            }
            return lista;
        }

        //DEFAULT
        if (this.soyNodo("DEFAULT", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object instrucciones = this.recorrer(nodoA.getHijos().get(0));
            return new Caso(null, instrucciones, true);
        }

        //INSTRUCCION_SI
        if (this.soyNodo("INSTRUCCION_SI", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            switch (nodoA.getHijos().size()) {
                //SI
                case 1:
                    ArrayList<Object> listSi = new ArrayList<>();
                    Object inst = this.recorrer(nodoA.getHijos().get(0));
                    listSi.add(inst);
                    return new InstruccionSi(nodoA.getLinea(), listSi);
                case 2:
                    //SI SINO
                    if (this.soyNodo("SI", nodoA.getHijos().get(0)) && this.soyNodo("SINO", nodoA.getHijos().get(1))) {
                        ArrayList<Object> listaInst = new ArrayList<>();
                        Object instSi = this.recorrer(nodoA.getHijos().get(0));
                        Object instSino = this.recorrer(nodoA.getHijos().get(1));
                        listaInst.add(instSi);
                        listaInst.add(instSino);
                        return new InstruccionSi(nodoA.getLinea(), listaInst);
                    }
                    //SI LISTA_SINO_SI
                    if (this.soyNodo("SI", nodoA.getHijos().get(0)) && this.soyNodo("LISTA_SINO_SI", nodoA.getHijos().get(1))) {
                        ArrayList<Object> listaInst = new ArrayList<>();
                        Object instSi = this.recorrer(nodoA.getHijos().get(0));
                        Object listaSis = this.recorrer(nodoA.getHijos().get(1));
                        listaInst.add(instSi);
                        ArrayList<Object> listaSisArray = (ArrayList<Object>) listaSis;
                        ArrayList<Object> union = this.unirArrayList(listaInst, listaSisArray);
                        return new InstruccionSi(nodoA.getLinea(), union);
                    }
                case 3:
                    //SI LISTA_SINO_SI SINO
                    ArrayList<Object> listaInst = new ArrayList<>();
                    Object instSi = this.recorrer(nodoA.getHijos().get(0));
                    Object listaSis = this.recorrer(nodoA.getHijos().get(1));
                    Object instSino = this.recorrer(nodoA.getHijos().get(2));
                    listaInst.add(instSi);
                    ArrayList<Object> listaSisArray = (ArrayList<Object>) listaSis;
                    ArrayList<Object> union = this.unirArrayList(listaInst, listaSisArray);
                    union.add(instSino);
                    return new InstruccionSi(nodoA.getLinea(), union);
            }
        }

        //SI
        if (this.soyNodo("SI", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object condicion = this.recorrer(nodoA.getHijos().get(0));
            Object instrucciones = this.recorrer(nodoA.getHijos().get(1));
            return new Si(condicion, instrucciones);
        }

        //SINO
        if (this.soyNodo("SINO", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object condicion = new Nativo(nodoA.getLinea(), "1", TipoNativo.BOOLEAN);
            Object instrucciones = this.recorrer(nodoA.getHijos().get(0));
            return new Si(condicion, instrucciones);
        }

        //SINO_SI
        if (this.soyNodo("SINO_SI", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            Object condicion = this.recorrer(nodoA.getHijos().get(0));
            Object instrucciones = this.recorrer(nodoA.getHijos().get(1));
            return new Si(condicion, instrucciones);
        }

        //LISTA_SINO_SI
        if (this.soyNodo("LISTA_SINO_SI", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> lista = new ArrayList<>();

            for (Object h : nodoA.getHijos()) {
                Object resp = this.recorrer(h);
                if (resp instanceof Si) {
                    lista.add(resp);
                }
            }
            return lista;
        }

        //TIPO_IGUAL
        if (this.soyNodo("TIPO_IGUAL", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            String tipoIgualC = (String) ((NodoAST) nodoA.getHijos().get(0)).getHijos().get(0);
            switch (tipoIgualC) {
                case "=":
                    return new TipoIgualVar(TipoIgual.ASIGNACION);
                case "+=":
                    return new TipoIgualVar(TipoIgual.MAS_IGUAL);
                case "-=":
                    return new TipoIgualVar(TipoIgual.MENOS_IGUAL);
            }
        }

        //DECLARACION_VARIABLE
        if (this.soyNodo(
                "DECLARACION_VARIABLE", nodo)) {
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
            boolean isReasignable = reasignable.equals("Var");
            switch (listaMiembros.size()) {
                case 3:
                    ListaVars listaVars = (ListaVars) listaMiembros.get(2);
                    DeclaracionVariable dv = new DeclaracionVariable(nodoA.getLinea(), tipo, listaVars.getVariables(), listaVars.getExpresion(), isReasignable, hasKeep);
                    return dv;
                case 4:
                    ArrayList<Object> miembrosArr = (ArrayList<Object>) listaMiembros.get(3);
                    Object ids = miembrosArr.get(0);
                    Object dimensiones = miembrosArr.get(1);
                    Object estructura = miembrosArr.get(2);
                    DeclaracionArreglo da = new DeclaracionArreglo(nodoA.getLinea(), tipo, ids, dimensiones, estructura, isReasignable, hasKeep);
                    return da;
            }

        }

        //DEF_VARIABLE
        if (this.soyNodo(
                "DEF_VARIABLE", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            ArrayList<Object> listaId = new ArrayList<>();
            int indiceUltimoHijo = nodoA.getHijos().size() - 1;

            if (nodoA.getHijos().get(indiceUltimoHijo) instanceof NodoAST) {
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
        if (this.soyNodo(
                "VARIABLE", nodo)) {
            NodoAST nodoA = (NodoAST) nodo;
            switch (nodoA.getHijos().size()) {
                case 3: {
                    ArrayList<Object> hijos = nodoA.getHijos();
                    ArrayList<Object> lista = new ArrayList<>();
                    Object tipo = this.recorrer(hijos.get(1));
                    Object variables = this.recorrer(hijos.get(2));
                    lista.add(hijos.get(0));
                    lista.add(tipo);
                    lista.add(variables);
                    return lista;
                }
                case 4: {
                    ArrayList<Object> hijos = nodoA.getHijos();
                    ArrayList<Object> lista = new ArrayList<>();
                    Object tipo = this.recorrer(hijos.get(1));
                    Object defArreglo = this.recorrer(hijos.get(3));
                    lista.add(hijos.get(0));
                    lista.add(tipo);
                    lista.add(hijos.get(2));
                    lista.add(defArreglo);
                    return lista;
                }

            }

        }

        //TIPOS_VARIABLE_NATIVA
        if (this.soyNodo(
                "TIPOS_VARIABLE_NATIVA", nodo)) {
            return this.recorrer(((NodoAST) nodo).getHijos().get(0));
        }

        //MENSAJE
        if (this.soyNodo(
                "MENSAJE", nodo)) {
            Object lista = this.recorrer(((NodoAST) nodo).getHijos().get(0));
            return new Mensaje("sus_linea", lista);

        }

        //LISTA_EXPRESIONES
        if (this.soyNodo(
                "LISTA_EXPRESIONES", nodo)) {
            ArrayList<Object> lista = new ArrayList<>();
            for (Object hijo : ((NodoAST) nodo).getHijos()) {
                Object exp = this.recorrer(hijo);
                lista.add(exp);
            }
            return lista;
        }

        //EXP
        if (this.soyNodo(
                "EXP", nodo)) {
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
        if (this.soyNodo(
                "ENTERO_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.ENTERO);
        }

        //DOBLE_VAL
        if (this.soyNodo(
                "DOBLE_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.DECIMAL);
        }

        //VERDADERO
        if (this.soyNodo(
                "VERDADERO", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.BOOLEAN);
        }

        //FALSO
        if (this.soyNodo(
                "FALSO", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.BOOLEAN);
        }

        //CADENA_VAL
        if (this.soyNodo(
                "CADENA_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.CADENA);
        }

        //CARACTER_VAL
        if (this.soyNodo(
                "CARACTER_VAL", nodo)) {
            String num = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Nativo(((NodoAST) nodo).getLinea(), num, TipoNativo.CARACTER);
        }

        //ID
        if (this.soyNodo(
                "ID", nodo)) {
            String id = (String) ((NodoAST) nodo).getHijos().get(0);
            return new Id(((NodoAST) nodo).getLinea(), id);
        }

        //------VARIABLES NATIVAS--------
        //ENTERO
        if (this.soyNodo(
                "ENTERO", nodo)) {
            return new TipoNativoVar(TipoNativo.ENTERO);
        }

        //DOBLE
        if (this.soyNodo(
                "DOBLE", nodo)) {
            return new TipoNativoVar(TipoNativo.DECIMAL);
        }

        //CARACTER
        if (this.soyNodo(
                "CARACTER", nodo)) {
            return new TipoNativoVar(TipoNativo.CARACTER);
        }

        //CADENA
        if (this.soyNodo(
                "CADENA", nodo)) {
            return new TipoNativoVar(TipoNativo.CADENA);
        }

        //BOOLEAN
        if (this.soyNodo(
                "BOOLEAN", nodo)) {
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

    private ArrayList<Object> unirArrayList(ArrayList<Object> lista1, ArrayList<Object> lista2) {
        ArrayList<Object> listaUnida = new ArrayList<>(lista1);
        listaUnida.addAll(lista2);
        return listaUnida;
    }

}
