package aruscrm;

import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.SwingUtilities;

public class ArusCRM {

    public static void main(String[] args) {

        // Activar el estilo visual FlatLaf
        FlatLightLaf.setup();

        // Lanzar la ventana principal en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
