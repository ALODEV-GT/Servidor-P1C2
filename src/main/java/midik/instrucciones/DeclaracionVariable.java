package midik.instrucciones;

import java.util.ArrayList;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Entorno;
import midik.defvariables.TipoNativoVar;
import midik.ejecucion.Variable;
import midik.Singletons.Errores;
import midik.Singletons.Error;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class DeclaracionVariable extends Instruccion {
    
    private ArrayList<String> ids;
    private Instruccion expresion;
    private TipoNativoVar tipo;
    private Boolean reasignable;
    private Boolean isKeep;
    
    public DeclaracionVariable(String linea, TipoNativoVar tipo, Object ids, Object expresion, Boolean reasignable, Boolean isKeep) {
        super(linea);
        this.tipo = tipo;
        this.ids = (ArrayList<String>) ids;
        this.expresion = (Instruccion) expresion;
        this.reasignable = reasignable;
        this.isKeep = isKeep;
    }
    
    @Override
    public Object ejecutar(Entorno entorno) {
        
        for (String id : this.ids) {
            //Verificar si existe la variable en el entorno actual
            Variable var = entorno.getVariable(id);
            if (var == null) {

                //Declarar variables
                if (this.expresion == null) {
                    entorno.setVariable(new Variable(id, this.tipo.getTipo(), null, this.reasignable, this.isKeep));
                } else {
                    Nativo valor = (Nativo) this.expresion.ejecutar(entorno);

                    //Verificacion de tipos y casteos
                    switch (this.tipo.getTipo()) {
                        case ENTERO:
                            switch (valor.getTipo()) {
                                case ENTERO:
                                    //Dejar pasar
                                    break;
                                case DECIMAL:
                                    valor = new Nativo(valor.getLinea(), (Integer.valueOf(((String) valor.getValor()).split("\\.")[0])).toString(), TipoNativo.ENTERO);
                                    break;
                                case BOOLEAN:
                                    valor = new Nativo(valor.getLinea(), this.getValorBoolean(valor.getValor()), TipoNativo.ENTERO);
                                    break;
                                case CADENA:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo entero"));
                                    return null;
                                case CARACTER:
                                    valor = new Nativo(valor.getLinea(), this.getValorChar(valor.getValor()), TipoNativo.ENTERO);
                                    break;
                                default:
                                    break;
                            }
                            
                            break;
                        case DECIMAL:
                            switch (((Nativo) valor).getTipo()) {
                                case ENTERO:
                                    valor = new Nativo(this.getLinea(), (Double.valueOf((String) valor.getValor())).toString(), TipoNativo.DECIMAL);
                                    break;
                                case DECIMAL:
                                    //No hacer nada
                                    break;
                                case BOOLEAN:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una booleano a una variable de tipo doble"));
                                    return null;
                                case CADENA:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo doble"));
                                    return null;
                                case CARACTER:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una caracter a una variable de tipo doble"));
                                    return null;
                                default:
                                    break;
                            }
                            break;
                        case BOOLEAN:
                            switch (((Nativo) valor).getTipo()) {
                                case ENTERO:
                                    boolean valido = this.esValidoValorIntParaBoolean(valor.getValor());
                                    if (!valido) {
                                        Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una entero diferente a 0 o 1 a una variable de tipo booleano"));
                                        return null;
                                    }
                                    break;
                                case DECIMAL:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una decimal a una variable de tipo booleano"));
                                    return null;
                                case BOOLEAN:
                                    //No hacer nada
                                    break;
                                case CADENA:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo booleano"));
                                    return null;
                                case CARACTER:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una caracter a una variable de tipo booleano"));
                                    return null;
                                default:
                                    break;
                            }
                            break;
                        case CADENA:
                            switch (((Nativo) valor).getTipo()) {
                                case ENTERO:
                                case DECIMAL:
                                case CARACTER:
                                    valor.setTipo(TipoNativo.CADENA);
                                    break;
                                case BOOLEAN:
                                    valor = new Nativo(this.getLinea(), this.getValorBoolean(valor.getValor()), TipoNativo.CARACTER);
                                    break;
                                case CADENA:
                                    //No hacer nada
                                    break;
                                default:
                                    break;
                            }
                            break;
                        case CARACTER:
                            switch (((Nativo) valor).getTipo()) {
                                case ENTERO:
                                    valor = new Nativo(this.getLinea(), this.getValorIntAChar(valor.getValor()), TipoNativo.CARACTER);
                                    break;
                                case DECIMAL:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una doble a una variable de tipo caracter"));
                                    return null;
                                case BOOLEAN:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una boolean a una variable de tipo caracter"));
                                    return null;
                                case CADENA:
                                    Errores.getInstance().push(new Error("Semantico", this.getLinea(), "No puedes asignar una cadena a una variable de tipo caracter"));
                                    return null;
                                case CARACTER:
                                    //No hacer nada
                                    break;
                                default:
                                    break;
                            }
                            break;
                        default:
                            break;
                    }
                    
                    entorno.setVariable(new Variable(id, this.tipo.getTipo(), valor, this.reasignable, this.isKeep));
                }
            } else {
                Errores.getInstance().push(new Error("Semantico", this.getLinea(), "Ya existe una variable con el nombre " + id));
            }
            
        }
        return null;
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
