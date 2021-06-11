package br.com.upperselenium.base.listener;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.runner.Result;
import org.junit.runner.notification.RunListener;

import br.com.upperselenium.base.BaseContext;
import br.com.upperselenium.base.BaseReport;
import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.EnumContextPRM;
import br.com.upperselenium.base.constant.MessagePRM;

public class FinalResultsListener extends RunListener {

	private static Boolean isFinished = false;
	private static Integer runCount;
	private static Integer failureCount;
	private static Integer ignoreCount;

	@Override
	public void testRunFinished(Result result) throws java.lang.Exception {
		appendLogInfoResults(result);
	}

	public static void appendLogInfoResults(Result result) {
		if (isFinished == true) {
			return;
		} else {
			getResults(result);			
			logToTotalResults(result);
			prepareDataResults(result);			
			isFinished = true;
		}
	}

	private static void getResults(Result result) {
		try {
			runCount = result.getRunCount();
			failureCount = result.getFailureCount();
			ignoreCount = result.getIgnoreCount();	
		} catch (Throwable e) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.RESULTS_NOT_CALCULATED), e);
		}
	}
	
	private static void logToTotalResults(Result result) {
		String timeExecution = BaseContext.getParameter(EnumContextPRM.TOTAL_TIME_EXECUTION.getValue()).toString();		
		if (runCount > 1){
			BaseLogger.logInfoWithContinuousLine("---------------------");
			BaseLogger.logInfoFirstOrLastHeaderTitle("Test Results".toUpperCase());
			BaseLogger.logInfoWithContinuousLine("---------------------");
			BaseLogger.logInfoBlankLine();			
			BaseLogger.logInfoHeaderSubtitle("Flows executed", runCount);
			BaseLogger.logInfoHeaderSubtitle("Total failed", failureCount);
			checkTheTestSuccess();
			BaseLogger.logInfoHeaderSubtitle("Flows ignored", ignoreCount);
			BaseLogger.logInfoHeaderSubtitle("Duration", timeExecution);
			BaseLogger.logInfoBlankLine();
			BaseLogger.logInfoWithContinuousLine("---------------------");
		} else {
			BaseLogger.logInfoWithContinuousLine("------------------");
			BaseLogger.logInfoFirstOrLastHeaderTitle("Test Results".toUpperCase());
			BaseLogger.logInfoWithContinuousLine("------------------");
			BaseLogger.logInfoBlankLine();
			checkStatusOfTest();
			BaseLogger.logInfoHeaderSubtitle("Flow executed", runCount);
			BaseLogger.logInfoHeaderSubtitle("Flow ignored", ignoreCount);
			BaseLogger.logInfoHeaderSubtitle("Duration", timeExecution);
			BaseLogger.logInfoBlankLine();
			BaseLogger.logInfoWithContinuousLine("------------------");
		}
	}
	
	private static void checkTheTestSuccess() {
		int passed = runCount - failureCount;
		BaseLogger.logInfoHeaderSubtitle("Total passed", passed);
	}
	
	private static void checkStatusOfTest() {
		if (failureCount == 0){
			BaseLogger.logInfoTextHighlight("Status: PASS!");
		} else {
			BaseLogger.logInfoTextHighlight("Status: FAIL!");
		}
	}

	public static void prepareDataResults(Result result) {
		int getFailed = failureCount;
		int getIgnored = ignoreCount;
		int getSuccess = runCount - getFailed;
		BigDecimal getTotalTests = new BigDecimal(getFailed+getIgnored+getSuccess);
		BigDecimal percentIgnores = new BigDecimal(getIgnored * 100).divide(getTotalTests,2,RoundingMode.DOWN);
		BigDecimal percentFails = new BigDecimal(getFailed * 100).divide(getTotalTests,2,RoundingMode.DOWN);
		BigDecimal percentSuccess = new BigDecimal(getSuccess * 100).divide(getTotalTests,2,RoundingMode.DOWN);		
		BaseReport.reportContentFromBlockResults(getFailed, getIgnored, getSuccess, getTotalTests, percentIgnores, percentFails, percentSuccess, failureCount, runCount);
	}
		
}
