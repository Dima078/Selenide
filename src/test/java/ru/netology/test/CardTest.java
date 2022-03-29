package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    String planningDate = generateDate(6);

    @Test
    public void souldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[data-test-id=\"notification\"]").should(Condition.appear, Duration.ofSeconds(14));
    }

    @Test
    public void souldDateCheck() {
        String dateCheck = generateDate(10);
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val(planningDate);
        $("[data-test-id='date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        $x("//input[@type= 'tel']").val(dateCheck);
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $("[data-test-id=agreement]").click();
        $$(withText("Забронировать")).first().click();
        $("[class='notification__content']")
                .shouldHave(Condition.text("Встреча успешно забронирована на " + dateCheck), Duration.ofSeconds(14));
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
        $("[data-test-id = 'name'] .input__sub").should(Condition.appear);
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
        $("[data-test-id = 'phone'] .input__sub").should(Condition.appear);
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
        $("[data-test-id=agreement]").should(Condition.appear);
    }
}