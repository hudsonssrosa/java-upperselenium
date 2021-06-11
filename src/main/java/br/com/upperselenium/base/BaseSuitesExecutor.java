package br.com.upperselenium.base;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runners.model.InitializationError;

import br.com.upperselenium.base.asset.ReportConfig;
import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.TestLogger;
import br.com.upperselenium.base.constant.ContextPRM;
import br.com.upperselenium.base.constant.EnumContextPRM;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.util.FileCopyUtil;
import br.com.upperselenium.base.util.JodaTimeUtil;
import br.com.upperselenium.base.util.RandomDataGeneratorUtil;
import br.com.upperselenium.test.SuitesRunner;
import br.com.upperselenium.test.SuitesRunnerBdd;

/**
 * Base class that instantiates the WebDriver and prepares the Flows execution
 * associated with the Suite.
 * 
 * @author HudsonRosa
 *
 */
public abstract class BaseSuitesExecutor extends BaseLogger implements TestLogger {

	private static ArrayList<Object> listPassed = new ArrayList<>();
	private static ArrayList<Object> listFailed = new ArrayList<>();
	protected static List<Class<?>> suites = new ArrayList<Class<?>>();
	
	@SuppressWarnings("unused")
	private static Integer passedCount;
	private static Integer runCount;
	private static Integer failureCount;
	private static Integer ignoreCount;
	
	@Rule
	public static ExpectedException exceptionExpected = ExpectedException.none();
	
	public static void runAll(String executionParam) throws AssertionError {
		try {
			insertFlagsInContext();
			ReportConfig.deleteOldReportFiles();
			String origResources = FileDirPRM.REPORT_RESOURCES;
			String destResources = ReportConfig.getReportDirectory()+FileDirPRM.REPORT_RESOURCES;			
			FileCopyUtil.copyDirsAndFiles(origResources, destResources);
			insertRandomDatasForTestsInContext();		
			WebDriverMaster.initWebDriverSession();
			insertTimeInfoWhenSuiteHasStarted();
			startExecution(executionParam);
 		} catch (Throwable failSuite) {
			exceptionExpected.expect(Exception.class);
			exceptionExpected.expect(RuntimeException.class);
			exceptionExpected.expect(AssertionError.class);
			exceptionExpected.expect(IllegalArgumentException.class);
			exceptionExpected.expect(org.openqa.selenium.NoSuchElementException.class);
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.EXECUTION_SUITE_ERROR), failSuite);
		} finally {
			WebDriverMaster.destroyWebDriverSession();			
		}
	}

	private static void insertTimeInfoWhenSuiteHasStarted() {
		BaseContext.setParameter(EnumContextPRM.START_TIME_TO_SUITE_EXECUTION.getValue(), JodaTimeUtil.getCurrentPeriodPatternDDMMYYYYHHMMSS());
	}

	private static void insertRandomDatasForTestsInContext() {
		BaseContext.setParameter(EnumContextPRM.RANDOM_NUMBER.getValue(),RandomDataGeneratorUtil.generateRandomNumber(7));
		BaseContext.setParameter(EnumContextPRM.RANDOM_CRYPTED_STRING.getValue(),RandomDataGeneratorUtil.generateRandomData(7));
		BaseContext.setParameter(EnumContextPRM.RANDOM_PURE_SHA1_STRING.getValue(),RandomDataGeneratorUtil.generateRandomData(39));
	}

	private static void insertFlagsInContext() {
		BaseContext.addParameterOnList(ContextPRM.FLAG_DELETE_REPORTS_LIST, "0");
		BaseContext.setParameter(EnumContextPRM.FLAG_RUN_ALL.getValue(),"1");
	}
	
	public static void startExecution(String executionParam) throws InitializationError{		
		if (executionParam == null)
			suites = SuitesRunner.runSuites();
		else {
			suites = SuitesRunnerBdd.runSuites();
		}
		for (final Class<?> suitesToRun : suites) {
			JUnitCore.runClasses(suitesToRun);			
		}
		calculateFlowResults();
		getResults();			
		logToTotalResults();
		prepareDataResults();	
	}

	/**
	 * Main method to obtain the calculation of results between executed Flows
	 */
	private static void calculateFlowResults() {
		addResultToList();
		sendResultsToContext();
	}

	private static void addResultToList() {
		List<Object> sizeListOfRanFlows = BaseContext.getListParameters(ContextPRM.TEST_RESULT_LIST);
		for(int i=0; i < sizeListOfRanFlows.size();i++){
			if(sizeListOfRanFlows.get(i).toString().contains("PASSED")){
				listPassed.add(sizeListOfRanFlows.get(i));
			}
			if(sizeListOfRanFlows.get(i).toString().contains("FAILED")){
				listFailed.add(sizeListOfRanFlows.get(i));
			}
		}
	}

	private static void sendResultsToContext() {
		int runCount = getFlowsPassedIfNull() + getFlowsFailedIfNull();
		int ignoreCount = runCount - (getFlowsPassedIfNull() + getFlowsFailedIfNull());
		BaseContext.setParameter(EnumContextPRM.PASSED_COUNT.getValue(),""+listPassed.size());
		BaseContext.setParameter(EnumContextPRM.FAILURE_COUNT.getValue(),""+listFailed.size());		
		BaseContext.setParameter(EnumContextPRM.RUN_COUNT.getValue(),""+runCount);
		BaseContext.setParameter(EnumContextPRM.IGNORE_COUNT.getValue(),""+ignoreCount);		
	}
	
	private static int getFlowsFailedIfNull() {
		if (listFailed == null)
			return 0;
		return listFailed.size();
	}

	private static int getFlowsPassedIfNull() {
		if(listPassed == null)
			return 0;		
		return listPassed.size();
	}
	
	/**
	 * Main method for adjusting Results variables with integer values
	 */
	private static void getResults() {
		try {
			String ranFlowsContext = BaseContext.getParameter(EnumContextPRM.RUN_COUNT.getValue()).toString();
			String passedFlowsContext = BaseContext.getParameter(EnumContextPRM.PASSED_COUNT.getValue()).toString();				
			String failedFlowsContext = BaseContext.getParameter(EnumContextPRM.FAILURE_COUNT.getValue()).toString();
			String ignoredFlowsContext = BaseContext.getParameter(EnumContextPRM.IGNORE_COUNT.getValue()).toString();				
			runCount = Integer.parseInt(ranFlowsContext);
			passedCount = Integer.parseInt(passedFlowsContext);
			failureCount = Integer.parseInt(failedFlowsContext);
			ignoreCount = Integer.parseInt(ignoredFlowsContext);
			
		} catch (Throwable failSuites) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.RESULTS_NOT_CALCULATED), failSuites);
		}
	}
	
	/**
	 * LOG - FINAL RESULTS
	 */
	private static void logToTotalResults() {
		String timeExecution = BaseContext.getParameter(EnumContextPRM.TOTAL_TIME_EXECUTION.getValue()).toString();		
		if (runCount > 1){
			BaseLogger.logInfoWithContinuousLine("---------------------");
			BaseLogger.logInfoFirstOrLastHeaderTitle("Test Results".toUpperCase());
			BaseLogger.logInfoWithContinuousLine("---------------------");
			BaseLogger.logInfoBlankLine();			
			BaseLogger.logInfoHeaderSubtitle("Test Flows executed", runCount);
			BaseLogger.logInfoHeaderSubtitle("Total failed", failureCount);
			checkTheTestSuccess();
			BaseLogger.logInfoHeaderSubtitle("Total Flows ignored", ignoreCount);
			BaseLogger.logInfoHeaderSubtitle("Total execution time", timeExecution);
			BaseLogger.logInfoBlankLine();
			BaseLogger.logInfoWithContinuousLine("---------------------");
		} else {
			BaseLogger.logInfoWithContinuousLine("------------------");
			BaseLogger.logInfoFirstOrLastHeaderTitle("Test Result".toUpperCase());
			BaseLogger.logInfoWithContinuousLine("------------------");
			BaseLogger.logInfoBlankLine();
			checkStatusOfTest();
			BaseLogger.logInfoHeaderSubtitle("Test Flow executed", runCount);
			BaseLogger.logInfoHeaderSubtitle("Ignored flow", ignoreCount);
			BaseLogger.logInfoHeaderSubtitle("Total execution time", timeExecution);
			BaseLogger.logInfoBlankLine();
			BaseLogger.logInfoWithContinuousLine("------------------");
		}
	}
	
	private static void checkTheTestSuccess() {
		int passed = runCount - failureCount;
		BaseLogger.logInfoHeaderSubtitle("Total successes", passed);
	}
	
	private static void checkStatusOfTest() {
		if (failureCount == 0){
			BaseLogger.logInfoTextHighlight("Status: PASSED!");
		} else {
			BaseLogger.logInfoTextHighlight("Status: FAILED!");
		}
	}

	/**
	 * Main method to retrieve Flows results from all executed suites
	 */
	public static void prepareDataResults() {
		int getFailed = failureCount;
		int getIgnored = ignoreCount;
		int getSuccess = runCount - getFailed;
		BigDecimal getTotalTests = new BigDecimal(getFailed+getIgnored+getSuccess);
		if (getTotalTests.compareTo(BigDecimal.ZERO) == 1){
			BigDecimal percentIgnores = new BigDecimal(getIgnored * 100).divide(getTotalTests,2,RoundingMode.DOWN);
			BigDecimal percentFails = new BigDecimal(getFailed * 100).divide(getTotalTests,2,RoundingMode.DOWN);
			BigDecimal percentSuccess = new BigDecimal(getSuccess * 100).divide(getTotalTests,2,RoundingMode.DOWN);
			BaseReport.reportContentFromBlockResults(getFailed, getIgnored, getSuccess, getTotalTests, percentIgnores, percentFails, percentSuccess, failureCount, runCount);
		} else {
			BigDecimal percentIgnores = new BigDecimal(0);
			BigDecimal percentFails = new BigDecimal(1);
			BigDecimal percentSuccess = new BigDecimal(0);
			BaseReport.reportContentFromBlockResults(getFailed, getIgnored, getSuccess, new BigDecimal((char[]) BaseContext.getParameter(EnumContextPRM.RUN_COUNT.getValue())), percentIgnores, percentFails, percentSuccess, failureCount, runCount);
		}
	}

}