package ru.netology;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardTest {

    @Test
    void shouldCardTest() {

        Configuration.holdBrowserOpen = true;
        open("http://localhost:9999");

        String meetingDateNearest = LocalDate.now().plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        $("[data-test-id='city'] input").setValue("Иркутск");
        $("[data-test-id='date'] input").setValue(meetingDateNearest);

        $("[data-test-id=name] input").setValue("Афанасов Антон");
        $("[data-test-id=phone] input").setValue("+79000000880");
        $("[data-test-id='agreement'] .checkbox__box").click();
        $(byText("Запланировать")).click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(Condition.appear, Duration.ofSeconds(15))
                .shouldHave((text("Успешно!")));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(text("Встреча успешно запланирована на " + meetingDateNearest));

        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__title")
                .shouldBe(Condition.appear, Duration.ofSeconds(15))
                .shouldHave((text("Необходимо подтверждение")));
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(text("У вас уже запланирована встреча на другую дату. Перепланировать?"));

        $$("button").find((exactText("Перепланировать"))).click();
        $("[data-test-id='success-notification'] .notification__title")
                .shouldBe(Condition.appear, Duration.ofSeconds(4))
                .shouldHave((text("Успешно!")));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(Condition.visible)
                .shouldHave(text("Встреча успешно запланирована на " + meetingDateNearest));

    }
}
