package midik.instrucciones;

import java.util.ArrayList;
import midik.Singletons.Errores;
import midik.ejecucion.Arreglo;
import midik.ejecucion.Entorno;
import midik.ejecucion.Instruccion;
import midik.ejecucion.Nativo;
import midik.ejecucion.TipoNativo;

public class Ordenar extends Instruccion {

    private String id;
    private String tipoOrden;
    private Entorno entorno;

    public Ordenar(String linea, Object id, Object tipoOrden) {
        super(linea);
        this.id = (String) id;
        this.tipoOrden = (String) tipoOrden;
    }

    @Override
    public Object ejecutar(Entorno entorno) {
        this.entorno = entorno;
        //Verificar si existe un arreglo con el id
        Arreglo arr = entorno.getArreglo(id);
        if (arr == null) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "La variable " + id + " no es un arreglo o no existe."));
            return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
        }

        //Verificar que sea de una dimension
        if (arr.getDimensiones() != 1) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Solo se pueden ordenar arreglos de una dimension."));
            return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
        }

        //Verificar que sea de tipo Entero
        if (arr.getTipoAsignado() != TipoNativo.ENTERO) {
            Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "El arreglo debe ser de tipo Entero."));
            return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
        }

        //Verificar que todos los datos sean enteros
        ArrayList<Object> arreglo = (ArrayList<Object>) ((ArrayList<Object>) arr.getValor()).get(0);
        for (Object h : arreglo) {
            if (h instanceof ArrayList) {
                Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Hay valores nulos en el arreglo"));
                return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
            } else {
                Nativo nat = (Nativo) ((Instruccion) h).ejecutar(entorno);
                if (nat.getTipo() != TipoNativo.ENTERO) {
                    Errores.getInstance().push(new midik.Singletons.Error("Semantico", this.getLinea(), "Los valores en el arreglo deben ser de tipo entero"));
                    return new Nativo(this.getLinea(), 0, TipoNativo.ENTERO);
                }
            }

        }

        //Ordenar
        int ordenar = this.modoOrdenar(arreglo, this.tipoOrden);
        return new Nativo(this.getLinea(), ordenar, TipoNativo.ENTERO);
    }

    private int modoOrdenar(ArrayList<Object> arreglo, String ordenStr) {
        switch (ordenStr) {
            case "Ascendente":
            case "ascendente":
                return this.ordenarAsc(arreglo);
            case "Descendente":
            case "descendente":
                return ordenarDesc(arreglo);
            case "Pares":
            case "pares":
                return ordenarPares(arreglo);
            case "Impares":
            case "impares":
                return ordenarImpares(arreglo);
            case "Primos":
            case "primos":
                return ordenarPrimos(arreglo);
            default:
                return 0;
        }
    }

    private int ordenarAsc(ArrayList<Object> arreglo) {
        int n = arreglo.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Nativo num1 = (Nativo) ((Instruccion) arreglo.get(j)).ejecutar(entorno);
                Nativo num2 = (Nativo) ((Instruccion) arreglo.get(j + 1)).ejecutar(entorno);
                Integer numi1 = Integer.valueOf((String) num1.getValor());
                Integer numi2 = Integer.valueOf((String) num2.getValor());

                if (numi1 > numi2) {
                    Object temp = arreglo.get(j);
                    arreglo.set(j, arreglo.get(j + 1));
                    arreglo.set(j + 1, temp);
                }
            }
        }

        return 1;
    }

    private int ordenarDesc(ArrayList<Object> arreglo) {
        int n = arreglo.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Nativo num1 = (Nativo) ((Instruccion) arreglo.get(j)).ejecutar(entorno);
                Nativo num2 = (Nativo) ((Instruccion) arreglo.get(j + 1)).ejecutar(entorno);
                Integer numi1 = Integer.valueOf((String) num1.getValor());
                Integer numi2 = Integer.valueOf((String) num2.getValor());

                if (numi1 < numi2) {
                    Object temp = arreglo.get(j);
                    arreglo.set(j, arreglo.get(j + 1));
                    arreglo.set(j + 1, temp);
                }
            }
        }

        return 1;
    }

    private int ordenarPares(ArrayList<Object> arreglo) {
        int n = arreglo.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Nativo num1 = (Nativo) ((Instruccion) arreglo.get(j)).ejecutar(entorno);
                Nativo num2 = (Nativo) ((Instruccion) arreglo.get(j + 1)).ejecutar(entorno);
                Integer numi1 = Integer.valueOf((String) num1.getValor());
                Integer numi2 = Integer.valueOf((String) num2.getValor());
                boolean esImparA = numi1 % 2 != 0;
                boolean esImparB = numi2 % 2 != 0;
                if (esImparA && !esImparB) {
                    Object temp = arreglo.get(j);
                    arreglo.set(j, arreglo.get(j + 1));
                    arreglo.set(j + 1, temp);
                }
            }
        }
        return 1;
    }

    private int ordenarImpares(ArrayList<Object> arreglo) {
        int n = arreglo.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Nativo num1 = (Nativo) ((Instruccion) arreglo.get(j)).ejecutar(entorno);
                Nativo num2 = (Nativo) ((Instruccion) arreglo.get(j + 1)).ejecutar(entorno);
                Integer numi1 = Integer.valueOf((String) num1.getValor());
                Integer numi2 = Integer.valueOf((String) num2.getValor());
                boolean esImparA = numi1 % 2 == 0;
                boolean esImparB = numi2 % 2 == 0;
                if (esImparA && !esImparB) {
                    Object temp = arreglo.get(j);
                    arreglo.set(j, arreglo.get(j + 1));
                    arreglo.set(j + 1, temp);
                }
            }
        }
        return 1;
    }

    private int ordenarPrimos(ArrayList<Object> arreglo) {
        int n = arreglo.size();
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                Nativo num1 = (Nativo) ((Instruccion) arreglo.get(j)).ejecutar(entorno);
                Nativo num2 = (Nativo) ((Instruccion) arreglo.get(j + 1)).ejecutar(entorno);
                Integer numi1 = Integer.valueOf((String) num1.getValor());
                Integer numi2 = Integer.valueOf((String) num2.getValor());
                boolean esPrimoA = !esPrimo(numi1);
                boolean esPrimoB = !esPrimo(numi2);
                if (esPrimoA && !esPrimoB) {
                    Object temp = arreglo.get(j);
                    arreglo.set(j, arreglo.get(j + 1));
                    arreglo.set(j + 1, temp);
                }
            }
        }
        return 1;
    }

    private boolean esPrimo(Integer numero) {
        if (numero <= 1) {
            return false;
        }
        for (int i = 2; i * i <= numero; i++) {
            if (numero % i == 0) {
                return false;
            }
        }
        return true;
    }

}
