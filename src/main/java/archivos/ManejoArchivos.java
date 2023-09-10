package archivos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import midik.musica.ListaReproduccion;
import midik.musica.Pista;

public class ManejoArchivos {

    public void guardarPistasActivacion(String nombreArchivo, ArrayList<Pista> pistas) {
        this.crearArchivoBin(nombreArchivo);
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(pistas);
        } catch (IOException e) {

            System.err.println("Error Guardar del archivo Binario de las Pistas");
            e.printStackTrace();
        }
    }
    
    public void guardarListasActivacion(String nombreArchivo, ArrayList<ListaReproduccion> listas) {
        this.crearArchivoBin(nombreArchivo);
        try (ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(nombreArchivo))) {
            salida.writeObject(listas);
        } catch (IOException e) {

            System.err.println("Error Guardar del archivo Binario de las Listas");
            e.printStackTrace();
        }
    }

    public ArrayList<Pista> readPistasActivacion(String nombreArchivo) {
        ArrayList<Pista> pistas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            return pistas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            pistas = (ArrayList<Pista>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error la lectura del archivo Binario, de las Pistas :(");
        }
        return pistas;
    }

    public ArrayList<ListaReproduccion> readListasActivacion(String nombreArchivo) {
        ArrayList<ListaReproduccion> listas = new ArrayList<>();
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            //validacion de archivo
            return listas;
        }
        try (ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(nombreArchivo))) {
            listas = (ArrayList<ListaReproduccion>) entrada.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error la lectura del archivo Binario, de las Pistas :(");
        }
        return listas;
    }

    private void crearArchivoBin(String nombreArchivo) {
        File archivo = new File(nombreArchivo);
        if (!archivo.exists()) {
            try {
                archivo.createNewFile();
            } catch (IOException e) {
                System.err.println("Error al crear el archivo");
            }
        }
    }

}
