package br.com.upperselenium.base.sample.test.stage.rest_api;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import br.com.upperselenium.base.BaseReport;
import br.com.upperselenium.base.BaseStage;
import br.com.upperselenium.base.sample.test.stage.rest_api.dpo.RestApiValidateGETParamsDPO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

public class RestApiValidateGETParamsStage extends BaseStage {
	private RestApiValidateGETParamsDPO restApiMethods;

	public RestApiValidateGETParamsStage(String dpRestApi) {
		restApiMethods = loadDataProviderFile(RestApiValidateGETParamsDPO.class, dpRestApi);
	}

	@Override
	public void initMappedPages() {}

	@Override
	public void runStage() {
		waitForPageToLoadUntil10s();
		String uri = restApiMethods.getUri();
		int status = Integer.parseInt(restApiMethods.getStatus());
		String valueHost = restApiMethods.getValueHost();		
		String valueXForwardedProto = restApiMethods.getValueXForwardedProto();		
		for (int i=0; i < restApiMethods.getParams().size();i++){
			String key = restApiMethods.getParams().get(i).getKey();
			String value = restApiMethods.getParams().get(i).getValue();
			String valueExpected = restApiMethods.getParams().get(i).getValueExpected();
			ValidatableResponse response = given().relaxedHTTPSValidation()
				.param(key, value)
				.log().all()
			.when()
				.get(uri)
			.then()				
				.statusCode(status)
				.contentType(ContentType.JSON)
				.header("Content-Encoding", "gzip")
				.header("Connection", "keep-alive")
				.body("headers.host", is(valueHost))
				.body("headers.x-forwarded-proto", is(valueXForwardedProto))
				.body("args." + key, containsString(valueExpected))
			.assertThat()
			.log().all();			
			BaseReport.reportContentOfElementEvent(response.log().toString());
		}
	}

	@Override
	public void runValidations() {
	}

}
