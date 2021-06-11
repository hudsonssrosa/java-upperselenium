package br.com.upperselenium.base.sample.test.stage.postman;

import br.com.upperselenium.base.BaseStage;
import br.com.upperselenium.base.sample.test.stage.postman.dpo.PostmanSigInDPO;
import br.com.upperselenium.base.sample.test.stage.postman.page.PostmanSignInPage;

public class PostmanSignInStage extends BaseStage {
	private PostmanSigInDPO postmanSignInDPO;
	private PostmanSignInPage postmanSignInPage;
	
	public PostmanSignInStage(String dpSearch) {
		postmanSignInDPO = loadDataProviderFile(PostmanSigInDPO.class, dpSearch);
	}
	
	@Override
	public void initMappedPages() {
		postmanSignInPage = initElementsFromPage(PostmanSignInPage.class);
	}

	@Override
	public void runStage() {
		getIssue(postmanSignInDPO.getIssue());	
		waitForPageToLoadUntil10s();
		postmanSignInPage.typeTextEmailOrUsername(postmanSignInDPO.getEmailOrUsername());
		postmanSignInPage.typeTextPassword(postmanSignInDPO.getPassword());
		postmanSignInPage.clickSignIn();
	}
	
	@Override
	public void runValidations() {
		postmanSignInPage.validateSignInMessage(postmanSignInDPO.getOperationMessage());
	}

}
