package br.com.upperselenium.base.sample.test.flow;

import org.junit.runners.Suite.SuiteClasses;

import br.com.upperselenium.base.BaseFlow;
import br.com.upperselenium.base.annotation.FlowParams;
import br.com.upperselenium.base.sample.test.stage.postman.PostmanHomeStage;
import br.com.upperselenium.base.sample.test.stage.postman.PostmanSignInStage;
import br.com.upperselenium.constant.DPConstant;
import br.com.upperselenium.test.S001PIMAreaCadastrosSuite;

@SuiteClasses(T0000SignInFlow.class)
@FlowParams(idTest = "POSTMAN_WEBSITE-Sample-T0000-SignIn",
	testDirPath = DPConstant.Path.SAMPLE_DP_FOLDER + "T0000SignInFlow", // Leave blank if you have a APP_DP_FOLDER constant
	loginDirPath = DPConstant.Path.LOGIN_SAMPLE_DP_FOLDER + "T0000SignInFlow", // Leave blank if you have a APP_LOGIN_DP_FOLDER constant
	goal = "Valida a tentativa de SignIn utilizando usuário e senha inadequados.",
	suiteClass = S001PIMAreaCadastrosSuite.class)
public class T0000SignInFlow extends BaseFlow {
	
	@Override
	protected void addFlowStages() {
		addStage(new PostmanHomeStage(getDP("PostmanHomeDP")));
		addStage(new PostmanSignInStage(getDP("PostmanSignInDP")));
	}	
}
