package midik.analizadoresRecibirCancion.jlex;
import java_cup.runtime.Symbol;
import midik.analizadoresRecibirCancion.cup.sym;

%% //Opciones y declaraciones
%class AnalizadorLexicoRC //Nombre de la clase a generar
%public //Modificador de acceso de la clase
%unicode
%type Symbol //Tipo de retorno de los tokens
%cup //Indica que sera compatible con cup
%line //Contador de lineas
%column //Contador de columnas

%{
    public AnalizadorLexicoRC(java.io.Reader in, String nombreArchivo){
        this(in);
    }

    private Symbol symbol(int type) {
        return new Symbol(type, yycolumn+1, yyline+1);
    }

    private Symbol symbol(int type, Object value) {
        return new Symbol(type, yycolumn+1, yyline+1, value);
    }
%}

//PALABRAS RESERVADAS
SOLICITUD_A = "<solicitud>"
TIPO_A = "<tipo>"
DATOS_A = "<datos>"
CANAL_A = "<canal>"
NOTA_A = "<nota>"
OCTAVA_A = "<octava>"
DURACION_A = "<duracion>"
SOLICITUD_C = "</solicitud>"
TIPO_C = "</tipo>"
DATOS_C = "</datos>"
CANAL_C = "</canal>"
NOTA_C = "</nota>"
OCTAVA_C = "</octava>"
DURACION_C = "</duracion>"

//Notas
DO = "Do"
DOS = "Do#"
RE = "Re"
RES = "Re#"
MI = "Mi"
FA = "Fa"
FAS = "Fa#"
SOL = "Sol"
SOLS = "Sol#"
LA = "La"
LAS = "La#"
SI = "Si"
SILENCIO = "R"
//Partes
NUMERO = [0-9]
LETRA = [a-zA-Z]
ESPACIO = " "
FIN_LINEA = \r|\n|\r\n|\u2028|\u2029|\u000B|\u000C|\u0085|\t

//valores
NUMEROS = {NUMERO}{NUMERO}*
ID = ({LETRA})({ESPACIO} | {LETRA} | {NUMERO})*

%%
<YYINITIAL> {SOLICITUD_A} {return symbol(sym.SOLICITUD_A,yytext());}
<YYINITIAL> {TIPO_A} {return symbol(sym.TIPO_A,yytext());}
<YYINITIAL> {DATOS_A} {return symbol(sym.DATOS_A,yytext());}
<YYINITIAL> {CANAL_A} {return symbol(sym.CANAL_A,yytext());}
<YYINITIAL> {NOTA_A} {return symbol(sym.NOTA_A,yytext());}
<YYINITIAL> {OCTAVA_A} {return symbol(sym.OCTAVA_A,yytext());}
<YYINITIAL> {DURACION_A} {return symbol(sym.DURACION_A,yytext());}
<YYINITIAL> {SOLICITUD_C} {return symbol(sym.SOLICITUD_C,yytext());}
<YYINITIAL> {TIPO_C} {return symbol(sym.TIPO_C,yytext());}
<YYINITIAL> {DATOS_C} {return symbol(sym.DATOS_C,yytext());}
<YYINITIAL> {CANAL_C} {return symbol(sym.CANAL_C,yytext());}
<YYINITIAL> {NOTA_C} {return symbol(sym.NOTA_C,yytext());}
<YYINITIAL> {OCTAVA_C} {return symbol(sym.OCTAVA_C,yytext());}
<YYINITIAL> {DURACION_C} {return symbol(sym.DURACION_C,yytext());}
<YYINITIAL> {DO} {return symbol(sym.DO,yytext());}
<YYINITIAL> {DOS} {return symbol(sym.DOS,yytext());}
<YYINITIAL> {RE} {return symbol(sym.RE,yytext());}
<YYINITIAL> {RES} {return symbol(sym.RES,yytext());}
<YYINITIAL> {MI} {return symbol(sym.MI,yytext());}
<YYINITIAL> {FA} {return symbol(sym.FA,yytext());}
<YYINITIAL> {FAS} {return symbol(sym.FAS,yytext());}
<YYINITIAL> {SOL} {return symbol(sym.SOL,yytext());}
<YYINITIAL> {SOLS} {return symbol(sym.SOLS,yytext());}
<YYINITIAL> {LA} {return symbol(sym.LA,yytext());}
<YYINITIAL> {LAS} {return symbol(sym.LAS,yytext());}
<YYINITIAL> {SI} {return symbol(sym.SI,yytext());}
<YYINITIAL> {SILENCIO} {return symbol(sym.SILENCIO,yytext());}
<YYINITIAL> {NUMEROS} {return symbol(sym.NUMEROS,yytext());}
<YYINITIAL> {ID} {return symbol(sym.ID,yytext());}

<YYINITIAL> {FIN_LINEA}                         {}

<<EOF>>                                         { return symbol(sym.EOF,"FIN"); }
<YYINITIAL> .                                   { System.out.println("Error Lexico,  Linea: "+ String.valueOf(yyline+1) + " Columna:" + yycolumn + ", El simbolo " + yytext() + " no existe en el lenguaje.");}

