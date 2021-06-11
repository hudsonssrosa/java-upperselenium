package br.com.upperselenium.base.sample.test.stage.postman.page;

import org.openqa.selenium.Keys;

import br.com.upperselenium.base.sample.test.stage.helper.BaseSampleHelperPage;

public class PostmanHomePage extends BaseSampleHelperPage{
	
	private static final String MENU_PRODUCT = "//*[@id='header']/div[3]/ul/li/a[contains(text(),'PRODUCT')]";	
	private static final String MENU_ENTERPRISE = "//*[@id='header']/div[3]/ul/li/a[contains(text(),'ENTERPRISE')]";	
	private static final String MENU_PLANS_E_PRICING = "//*[@id='header']/div[3]/ul/li/a[contains(text(),'PLANS & PRICING')]";	
	private static final String MENU_DOCS = "//*[@id='header']/div[3]/ul/li/a[contains(text(),'DOCS')]";	
	private static final String MENU_API_NETWORK = "//*[@id='header']/div[3]/ul/li/a[contains(text(),'API NETWORK')]";
	private static final String BUTTON_SIGN_IN = "//*[@id='header']/div[3]/ul/li[7]/a";	
		
	public boolean isClickableButtonSignIn() {
		return isDisplayedElement(BUTTON_SIGN_IN);
	}

	public void clickSignIn(){
		clickOnElement(BUTTON_SIGN_IN);
	}

	public void keyEnterSignIn(){
		useKey(BUTTON_SIGN_IN, Keys.ENTER);
	}
	
	public void clickProduct(){
		clickOnElement(MENU_PRODUCT);
	}
	
	public void clickEnterprise(){
		clickOnElement(MENU_ENTERPRISE);
	}
	
	public void clickPlansEPricing(){
		clickOnElement(MENU_PLANS_E_PRICING);
	}
	
	public void clickDocs(){
		clickOnElement(MENU_DOCS);
	}
	
	public void clickApiNetwork(){
		clickOnElement(MENU_API_NETWORK);
	}
	
}