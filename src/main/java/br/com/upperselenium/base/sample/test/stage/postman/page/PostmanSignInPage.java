package br.com.upperselenium.base.sample.test.stage.postman.page;

import br.com.upperselenium.base.sample.test.stage.helper.BaseSampleHelperPage;

public class PostmanSignInPage extends BaseSampleHelperPage{
	
	private static final String TEXT_EMAIL_USERNAME = "//*[@id='username']";
	private static final String TEXT_PASSWORD = "//*[@id='password']";
	private static final String BUTTON_SIGN_IN = "//*[@id='sign-in-btn']";
	private static final String MESSAGE = "//*[@id='notification-div']";
		
	public void typeTextEmailOrUsername(String value){
		typeText(TEXT_EMAIL_USERNAME, value);
	}
	
	public void typeTextPassword(String value){
		typeText(TEXT_PASSWORD, value);
	}
	
	public void clickSignIn(){
		clickOnElement(BUTTON_SIGN_IN);
	}	
	
	public void validateSignInMessage(String value){
		validateMessageDefault(MESSAGE, value);
	}
}