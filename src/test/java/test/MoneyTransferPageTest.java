package test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelper.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static page.DashboardPage.cardsBalance;

class MoneyTransferPageTest {
    private int[] cardsBalance;
    private int value1 = 5_637;
    private int value2 = 4_126;
    private int value3 = 1_968;

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        new LoginPage().validLogin(getAuthInfo()).validVerify(getVerificationCodeFor(getAuthInfo()));
        cardsBalance = cardsBalance();
        cardsBalance = justifyBalance(cardsBalance[1], cardsBalance[2]);
        assertEquals(cardsBalance[1], cardsBalance[2]);
    }

    @AfterEach
    void asserting() {
        cardsBalance = cardsBalance();
        cardsBalance = justifyBalance(cardsBalance[1], cardsBalance[2]);
        assertEquals(cardsBalance[1], cardsBalance[2]);
    }

    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        int expectedFirst = cardsBalance[1] + value1 + value2 - value3;
        int expectedSecond = cardsBalance[2] - value1 - value2 + value3;
        var dashboard = new DashboardPage();
        dashboard.moneyTransfer(cardNumber(1)).transaction(Integer.toString(value1), cardNumber(2));
        dashboard.moneyTransfer(cardNumber(1)).transaction(Integer.toString(value2), cardNumber(2));
        dashboard.moneyTransfer(cardNumber(2)).transaction(Integer.toString(value3), cardNumber(1));
        cardsBalance = cardsBalance();
        assertEquals(expectedFirst, cardsBalance[1]);
        assertEquals(expectedSecond, cardsBalance[2]);
    }
}
