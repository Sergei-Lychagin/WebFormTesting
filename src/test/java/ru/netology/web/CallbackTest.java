package ru.netology.web;

import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;


class CallbackTest {

    @Test
    void shouldFillCorrectForm() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Петров-Васечкин");
        $("[data-test-id=phone] input").setValue("+79270000000");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=order-success]").shouldHave(exactText("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время."));

    }

    @Test
    void shouldFillNameWithNumber() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").setValue("56321");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=name] span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldFillNameWithEnglish() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").setValue("Balkonsky Andrew");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=name] span.input__sub").shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldFillPhoneWithLetters() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").setValue("Василий Петров");
        $("[data-test-id=phone] input").setValue("телефон");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=phone] span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }


    @Test
    void shouldFillPhoneWithLowNumbers() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").setValue("Василий Петров");
        $("[data-test-id=phone] input").setValue("+75");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=phone] span.input__sub").shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldFillCorrectFormWithoutAgreement() {
        open("http://localhost:9999");

        $("[data-test-id=name] input").setValue("Василий Петров");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $(".button").click();
        $("[data-test-id=agreement] span.checkbox__text").shouldHave(cssValue("color", "rgba(255, 92, 92, 1)"));

    }

    @Test
    void shouldNotFillName() {
        open("http://localhost:9999");
        $("[data-test-id=phone] input").setValue("+71234567890");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=name] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

    @Test
    void shouldNotFillPhone() {
        open("http://localhost:9999");
        $("[data-test-id=name] input").setValue("Василий Петров");
        $("[data-test-id=agreement]").click();
        $(".button").click();
        $("[data-test-id=phone] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));

    }

    @Test
    void shouldNotFillAny() {
        open("http://localhost:9999");
        $(".button").click();
        $("[data-test-id=name] span.input__sub").shouldHave(exactText("Поле обязательно для заполнения"));
    }

}

