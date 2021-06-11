package br.com.upperselenium.base;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.joda.time.DateTime;
import org.openqa.selenium.JavascriptExecutor;

import br.com.upperselenium.base.asset.ReportConfig;
import br.com.upperselenium.base.entity.PageLoaded;
import br.com.upperselenium.base.entity.PerformanceData;
import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.TestLogger;
import br.com.upperselenium.base.constant.EnumContextPRM;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.constant.TimePRM;
import br.com.upperselenium.base.util.DPOUtil;
import br.com.upperselenium.base.util.FindElementUtil;
import br.com.upperselenium.base.util.JSONUtil;
import br.com.upperselenium.base.util.WaitElementUtil;
import br.com.upperselenium.test.stage.wrapper.BaseHelperPage;

/**
 * Base Class used in each Flow Stage call,
 * loading of DPOs and JSon (data mass) as well as
 * execution of the methods corresponding to the Pages involved.
 * 
 * @author Hudson
 */
public abstract class BaseStage extends DPOUtil implements IBaseStage, TestLogger {
	
	private static List<PageLoaded> pages = new ArrayList<PageLoaded>();
	private static List<PageLoaded> newList = new ArrayList<PageLoaded>();
	private static String fileSufix; 	
	private static PerformanceData pagesTimeLoadingList = new PerformanceData();
	
	private static int counterItemStage = 1;

	public static int getCounterItemStage() {
		return counterItemStage++;
	}

	public static int getCounterItemStageRestartedOnList() {
		return counterItemStage = 1;
	}

	// DEFAULT WAITINGS
    public static void waitForAPresentAlert(int timeWaitInSeconds) {
    	WaitElementUtil.waitForAPresentAlert(WebDriverMaster.getWebDriver(), TimePRM._10_SECS);
    }

    public static void waitForPageToLoad(int timeWaitInSeconds) {
    	WaitElementUtil.waitForPageToLoad(WebDriverMaster.getWebDriver(), timeWaitInSeconds);
    }

    public static void waitForPageToLoadUntil10s() {
    	WaitElementUtil.waitForPageToLoad(WebDriverMaster.getWebDriver(), TimePRM._10_SECS);
    }

    // RANDOM STRINGS AND NUMBERS
    public static String getRandomNumber() {
    	String random = BaseContext.getParameter(EnumContextPRM.RANDOM_NUMBER.getValue()).toString();
		return random;    	
    }
    
    public static String getRandomNumberSpaced() {
    	String random = " " + BaseContext.getParameter(EnumContextPRM.RANDOM_NUMBER.getValue()).toString();
    	return random;    	
    }
    
    public static String getRandomStringSpaced() {
    	String random = " " + BaseContext.getParameter(EnumContextPRM.RANDOM_CRYPTED_STRING.getValue()).toString();    	
		return random;
    }
    
    public static String getRandomStringNonSpaced() {
    	String random = BaseContext.getParameter(EnumContextPRM.RANDOM_CRYPTED_STRING.getValue()).toString();    	
    	return random;
    }
    
    public static String getRandomSHA1NonSpaced() {
    	String random = BaseContext.getParameter(EnumContextPRM.RANDOM_PURE_SHA1_STRING.getValue()).toString();    	
    	return random;
    }

    public static String getRandomStringBySize(int sizeString) {
    	String random = BaseContext.getParameter(EnumContextPRM.RANDOM_CRYPTED_STRING.getValue()).toString();    	
    	return random.substring(0, sizeString);
    }

    public static String getRandomSHA1Spaced() {
    	String random = " " + BaseContext.getParameter(EnumContextPRM.RANDOM_PURE_SHA1_STRING.getValue()).toString();    	
    	return random;
    }

    /**
     * Method used to recognize the issues defined in the DP.
     * @param issueValue
     */
    public static void getIssue(String issueValue) {
    	if(StringUtils.isNotBlank(issueValue) || StringUtils.isNotEmpty(issueValue)){
	    	BaseReport.reportContentOfIssueLink(issueValue);
    	}
    }

	/**
	 * Method used to generate logs of the currently running Stage.
	 */
	public void getCurrentStage() {
		Class<? extends BaseStage> currentStage = this.getClass();
		String flowNumberNow = BaseContext.getParameter(EnumContextPRM.FLOW_NUMBER.getValue()).toString();
		BaseContext.setParameter(EnumContextPRM.PAGE_PERFORMANCE_STAGE.getValue(), currentStage.getSimpleName());
		int stageItemNow = getCounterItemStage();
		String itemFlowAndStage = flowNumberNow + "." + stageItemNow;
		BaseReport.reportContentFromStageDescriptions(currentStage, flowNumberNow, stageItemNow, itemFlowAndStage);		
	}

	/**
	* Load data from Data Provider (Path and name of JSon file). <br>
	* It is necessary to set the paths properly in the constants in the StagePRM file e, <br>
	* then insert the constant as the String parameter of the new instance added in Flow. <br><br>
	*
	* <b>CLASS "ExampleTestFlow.java":</b><br><br>
	* Ex.:<br>
	* [...]<br>
	* addStage(new StageExample(<b>StagePRM.DataProviderFile.EXAMPLE_DP_JSON</b>)); <br>
	* [...]<br><br>
	*
	* <b>CLASS "Example2Flow.java":</b><br><br>
	* Ex.:<br>
	* [...]<br>
	* addStage(newStageExamples(<b>&lt;firstStringPathJsonDP&gt;,&lt;secondStringPathJsonDP&gt;</b>)); <br>
	* [...]<br><br>
	*
	* The constructor of 'ExampleStage.java' must be implemented as follows:
	*
	* <b>CLASS "Example2Stage.java":</b><br><br>
	* <i>
	* public LoginStage(String <u>dataProviderPathFile1</u>, String <u>dataProviderPathFile2</u>) { <br>
	* &nbsp;&nbsp;&nbsp; <b>firstDPO</b> = loadDataProviderFile(<b>ExampleDPO.class</b>, <u>dataProviderPathFile1</u>); <br>
	* &nbsp;&nbsp;&nbsp; <b>secondDPO</b> = loadDataProviderFile(<b>OtherExampleDPO.class</b>, <u>dataProviderPathFile2</u>); <br>
	* &nbsp;&nbsp;&nbsp; initElementsFromPage(); <br>
	* }<br>
	*</i>
	*/
	public <D> D loadDataProviderFile(Class<D> dpoClass, String dataProviderPathFile) {
		D dataProvider = getDataProvider(dataProviderPathFile, dpoClass);
		return dataProvider;
	}
	
	/**
	* Loads one or more Pages for the Stage to initialize fields <br>
	* with or without @FindBy tag. For this it is only necessary to invoke the method that
	* initializes the instance of the Page class passed by parameter, according to <br>
	* with the example below:<br>
	* <br>
	*
	* <b>CLASS "ExampleStage.java":</b><br>
	* <br>
	* <i>ExamplePage exPage;<br>
	* <br>
	*
	* public void initElementsFromPage() {<br>
	* &nbsp;&nbsp;&nbsp; exPage = initElementsFromPage(ExamplePage.class);<br>
	* }<br>
	*</i>
	*/
	public <E extends BasePage> E initElementsFromPage(Class<E> pageClass) {
		try {
			E newPageClass = pageClass.newInstance();
			newPageClass.initPageElements();
			return newPageClass;
		} catch (InstantiationException e) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.INSTANTIATION_PAGE_ERROR), e);
		} catch (IllegalAccessException e) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.REFLECTION_PAGE_ERROR), e);
		}		
	}
	
	/**
	* Thead to perform simultaneous message validation action while
	* other interactions occur in the browser.
	*
	* @param pageClass
	* @param operationMessage
	*/
	public void paralelizeOperationMessage(final BaseHelperPage page, final String messageDPO){
		new Thread() {
			public void run() {
				try {
					waitForPageToLoad(TimePRM._10_SECS);
					page.validateMessageDefault(messageDPO);
				} catch (Exception e) {}
			}
		}.start();
	}
	
	/**
	* Thead to perform simultaneous Alert action while
	* other interactions occur in the browser.
	*
	* @param pageClass
	* @param operationMessage
	*/
	public void paralelizeOperation(){
		new Thread() {
			public void run() {
				try {
					FindElementUtil.acceptAlert();
				} catch (Exception e) {}
			}
		}.start();
	}
	
	/**
	* This method is implemented by BaseFlow.
	*/
	public void runCompleteStage() {
		initMappedPages();
		getCurrentStage();
		runStage();
		runValidations();
	}
	
	/**
	* This method performs the verification and triage of data from the URLs considering the greater degradation of time in loading the page.
	*/
	public static void calcPageLoadPerformanceDegradation() {
		StopWatch pageLoad = new StopWatch();
		JavascriptExecutor js = (JavascriptExecutor) WebDriverMaster.getWebDriver();
		try {
			pageLoad.start();
			double loadTimeSecs = (double) js.executeScript(
					"return (window.performance.timing.loadEventEnd - window.performance.timing.navigationStart) / 1000");
			pageLoad.stop();
			PageLoaded page = new PageLoaded();
			if (pageLoad.isStopped()) {
				page.setCurrentStage(
						BaseContext.getParameter(EnumContextPRM.PAGE_PERFORMANCE_STAGE.getValue()).toString());
				page.setCurrentUrl(BasePage.getCurrentPage());
				page.setTimePageLoadSec(loadTimeSecs);
				BaseReport.reportContentOfUrl("<a href=" + page.getCurrentUrl() + " target=\"_blank\">"
						+ page.getCurrentUrl() + "</a>: " + page.getTimePageLoadSec() + " seconds");
				newList = new ArrayList<>();
				if (pages.size() == 0) {
					pages.add(page);
				}
				for (int i = 0; i < pages.size(); i++) {
					if ((page.getCurrentUrl()).equals(pages.get(i).getCurrentUrl())) {
						if ((Double) page.getTimePageLoadSec() >= (Double) pages.get(i).getTimePageLoadSec()) {
							pages.set(i, page);
						}
						break;
					} else if ((i + 1) > pages.size() - 1) {
						newList.add(page);
					}
				}
				if (newList.size() > 0) {
					pages.addAll(newList);
				}
			}
		} catch (Throwable failStage) {
			pageLoad.stop();
			throw new RunningException(
					BaseLogger.logException(MessagePRM.AsException.PERFORMANCE_RESULTS_NOT_CALCULATED), failStage);
		}
	}

	public static void createJSONPagesTimeLoadingFile() {
		String jsonFile = ReportConfig.getReportDirectory()+"PagePerformance_"+getSufixDay()+"_"+getRandomStringNonSpaced()+".json";
		BaseContext.setParameter(EnumContextPRM.PAGE_PERFORMANCE_FILE.getValue(),jsonFile);		
		pagesTimeLoadingList.setDegradationList(pages);				
		JSONUtil.createJSON(jsonFile, pagesTimeLoadingList);
		BaseLogger.logInfoTextHighlight("Page performance data saved successfully!");
		BaseLogger.logInfoTextHighlight("Check the file: '" + jsonFile + "'");
	}
	
	/**
	 * Método para montar a estrutura de data para o nome do arquivo JSON.
	 * @return
	 */
	public static String getSufixDay() {
		String hif = "_";						
		String day = normalizeTimeStrings(DateTime.now().dayOfMonth().getAsString());		
		String month = normalizeTimeStrings(DateTime.now().monthOfYear().getAsString());		
		String year = normalizeTimeStrings(DateTime.now().year().getAsString());				
		fileSufix = day + hif + month + hif + year; 
		return fileSufix;
	}
	
	/**
	 * Método para montar a estrutura de hora para o nome do arquivo JSON.
	 * @return
	 */
	public static String getSufixTime() {
		String hif = "_";						
		String hour = normalizeTimeStrings(DateTime.now().hourOfDay().getAsString());		
		String minute = normalizeTimeStrings(DateTime.now().minuteOfHour().getAsString());		
		String seconds = normalizeTimeStrings(DateTime.now().secondOfMinute().getAsString());		
		fileSufix = hif + hour + hif + minute + hif + seconds; 
		return fileSufix;
	}

	private static String normalizeTimeStrings(String timeType){
		if (timeType.length() <= 1){
			return "0" + timeType;
		}
		return timeType;
	}	
	
}
