public class RegistryPageSteps {

    public static void fillIntoAllRegistryFields(String textFieldName, String textTypingIn){
        RegistryPage.getTextField(textFieldName).setValue(textTypingIn);
    }
    public static void clickRegistrationButton(){
        RegistryPage.getRegistrationButton().click();
    }
}
