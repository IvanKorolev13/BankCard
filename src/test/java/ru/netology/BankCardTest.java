package ru.netology;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class BankCardTest {
    private WebDriver driver;
    @BeforeAll
    public static void setUpAll() {
//        WebDriverManager.chromedriver().setup();
        System.setProperty("webdriver.chrome.driver", "/driver/win/chromedriver.exe");
    }
    @BeforeEach
    public void setUpEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
//        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    public void testInputValidData() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+2345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
}
