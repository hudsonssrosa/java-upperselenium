package br.com.upperselenium.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import br.com.upperselenium.base.annotation.FlowParams;
import br.com.upperselenium.base.annotation.SuiteParams;
import br.com.upperselenium.base.listener.ObserverFinalResultsListener;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.TestLogger;
import br.com.upperselenium.base.constant.CmdPRM;
import br.com.upperselenium.base.constant.ContextPRM;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.SymbolPRM;
import br.com.upperselenium.base.constant.TimePRM;
import br.com.upperselenium.base.util.WaitElementUtil;
import br.com.upperselenium.constant.ConstantContext;
import br.com.upperselenium.constant.DPConstant;

/**
 * Base Class used in the execution of the WebDriver that aggregates Flow Stages
 * corresponding.
 * 
 * @author HudsonRosa
 */
@RunWith(ObserverFinalResultsListener.class)
public abstract class BaseFlow implements TestLogger {
	private List<IBaseStage> stages = new ArrayList<IBaseStage>();
	private static final String JAVA_FILE_EXTENSION = FileDirPRM.Extension.JAVA_FILE;
	private String fileEvidenceName = this.getClass().getSimpleName();
	private String goal;
	private String testDirPath;
	private String loginDirPath;
	private String idFlow;
	private Class<?> suiteName;
	private static int counterFlow = 1;
	private static int counterIdFlow = 1;
	private static int counterItemStage = 1;
	private static int counterReportFlow = 1;
	private static int counterReportItemStage = 1;
	private static int counterSuccessResult = 0;
	private static int counterFailedResult = 0;
	static WebDriver webDriver = null;
	
	protected abstract void addFlowStages();
		
	@Rule
	public ExpectedException exceptionExpected = ExpectedException.none();
	
	public static int getCounterFlow() {
		return counterFlow++;
	}
	
	public static int getCounterIdFlow() {
		return counterIdFlow++;
	}
	
	public static int getCounterItemStage() {
		return counterItemStage++;
	}
		
	public static int getCounterReportFlow() {
		return counterReportFlow++;
	}

	public static int getCounterReportItemStage() {
		return counterReportItemStage++;
	}

	public static int getCounterSuccessResult() {
		return counterSuccessResult++;
	}

	public static int getCounterFailedResult() {
		return counterFailedResult++;
	}
	
	private void restartCounterItemStageOnFlow() {
		counterItemStage = 1;
	}

	private void restartCounterItemReportStageOnFlow() {
		counterReportItemStage = 1;
	}
	
	private long getStartFlowTime() {
		long initFlowTime = System.currentTimeMillis();
		return initFlowTime;
	}

	private long getFlowTime(long initFlowTime) {
		long finishFlowTime = (System.currentTimeMillis() - initFlowTime);
		int time = (int) (finishFlowTime /1000 % 60);
		return time;
	}
	
	/**
	 * -----------------------------------------------------------------------------
	 * Run the SetUp test by retrieving the WebDriver instance and initializing
     * the results context.
	 * 
	 * @throws IOException
	 */
	@Before
	public void setUp() throws IOException {
		webDriver = WebDriverMaster.getWebDriver();
		webDriver.switchTo().window(webDriver.getWindowHandle()).navigate();		
		BaseReport.reportContentFromHead();
		BaseReport.reportContentFromInitialBody();		
	}
		
	/**
	 * ----------------------------------------------------------------------------------------------
	 * Runs step by step (Stage by Stage) and throws expected exceptions during test failures.
	 * Files with screenshots of crashed screens are created and stored in the folder
	 * "report/test-evidence" of the project.
	 * 
	 * @throws AssertionError
	 */
	@Test
	public void runFlow() throws AssertionError {		
		long initFlowTime = getStartFlowTime();
		try {
			buildFlowStages();
			for (IBaseStage stagesToRun : this.stages) {
				stagesToRun.runCompleteStage();
				BaseLogger.logInfoTextHighlight("Stage PASSED.");
				BaseReport.reportContentOfSuccessStage();
			}			
			setListResultsSuccessfulInContext();
		} catch (Throwable failTest) {			
			exceptionExpected.expect(Exception.class);
			exceptionExpected.expect(RuntimeException.class);
			exceptionExpected.expect(AssertionError.class);
			exceptionExpected.expect(IllegalArgumentException.class);
			exceptionExpected.expect(org.openqa.selenium.NoSuchElementException.class);
			BaseReport.reportContentToFailureStage(webDriver, failTest, fileEvidenceName);			
			setListResultsUnsuccessfulInContext();
		} finally {
			BaseLogger.logInfoTextHighlight("Flow '" + this.getClass().getSimpleName() + ".java' finished. Time taken: " + getFlowTime(initFlowTime) + " second(s).");
			BaseLogger.logInfoBlankLine();
			BaseReport.reportContentOfFlowDuration(initFlowTime, this.getClass().getSimpleName());			
		}
	}

	private void setListResultsUnsuccessfulInContext() {
		BaseContext.addParameterOnList(ContextPRM.TEST_RESULT_LIST, SymbolPRM.DINGBAT_NOTOK_10008 + " FAILED - " + "Suite: " + getSuiteName().getSimpleName() 
										+ "  " + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 + " Flow: " + this.getClass().getSimpleName());
	}

	private void setListResultsSuccessfulInContext() {
		BaseContext.addParameterOnList(ContextPRM.TEST_RESULT_LIST, SymbolPRM.DINGBAT_OK_10004 + " PASSED - " + "Suite: " + getSuiteName().getSimpleName() 
										+ SymbolPRM.DINGBAT_RIGHT_ARROW_10140 + " Flow: " + this.getClass().getSimpleName());
	}
	
	/**
	 * -----------------------------------------------------------------------------
	 * Method implemented in Flow to receive all Stages calls in Flow.
	 * 
	 */
	private void buildFlowStages() {		
		stages = new ArrayList<IBaseStage>();
		String idFlowTest = getIdFlow();		
		logToGoalAndParameterDescriptions(getCounterFlow(), idFlowTest);	
		String flowClassName = this.getClass().getSimpleName();
		BaseReport.reportContentFromParameterDescriptions(idFlowTest, getSuiteName(), getSuiteDescription(), getGoalFlow(), flowClassName, counterReportFlow++);		
		addFlowStages();
		restartCounterItemStageOnFlow();
		restartCounterItemReportStageOnFlow();
		BaseStage.getCounterItemStageRestartedOnList();
		cleanContext();
	}
	
	/**
	 * Method used exclusively for adding a Stage to be executed in Flow.
	 * 
	 * @param stage
	 */
	protected void addStage(IBaseStage stage) {		
		this.stages.add(stage);
		int flowNumber = counterFlow - 1;
		int flowReportNumber = counterReportFlow -1;
		BaseLogger.logInfoTextHighlight(flowNumber + "." + getCounterItemStage() + ") " + stage.getClass().getSimpleName());		
		BaseReport.reportContentFromCounterFlow(stage, flowReportNumber, getCounterReportItemStage());
	}

	private void cleanContext() {
		BaseContext.setParameter(ConstantContext.NEW_USER.getValue(),null);
	}
	
	/**
	 * -------------------------------------------------------------------------------------
	 * Run TearDown with post-stream conditions such as deleting WebDriver Cookies. 
	 */
	@After
	public void tearDown() {
		deleteCookiesBetweenSuites();		
		BaseReport.reportContentFromClosedBlockExecution();
		WaitElementUtil.setImplicitlyWait(webDriver, TimePRM._2_SECS);
	}	
	
	private void logToGoalAndParameterDescriptions(int countFlow, String idFlowTest) {				
		BaseLogger.logInfoWithContinuousLine("----------------------------------------------");		
		BaseLogger.logInfoFirstOrLastHeaderTitle("STAGE FLOW - " + countFlow++ + " (" + idFlowTest + ")");
		BaseLogger.logInfoWithContinuousLine("----------------------------------------------");		
		if(!StringUtils.isBlank(getSuiteDescription())){
			BaseLogger.logInfoHeaderSubtitle("Origin Suite", "'" + getSuiteName().getSimpleName() + JAVA_FILE_EXTENSION + "' - " + getSuiteDescription());
		}
		BaseLogger.logInfoBlankLine();
		BaseLogger.logInfoHeaderSubtitle("Flow classes", this.getClass().getSimpleName() + JAVA_FILE_EXTENSION);
		BaseLogger.logInfoHeaderSubtitle("Description", getGoalFlow());
		BaseLogger.logInfoBlankLine();
		BaseLogger.logInfoHeaderSubtitle("Stages", "");
	}
			
	public String getDP(String dpFileName) {
		String pathLoginDP = getLoginDP(dpFileName);
		try {
			ClassLoader.getSystemResource(pathLoginDP).getFile();
			return getLoginDP(dpFileName);
		} catch (Exception e) {
			return getFlowDP(dpFileName);
		}
	}
	
	/**
	 * Method to get and handle the declared "DP.json" login file directories
	 * in Flow parameters
	 * @return
	 */
	public String getLoginDP(String dpFileName){
		String dpDirPath = getLoginFolderPath().toString().trim();
		return checkFilePath(dpFileName, dpDirPath);
	}
	
	private String getLoginFolderPath() {
		if (loginDirPath == null) {
			if (this.getClass().isAnnotationPresent(FlowParams.class)) {
				FlowParams flowSets = this.getClass().getAnnotation(FlowParams.class);
				String path = flowSets.loginDirPath();
				if (path.equalsIgnoreCase("")) {
					return DPConstant.Path.APP_LOGIN_DP_FOLDER;
				} else {
					return path;
				}
			}
		} else {
			return this.loginDirPath;
		}
		return "";
	}
	
	/**
	 * Method to get and handle declared "DP.json" file directories
	 * in Flow parameters
	 * @param dpFileName
	 * @return
	 */
	public String getFlowDP(String dpFileName){
		String dpDirPath = getStagesByFlowFolderPath().toString().trim();
		return checkFilePath(dpFileName, dpDirPath);
	}
	
	private String getStagesByFlowFolderPath() {
		if (testDirPath == null) {
			if (this.getClass().isAnnotationPresent(FlowParams.class)) {
				FlowParams flowSets = this.getClass().getAnnotation(FlowParams.class);
				String path = flowSets.testDirPath();
				if (path.equalsIgnoreCase("")) {
					String flowName = this.getClass().getName().replace(DPConstant.Path.PROJECT_PACKAGE, "").replace(".", "/");
					return DPConstant.Path.APP_DP_FOLDER + flowName;
				} else {
					return path;
				}
			}
		} else {
			return this.testDirPath;
		}
		return "";
	}
	
	private String checkFilePath(String dpFileName, String dpDirPath) {
		if (dpDirPath.endsWith(CmdPRM.Symbol.SLASH)){
			return checkJsonExtension(dpFileName, dpDirPath);
		} else {
			return checkBarsAndJsonExtension(dpFileName, dpDirPath);
		}
	}

	private String checkBarsAndJsonExtension(String dpFileName, String dpDirPath) {
		if (dpFileName.contains(FileDirPRM.Extension.JSON_FILE)){
			return dpDirPath + CmdPRM.Symbol.SLASH + dpFileName;
		} else {
			return dpDirPath + CmdPRM.Symbol.SLASH + dpFileName + FileDirPRM.Extension.JSON_FILE;
		}
	}

	private String checkJsonExtension(String dpFileName, String dpDirPath) {
		if (dpFileName.contains(FileDirPRM.Extension.JSON_FILE)){
			return dpDirPath + dpFileName;
		} else {
			return dpDirPath + dpFileName + FileDirPRM.Extension.JSON_FILE;
		}
	}
	
	/**
	 * Method to get the SuiteName parameter
	 * @return
	 */
	private Class<?> getSuiteName() {
		if (suiteName == null) {
			if (this.getClass().isAnnotationPresent(FlowParams.class)) {
				FlowParams flowSets = this.getClass().getAnnotation(FlowParams.class);
				Class<?> suite = flowSets.suiteClass();
				if (StringUtils.isNotBlank(suite.getName())) {
					return suite;
				}
			}
		} else {
			return this.suiteName;
		}
		return this.getClass();
	}
	
	/**
	 * Method for getting the SuiteDescription parameter from the Suite
	 * @return
	 */
	private String getSuiteDescription() {
		boolean hasAnnotationPresent = getSuiteName().isAnnotationPresent(SuiteParams.class);
		if (hasAnnotationPresent) {
			org.junit.runner.JUnitCore.runClasses(BaseSuite.class);
			SuiteParams annotationSuiteParams = (SuiteParams) getSuiteName().getAnnotation(SuiteParams.class);
			SuiteParams suiteSets = annotationSuiteParams;
			String description = suiteSets.description();
			if (StringUtils.isNotBlank(description)) {
				return description;
			}
		}
		return "";
	}
	
	/**
	 * Method to get the Flow Id parameter
	 * @return
	 */
	private String getIdFlow(){
		if (idFlow == null) {
			if (this.getClass().isAnnotationPresent(FlowParams.class)) {
				FlowParams flowSets = this.getClass().getAnnotation(FlowParams.class);
				String id = flowSets.idTest();
				if (StringUtils.isNotBlank(id)) {
					return id;
				}
			}
		} else {
			return this.idFlow;
		}
		return "Flow_Unnamed_" + getCounterIdFlow();
	}
	
	/**
	 * Method to get the Flow Goal parameter
	 * @return
	 */
	private String getGoalFlow() {
		if (goal == null) {
			if (this.getClass().isAnnotationPresent(FlowParams.class)) {
				FlowParams flowSets = this.getClass().getAnnotation(FlowParams.class);
				String goal = flowSets.goal();
				if (StringUtils.isNotBlank(goal)) {
					return goal;
				}
			}
		} else {
			return this.goal;
		}
		return "";
	}
	
	// --- AFTER ---
	
    /**
     * Method to delete WebDriver cookies.
     */
	private static void deleteCookiesBetweenSuites() {
		BaseLogger.logInfoTextHighlight("Deletando Cookies...");
		Set<Cookie> allCookies = webDriver.manage().getCookies();
		for (Cookie loadedCookie : allCookies) {
			BaseLogger.logInfoSimpleText(String.format("%s -> %s", loadedCookie.getName(), loadedCookie.getValue()));
		    webDriver.manage().deleteCookie(loadedCookie);
		}	
		webDriver.manage().deleteAllCookies();
	}

}