package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CardTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(6);


    @Test
    public void shouldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate), Duration.ofSeconds(14));
    }

    @Test
    public void souldWrongCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Обнинск");
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $("[data-test-id=agreement]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id = 'city'] .input__sub")
                .shouldHave(exactText("Доставка в выбранный город недоступна"));
    }

    @Test
    public void souldWrongName() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Vasia");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $("[data-test-id=agreement]").click();
        $("[class=\"button__text\"]").click();
        $("[data-test-id = 'name'] .input__sub")
                .shouldHave(exactText("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));
    }

    @Test
    public void souldWrongTel() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+999999999");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[data-test-id = 'phone'] .input__sub")
                .shouldHave(exactText("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));
    }

    @Test
    public void souldNotAgree() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $$(withText("Забронировать")).first().click();
        $("[data-test-id=agreement]")
                .shouldHave(exactText("Я соглашаюсь с условиями обработки и использования моих персональных данных"));
    }
}