package ru.netology.pages;

import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$x;

public class MainPage {
    private final SelenideElement buttonByCard = $x("//*[@id='root']/div/button[1]");
    private final SelenideElement buttonByCredit = $x("//*[@id='root']/div/button[2]");

    public PaymentPage selectPayByCard() {
        buttonByCard.click();
        return new PaymentPage();
    }

    public PaymentPage selectPayByCredit() {
        buttonByCredit.click();
        return new PaymentPage();
    }
}
