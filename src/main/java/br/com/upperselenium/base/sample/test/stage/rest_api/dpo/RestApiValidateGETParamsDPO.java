package br.com.upperselenium.base.sample.test.stage.rest_api.dpo;

import java.util.List;

public class RestApiValidateGETParamsDPO {

	private String uri;
	private List<ParamsDPO> params;
	private String args;
	private String status;
	private String assertHost;
	private String assertXForwardedProto;
	private String valueHost;
	private String valueXForwardedProto;

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

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAssertHost() {
		return assertHost;
	}

	public void setAssertHost(String assertHost) {
		this.assertHost = assertHost;
	}

	public String getAssertXForwardedProto() {
		return assertXForwardedProto;
	}

	public void setAssertXForwardedProto(String assertXForwardedProto) {
		this.assertXForwardedProto = assertXForwardedProto;
	}

	public String getValueHost() {
		return valueHost;
	}

	public void setValueHost(String valueHost) {
		this.valueHost = valueHost;
	}

	public String getValueXForwardedProto() {
		return valueXForwardedProto;
	}

	public void setValueXForwardedProto(String valueXForwardedProto) {
		this.valueXForwardedProto = valueXForwardedProto;
	}

}
