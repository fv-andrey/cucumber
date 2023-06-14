package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Selenide.$;

public class TransferPage {
    private SelenideElement heading = $("h1.heading");
    private SelenideElement amountField = $("[data-test-id='amount'] input");
    private SelenideElement fromField = $("[data-test-id='from'] input");
    private SelenideElement buttonTransfer = $("[data-test-id='action-transfer']");
    private SelenideElement buttonCancel = $("[data-test-id='action-cancel']");
    private SelenideElement invalidInput = $("[data-test-id='error-notification'] .notification__content");

    public TransferPage() {
    }

    public void transfer(String amount, String from) {
        amountField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        amountField.setValue(amount);
        fromField.sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        fromField.setValue(from);
    }

    public DashboardPage actionTransfer(String amount, String from) {
        transfer(amount, from);
        buttonTransfer.click();
        return new DashboardPage();
    }

    public DashboardPage cancelTransfer(String amount, String from) {
        transfer(amount, from);
        buttonCancel.click();
        return new DashboardPage();
    }

    public SelenideElement invalidInput(String amount, String from) {
        transfer(amount, from);
        buttonTransfer.click();
        return invalidInput;
    }
}
