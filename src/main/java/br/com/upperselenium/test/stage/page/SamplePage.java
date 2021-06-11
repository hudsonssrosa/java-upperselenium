package br.com.upperselenium.test.stage.page;

import org.openqa.selenium.Keys;
import br.com.upperselenium.base.constant.TimePRM;
import br.com.upperselenium.test.stage.helper.BaseHelperPage;

public class LoginSamplePage extends BaseHelperPage{
    private static final String BUTTON_ENTER = ".//div[2]/div/form/div/button";
    private static final String TEXT_USER = "//*[@id='UserName']";
    private static final String TEXT_PASS = "//*[@id='Password']";

    public void clickEnter(){
        clickOnElement(BUTTON_ENTER);
    }

    public void typeTextUser(String value){
        typeTextField(TEXT_USER, value);
    }

    public void typeTextPassword(String value){
        typeTextField(TEXT_PASS, value);
    }
}