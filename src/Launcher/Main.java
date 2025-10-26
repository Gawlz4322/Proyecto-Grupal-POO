package Launcher;

import Controlador.AuthService;
import Controlador.UserStore;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        String userDbPath = "data/users.json";

        UserStore store = new UserStore(userDbPath);
        AuthService auth = new AuthService(store);

        SwingUtilities.invokeLater(() -> {
        });
    }
}