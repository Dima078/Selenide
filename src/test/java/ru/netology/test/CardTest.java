package ru.netology.test;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.openqa.selenium.By;
import com.codeborne.selenide.Selectors;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;

public class CardTest {

    @Test
    public void souldSendForm() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val("01.04.2022");
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[@id=\"root\"]/div/form/fieldset/div[6]/div[2]/div/button").click();
        $("[data-test-id=\"notification\"]").should(Condition.appear, Duration.ofSeconds(14));
    }

    /*@Test
    public void souldWrongCity() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Обнинск");
        $x("//input[@type= 'tel']").val("01.04.2022");
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[@id=\"root\"]/div/form/fieldset/div[6]/div[2]/div/button").click();
        $("[data-test-id = 'name'] .input__sub").should(Condition.appear, Duration.ofSeconds(3));
    }

    @Test
    public void souldWrongTel() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val("01.04.2022");
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+999999999");
        $("[data-test-id=agreement]").click();
        $x("//*[@id=\"root\"]/div/form/fieldset/div[6]/div[2]/div/button").click();
        $("[data-test-id = 'phone'] .input__sub").should(Condition.appear, Duration.ofSeconds(3));
    }

    @Test
    public void souldNotAgree() {
        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");
        $x("//input[@type= 'text']").val("Калуга");
        $x("//input[@type= 'tel']").val("01.04.2022");
        $("[data-test-id=name] input").setValue("Вася Теркин");
        $("[data-test-id=phone] input").setValue("+99999999999");
        $x("//*[@id=\"root\"]/div/form/fieldset/div[6]/div[2]/div/button").click();
        $("[data-test-id=agreement]").should(Condition.appear, Duration.ofSeconds(3));
    }*/
}