package com.healthtrack;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

import static spark.Spark.*;

public class UsuarioWebTest {

    private WebDriver driver;
    private WebDriverWait wait;

    // Este método se ejecuta una vez antes de todos los tests
    // Aquí se inicia un servidor web embebido con Spark que simula la aplicación web para pruebas
    @BeforeAll
    public static void iniciarServidor() {
        port(8080); // Configura el puerto donde correrá el servidor

        // Define la ruta principal "/" que devuelve una página HTML simple con inputs y botón
        get("/", (req, res) -> {
            return "<html>" +
                    "<body>" +
                    "<input id='nombre' type='text' />" +
                    "<input id='peso' type='text' />" +
                    // El botón "Actualizar" tiene un onclick que actualiza el div con el peso ingresado + " kg"
                    "<button id='actualizar' onclick=\"document.getElementById('pesoActual').innerText = document.getElementById('peso').value + ' kg';\">Actualizar</button>" +
                    "<div id='pesoActual'></div>" +
                    "</body>" +
                    "</html>";
        });

        awaitInitialization(); // Espera a que el servidor Spark haya arrancado antes de continuar
    }

    // Este método se ejecuta una vez después de todos los tests
    // Aquí se detiene el servidor Spark para liberar recursos
    @AfterAll
    public static void detenerServidor() {
        stop();       // Detiene el servidor
        awaitStop();  // Espera que el servidor se haya cerrado completamente
    }

    // Este método se ejecuta antes de cada test
    // Configura el WebDriver para Selenium y abre el navegador Chrome
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();  // Descarga y configura el driver de Chrome automáticamente

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--headless=new");   // Para modo headless moderno
        options.addArguments("--disable-gpu");

        driver = new ChromeDriver(options);        // Instancia el navegador Chrome con opciones
        wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Esperas explícitas para elementos

        driver.manage().window().setSize(new Dimension(1920, 1080)); // Tamaño fijo en lugar de maximize()
    }

    // Test que simula el flujo completo de actualizar el peso de un usuario en la web
    @Test
    public void testActualizarPesoFlujoCompleto() {
        driver.get("http://localhost:8080");  // Navega a la página del servidor local

        // Espera que el input "nombre" sea visible para continuar
        WebElement inputNombre = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nombre")));
        WebElement inputPeso = driver.findElement(By.id("peso"));         // Localiza input peso
        WebElement botonActualizar = driver.findElement(By.id("actualizar")); // Localiza botón actualizar

        inputNombre.clear();              // Limpia input nombre
        inputNombre.sendKeys("Helena");  // Escribe nombre "Helena"

        inputPeso.clear();                // Limpia input peso
        inputPeso.sendKeys("65.6");      // Escribe peso "65.6"

        botonActualizar.click();          // Hace clic en el botón "Actualizar"

        // Espera que aparezca el div con el peso actualizado
        WebElement pesoActual = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("pesoActual")));
        String textoPeso = pesoActual.getText().trim();  // Obtiene el texto mostrado

        // Verifica que el texto sea el esperado "65.6 kg"
        assertEquals("65.6 kg", textoPeso, "El peso actualizado no coincide con el esperado");
    }

    // Este método se ejecuta después de cada test
    // Cierra el navegador para liberar recursos y evitar interferencias entre tests
    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();  // Cierra el navegador
        }
    }
}