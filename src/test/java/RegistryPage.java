import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;

public class RegistryPage {
    public static SelenideElement getTextField(String textFieldName){
        SelenideElement textField = $(By.name(textFieldName));
        return textField;
    }
    public static SelenideElement getRegistrationButton(){
        SelenideElement registryButton = $(By.id("registration_button"));
        return registryButton;
    }
    public static SelenideElement getErrorNoticeLabel(){
        SelenideElement errorNoticeLabel = $(By.className("notice__text"));
        return errorNoticeLabel;
    }
}
