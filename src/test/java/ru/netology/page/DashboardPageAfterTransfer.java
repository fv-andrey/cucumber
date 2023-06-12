package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPageAfterTransfer {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р.";
    private ElementsCollection buttonReplenishment = $$("[data-test-id='action-deposit']");

    public DashboardPageAfterTransfer(String amount) {
        heading.shouldBe(visible);
        int replenishment = Integer.parseInt(amount);
        String balancePlus = Integer.toString(10000 + replenishment);
        String balanceMinus = Integer.toString(10000 - replenishment);
        if (getCardBalance(0) > getCardBalance(1)) {
            cards.get(0).shouldBe(text(balancePlus));
            cards.get(1).shouldBe(text(balanceMinus));
        } else {
            cards.get(0).shouldBe(text(balanceMinus));
            cards.get(1).shouldBe(text(balancePlus));
        }
    }

    public void getBalance(String balance) {
        cards.get(0).shouldBe(text(balance));
    }

    public DashboardPageAfterTransfer() {
        heading.shouldBe(visible);
        cards.get(0).shouldBe(text("10000"));
        cards.get(1).shouldBe(text("10000"));
    }

    public int getCardBalance(int index) {
        var text = cards.get(index).text();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        var start = text.indexOf(balanceStart);
        var finish = text.indexOf(balanceFinish);
        var value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }
}
