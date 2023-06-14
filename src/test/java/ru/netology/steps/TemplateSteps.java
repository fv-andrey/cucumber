package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.data.DataHelper;
import ru.netology.page.*;

import static com.codeborne.selenide.Condition.text;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;

    public void setBalance() {
        int firstCard = dashboardPage.getCardBalance(0);
        int secondCard = dashboardPage.getCardBalance(1);
        if (firstCard < 10000) {
            dashboardPage.replenishmentCard(0)
                    .actionTransfer(Integer.toString(10000 - firstCard), DataHelper.getCard2Info().getCard());
        } else if (firstCard > 10000) {
            dashboardPage.replenishmentCard(1)
                    .actionTransfer(Integer.toString(10000 - secondCard), DataHelper.getCard1Info().getCard());
        }
    }

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void login(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(login, password);
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor());
        setBalance();
    }

    @Когда("пользователь переводит {int} рублей с карты с номером {string} на свою {int} карту с главной страницы")
    public void transfer(int amount, String from, int index) {
        transferPage = dashboardPage.replenishmentCard(index - 1);
        dashboardPage = transferPage.actionTransfer(Integer.toString(amount), from);
    }

    @Тогда("баланс его {int} карты из списка на главной странице должен стать {int} рублей")
    public void balance(int index, int balance) {
        dashboardPage.getCards().get(index - 1).shouldBe(text(Integer.toString(balance)));
        //assertEquals(balance, dashboardPage.getCardBalance(index - 1));
    }
}
