import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class WebSteps {

    @Step("Открываем github")
    public void openMainPage() {
        open("https://github.com");
    }

    @Step("Ищем репозиторий {nameRepository}")
    public void searchRepository(String nameRepository) {
        $(".header-search-input").sendKeys(nameRepository);
        $(".header-search-input").submit();
    }

    @Step("Переходим в репозиторий {nameRepository}")
    public void goToRepository(String nameRepository) {
        $(By.linkText(nameRepository)).click();
    }

    @Step("Переходим в issues")
    public void goToIssues() {
        $(withText("Issues")).click();
    }

    @Step("Проверяем название issue #68 {issueName}")
    public void assertIssueName(String issueName) {
        $("#issue_68_link").should(text(issueName));
    }

    @Attachment(value = "Скриншот", type = "image/png")
    public byte[] makeScreenshot() {
        return screenshot(OutputType.BYTES);
    }
}
