package br.com.upperselenium.base.sample.test.flow;

import org.junit.runners.Suite.SuiteClasses;

import br.com.upperselenium.base.BaseFlow;
import br.com.upperselenium.base.annotation.FlowParams;
import br.com.upperselenium.base.sample.test.stage.rest_api.RestApiValidateGETParamsStage;
import br.com.upperselenium.base.sample.test.stage.rest_api.RestApiValidatePOSTParamsStage;
import br.com.upperselenium.constant.DPConstant;
import br.com.upperselenium.test.S001PIMAreaCadastrosSuite;

@SuiteClasses(T0002RestApiPostmanFlow.class)
@FlowParams(idTest = "POSTMAN-Sample-T0001-RestApi",
	testDirPath = DPConstant.Path.SAMPLE_DP_FOLDER + "T0002RestApiPostmanFlow", // Leave blank if you have an APP_DP_FOLDER constant
	loginDirPath = DPConstant.Path.LOGIN_SAMPLE_DP_FOLDER + "T0002RestApiPostmanFlow", // Leave blank if you have an APP_LOGIN_DP_FOLDER constant
	goal = "Valida a API do Postman (https://docs.postman-echo.com/), testando-se os métodos GET, POST, PUT e DELETE.",
	suiteClass = S001PIMAreaCadastrosSuite.class)
public class T0002RestApiPostmanFlow extends BaseFlow {
	
	@Override
	protected void addFlowStages() {
		addStage(new RestApiValidatePOSTParamsStage(getDP("RestApiValidatePOSTParamsDP")));
		addStage(new RestApiValidateGETParamsStage(getDP("RestApiValidateGETParamsDP")));
	}
}
