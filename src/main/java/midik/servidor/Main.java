package midik.servidor;

import javax.swing.SwingUtilities;
import midik.frontend.VentanaPrincipal;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal vtnMain = new VentanaPrincipal();
            vtnMain.setVisible(true);
        });

    }
}