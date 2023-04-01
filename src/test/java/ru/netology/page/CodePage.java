package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.Database;

import static com.codeborne.selenide.Selenide.$;

public class CodePage {
    private SelenideElement codeField = $("[data-test-id=code] input");
    private SelenideElement verifyButton = $("[data-test-id=action-verify]");


    public CardPage validVerify(Database.UserCode verifyCode) {
        codeField.setValue(verifyCode.getVerifyCode());
        verifyButton.click();
        return new CardPage();
    }

}