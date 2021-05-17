package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.Color;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CallbackTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
        //System.setProperty("webdriver.chrome.driver", "C:\\tmp\\chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestV1() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Василий Петров-Васечкин");
        elements.get(1).sendKeys("+79270000000");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("Success_successBlock__2L3Cw")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldFillCorrectForm() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров-Васечкин");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79270000000");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    void shouldFillNameWithNumber() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("56321");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+71234567890");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldFillNameWithEnglish() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Balkonsky Andrew");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+71234567890");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    void shouldFillPhoneWithLetters() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("телефон");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldFillPhoneWithLowNumbers() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+75");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    void shouldFillCorrectFormWithoutAgreement() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+71234567890");
        form.findElement(By.cssSelector(".button")).click();
        String color = form.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getCssValue("color");
        assertEquals("#ff5c5c", Color.fromString(color).asHex());
    }

    @Test
    void shouldNotFillName() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+71234567890");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotFillPhone() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("[action]"));
        form.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Василий Петров");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    void shouldNotFillAny() {
        driver.get("http://localhost:9999");
        driver.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

}

