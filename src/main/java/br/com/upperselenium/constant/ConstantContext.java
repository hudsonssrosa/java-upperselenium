package br.com.upperselenium.constant;

public enum ConstantContext {

	// Used in Sample tests
	EXISTING_USER("existingUser"),
	EXISTING_NOTE("existingNote"),
	NEW_USER("newUser"),
	RESULT_NAME("resultName");
	
	private String value;

	private ConstantContext(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}