package Modelo;

import Controller.FinanceStore;
import Model.FinanceData;
import Model.FinanceSystem;
import Model.Transaction;
import Model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.After;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.Assert.*;

public class SistemaFinanzasTest {

    private FinanceSystem financeSystem;
    private FinanceStore financeStore;
    private User testUser;
    private String testDbPath;

    @Before
    public void setUp() {
        // Crear un archivo temporal para los tests
        testDbPath = "src/test/resources/test_finances.json";
        financeStore = new FinanceStore(testDbPath);
        financeSystem = new FinanceSystem(financeStore);
        testUser = new User("testuser", "hash", "salt");
        financeSystem.login(testUser);
    }

    @After
    public void tearDown() {
        // Limpiar el archivo de test después de cada prueba
        try {
            Files.deleteIfExists(Paths.get(testDbPath));
        } catch (Exception e) {
            // Ignorar errores de limpieza
        }
    }

    @Test
    public void testSaldoInicialEsCero() {
        User newUser = new User("newuser", "hash", "salt");
        financeSystem.login(newUser);

        assertEquals("El saldo inicial debe ser 0", 0.0, financeSystem.getBalance(), 0.001);
    }

    @Test
    public void testAgregarGastoDisminuyeSaldo() {
        double gastoInicial = 100.0;
        financeSystem.addExpense(gastoInicial, "Comida");

        assertEquals("El saldo debe disminuir después de un gasto",
                -gastoInicial, financeSystem.getBalance(), 0.001);
    }

    @Test
    public void testAgregarIngresoAumentaSaldo() {
        double ingreso = 500.0;
        financeSystem.addIncome(ingreso);

        assertEquals("El saldo debe aumentar después de un ingreso",
                ingreso, financeSystem.getBalance(), 0.001);
    }

    @Test
    public void testMultiplesGastosEIngresos() {
        financeSystem.addIncome(1000.0);
        financeSystem.addExpense(200.0, "Transporte");
        financeSystem.addExpense(150.0, "Comida");
        financeSystem.addIncome(300.0);

        double saldoEsperado = 1000.0 - 200.0 - 150.0 + 300.0;
        assertEquals("El saldo debe reflejar múltiples transacciones",
                saldoEsperado, financeSystem.getBalance(), 0.001);
    }

    @Test
    public void testNoSeAceptanMontosNegativos() {
        double saldoInicial = financeSystem.getBalance();

        // Intentar agregar gasto negativo (no debería cambiar el saldo)
        financeSystem.addExpense(-50.0, "Gasto negativo");
        assertEquals("No se deben aceptar gastos negativos",
                saldoInicial, financeSystem.getBalance(), 0.001);

        // Intentar agregar ingreso negativo (no debería cambiar el saldo)
        financeSystem.addIncome(-100.0);
        assertEquals("No se deben aceptar ingresos negativos",
                saldoInicial, financeSystem.getBalance(), 0.001);
    }

    @Test
    public void testNoSeAceptanMontosEnCero() {
        double saldoInicial = financeSystem.getBalance();

        financeSystem.addExpense(0.0, "Gasto cero");
        assertEquals("No se deben aceptar gastos en 0",
                saldoInicial, financeSystem.getBalance(), 0.001);

        financeSystem.addIncome(0.0);
        assertEquals("No se deben aceptar ingresos en 0",
                saldoInicial, financeSystem.getBalance(), 0.001);
    }

    @Test
    public void testHistorialSeGuardaCorrectamente() {
        financeSystem.addIncome(500.0);
        financeSystem.addExpense(100.0, "Comida");

        List<Transaction> historial = financeSystem.getHistory();

        assertNotNull("El historial no debe ser null", historial);
        assertEquals("El historial debe tener 2 entradas", 2, historial.size());
    }

    @Test
    public void testHistorialMuestraFormatoCorrecto() {
        financeSystem.addIncome(500.0);
        financeSystem.addExpense(100.0, "Comida");

        List<Transaction> historial = financeSystem.getHistory();

        assertEquals("El primer registro debe ser un Ingreso", "Ingreso", historial.get(0).getType());
        assertEquals("El monto del ingreso debe ser correcto", 500.0, historial.get(0).getAmount(), 0.001);

        assertEquals("El segundo registro debe ser un Gasto", "Gasto", historial.get(1).getType());
        assertEquals("La categoría del gasto debe ser Comida", "Comida", historial.get(1).getCategory());
        assertEquals("El monto del gasto debe ser correcto", 100.0, historial.get(1).getAmount(), 0.001);
    }

    @Test
    public void testGetSaldoDevuelveSaldoCorrecto() {
        financeSystem.addIncome(1000.0);
        financeSystem.addExpense(250.0, "Ropa");

        double saldoEsperado = 750.0;
        assertEquals("getSaldo debe devolver el saldo correcto",
                saldoEsperado, financeSystem.getBalance(), 0.001);
    }

    @Test
    public void testGetHistorialDevuelveListaCorrecta() {
        financeSystem.addIncome(100.0);
        financeSystem.addExpense(50.0, "Test");

        List<Transaction> historial = financeSystem.getHistory();

        assertNotNull("getHistory no debe devolver null", historial);
        assertTrue("getHistory debe devolver una lista", historial instanceof List);
        assertEquals("La lista debe tener el tamaño correcto", 2, historial.size());
    }

    @Test
    public void testHistorialVacioAlInicio() {
        User nuevoUsuario = new User("usuarionuevo", "hash", "salt");
        financeSystem.login(nuevoUsuario);

        List<Transaction> historial = financeSystem.getHistory();

        assertNotNull("El historial no debe ser null", historial);
        assertEquals("El historial debe estar vacío al inicio", 0, historial.size());
    }
}
