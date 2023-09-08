package midik.servidor;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import midik.frontend.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {
        String filePath = "/home/midik/Desktop/Proyecto1Compi2/Servidor/Servidor/src/main/java/midik/entrada/entrada.txt"; // Ruta al archivo de texto
        StringReader stringReader = null;

        try {
            // Lee el archivo de texto
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(System.lineSeparator()); // Agrega un salto de línea
            }
            reader.close();

            // Convierte el contenido en un StringReader
            stringReader = new StringReader(stringBuilder.toString());
            
            //Mostrar ventana
            VentanaPrincipal vtnMain = new VentanaPrincipal();
            vtnMain.cambiarTextoEntradaTa(stringBuilder.toString());
            vtnMain.setVisible(true);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (stringReader != null) {
                stringReader.close();
            }
        }
    }
}
