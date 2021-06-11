package br.com.upperselenium.base;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;
import org.junit.AfterClass;
import org.junit.BeforeClass;

import br.com.upperselenium.base.asset.ReportConfig;
import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.TestLogger;
import br.com.upperselenium.base.constant.EnumContextPRM;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.util.JodaTimeUtil;

/**
 * Base Class that prepares the execution of the Flows associated with the Suite.
 * 
 * @author HudsonRosa
 *
 */
public abstract class BaseSuite extends BaseLogger implements TestLogger {
	private static String ALL_SUITES = "1";
	private static String runningAllSuites;
	private static String dateForHTMLFileName;
	private static String dateForExecutedTest;
	private static String pathHtmlFile = ReportConfig.getReportDirectory() + "SuiteReport_" + getNameHTMLFile() + "_" + BaseStage.getRandomStringNonSpaced();
	private static File htmlFile = new File(pathHtmlFile);
		
		
	/**
	* Method to assemble the date and time structure for the file name.
	* HTML Report.
	* @return
	*/
	public static String getNameHTMLFile() {
		String hif = "-";						
		String day = normalizeTimeStrings(DateTime.now().dayOfMonth().getAsString());		
		String month = normalizeTimeStrings(DateTime.now().monthOfYear().getAsString());		
		String year = normalizeTimeStrings(DateTime.now().year().getAsString());		
		String hour = normalizeTimeStrings(DateTime.now().hourOfDay().getAsString());		
		String minute = normalizeTimeStrings(DateTime.now().minuteOfHour().getAsString());		
		String seconds = normalizeTimeStrings(DateTime.now().secondOfMinute().getAsString());		
		
		dateForHTMLFileName = day + hif + month + hif + year + "_at_" + hour + hif + minute + hif + seconds; 
		dateForExecutedTest = JodaTimeUtil.getCurrentDatePatternDDMMYYYY() + " at " + JodaTimeUtil.getCurrentPeriodPatternHHMMSS(); 
		return dateForHTMLFileName;
	}

	private static String normalizeTimeStrings(String timeType){
		if (timeType.length() <= 1){
			return "0" + timeType;
		}
		return timeType;
	}
	
	public static void setNameHTMLFile(String nameHTMLFile) {
		BaseSuite.dateForHTMLFileName = nameHTMLFile;
	}	
	
	public static String getDateForExecutedTest() {
		return dateForExecutedTest;
	}

	public static String getPathAndNameForIndexHTML() {
		return pathHtmlFile;
	}

	public static File getFileHTML() {
		return htmlFile;
	}

	/**
	* --------------------------------------------
	* Executes the pre-conditions of the suite, such as:
	* <br>
	* <ul>
	* <li>Delete old report files</li>
	* <li>Preparing the Webdriver with capabilities</li>
	* <li>Generate test startup logs</li>
	* </ul>
	*
	* @throws IOException
	*/
	@BeforeClass
	public static void setUpClass() throws IOException {
		flagStartWebdriver();
	}

	private static void flagStartWebdriver() {
		try {
			String isNullFlagRunAll =(String) BaseContext.getParameter(EnumContextPRM.FLAG_RUN_ALL.getValue());
			if (isNullFlagRunAll == null){
				BaseContext.setParameter(EnumContextPRM.START_TIME_TO_SUITE_EXECUTION.getValue(), JodaTimeUtil.getCurrentPeriodPatternDDMMYYYYHHMMSS());
				BaseContext.setParameter(EnumContextPRM.FLAG_RUN_ALL.getValue(),"0");
				runningAllSuites = BaseContext.getParameter(EnumContextPRM.FLAG_RUN_ALL.getValue()).toString();
				initWebDriver();
			}
		} catch (Exception e) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ISOLATED_SUITE_EXECUTION_ERROR), e);
		}
	}

	private static void initWebDriver() {
		if (!ALL_SUITES.equals(runningAllSuites)){
			ReportConfig.deleteOldReportFiles();
			WebDriverMaster.initWebDriverSession();
		}
	}
	
	/**
	* --------------------------------------------
	* Executes the suite's post-conditions, such as:
	* <br>
	* <ul>
	* <li>End WebDriver instance</li>
	* <li>Generate reports in "suiteReport.html" file</li>
	* </ul>
	*
	* @throws IOException
	*/
	@AfterClass
	public static void tearDownClass() {		
		logInfoWithContinuousLine("----------------");
		logInfoFirstOrLastHeaderTitle("SUITE FINISHED");
		logInfoWithContinuousLine("----------------");
		diffForRuntimeExecution();
		closeWebDriver();
		logInfoSimpleText("Check out the results of the Suite Evidence in REPORT:");
		logInfoSimpleText(pathHtmlFile+".html");
	}

	private static void closeWebDriver() {
		runningAllSuites = BaseContext.getParameter(EnumContextPRM.FLAG_RUN_ALL.getValue()).toString();
		if (!ALL_SUITES.equals(runningAllSuites)){
			WebDriverMaster.destroyWebDriverSession();
		}
	}

	/**
	* Method to calculate the total execution time of the Suite and adjust the
	* context variables.
	*/
	private static void diffForRuntimeExecution() {
		BaseContext.setParameter(EnumContextPRM.FINISHED_TIME_FOR_SUITE.getValue(), JodaTimeUtil.getCurrentPeriodPatternDDMMYYYYHHMMSS());
		String startSuite = BaseContext.getParameter(EnumContextPRM.START_TIME_TO_SUITE_EXECUTION.getValue()).toString(); 
		String endSuite = BaseContext.getParameter(EnumContextPRM.FINISHED_TIME_FOR_SUITE.getValue()).toString();
		BaseContext.setParameter(EnumContextPRM.TOTAL_TIME_EXECUTION.getValue(), JodaTimeUtil.getDurationTimeDDHHMMSS(startSuite, endSuite));
		
		BaseStage.createJSONPagesTimeLoadingFile();
	}
		
}