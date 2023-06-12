package ru.netology.steps;

import com.codeborne.selenide.Selenide;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.data.DataHelper;
import ru.netology.page.*;

public class TemplateSteps {
    private static LoginPage loginPage;
    private static DashboardPage dashboardPage;
    private static VerificationPage verificationPage;
    private static TransferPage transferPage;
    private static DashboardPageAfterTransfer dashboardPageAfterTransfer;

    @Пусть("пользователь залогинен с именем {string} и паролем {string}")
    public void login(String login, String password) {
        loginPage = Selenide.open("http://localhost:9999", LoginPage.class);
        verificationPage = loginPage.validLogin(login, password);
        dashboardPage = verificationPage.validVerify(DataHelper.getVerificationCodeFor(DataHelper.getAuthInfo()));
    }

    @Когда("пользователь переводит {string} рублей с карты с номером {string} на свою 1 карту с главной страницы")
    public void transfer(String amount, String from) {
        transferPage = dashboardPage.replenishmentCard(0);
        dashboardPageAfterTransfer = transferPage.actionTransfer(amount, from);
    }

    @Тогда("баланс его пополняемой карты из списка на главной странице должен стать {string} рублей")
    public void balance(String balance) {
        dashboardPageAfterTransfer.getBalance(balance);
    }
}
