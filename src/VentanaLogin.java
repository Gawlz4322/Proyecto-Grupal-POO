import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class VentanaLogin {
    public static final List<Usuario> USUARIOS = new ArrayList<>();
    private final JFrame frame = new JFrame("Login - Control de Finanzas Personales (CFP)");
    private final JLabel lblUsuario = new JLabel("Usuario:");
    private final JTextField txtUsuario = new JTextField();
    private final JLabel lblClave = new JLabel("Clave:");
    private final JPasswordField txtClave = new JPasswordField();
    private final JButton btnIngresar = new JButton("Ingresar");

    public VentanaLogin() {
        //llamar metodos
        inicializarUsuarios();
    }

    public void inicializarUsuarios(){
        USUARIOS.add(new Usuario("admin", "1234", "Administrador"));
        USUARIOS.add(new Usuario("Juanin", "abcd", "Juanin Juan Harry"));
    }
    private void iniciarVentanaLogin(){
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400,400);
        frame.setLayout(null);
        inicializarBotones();
        mostrarVentana();
    }
    public void mostrarVentana(){
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(true);
    }
    private void inicializarBotones(){
        frame.add(lblUsuario);
        frame.add(txtUsuario);
        frame.add(lblClave);
        frame.add(txtClave);
        frame.add(btnIngresar);

        lblUsuario.setBounds(50, 80,300,25);
        txtUsuario.setBounds(50, 120,300,25);
        lblClave.setBounds(50, 160,300,25);
        txtClave.setBounds(50, 200,300,25);
        btnIngresar.setBounds(50, 240,300,25);
    }
}
