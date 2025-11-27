package Controlador;

import Modelo.Usuario;
import java.util.Optional;

public class AuthService {
    private final UserStore store;

    public AuthService(UserStore store) {
        this.store = store;
    }

    public Usuario register(String username, char[] password) {
        validateUsername(username);
        validatePassword(password);
        if (store.exists(username)) throw new IllegalArgumentException("El usuario ya existe");

        String salt = Contraseña.genSalt(16);
        String hash = Contraseña.hash(password, salt);
        try {
            Usuario u = new Usuario(username.trim(), hash, salt);
            store.saveNew(u);
            return u;
        } finally {
            Contraseña.zero(password);
        }
    }

    public Usuario login(String username, char[] password) {
        Usuario u = store.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario o contraseña inválidos"));

        boolean ok = Contraseña.verify(password, u.getSalt(), u.getPasswordHash());
        Contraseña.zero(password);
        if (!ok) throw new IllegalArgumentException("Usuario o contraseña inválidos");
        return u;
    }

    public void changePassword(String username, char[] oldPass, char[] newPass) {
        Usuario u = store.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no existe"));

        boolean ok = Contraseña.verify(oldPass, u.getSalt(), u.getPasswordHash());
        Contraseña.zero(oldPass);
        if (!ok) throw new IllegalArgumentException("Contraseña actual incorrecta");

        validatePassword(newPass);
        String newSalt = Contraseña.genSalt(16);
        String newHash = Contraseña.hash(newPass, newSalt);
        Contraseña.zero(newPass);

        u.setSalt(newSalt);
        u.setPasswordHash(newHash);
        store.update(u);
    }

    private void validateUsername(String username) {
        if (username == null || username.trim().length() < 3)
            throw new IllegalArgumentException("El nombre de usuario debe tener al menos 3 caracteres");
        if (username.contains(" "))
            throw new IllegalArgumentException("El nombre de usuario no puede contener espacios");
    }

    private void validatePassword(char[] password) {
        if (password == null || password.length < 8)
            throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres");
    }
}