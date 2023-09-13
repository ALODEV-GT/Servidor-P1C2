package midik.recibirCancion;

import java.io.StringReader;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import midik.Singletons.Biblioteca;
import midik.Singletons.CentroCanales;
import midik.Singletons.CentroCanalesThread;
import midik.Singletons.Consola;
import midik.Singletons.Errores;
import midik.Singletons.FrontendSingleton;
import midik.analizadoresRecibirCancion.cup.parser;
import midik.analizadoresRecibirCancion.jlex.AnalizadorLexicoRC;
import midik.arbol.NodoAST;
import midik.ejecucion.Ejecucion;
import midik.jflex.AnalizadorLexico;
import midik.musica.Pista;

public class ProcesarCancion {

    private String nombrePista;
    private final String codigoFuente;
    private String codigoFuenteMusic;

    public ProcesarCancion(String codigoFuente) {
        this.codigoFuente = codigoFuente;
    }

    public boolean procesar() {
        boolean guardado = true;
        StringReader sr = new StringReader(codigoFuente);
        System.out.println(this.codigoFuente);
        AnalizadorLexicoRC alrc = new AnalizadorLexicoRC(sr, "");
        parser par = new parser(alrc, "");

        try {
            par.parse();
            this.codigoFuenteMusic = par.getCodigoFuente();
            System.out.println(this.codigoFuenteMusic);
            this.crearMusica(codigoFuenteMusic);
        } catch (Exception ex) {
            guardado = false;
        }

        return guardado;
    }

    private boolean crearMusica(String codigoFuenteMusic) {
        boolean guardado = true;
        Errores.getInstance().clear();
        CentroCanales.getInstance().clear();
        CentroCanalesThread.getInstance().clear();
        Consola.getInstance().clear();

        StringReader sr = new StringReader(codigoFuenteMusic);
        AnalizadorLexico lexer = new AnalizadorLexico(sr, "");
        midik.cup.parser par = new midik.cup.parser(lexer, "");

        try {
            par.parse();
            NodoAST raiz = par.getRaiz();
            Ejecucion ejecucion = new Ejecucion(raiz);
            ejecucion.ejecutar();
            this.nombrePista = ejecucion.getNombrePista();
        } catch (Exception ex) {
            guardado = false;
            System.out.println("Hay errores");

        }

        Errores errs = Errores.getInstance();
        ArrayList<midik.Singletons.Error> lista = errs.getErrors();
        for (midik.Singletons.Error e : lista) {
            System.out.println(e.toString() + "\n");
        }

        CentroCanales.getInstance().guardarPista(this.nombrePista, codigoFuenteMusic);
        this.mostrarPistas(FrontendSingleton.getInstance().getJlist());
        return guardado;
    }

    private void mostrarPistas(JList<String> jlist) {
        DefaultListModel<String> listaModel = new DefaultListModel<>();
        jlist.setModel(listaModel);
        ArrayList<Pista> pistas = Biblioteca.getInstance().getPistas();
        for (Pista p : pistas) {
            listaModel.addElement(p.getNombre() + "      " + p.getDuracion());
        }
    }

}
