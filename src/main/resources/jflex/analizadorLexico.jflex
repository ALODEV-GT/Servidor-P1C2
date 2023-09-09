//importanciones y codigo del usuario
package midik.jflex;
import java_cup.runtime.Symbol;
import midik.cup.sym;
import midik.Singletons.Errores;

%% //Opciones y declaraciones
%class AnalizadorLexico //Nombre de la clase a generar
%public //Modificador de acceso de la clase
%unicode
%type Symbol //Tipo de retorno de los tokens
%cup //Indica que sera compatible con cup
%line //Contador de lineas
%column //Contador de columnas
%state COMENTARIO_BLOQUE

%{
    private String comentarioBloque="";

    public AnalizadorLexico(java.io.Reader in, String nombreArchivo){
        this(in);
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yycolumn+1, yyline+1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yycolumn+1, yyline+1, value);
    }

    private void agregarComentarioLinea(String comentario){
        //System.out.println("Comentario en linea agregado");
    }

    private void agregarComentarioBloque(String comentario){
        //System.out.println("Comentario en bloque agregado");
    }
%}

//PALABRAS RESERVADAS
// Ejemplo: final | Final
PISTA = "Pista" | "pista"
EXTIENDE = "Extiende" | "extiende"
ENTERO = "Entero" | "entero"
DOBLE = "Doble" | "doble"
BOOLEAN = "Boolean" | "boolean"
CARACTER = "Caracter" | "caracter"
CADENA = "Cadena" | "cadena"
KEEP = "Keep" | "keep"
VAR = "Var" | "var"
ARREGLO = "Arreglo" | "arreglo"
SI = "Si" | "si"
SINO = "Sino" | "sino"
SWITCH = "Switch" | "switch"
CASO = "Caso" | "caso"
SALIR = "Salir" | "salir"
DEFAULT = "Default" | "default"
PARA = "Para" | "para"
MIENTRAS = "Mientras" | "mientras"
HACER = "Hacer" | "hacer"
CONTINUAR = "Continuar" | "continuar"
RETORNA = "Retorna" | "retorna"
REPRODUCIR = "Reproducir" | "reproducir"
ESPERAR = "Esperar" | "esperar"
ORDENAR = "Ordenar" | "ordenar"
SUMARIZAR = "Sumarizar" | "sumarizar"
LONGITUD = "Longitud" | "longitud"
MENSAJE = "Mensaje" | "mensaje"
PRINCIPAL = "Principal" | "principal"
ASC_ORD = "Ascendente" | "ascendente"
DESC_ORD = "Descendente" | "descendente"
PAR_ORD = "Pares" | "pares"
IMP_ORD = "Impares" | "impares"
PRIM_ORD = "Primos" | "primos"

//Signos de puntuacion
P_COMA = ";"
PUNTO = "."
COMA = ","
DOS_PUNTOS = ":"

//Signos de agrupacion
LLAVE_A = "{"
LLAVE_C = "}"
CORCHETE_A = "["
CORCHETE_C = "]"
PARENTESIS_A = "("
PARENTESIS_C = ")"

//Partes (Solo seran utiles para formar otras estructuras)
FIN_LINEA = \r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085

//Comentarios
COMENTARIO_LINEA = ">>"~{FIN_LINEA}
INICIO_COMENTARIO_BLOQUE = "<-"
FIN_COMENTARIO_BLOQUE = "->"

//Operadores relacionales
IGUAL = "=="
MAYOR_IGUAL = ">="
MENOR_IGUAL = "<="
ASIGNACION = "="
MAYOR_QUE = ">"
MENOR_QUE = "<"
DIFERENTE = "!="
IS_NULL = "!!"

//Operadores logicos
AND = "&&"
OR = "||"
NOT = "!"
NAND = "!&&"
NOR = "!||"
XOR = "&|"

//Operadores aritmeticos
MAS = "+"
MENOS = "-"
POR = "*"
DIVISION = "/"
MOD = "%"
POT = "^"

//Operadores incremento/decremento
INCREMENTO = "++"
DECREMENTO = "--"

//Partes (Solo seran utiles para formar otras estructuras)
LETRA = [a-zA-Z]
NUMERO = [0-9]
GUION_BAJO = "_"
FIN_LINEA = \r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085
ESPACIO = " " | "\t"
CARACTERES = [^\r\n]

//Valores
ID = ({GUION_BAJO} | {LETRA})({GUION_BAJO} | {LETRA} | {NUMERO})*
ENTERO_VAL = {NUMERO}{NUMERO}*
DOBLE_VAL = {NUMERO}{NUMERO}*{PUNTO}{NUMERO}{NUMERO}*
CADENA_VAL = "\""~"\""
VERDADERO = "true" | "verdadero"
FALSO = "false" | "falso"
CARACTER_VAL = "'"{CARACTERES}"'"

%% //Reglas lexicas
<YYINITIAL> {PUNTO} {return symbol(sym.PUNTO, yytext());}
<YYINITIAL> {CADENA_VAL} {return symbol(sym.CADENA_VAL, yytext());}
<YYINITIAL> {VERDADERO} {return symbol(sym.VERDADERO, yytext());}
<YYINITIAL> {FALSO} {return symbol(sym.FALSO, yytext());}
<YYINITIAL> {CARACTER} {return symbol(sym.CARACTER, yytext());}
<YYINITIAL> {PISTA} {return symbol(sym.PISTA, yytext());}
<YYINITIAL> {EXTIENDE} {return symbol(sym.EXTIENDE, yytext());}
<YYINITIAL> {ENTERO} {return symbol(sym.ENTERO, yytext());}
<YYINITIAL> {DOBLE} {return symbol(sym.DOBLE, yytext());}
<YYINITIAL> {BOOLEAN} {return symbol(sym.BOOLEAN, yytext());}
<YYINITIAL> {CARACTER_VAL} {return symbol(sym.CARACTER_VAL, yytext());}
<YYINITIAL> {CADENA} {return symbol(sym.CADENA, yytext());}
<YYINITIAL> {KEEP} {return symbol(sym.KEEP, yytext());}
<YYINITIAL> {VAR} {return symbol(sym.VAR, yytext());}
<YYINITIAL> {ARREGLO} {return symbol(sym.ARREGLO, yytext());}
<YYINITIAL> {SI} {return symbol(sym.SI, yytext());}
<YYINITIAL> {SINO} {return symbol(sym.SINO, yytext());}
<YYINITIAL> {SWITCH} {return symbol(sym.SWITCH, yytext());}
<YYINITIAL> {CASO} {return symbol(sym.CASO, yytext());}
<YYINITIAL> {SALIR} {return symbol(sym.SALIR, yytext());}
<YYINITIAL> {DEFAULT} {return symbol(sym.DEFAULT, yytext());}
<YYINITIAL> {PARA} {return symbol(sym.PARA, yytext());}
<YYINITIAL> {MIENTRAS} {return symbol(sym.MIENTRAS, yytext());}
<YYINITIAL> {HACER} {return symbol(sym.HACER, yytext());}
<YYINITIAL> {CONTINUAR} {return symbol(sym.CONTINUAR, yytext());}
<YYINITIAL> {RETORNA} {return symbol(sym.RETORNA, yytext());}
<YYINITIAL> {REPRODUCIR} {return symbol(sym.REPRODUCIR, yytext());}
<YYINITIAL> {ESPERAR} {return symbol(sym.ESPERAR, yytext());}
<YYINITIAL> {ORDENAR} {return symbol(sym.ORDENAR, yytext());}
<YYINITIAL> {SUMARIZAR} {return symbol(sym.SUMARIZAR, yytext());}
<YYINITIAL> {LONGITUD} {return symbol(sym.LONGITUD, yytext());}
<YYINITIAL> {MENSAJE} {return symbol(sym.MENSAJE, yytext());}
<YYINITIAL> {PRINCIPAL} {return symbol(sym.PRINCIPAL, yytext());}
<YYINITIAL> {ASC_ORD} {return symbol(sym.ASC_ORD, yytext());}
<YYINITIAL> {DESC_ORD} {return symbol(sym.DESC_ORD, yytext());}
<YYINITIAL> {PAR_ORD} {return symbol(sym.PAR_ORD, yytext());}
<YYINITIAL> {IMP_ORD} {return symbol(sym.IMP_ORD, yytext());}
<YYINITIAL> {PRIM_ORD} {return symbol(sym.PRIM_ORD, yytext());}
<YYINITIAL> {P_COMA} {return symbol(sym.P_COMA, yytext());}
<YYINITIAL> {PUNTO} {return symbol(sym.PUNTO, yytext());}
<YYINITIAL> {COMA} {return symbol(sym.COMA, yytext());}
<YYINITIAL> {DOS_PUNTOS} {return symbol(sym.DOS_PUNTOS, yytext());}
<YYINITIAL> {LLAVE_A} {return symbol(sym.LLAVE_A, yytext());}
<YYINITIAL> {LLAVE_C} {return symbol(sym.LLAVE_C, yytext());}
<YYINITIAL> {CORCHETE_A} {return symbol(sym.CORCHETE_A, yytext());}
<YYINITIAL> {CORCHETE_C} {return symbol(sym.CORCHETE_C, yytext());}
<YYINITIAL> {PARENTESIS_A} {return symbol(sym.PARENTESIS_A, yytext());}
<YYINITIAL> {PARENTESIS_C} {return symbol(sym.PARENTESIS_C, yytext());}
<YYINITIAL> {INCREMENTO} {return symbol(sym.INCREMENTO, yytext());}
<YYINITIAL> {DECREMENTO} {return symbol(sym.DECREMENTO, yytext());}
<YYINITIAL> {MAS} {return symbol(sym.MAS, yytext());}
<YYINITIAL> {MENOS} {return symbol(sym.MENOS, yytext());}
<YYINITIAL> {POR} {return symbol(sym.POR, yytext());}
<YYINITIAL> {DIVISION} {return symbol(sym.DIVISION, yytext());}
<YYINITIAL> {MOD} {return symbol(sym.MOD, yytext());}
<YYINITIAL> {POT} {return symbol(sym.POT, yytext());}
<YYINITIAL> {INICIO_COMENTARIO_BLOQUE}          {yybegin(COMENTARIO_BLOQUE);}
<YYINITIAL> {COMENTARIO_LINEA}                  {agregarComentarioLinea(yytext());}
<YYINITIAL> {IGUAL} {return symbol(sym.IGUAL, yytext());}
<YYINITIAL> {MAYOR_IGUAL} {return symbol(sym.MAYOR_IGUAL, yytext());}
<YYINITIAL> {MENOR_IGUAL} {return symbol(sym.MENOR_IGUAL, yytext());}
<YYINITIAL> {ASIGNACION} {return symbol(sym.ASIGNACION, yytext());}
<YYINITIAL> {MAYOR_QUE} {return symbol(sym.MAYOR_QUE, yytext());}
<YYINITIAL> {MENOR_QUE} {return symbol(sym.MENOR_QUE, yytext());}
<YYINITIAL> {DIFERENTE} {return symbol(sym.DIFERENTE, yytext());}
<YYINITIAL> {IS_NULL} {return symbol(sym.IS_NULL, yytext());}
<YYINITIAL> {AND} {return symbol(sym.AND, yytext());}
<YYINITIAL> {OR} {return symbol(sym.OR, yytext());}
<YYINITIAL> {NOT} {return symbol(sym.NOT, yytext());}
<YYINITIAL> {NAND} {return symbol(sym.NAND, yytext());}
<YYINITIAL> {NOR} {return symbol(sym.NOR, yytext());}
<YYINITIAL> {XOR} {return symbol(sym.XOR, yytext());}
<YYINITIAL> {ID} {return symbol(sym.ID, yytext());}
<YYINITIAL> {ENTERO_VAL} {return symbol(sym.ENTERO_VAL, yytext());}
<YYINITIAL> {DOBLE_VAL} {return symbol(sym.DOBLE_VAL, yytext());}
<YYINITIAL> {FIN_LINEA}                         {}
<YYINITIAL> {ESPACIO}                           {}

<COMENTARIO_BLOQUE> {
    {FIN_COMENTARIO_BLOQUE}                     {yybegin(YYINITIAL); agregarComentarioBloque(comentarioBloque); comentarioBloque="";}
    [^]                                         {comentarioBloque+=yytext();}
}

<<EOF>>                                         { return symbol(sym.EOF,"FIN"); }
<YYINITIAL> .                                   { Errores.getInstance().push(new midik.Singletons.Error("Lexico", String.valueOf(yyline+1) + " Columna:" + yycolumn, "El simbolo " + yytext() + " no existe en el lenguaje."));}

