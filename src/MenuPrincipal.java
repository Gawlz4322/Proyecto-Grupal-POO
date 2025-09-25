import javax.swing.*;

public class MenuPrincipal {
    //menu principal, se inicializa después de login

    //preguntas iniciales, aparecen apenas cierra login y abre menu. Historial y display de dinero actual todavia no implementados
    public void preguntasInicialesMenu() {
        int gastos = JOptionPane.showConfirmDialog(null, "¿Has realizado gastos?", "Gastos", JOptionPane.YES_NO_OPTION);
        if (gastos == JOptionPane.YES_OPTION) {
            String montoGastos = JOptionPane.showInputDialog(null, "Ingrese el monto de gastos:");
        }

        int ingresos = JOptionPane.showConfirmDialog(null, "¿Has recibido ingresos?", "Ingresos", JOptionPane.YES_NO_OPTION);
        if (ingresos == JOptionPane.YES_OPTION) {
            String montoIngresos = JOptionPane.showInputDialog(null, "Ingrese el monto de ingresos:");
        }

    }
}
