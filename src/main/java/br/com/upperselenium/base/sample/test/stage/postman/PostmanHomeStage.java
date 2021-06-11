package br.com.upperselenium.base.sample.test.stage.postman;

import br.com.upperselenium.base.BaseStage;
import br.com.upperselenium.base.WebDriverMaster;
import br.com.upperselenium.base.sample.test.stage.postman.dpo.PostmanHomeDPO;
import br.com.upperselenium.base.sample.test.stage.postman.page.PostmanHomePage;

public class PostmanHomeStage extends BaseStage {
	private PostmanHomeDPO postmanHomeDPO;
	private PostmanHomePage postmanHomePage;

	public PostmanHomeStage(String dpAccess) {
		postmanHomeDPO = loadDataProviderFile(PostmanHomeDPO.class, dpAccess);
	}

	@Override
	public void initMappedPages() {
		postmanHomePage = initElementsFromPage(PostmanHomePage.class);
	}

	@Override
	public void runStage() {
		WebDriverMaster.getWebDriver().get(postmanHomeDPO.getUrl());
		waitForPageToLoadUntil10s();
		postmanHomePage.clickEnterprise();
		postmanHomePage.clickPlansEPricing();
		postmanHomePage.clickSignIn();
	}

	@Override
	public void runValidations() {
	}

}
