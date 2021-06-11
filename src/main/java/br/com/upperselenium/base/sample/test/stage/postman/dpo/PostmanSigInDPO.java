package br.com.upperselenium.base.sample.test.stage.postman.dpo;

import br.com.upperselenium.base.sample.test.stage.helper.BaseHelperDPO;

public class PostmanSigInDPO extends BaseHelperDPO {

	private String emailOrUsername;
	private String password;

	public String getEmailOrUsername() {
		return emailOrUsername;
	}

	public void setEmailOrUsername(String emailOrUsername) {
		this.emailOrUsername = emailOrUsername;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
