package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import page.LoginPage;
import page.DashboardPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTestPositive {

    @BeforeEach
    void shouldVerify() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfoValid();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")

    void shouldTransferMoneyFromSecondToFirst(int transfer) {

        val dashboardPage = new DashboardPage();
        int startBalanceOfFirstCard = dashboardPage.getBalanceOfFirstCard();
        int startBalanceOfSecondCard = dashboardPage.getBalanceOfSecondCard();

        val transferPage = dashboardPage.replenishBalanceFirsCard();
        val transferFrom2To1Card = DataHelper.getSecondCardInfo();
        transferPage.transferFromSecondToFirst(transferFrom2To1Card, transfer);
        val balanceFirstCard = DataHelper.getBalanceCardPlus(startBalanceOfFirstCard, transfer);
        val balanceSecondCard = DataHelper.getBalanceCardMinus(startBalanceOfSecondCard, transfer);

        assertEquals(balanceFirstCard, dashboardPage.getBalanceOfFirstCard());
        assertEquals(balanceSecondCard, dashboardPage.getBalanceOfSecondCard());
    }
    @ParameterizedTest
    @CsvFileSource(resources = "/data.csv")

    void shouldTransferMoneyFromFirstToSecond(int transfer) {

        val dashboardPage = new DashboardPage();
        int startBalanceOfFirstCard = dashboardPage.getBalanceOfFirstCard();
        int startBalanceOfSecondCard = dashboardPage.getBalanceOfSecondCard();

        val transferPage = dashboardPage.replenishBalanceSecondCard();
        val transferFrom1To2Card = DataHelper.getFirstCardInfo();
        transferPage.transferFromFirstToSecond(transferFrom1To2Card, transfer);
        val balanceFirstCardAfterTrans = DataHelper.getBalanceCardMinus(startBalanceOfFirstCard, transfer);
        val balanceSecondCardAfterTrans = DataHelper.getBalanceCardPlus(startBalanceOfSecondCard, transfer);

        assertEquals(balanceFirstCardAfterTrans, dashboardPage.getBalanceOfFirstCard());
        assertEquals(balanceSecondCardAfterTrans, dashboardPage.getBalanceOfSecondCard());
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondMinus() {

        val dashboardPage = new DashboardPage();
        int startBalanceOfFirstCard = dashboardPage.getBalanceOfFirstCard();
        int startBalanceOfSecondCard = dashboardPage.getBalanceOfSecondCard();

        val transferPage = dashboardPage.replenishBalanceSecondCard();
        val transferFrom1To2Card = DataHelper.getFirstCardInfo();
        int transfer = -1000;
        transferPage.transferFromFirstToSecond(transferFrom1To2Card, transfer);
        val balanceFirstCardAfterTrans = startBalanceOfFirstCard + transfer;
        val balanceSecondCardAfterTrans = startBalanceOfSecondCard - transfer;

        assertEquals(balanceFirstCardAfterTrans, dashboardPage.getBalanceOfFirstCard());
        assertEquals(balanceSecondCardAfterTrans, dashboardPage.getBalanceOfSecondCard());
    }
}