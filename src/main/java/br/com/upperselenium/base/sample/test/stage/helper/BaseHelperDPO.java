package br.com.upperselenium.base.sample.test.stage.helper;

/**
 * Atributos comuns aplicados nos cenários de teste.
 * @param operationMessage
 * @param issue
 */
public class BaseHelperDPO {

	private String operationMessage;
	private String issue;


	public String getOperationMessage() {
		return operationMessage;
	}

	public void setOperationMessage(String operationMessage) {
		this.operationMessage = operationMessage;
	}

	public String getIssue() {
		return issue;
	}

	public void setIssue(String issue) {
		this.issue = issue;
	}

	@Override
	public String toString() {
		return "BaseDPO [operationMessage=" + operationMessage + ", issue=" + issue + "]";
	}

}
