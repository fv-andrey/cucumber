package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = ", баланс: ";
    private final String balanceFinish = " р.";
    private ElementsCollection buttonReplenishment = $$("[data-test-id='action-deposit']");

    public DashboardPage() {
        heading.shouldBe(visible);
        int firstCard = getCardBalance(0);
        int secondCard = getCardBalance(1);
        if (firstCard < 10000) {
            replenishmentCard(0)
                    .actionTransferForEquals(Integer.toString(10000 - firstCard), DataHelper.CardsNumber.getCardsNumber().getCard2());
        } else if (firstCard > 10000) {
            replenishmentCard(1)
                    .actionTransferForEquals(Integer.toString(10000 - secondCard), DataHelper.CardsNumber.getCardsNumber().getCard1());
        }
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

    public TransferPage replenishmentCard(int index) {
        buttonReplenishment.get(index).click();
        return new TransferPage();
    }
}
