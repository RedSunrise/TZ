import com.codeborne.selenide.*;
import com.codeborne.selenide.commands.Exists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

public class Test {
    @BeforeAll
    public static void configs(){
        try (InputStream input = new FileInputStream("src/test/resources/config.properties")) {

            Properties prop = new Properties();
            prop.load(input);
            Configuration.headless = Boolean.valueOf(prop.getProperty("headless"));

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @BeforeEach
    public void setup(){
        Selenide.open("https://account.habr.com/register/?consumer=habr&hl=ru_RU");
        Configuration.startMaximized = true;
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/values.csv")
    public void registrationButtonUnavailableWithWrongFormData(String email, String nickname, String password, String passwordRepeat){
        RegistryPageSteps.fillIntoAllRegistryFields("email", email);
        RegistryPageSteps.fillIntoAllRegistryFields("login", nickname);
        RegistryPageSteps.fillIntoAllRegistryFields("password", password);
        RegistryPageSteps.fillIntoAllRegistryFields("password2", passwordRepeat);
        assertThat(RegistryPage.getRegistrationButton().getAttribute("disabled")).overridingErrorMessage(() -> "Expecting registration button be unavailable but it was available.").isNotNull();
    }

//    @Disabled
    @org.junit.jupiter.api.Test
    public void errorMessageAppearsWithUnsolvedCaptcha(){
        RegistryPageSteps.fillIntoAllRegistryFields("email", "someone@mail.ru");
        RegistryPageSteps.fillIntoAllRegistryFields("login", "Somename");
        RegistryPageSteps.fillIntoAllRegistryFields("password", "Qwerty123456");
        RegistryPageSteps.fillIntoAllRegistryFields("password2", "Qwerty123456");
        RegistryPageSteps.clickRegistrationButton();
        assertThat(RegistryPage.getErrorNoticeLabel().getText()).isEqualTo("Форма содержит ошибки");
    }

    @AfterEach
    public void tearDown(){
        Selenide.clearBrowserCookies();
        Selenide.clearBrowserLocalStorage();
        Selenide.closeWebDriver();
    }
}
