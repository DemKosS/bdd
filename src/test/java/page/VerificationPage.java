package page;

import com.codeborne.selenide.SelenideElement;
import data.DataHelper;


import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    public SelenideElement verification = $("[data-test-id=code] input");
    public SelenideElement codeInput = $("[data-test-id=code] input");
    public SelenideElement buttonVerify = $("[data-test-id=action-verify]");

    public VerificationPage() {
        verification.shouldBe(visible);
    }

    public void validVerify(DataHelper.VerificationCode verificationCode) {
        codeInput.setValue(verificationCode.getCodeForVerification());
        buttonVerify.click();
    }
}
