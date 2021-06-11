package br.com.upperselenium.test.stage;

import br.com.upperselenium.base.BaseStage;
import br.com.upperselenium.base.constant.TimePRM;
import br.com.upperselenium.test.stage.page.SamplePage;

public class LoginStage extends BaseStage {
private LoginSampleDPO loginSampleDPO;
private LoginSamplePage loginSamplePage;

	@Override
	public void initMappedPages() {
		loginSamplePage = initElementsFromPage(LoginSamplePage.class);
	}

	@Override
	public void runStage() {
		WebDriverMaster.getWebDriver().get(loginSampleDPO.getUrl());
		waitForPageToLoad(TimePRM._10_SECS);
		loginSamplePage.typeTextUser("Test User");
        loginSamplePage.typeTextPassword("me123456");
		loginSamplePage.clickEnter();
	}

	@Override
	public void runValidations() {
		validateField();
		//... Add new validations here
	}

	private void validateField() {
		String expectedMessage = "Connected successfully!";
		String actualMessage = loginSamplePage.getMessage();
		AssertUtil.assertEquals(expectedMessage, actualMessage);
	}
}