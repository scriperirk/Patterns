package ru.netology;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

import static ru.netology.AuthTest.Registration.*;

public class Testov {
    @Test
    void shouldLoginActiveUser() {
        open("http://localhost:9999");

        var user = generateUser("active");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[id='root']").shouldHave(text("Личный кабинет"), Duration.ofSeconds(15));
    }
}
