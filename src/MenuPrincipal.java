import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MenuPrincipal {
    //menu principal, se inicializa después de login
    private final JFrame frame = new JFrame("Menú Principal - CFP");

    public MenuPrincipal() {
        //aun no se crean los métodos.
        iniciarVentanaMenuPrincipal();
    }

    private void iniciarVentanaMenuPrincipal() {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(null);
    }
    private void iniciarComponentes() {

    }
}
