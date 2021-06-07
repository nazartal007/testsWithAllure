import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.*;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.qameta.allure.Allure.step;

@Owner("a.nazarov")
@Link(value = "issuesPage", url = "https://github.com/eroshenkoam/allure-example/issues")
@Severity(SeverityLevel.BLOCKER)
public class AllureTests {
    WebSteps steps = new WebSteps();

    private static final String
            BASE_URL = "https://github.com",
            REPOSITORY_NAME = "eroshenkoam/allure-example",
            NAME_ISSUE_68 = "Listeners NamedBy";


    @Test
    @Story("Тесты на чистом селениде")
    @DisplayName("Проверка имени issue 68 - просто селенид")
    public void confirmIssueNamePureSelenide() {
        SelenideLogger.addListener("allure", new AllureSelenide());

        open(BASE_URL);
        $(".header-search-input").sendKeys(REPOSITORY_NAME);
        $(".header-search-input").submit();
        $(By.linkText(REPOSITORY_NAME)).click();
        $(withText("Issues")).click();
        $("#issue_68_link").should(text(NAME_ISSUE_68));
    }

    @Test
    @Story("Тесты с лямбда шагами")
    @DisplayName("Проверка имени issue 68 - лямбда шаги")
    public void confirmIssueNameLambdaSteps() {
        step("Открываем " + BASE_URL, (s) -> {
            s.parameter("url", BASE_URL);
            open(BASE_URL);
        });
        step("Ищем репозиторий " + REPOSITORY_NAME, (s) -> {
            s.parameter("repository", REPOSITORY_NAME);
            $(".header-search-input").sendKeys(REPOSITORY_NAME);
            $(".header-search-input").submit();
        });
        step("Переходим в репозиторий " + REPOSITORY_NAME, (s) -> {
            s.parameter("repository", REPOSITORY_NAME);
            $(By.linkText(REPOSITORY_NAME)).click();
        });
        step("Переходим в issues", () -> {
            $(withText("Issues")).click();
        });
        step("Проверяем что название issue #68 равно " + NAME_ISSUE_68, (s) -> {
            s.parameter("issue68Name", NAME_ISSUE_68);
            $("#issue_68_link").should(text(NAME_ISSUE_68));
        });
    }

    @Test
    @Story("Тесты где шаги с аннотаей")
    @DisplayName("Проверка имени issue 68 - шаги с аннотациями")
    public void confirmIssueNameAnnotationsSteps() {
        steps.openMainPage();
        steps.searchRepository(REPOSITORY_NAME);
        steps.goToRepository(REPOSITORY_NAME);
        steps.goToIssues();
        steps.assertIssueName(NAME_ISSUE_68);
    }
}
