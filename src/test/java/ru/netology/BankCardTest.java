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
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
//        System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
    }
    @BeforeEach
    void setUpEach() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999/");
    }
    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    public void testInputValidData() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputNameByLatinSymbol() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Korolev");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] span[class='input__sub']"))
                .getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputNameEmptyField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] span[class='input__sub']"))
                .getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputNameWithDigit() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев1");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] span[class='input__sub']"))
                .getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputNameWithSpecSymbol() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("К!@#$%^&*()_+-=/\\.,'\":;?><`№");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='name'] span[class='input__sub']"))
                .getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputNameOneCyrillicSymbol() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("К");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneEmptyField() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Поле обязательно для заполнения";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneOnlySymbolPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneSymbolPlusAndOneDigit() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneWithoutSymbolPlus() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("12345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneSymbolPlusAnd12Digits() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+123456789012");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneSymbolPlusAndLatinSymbol() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+W");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneSymbolPlusAndCyrillicSymbol() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+й");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneWithSpecSymbolDash() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+1-2345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testInputPhoneWithSpecSymbolAnotherDash() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+1#2345678901");
        driver.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String expected = "Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.";
        String actual = driver.findElement(By.cssSelector("[data-test-id='phone'] span[class='input__sub']")).getText().trim();
        Assertions.assertEquals(expected, actual);
    }
    @Test
    public void testDontClickCheckBox() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Королев-Петров Иван");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+12345678901");

        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String fullNameClass = driver
                .findElement(By.cssSelector("[data-test-id='agreement']"))
                .getAttribute("class");

        String nameInvalidClass = "input_invalid";
        Assertions.assertTrue(fullNameClass.contains(nameInvalidClass));
    }
    @Test
    public void testIsErrorMessageOnlyAtInputNameOnErrorInAllFields() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("к- №");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+йцукенгшщзх");

        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String fullNameClassName = driver
                .findElement(By.cssSelector("[data-test-id='name']"))
                .getAttribute("class");
        String fullNameClassPhone = driver
                .findElement(By.cssSelector("[data-test-id='phone']"))
                .getAttribute("class");
        String fullNameClassCheckbox = driver
                .findElement(By.cssSelector("[data-test-id='agreement']"))
                .getAttribute("class");

        String nameInvalidClass = "input_invalid";
        Assertions.assertTrue(fullNameClassName.contains(nameInvalidClass));
        Assertions.assertFalse(fullNameClassPhone.contains(nameInvalidClass));
        Assertions.assertFalse(fullNameClassCheckbox.contains(nameInvalidClass));
    }
    @Test
    public void testIsErrorMessageOnlyAtInputPhoneOnErrorInPhoneAndCheckboxFields() {
        driver.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys(" -петров ");
        driver.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+йцукенгшщзх");

        driver.findElement(By.className("button_theme_alfa-on-white")).click();

        String fullNameClassName = driver
                .findElement(By.cssSelector("[data-test-id='name']"))
                .getAttribute("class");
        String fullNameClassPhone = driver
                .findElement(By.cssSelector("[data-test-id='phone']"))
                .getAttribute("class");
        String fullNameClassCheckbox = driver
                .findElement(By.cssSelector("[data-test-id='agreement']"))
                .getAttribute("class");

        String nameInvalidClass = "input_invalid";
        Assertions.assertFalse(fullNameClassName.contains(nameInvalidClass));
        Assertions.assertTrue(fullNameClassPhone.contains(nameInvalidClass));
        Assertions.assertFalse(fullNameClassCheckbox.contains(nameInvalidClass));
    }
    //не стал добавлять тесты по SQL- и XSS- инъекциям в поля имя и телефон (это еще 4 теста)
    //SQL: 'OR 1 = 1; /*
    //XSS: <script>alert(123)</script>
}
