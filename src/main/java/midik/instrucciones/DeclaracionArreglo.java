package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.defvariables.TipoNativoVar;
import midik.ejecucion.Arreglo;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class DeclaracionArreglo extends Instruccion {

    private TipoNativoVar tipo;
    private ArrayList<String> ids;
    private ArrayList<Object> dimensiones;
    private ArrayList<Object> arreglo;
    private Boolean reasignable;
    private Boolean isKeep;
    private Entorno entorno;

    public DeclaracionArreglo(String linea, TipoNativoVar tipo, Object ids, Object dimensiones, Object arreglo, Boolean reasignable, Boolean isKeep) {
        super(linea);
        this.tipo = tipo;
        this.ids = (ArrayList<String>) ids;
        this.dimensiones = (ArrayList<Object>) dimensiones;
        this.arreglo = (ArrayList<Object>) arreglo;
        this.reasignable = reasignable;
        this.isKeep = isKeep;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        //Validaciones
        this.entorno = entorno;

        //Si tiene corchetes con tamaño y el arreglo tiene valor inicial;
        if (this.dimensiones.get(0) != null && this.arreglo != null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "No puedes definir la longitud del arreglo y ademas asignarle un valor al arreglo."));
            return null;
        }

        //Si tiene corchetes sin tamaño y el arreglo no tiene valor inicial;
        if (this.dimensiones.get(0) == null && this.arreglo == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Debes indicar la longitud del arreglo entre los corchetes o asignarle un valor inicial al arreglo."));
            return null;
        }

        int numero = 0;
        if (this.arreglo != null) {
            //Si se declaro un arreglo vacio
            if (((ArrayList<Object>) this.arreglo.get(0)).isEmpty()) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "No puedes definir un arreglo vacio."));
                return null;
            }

            boolean sonIguales = this.sonDimensionesIguales(this.arreglo.get(0), -1);
            //Verificar si todas las dimensiones tienen la misma longitud
            if (!sonIguales) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Todas las dimensiones deben tener la misma longitud."));
                return null;
            }

            //validar que los valores sean del  mismo tipo definino
            boolean sonDelMismoTipo = this.sonDelMismoTipo(this.arreglo, this.tipo);
            if (!sonDelMismoTipo) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Los valores asignados al arreglo no coiciden con el tipo definido."));
                return null;
            }

            //Verificar si las dimensiones declaradas son iguales a las dimensiones de el valor asignado. 
            numero = this.getNumeroDimensiones(this.arreglo) - 1;
            if (this.dimensiones.size() != numero) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Declaraste un arreglo de " + this.dimensiones.size() + " y el valor asignado es de " + numero + " dimensiones."));
                return null;
            }
        } else {
            for (Object h : this.dimensiones) {
                Nativo exp = (Nativo) ((Instruccion) h).ejecutar(entorno);
                //Validar que los indices sean de tipo entero
                if (exp.getTipo() != TipoNativo.ENTERO) {
                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Las longitudes deben ser de tipo entero."));
                    return null;
                } else {
                    //Validar que los indices sean mayor a 0
                    Integer valor = Integer.valueOf((String) exp.getValor());
                    if (valor < 1) {
                        Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Las longitudes/longitud debe ser mayor a 0."));
                        return null;
                    }
                }
            }

            //Validar que las longitudes declaradas sean iguales
            if (!this.sonLasLongitudesDeclaradasIguales(this.dimensiones)) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico ", this.getLinea(), "Todas las longitudes en cada dimension deben ser iguales."));
                return null;
            }

        }

        //Declaracion de variables
        for (String id : this.ids) {
            //Verificar si ya existe 
            Arreglo arr = entorno.getArreglo(id);
            if (arr == null) {
                //Crear arreglo
                if (this.arreglo != null) {
                    //Se crea el arreglo con valores
                    int l = ((ArrayList<Object>) this.arreglo.get(0)).size();
                    entorno.setArreglo(new Arreglo(id, this.tipo.getTipo(), this.arreglo, l, numero, reasignable, isKeep));
                } else {
                    //Solo se define la estructura del arreglo;
                    ArrayList<Object> arregloCreado = new ArrayList<>();
                    Nativo nativo = (Nativo) ((Instruccion) this.dimensiones.get(0)).ejecutar(entorno);
                    Integer intDim = Integer.valueOf((String) nativo.getValor());
                    ArrayList<Object> aux = this.crearEstructuraArreglo(arregloCreado, this.dimensiones.size(), intDim);
                    arregloCreado = aux;
                    ArrayList<Object> arregloPadre = new ArrayList<>();
                    arregloPadre.add(arregloCreado);
                    entorno.setArreglo(new Arreglo(id, this.tipo.getTipo(), arregloPadre, arregloCreado.size(), this.dimensiones.size(), reasignable, isKeep));
                }
            } else {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Ya existe un arreglo con el nombre " + id));
            }
        }

        return null;
    }

    private boolean sonLasLongitudesDeclaradasIguales(ArrayList<Object> longitudes) {
        boolean iguales = true;
        Nativo exp = (Nativo) ((Instruccion) longitudes.get(0)).ejecutar(this.entorno);
        String valor = (String) exp.getValor();
        for (Object h : longitudes) {
            Nativo expAux = (Nativo) ((Instruccion) h).ejecutar(this.entorno);
            String valorAux = (String) expAux.getValor();
            if (!valor.equals(valorAux)) {
                iguales = false;
                break;
            }
        }
        return iguales;
    }

    private ArrayList<Object> crearEstructuraArreglo(ArrayList<Object> arreglo, int dimensiones, int longitud) {
        int restantes = dimensiones - 1;
        if (restantes != -1) {
            for (int i = 0; i < longitud; i++) {
                ArrayList<Object> aux = new ArrayList<>();
                arreglo.add(this.crearEstructuraArreglo(aux, restantes, longitud));
            }
        } else {
            return arreglo;
        }
        return arreglo;
    }

    private int getNumeroDimensiones(Object estructura) {
        int numDimensiones = 1;
        ArrayList dim = (ArrayList) estructura;
        if (dim.get(0) instanceof ArrayList) {
            numDimensiones += this.getNumeroDimensiones(dim.get(0));
        }
        return numDimensiones;
    }

    private boolean sonDimensionesIguales(Object dimensiones, int dimension) {
        boolean iguales = true;
        if (dimension == -1) {
            ArrayList dim = (ArrayList) dimensiones;
            if (dim.get(0) instanceof ArrayList) {
                for (Object h : dim) {
                    if (!sonDimensionesIguales(h, dim.size())) {
                        iguales = false;
                        break;
                    }
                }
            }
        } else {
            ArrayList dim = (ArrayList) dimensiones;
            if (dim.get(0) instanceof ArrayList) {
                for (Object h : dim) {
                    if (!sonDimensionesIguales(h, dim.size())) {
                        iguales = false;
                        break;
                    }
                }
            } else {
                return dimension == dim.size();
            }
        }

        return iguales;
    }

    private boolean sonDelMismoTipo(Object estructura, TipoNativoVar tipo) {
        boolean iguales = true;
        ArrayList dim = (ArrayList) estructura;
        if (dim.get(0) instanceof ArrayList) {
            for (Object h : dim) {
                if (!sonDelMismoTipo(h, tipo)) {
                    iguales = false;
                    break;
                }
            }
        } else {
            for (int i = 0; i < dim.size(); i++) {
                Instruccion exp = (Instruccion) ((Instruccion) dim.get(i)).ejecutar(this.entorno);
                Nativo hn = (Nativo) exp;
                if (hn.getTipo() != this.tipo.getTipo()) {

                    switch (tipo.getTipo()) {
                        case ENTERO:
                            switch (hn.getTipo()) {
                                case ENTERO:
                                    //Dejar pasar
                                    break;
                                case DECIMAL:
                                    hn = new Nativo(hn.getLinea(), (Integer.valueOf(((String) hn.getValor()).split("\\.")[0])).toString(), TipoNativo.ENTERO);
                                    break;
                                case BOOLEAN:
                                    hn = new Nativo(hn.getLinea(), this.getValorBoolean(hn.getValor()), TipoNativo.ENTERO);
                                    break;
                                case CADENA:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo entero"));
                                    iguales = false;
                                    break;
                                case CARACTER:
                                    hn = new Nativo(hn.getLinea(), this.getValorChar(hn.getValor()), TipoNativo.ENTERO);
                                    break;
                            }

                            break;
                        case DECIMAL:
                            switch (((Nativo) hn).getTipo()) {
                                case ENTERO:
                                    hn = new Nativo(this.getLinea(), (Double.valueOf((String) hn.getValor())).toString(), TipoNativo.DECIMAL);
                                    break;
                                case DECIMAL:
                                    //No hacer nada
                                    break;
                                case BOOLEAN:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una booleano a una variable de tipo doble"));
                                    iguales = false;
                                    break;
                                case CADENA:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo doble"));
                                    iguales = false;
                                    break;
                                case CARACTER:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una caracter a una variable de tipo doble"));
                                    iguales = false;
                                    break;
                            }
                            break;
                        case BOOLEAN:
                            switch (((Nativo) hn).getTipo()) {
                                case ENTERO:
                                    boolean valido = this.esValidoValorIntParaBoolean(hn.getValor());
                                    if (!valido) {
                                        Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una entero diferente a 0 o 1 a una variable de tipo booleano"));
                                        iguales = false;
                                    }
                                    break;
                                case DECIMAL:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una decimal a una variable de tipo booleano"));
                                    iguales = false;
                                    break;
                                case BOOLEAN:
                                    //No hacer nada
                                    break;
                                case CADENA:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo booleano"));
                                    iguales = false;
                                    break;
                                case CARACTER:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una caracter a una variable de tipo booleano"));
                                    iguales = false;
                                    break;
                            }
                            break;
                        case CADENA:
                            switch (((Nativo) hn).getTipo()) {
                                case ENTERO:
                                case DECIMAL:
                                case CARACTER:
                                    hn.setTipo(TipoNativo.CADENA);
                                    break;
                                case BOOLEAN:
                                    hn = new Nativo(this.getLinea(), this.getValorBoolean(hn.getValor()), TipoNativo.CARACTER);
                                    break;
                                case CADENA:
                                    //No hacer nada
                                    break;
                            }
                            break;
                        case CARACTER:
                            switch (((Nativo) hn).getTipo()) {
                                case ENTERO:
                                    hn = new Nativo(this.getLinea(), this.getValorIntAChar(hn.getValor()), TipoNativo.CARACTER);
                                    break;
                                case DECIMAL:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una doble a una variable de tipo caracter"));
                                    iguales = false;
                                    break;
                                case BOOLEAN:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una boolean a una variable de tipo caracter"));
                                    iguales = false;
                                    break;
                                case CADENA:
                                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo caracter"));
                                    iguales = false;
                                    break;
                                case CARACTER:
                                    //No hacer nada
                                    break;
                            }
                            break;
                    }
                }
                dim.set(i, hn);
                if (!iguales) {
                    break;
                }
            }
        }
        return iguales;
    }

    private String getValorBoolean(Object valor) {
        String cadena = (String) valor;
        if (cadena.equals("verdadero") || cadena.equals("true") || cadena.equals("1")) {
            return "1";
        } else {
            return "0";
        }
    }

    private String getValorChar(Object valor) {
        String cadena = ((String) valor).replace("'", "");
        char caracter = cadena.toCharArray()[0];
        return String.valueOf((int) caracter);
    }

    private String getValorIntAChar(Object valor) {
        Integer entero = Integer.valueOf((String) valor);
        char caracter = (char) entero.intValue();
        return caracter + "";
    }

    private boolean esValidoValorIntParaBoolean(Object valor) {
        String cadena = (String) valor;
        if (cadena.equals("1") || cadena.equals("0")) {
            return true;
        } else {
            return false;
        }

    }

}
