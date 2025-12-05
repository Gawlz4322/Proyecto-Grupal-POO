package View;

import com.formdev.flatlaf.ui.FlatUIUtils;
import javax.swing.*;
import java.awt.*;

/**
 * Panel personalizado con estilo de "Tarjeta" (Card).
 * Tiene esquinas redondeadas y un fondo sutilmente diferente.
 */
public class CardPanel extends JPanel {
    private final int arc = 20;

    public CardPanel() {
        super(new BorderLayout());
        setOpaque(false);
        // Padding interno para que el contenido no toque los bordes
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            FlatUIUtils.setRenderingHints(g2);

            // Color de fondo de la tarjeta (ligeramente más claro que el fondo de la app)
            // Usamos un color fijo oscuro compatible con el tema Nord
            g2.setColor(new Color(59, 66, 82));

            // Dibujar rectángulo redondeado
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);
        } finally {
            g2.dispose();
        }
        super.paintComponent(g);
    }
}
