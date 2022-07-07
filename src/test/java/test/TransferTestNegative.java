package test;

import data.DataHelper;
import lombok.val;
import org.junit.jupiter.api.Test;
import page.DashboardPage;
import page.LoginPage;



import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TransferTestNegative {

    @Test
    void shouldInvalidVerification() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfoInvalid();
        loginPage.invalidLogin(authInfo);
    }

    @Test
    void shouldTransferMoneyFromFirstToSecondMoreThanExist() {
        val loginPage = open("http://localhost:9999", LoginPage.class);
        val authInfo = DataHelper.getAuthInfoValid();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode);

        val dashboardPage = new DashboardPage();
        int startBalanceOfFirstCard = dashboardPage.getBalanceOfFirstCard();
        int startBalanceOfSecondCard = dashboardPage.getBalanceOfSecondCard();

        val transferPage = dashboardPage.replenishBalanceSecondCard();
        val transferFrom1To2Card = DataHelper.getFirstCardInfo();
        int transfer = 0;
        transferPage.transferFromFirstToSecond(transferFrom1To2Card, transfer);
        val balanceFirstCardAfterTrans = DataHelper.getBalanceCardMinus(startBalanceOfFirstCard, transfer);
        val balanceSecondCardAfterTrans = DataHelper.getBalanceCardPlus(startBalanceOfSecondCard, transfer);

        assertEquals(balanceFirstCardAfterTrans, dashboardPage.getBalanceOfFirstCard());
        assertEquals(balanceSecondCardAfterTrans, dashboardPage.getBalanceOfSecondCard());
    }
}