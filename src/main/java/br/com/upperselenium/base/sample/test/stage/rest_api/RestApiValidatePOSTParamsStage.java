package br.com.upperselenium.base.sample.test.stage.rest_api;

import static io.restassured.RestAssured.given;

import br.com.upperselenium.base.BaseStage;
import br.com.upperselenium.base.sample.test.stage.rest_api.dpo.RestApiValidatePOSTParamsDPO;
import io.restassured.http.ContentType;

public class RestApiValidatePOSTParamsStage extends BaseStage {
	private RestApiValidatePOSTParamsDPO restApiMethods;

	public RestApiValidatePOSTParamsStage(String dpRestApi) {
		restApiMethods = loadDataProviderFile(RestApiValidatePOSTParamsDPO.class, dpRestApi);
	}

	@Override
	public void initMappedPages() {}

	@Override
	public void runStage() {
		waitForPageToLoadUntil10s();
		String uri = restApiMethods.getUri();
		int status = Integer.parseInt(restApiMethods.getStatus());
		for (int i=0; i < restApiMethods.getParams().size();i++){
			String key = restApiMethods.getParams().get(i).getKey();
			String value = restApiMethods.getParams().get(i).getValue();
			given().relaxedHTTPSValidation()
				.contentType(ContentType.JSON)
				.body(key + value)
			.when()
				.post(uri)
			.then()
				.statusCode(status)
				.assertThat();	
		}
	}

	@Override
	public void runValidations() {}

}
