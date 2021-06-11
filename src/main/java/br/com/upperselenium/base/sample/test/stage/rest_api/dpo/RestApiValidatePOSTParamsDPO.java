package br.com.upperselenium.base.sample.test.stage.rest_api.dpo;

import java.util.List;

public class RestApiValidatePOSTParamsDPO {

	private String uri;
	private List<ParamsDPO> params;
	private String status;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<ParamsDPO> getParams() {
		return params;
	}

	public void setParams(List<ParamsDPO> params) {
		this.params = params;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
