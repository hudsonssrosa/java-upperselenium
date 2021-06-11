package br.com.upperselenium.base;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import com.google.common.base.Throwables;
import com.lowagie.text.DocumentException;

import br.com.upperselenium.base.asset.ReportConfig;
import br.com.upperselenium.base.entity.GraphParameter;
import br.com.upperselenium.base.entity.PerformanceData;
import br.com.upperselenium.base.listener.ObserverFinalResultsListener;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.TestLogger;
import br.com.upperselenium.base.constant.ContextPRM;
import br.com.upperselenium.base.constant.EnumContextPRM;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.constant.SymbolPRM;
import br.com.upperselenium.base.util.JSONUtil;
import br.com.upperselenium.base.util.JodaTimeUtil;
import br.com.upperselenium.base.util.ScreenshotEvidencesUtil;

/**
 * Base Class used in the execution of the WebDriver that aggregates Flow Stages
 * corresponding.
 * 
 * @author HudsonRosa
 */
@RunWith(ObserverFinalResultsListener.class)
public abstract class BaseReport implements TestLogger {
	private static final String JAVA_FILE_EXTENSION = FileDirPRM.Extension.JAVA_FILE;
	private static final String SPAN_STYLE_FONTWEIGHT = "<span style=\"font-weight: lighter\">";
	private static final String EVENT_AFTER_BEHAVIOR = "eventAfterBehavior";
	private static final String EVENT_VALIDATION_TITLE = "eventValidationTitle";
	private static final String EVENT_VALIDATION_EXPECTED = "eventValidationExpected";
	private static final String EVENT_VALIDATION_CORRECT = "eventValidationCorrect";
	private static final String EVENT_VALIDATION_UNCORRECT = "eventValidationUncorrect";
	private static final String TARGET_BLANK = "\" target=\"_blank\"";
	private static final String SPACE_NBSP = "&nbsp;&nbsp;&nbsp;&nbsp;";
	private static String pathHtmlFile = BaseSuite.getPathAndNameForIndexHTML();
	private static File fileHTML = BaseSuite.getFileHTML();
	private static List<String> dataStage;
	private static List<String> dataItem;
	private static List<String> dataUrlTime;
	private static boolean hasGraph = true;
	private static File outputEvidenceDirectory = new File(ReportConfig.getEvidenceDirectory());
	private static final String JQUERY_ACCORDION_SCRIPT = "$(document).ready(function() { " +
			"    function close_accordion_section() { " +
			"        $('.accordion .accordion-section-title').removeClass('active'); " +
			"        $('.accordion .accordion-section-content').slideUp(200).removeClass('open'); " +
			"    } " +
			"    $('.accordion-section-title').click(function(e) { " +
			"        var currentAttrValue = $(this).attr('href'); " +
			"        if($(e.target).is('.active')) { " +
			"            close_accordion_section(); " +
			"        }else { " +
			"            close_accordion_section(); " +
			"            $(this).addClass('active'); " +
			"            $('.accordion ' + currentAttrValue).slideDown(200).addClass('open');  " +
			"        } " +
			"        e.preventDefault(); " +
			"    }); " +
			"}); ";

	private static String mountDegradationGraph() {
		String E_CHARTS_CONTENT_SCRIPT = "	$(document).ready(function () { " + 
				"		var echPerformanceGraph = echarts.init(document.getElementById('graphPageLoading')); " + 
				"		$('#graphPageLoading').css({ 'height': '100%' }); " + 
				"		var xAxisData = []; " + 
				"		var data1 = []; " + 
				"		var data2 = []; " + 
				"		echPerformanceGraph.setOption({ " + 
				"			title: { " + 
				"				text: '' " + 
				"			}, " + 
				"			legend: { " + 
				"				data: ['Current Version', 'Previous Version'], " + 
				"				align: 'left' " + 
				"			}, " + 
				"			toolbox: { " + 
				"				feature: { " + 
				"					magicType: { " + 
				"						type: ['line', 'bar', 'stack', 'tiled'] " + 
				"					}, " + 
				"					restore: {show: true}," +
				"					dataView: {}, " + 
				"					saveAsImage: { " + 
				"						pixelRatio: 2 " + 
				"					} " + 
				"				} " + 
				"			}, " + 				
    			"			tooltip: { " +
    			"				trigger: 'axis', " +
			    "    			formatter: '<b><i>{a}</b></i><br/>{b} <br/> Time Loading: {c} seconds'" +
			    "			}," +				
				"			xAxis: { " + 
				//				INSERÇÃO DOS DADOS DE LABEL DO EIXO X 
				"				data: " + dataItem + ", " +
			    " 				axisLabel: { " + 
		        "    				interval: 0, " + 
		        "    				rotate: 0 " + 
		        "				}, " + 
				"				silent: false, " +
				"	            textStyle: { " +
				"	                color: 'red' " +
				"               }, " +
				"				splitLine: { " + 
				"					show: true " + 
				"				} " + 
				"			}, " + 
				"			yAxis: { " + 
				"			}, " + 
				"			series: [" +
				"           { " + 
				"				name: 'STAGE -- URL -- Loading Time', " + 
				"				type: 'bar', " + 
				//				INSERTION OF DEGRADATION DATA 
				"				data: " + dataUrlTime + ", " + 
				"				label: { " +
				"		            normal: { " +
				"		                show: true, " +
				"		                position: 'top' " +            			
				"					} " +						
				"		        }, " +
				"				markPoint : { " + 
				" 					symbolSize: 100, " +			
				" 					symbolOffset: [0, '20%'], " +		
				"    				data : [ " + 
				"        				{type : 'max', name: 'Longest Screen Loading Time'}, " + 
				"        				{type : 'min', name: 'Shortest Screen Loading Time'} " + 
				"    				] " + 
				"				}, " + 
				"	        	itemStyle: { " + 
				"	            	normal: { " + 
				"	                	color: '#4682B4' " +
				"					} " +	
				"	            }, " + 
				"				animationDelay: function (idx) { " + 
				"					return idx * 10; " + 
				"				} " + 
				"			} " +
				//"           ,{ " + 
				//"				name: 'STAGE -- URL -- Tempo de Carregamento', " + 
				//"				type: 'bar', " + 
				//"				data: data2, " + 
				//"				animationDelay: function (idx) { " + 
				//"					return idx * 10 + 100; " + 
				//"				} " + 
				//"			}" +
				"           ], " + 
				"			animationEasing: 'elasticOut', " + 
				"			animationDelayUpdate: function (idx) { " + 
				"				return idx * 5; " + 
				"			} " + 
				"		}); " + 
				"    });  ";
		return E_CHARTS_CONTENT_SCRIPT;
	}
	
	private static List<GraphParameter> readyJSONAndUpdatePerformanceDegradation() {
		PerformanceData listDegradation = new PerformanceData();
		listDegradation = (PerformanceData) JSONUtil.getJSONData((String) BaseContext.getParameter(EnumContextPRM.PAGE_PERFORMANCE_FILE.getValue()), PerformanceData.class);		
		GraphParameter graphparameter = new GraphParameter();
		List<GraphParameter> graphData = new ArrayList<GraphParameter>();
		dataUrlTime = new ArrayList<>();
		dataItem = new ArrayList<>();
		dataStage = new ArrayList<>();
		for (int i=0; i < listDegradation.getDegradationList().size(); i++ ){
			graphparameter.setStage(listDegradation.getDegradationList().get(i).getCurrentStage());
			graphparameter.setName(listDegradation.getDegradationList().get(i).getCurrentUrl());
			graphparameter.setValue(listDegradation.getDegradationList().get(i).getTimePageLoadSec());
			graphData.add(graphparameter);
			int itemNumber = i+1;
			dataStage.add("'" + graphData.get(i).getStage() + "(" + itemNumber +")'");
			dataItem.add(""+itemNumber);
			dataUrlTime.add("{name: '" + graphData.get(i).getStage()+ "(" + itemNumber + ")" + " -- "+ graphData.get(i).getName() + "', value: "+ graphData.get(i).getValue() + "}");
			System.out.println(dataUrlTime.get(i));
		}
		if (listDegradation.getDegradationList().size() <= 0)
			hasGraph = false;
		return graphData;
	}
	
	// --- BEFORE - IN BaseFlow.java ---

	/**
	 * REPORT - PART 1
	 */
	public static void reportContentFromHead() {
		ReportConfig.appendTag(fileHTML, ReportConfig.getInitialHtmlTags(null), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeHeadOpened(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeMetaOnHead("charset", SymbolPRM.UTF8), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeCssImportOnHead(FileDirPRM.REPORT_RESOURCES+"/reportSkin.css"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeScriptImportsOnHead(FileDirPRM.REPORT_RESOURCES+"/jquery.min.js"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeScriptImportsOnHead(FileDirPRM.REPORT_RESOURCES+"/echarts.js"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeHeadClosed(), pathHtmlFile);
	}
	/**
	 * REPORT - PART 2
	 */
	public static void reportContentFromInitialBody() {
		String webDriverOnExecution = WebDriverMaster.getWebDriverName().getClass().getSimpleName();
		ReportConfig.appendTag(fileHTML, ReportConfig.writeBodyOpened(null), pathHtmlFile);				
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "headerReport"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "headerTitleBlock"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTag(null, null, null, "titleGroup", false), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(null, null, null, "logoA", false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeImgTag(FileDirPRM.REPORT_RESOURCES+"your_app_logo.png", 
				null, null, null, null), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTagClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(null, null, null, "logoB", false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeImgTag(FileDirPRM.REPORT_RESOURCES+"logo_upperselenium_lowres.png", 
				null, null, null, null), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTagClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag("UpperSelenium Test Automation", null, null, "reportTitle", true), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTagClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "headerDriver"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(getWebDriverLogo(webDriverOnExecution) + " Executed with WebDriver " + webDriverOnExecution + " in: " + BaseSuite.getDateForExecutedTest(), null, null, null, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "executionContent"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "flowBlock"), pathHtmlFile);
	}
	
	private static String getWebDriverLogo(String webDriverOnExecution){
		String imgAttrs =  " class=\"browserLogo\">";
		if ("MarionetteDriver".equalsIgnoreCase(webDriverOnExecution)) {
			return "<img src=\""+FileDirPRM.REPORT_RESOURCES+FileDirPRM.RES_BROWSER_LOGO_FIREFOX +"\" alt=\"FirefoxDriver\"" + imgAttrs;
		}
		if ("ChromeDriver".equalsIgnoreCase(webDriverOnExecution)) {
			return "<img src=\""+FileDirPRM.REPORT_RESOURCES+FileDirPRM.RES_BROWSER_LOGO_CHROME +"\" alt=\"ChromeDriver\"" + imgAttrs;
		}
		if ("PhantomJSDriver".equalsIgnoreCase(webDriverOnExecution)) {
			return "<img src=\""+FileDirPRM.REPORT_RESOURCES+FileDirPRM.RES_BROWSER_LOGO_PHANTOMJS +"\" alt=\"PhantomJS\"" + imgAttrs;
		}
		if ("IEDriver".equalsIgnoreCase(webDriverOnExecution)) {
			return "<img src=\""+FileDirPRM.REPORT_RESOURCES+FileDirPRM.RES_BROWSER_LOGO_IE +"\" alt=\"IEDriverServer\"" + imgAttrs;
		}
		if ("EdgeDriver".equalsIgnoreCase(webDriverOnExecution)) {
			return "<img src=\""+FileDirPRM.REPORT_RESOURCES+FileDirPRM.RES_BROWSER_LOGO_EDGE +"\" alt=\"EdgeDriver\"" + imgAttrs;
		}
		return webDriverOnExecution;
	}
	
	/**
	 * REPORT - PART 3
	 */
	public static void reportContentFromParameterDescriptions(String idFlowTest, Class<?> suiteName, 
			String suiteDescription, String goalFlow, String flowClassName, int flowNumber) {
		BaseContext.setParameter(EnumContextPRM.FLOW_NUMBER.getValue(), flowNumber);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, "flowSeq" + flowNumber, "flowTitle"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATag(null, "https://putYourCompanySiteHere.com" + TARGET_BLANK, null, null, null, false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeImgTag(FileDirPRM.REPORT_RESOURCES+FileDirPRM.RES_COMPANY_LOGO, null, null, "appLogo", "UpperSelenium Company Logo"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATagClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATag( flowNumber + ". Step/Stage Flows - Test ID: " + idFlowTest, "#timeExecution", null, null, null, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("flowSeq"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "suiteTitle"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag("Origin Suite: " + suiteName.getSimpleName() + JAVA_FILE_EXTENSION, null, null, "descriptionSuite", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTag(null, null, null, null, false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(suiteDescription, null, null, null, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTagClosed(), pathHtmlFile);	
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("suiteTitle"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag("", null, null, "descriptionFlow"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag("Flow Class: " + flowClassName + JAVA_FILE_EXTENSION, null, null, "flow", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTag(null, null, null, null, false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag("Goal Description: " + goalFlow, null, null, null, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTagClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag("", null, null, "descriptionStage"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag("Stages: ", null, null, "stage", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTag(null, null, null, null, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
	}
	
	/**
	 * REPORT - PART 4
	 */
	public static void reportContentFromCounterFlow(IBaseStage stage, int flowReportNumber, int counterItemStage) {
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "stageList"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, null), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATag(flowReportNumber + "." + counterItemStage + ") " + stage.getClass().getSimpleName(), 
				"#"+flowReportNumber+"."+counterItemStage+"_"+stage.getClass().getSimpleName()+"_Flow_" + flowReportNumber, null, null, null, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
	}
	
	// --- IN BaseStage.java ---
	
	/**
	 * Inserts data in the report about the value filled by WebDriver in an element on the screen.
	 * 
	 * @param textForEvidence
	 */
	public static void reportContentOfElementEvent(Object textForEvidence) {
		String text = textForEvidence.toString();
		BaseLogger.logInfoEvents("Value: " + text);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(
				SymbolPRM.DINGBAT_HANDWRITING_9997 + " | "
				+ text, null, null, EVENT_AFTER_BEHAVIOR, true), pathHtmlFile);
	}
	
	/**
	 * Inserts data into the report about the URL accessed by WebDriver in an element on the screen.
	 * 
	 * @param textForEvidence
	 */
	public static void reportContentOfUrl(Object textForEvidence) {
		String text = textForEvidence.toString();
		BaseLogger.logInfoEvents("URL: " + text);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(
				SymbolPRM.DINGBAT_RIGHT_ARROW_10140 + " | "
						+ text, "style=\"background-color: #e5fbff\"", null, EVENT_AFTER_BEHAVIOR, true), pathHtmlFile);
	}
	
	/**
	 * Inserts data in the report about the description title of the element accessed by WebDriver on the screen.
	 * 
	 * @param textForEvidence
	 */
	public static void reportContentOfElementTitle(Object textForEvidence) {
		String text = textForEvidence.toString();
		BaseLogger.logInfoEvents(" ");
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(text, "style=\"margin-top: 15px\"", null, EVENT_VALIDATION_TITLE, true), pathHtmlFile);
	}	
	
	/**
	 * Inserts data in the report about the element description title showing the Fault accessed by WebDriver on the screen.
	 * 
	 * @param textForEvidence
	 */
	public static void reportContentOfElementTitleFailed(Object textForEvidence) {
		String text = textForEvidence.toString();
		BaseLogger.logInfoEvents(" ");
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(text, "style=\"margin-top: 15px; color: #B22222\"", null, EVENT_VALIDATION_TITLE, true), pathHtmlFile);
	}	
	
	/**
	 * Inserts data in the report about the element description title presenting the Success accessed by WebDriver on the screen.
	 * 
	 * @param textForEvidence
	 */
	public static void reportContentOfElementTitleSuccess(Object textForEvidence) {
		String text = textForEvidence.toString();
		BaseLogger.logInfoEvents(" ");
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(text, "style=\"margin-top: 15px; color: #32CD32\"", null, EVENT_VALIDATION_TITLE, true), pathHtmlFile);
	}	
	
	static void reportContentOfIssueLink(String issueValue) {
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATag(null, "https://yourJiraIssueLink.com"+issueValue.toString() + TARGET_BLANK, null, null, null, false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag("There is an issue reported for this scenario: " + issueValue.toString(), null, null, "issueStage", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATagClosed(), pathHtmlFile);
	}  
	
	/**
	 * REPORT - PARTE 4.1
	 */
	static void reportContentFromStageDescriptions(Class<? extends BaseStage> currentStage, String flowNumberNow, int stageItemNow,String itemFlowAndStage) {
		BaseLogger.logInfoHeader("STAGE EXECUTION " + itemFlowAndStage);
		BaseLogger.logInfoHeaderSubtitle("Stage name", currentStage.getSimpleName() + FileDirPRM.Extension.JAVA_FILE);
		BaseLogger.logInfoBlankLine();	
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "executedStage"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATagClosed(), pathHtmlFile);	
		
		// Início da Estrutura de Accordion da Stage		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "accordion"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, itemFlowAndStage+"_"+currentStage.getSimpleName()+"_Flow_"+flowNumberNow, "accordion-section"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATag(itemFlowAndStage + " - " + currentStage.getSimpleName() + FileDirPRM.Extension.JAVA_FILE 
				+ SymbolPRM.DINGBAT_RIGHT_ARROW_10140 + "Stage executed", "#accordion-"+flowNumberNow+stageItemNow, null, null, "accordion-section-title", true), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, "accordion-"+flowNumberNow+stageItemNow, "accordion-section-content"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTag(null, null, null, "stageEvents", false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "previous"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeATag("Back to Stages", "#flowSeq" + flowNumberNow, null, null, null, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);		
	}
	
	/**
	 * REPORT - PART 5.A
	 */
	public static void reportContentOfSuccessStage() {
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTagClosed(), pathHtmlFile);
		
		// End of Accordion (Stage section)
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("accordion-section-content"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "resultStageSuccess"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(SymbolPRM.DINGBAT_OK_10004 + "Sucesso", null, null, "succeededToggle", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("resultStageSuccess"), pathHtmlFile);	
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("accordion-section"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("accordion"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("executedStage"), pathHtmlFile);
	}	
	
	/**
	 * REPORT - PART 5.B
	 */
	public static void reportContentToFailureStage(WebDriver webDriver, Throwable failTest, String fileEvidenceName) {
		BaseLogger.logErrorSimpleTextHighlight("Stage FAILED for this Test Flow.");
		stageThrowable(failTest);
		String messageException = Throwables.getStackTraceAsString(failTest);
		String threatedMessageException = messageException.replace("=true,", "=true,\n").replace("=false,", "=false,\n");
		File takeScreenShot = ScreenshotEvidencesUtil.takeScreenShot(webDriver, outputEvidenceDirectory, "Failed_" + fileEvidenceName);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTagClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "resultStageFail"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(SymbolPRM.DINGBAT_NOTOK_10008 + "Exceção thrown", null, null, "failed", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag("<textarea disabled>" + threatedMessageException + "</textarea>", null, null, "resultStageException"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("resultStageException"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "resultStageScreenshot"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag("Screenshot:", null, null, "screenshotPhrase", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeImgTag(ReportConfig.getAncestorDirectory() + takeScreenShot.getPath(), null, null, "screenshot", "Test Failure"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("resultStageScreenshot"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("resultStageFail"), pathHtmlFile);
		
		// Fim da Accordion Strutura (Stage)
		ReportConfig.appendTag(fileHTML, ReportConfig.writeBreakLine(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("accordion-section-content"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null, null, null, "resultStageFail"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(SymbolPRM.DINGBAT_ERROR_10060 + "Falha", null, null, "failedToggle", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("resultStageFail"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("accordion-section"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("accordion"), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("executedStage"), pathHtmlFile);
	}
	
	public static void stageThrowable(Throwable e) {		
		BaseLogger.logInfoBlankLine();
		BaseLogger.logThrowables(MessagePRM.AsException.STAGE_EXCEPTION, e);
	}
	
	/**
	 * REPORT - PART 6
	 */	
	public static void reportContentOfFlowDuration(long initFlowTime, String flowClassName) {
		String flowDateTime = JodaTimeUtil.getCurrentDatePatternDDMMYYYY() + " at " + JodaTimeUtil.getCurrentPeriodPatternHHMMSS();		
		long flowTime = getFlowTime(initFlowTime);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(SymbolPRM.DINGBAT_HOURGLASS_8987 + "The test took: " + flowTime + " second(s) - " + flowDateTime, null, null, "flowTime"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		BaseContext.setParameter(flowClassName, " - " + flowTime + " second(s)");
		BaseContext.getParameter(flowClassName);
	}
	
	private static long getFlowTime(long initFlowTime) {
		long finishFlowTime = (System.currentTimeMillis() - initFlowTime);
		int time = (int) (finishFlowTime /1000 % 60);
		return time;
	}
	
	
	// --- AFTER - IN BaseFlow.java ---
	
	/**
	 * REPORT - PART 7
	 */
	public static void reportContentFromClosedBlockExecution() {
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("flowBlock"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("executionContent"), pathHtmlFile);
	}
	
	// -- IN AssertUtil.java (FINAL STAGE) ---
	
	public static void reportConditionalAssertion(boolean condition, Object message) {
		BaseLogger.logInfoEvents("Element Validation");
		BaseLogger.logInfoEvents("condition: " + condition + ". " + message);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag("Element Validation", "style=\"margin-top: 15px\"", null, EVENT_VALIDATION_TITLE, true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(
				SymbolPRM.DINGBAT_EXPECTED_10068 + " Condition verification " + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 + condition + ". " + message, null, null, EVENT_VALIDATION_EXPECTED, true), pathHtmlFile);
	}
	
	public static void reportSuccessEqualsAssertion(Object expected, Object actual) {
    	String expectedMessage = " " + expected;
    	String actualMessage = " " + actual;
    	if (expected.equals(actual)){
	    	BaseLogger.logInfoEvents("Element Validation");
	    	BaseLogger.logInfoEvents("Expected Result: " + expectedMessage);
	    	BaseLogger.logInfoEvents("Found: " + actualMessage);
	    	ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag("Element Validation", "style=\"margin-top: 15px\"", null, EVENT_VALIDATION_TITLE, true), pathHtmlFile);
	    	ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(SymbolPRM.DINGBAT_EXPECTED_10068 + " Expected Result " + SPACE_NBSP + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 
    			+ expectedMessage, null, null, EVENT_VALIDATION_EXPECTED, true), pathHtmlFile);
    		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(SymbolPRM.DINGBAT_OK_9989 + " Found " + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 
        		+ actualMessage, null, null, EVENT_VALIDATION_CORRECT, true), pathHtmlFile);
    	} 
	}
	
	public static void reportFailedEqualsAssertion(Object expected, Object actual) {
    	String expectedMessage = " " + expected;
    	String actualMessage = " " + actual;
    	if (!expected.equals(actual)){
	    	BaseLogger.logInfoEvents("Element Validation");
	    	BaseLogger.logInfoEvents("Expected Result: " + expectedMessage);
	    	BaseLogger.logInfoEvents("Found: " + actualMessage);
	    	ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag("Element Validation", "style=\"margin-top: 15px\"", null, EVENT_VALIDATION_TITLE, true), pathHtmlFile);
	    	ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(SymbolPRM.DINGBAT_EXPECTED_10068 + " Expected Result " + SPACE_NBSP + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 
    			+ expectedMessage, null, null, EVENT_VALIDATION_EXPECTED, true), pathHtmlFile);
	    	ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(SymbolPRM.DINGBAT_ERROR_10060 + " Found " + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 
		    	+ actualMessage, null, null, EVENT_VALIDATION_UNCORRECT, true), pathHtmlFile);
    	} 
	}
	
	public static void reportNotEqualsAssertion(Object unexpected, Object actual) {
    	String unexpectedMessage = " " + unexpected;
    	String actualMessage = " " + actual;
    	if (!unexpected.equals(actual)){
    		BaseLogger.logInfoEvents("Element Validation");
    		BaseLogger.logInfoEvents("Expected Result: " + unexpectedMessage);
    		BaseLogger.logInfoEvents("Found: " + actualMessage);
    		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag("Element Validation", "style=\"margin-top: 15px\"", null, EVENT_VALIDATION_EXPECTED, true), pathHtmlFile);
    		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(SymbolPRM.DINGBAT_EXPECTED_10068 + " Expected Result " + SPACE_NBSP + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 
    				+ unexpectedMessage, null, null, EVENT_VALIDATION_EXPECTED, true), pathHtmlFile);
    		ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(SymbolPRM.DINGBAT_OK_10062 + " Found " + SymbolPRM.DINGBAT_RIGHT_ARROW_10140 
    				+ actualMessage, null, null, EVENT_VALIDATION_UNCORRECT, true), pathHtmlFile);
    	} 
	}
	
	// -- IN BaseSuiteRunner.java (FINAL SUITE) ---

	/**
	 * REPORT - PARTE 8
	 * Generate the result block at the end of the HTML report
	 * 
	 * @param getFailed
	 * @param getIgnored
	 * @param getSuccess
	 * @param getTotalTests
	 * @param percentIgnores
	 * @param percentFails
	 * @param percentSuccess
	 * @throws DocumentException 
	 * @throws IOException 
	 */
	public static void reportContentFromBlockResults(int getFailed, int getIgnored, int getSuccess, BigDecimal getTotalTests,
			BigDecimal percentIgnores, BigDecimal percentFails, BigDecimal percentSuccess, Integer failureCount, Integer runCount) {
		String timeExecution = BaseContext.getParameter(EnumContextPRM.TOTAL_TIME_EXECUTION.getValue()).toString();		
		String climate = checkClimate(failureCount, runCount);
		String finalTimeStamp = JodaTimeUtil.getCurrentDatePatternDDMMYYYY() + " às " + JodaTimeUtil.getCurrentPeriodPatternHHMMSS();
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,null, "timeExecution", "timeExecution"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(null,null,null, "totalSeconds", false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeCustomTag("span", SymbolPRM.DINGBAT_CLOCK_8986,null,null, "hourglass", false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeCustomTag("span", "Total execution time: " + timeExecution + " - Finished at: " 
		+ finalTimeStamp, null, null, "time", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeCustomTagClosed("span"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeCustomTag("span", "General condition: ",null,null, "healthStatus", false), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeCustomTag("span", climate,null,null, "climate", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeCustomTagClosed("span"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writePTagClosed(), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTagClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("timeExecution"), pathHtmlFile);
		
		// General List of Executed Tests
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,null,null, "executedTests"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTag(null, null, null, "listTestResults", false), pathHtmlFile);
		
		for (int index = 0; index < BaseContext.getListParameters(ContextPRM.TEST_RESULT_LIST).size(); index++) {				
			String flowNameResult = (String) BaseContext.getParameterOfList(ContextPRM.TEST_RESULT_LIST,index);
			String[] splitedFlowName = flowNameResult.toString().split("Flow: ");
			String flowTimeResult = (String) BaseContext.getParameter(splitedFlowName[1]);			
			int orderInList = index + 1;
			ReportConfig.appendTag(fileHTML, ReportConfig.writeATag(null, "#flowSeq" + orderInList + "", null, null, null, false), pathHtmlFile);			
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(flowNameResult.toString() + flowTimeResult.toString(), null, null, getCheckedFlowInFinalResults(flowNameResult), true), pathHtmlFile);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeATagClosed(), pathHtmlFile);
		}
		ReportConfig.appendTag(fileHTML, ReportConfig.writeUlTagClosed(), pathHtmlFile);		
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("executedTests"), pathHtmlFile);
		
		// Results Bar (Graph)
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,null,null, "headerGraph"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeH3Tag("<b>Suite Tests Overview</b>", null, null, "titleSuiteStatus", true), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,null,null,"graph"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,"style=\"width:100%\"",null,"fullbar"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,"style=\"width:"+percentIgnores+"%;\"",null,"barIgnore"),pathHtmlFile);
		checkPercentIgnores(percentIgnores);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,"style=\"width:"+percentFails+"%;\"",null,"barFail"),pathHtmlFile);
		checkPercentFailures(percentIgnores, percentFails);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,"style=\"width:"+percentSuccess+"%;\"",null,"barSuccess"),pathHtmlFile);
		checkPercentSuccess(percentSuccess);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("fullbar"), pathHtmlFile);
	
		// Result Inboxes
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,null,null,"statusbox"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(getBoldingIgnoreResults(getIgnored, getTotalTests, percentIgnores, percentFails, percentSuccess),"style=\"background-color:#ffeebc\"",null,"inbox"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(getBoldingFailResults(getFailed, getTotalTests, percentIgnores, percentFails, percentSuccess),"style=\"background-color:#e4a7a3\"",null,"inbox"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(getBoldingSuccessResults(getSuccess, getTotalTests, percentSuccess, percentFails, percentIgnores),"style=\"background-color:#aee0ae\"",null,"inbox"),pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("statusbox"), pathHtmlFile);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed("graph"), pathHtmlFile);
		
		
		//ECHARTS
		readyJSONAndUpdatePerformanceDegradation();
		
		if(hasGraph){
			String E_CHARTS_CONTENT_SCRIPT = mountDegradationGraph();
			
			ReportConfig.appendTag(fileHTML, ReportConfig.writeH3Tag("<b>Performance Degradation of Executed Pages</b>", null, null, "titleEchart", true), pathHtmlFile);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeDivTag(null,null,"graphPageLoading","performanceGraph"),pathHtmlFile);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeDivClosed(), pathHtmlFile);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeScriptTag(E_CHARTS_CONTENT_SCRIPT), pathHtmlFile);
		}
						
		// Script JQuery Accordion 
		ReportConfig.appendTag(fileHTML, ReportConfig.writeScriptTag(JQUERY_ACCORDION_SCRIPT), pathHtmlFile);
		
		// REPORT-TEST Closing the HTML
		ReportConfig.appendTag(fileHTML, ReportConfig.writeBodyAndHTMLClosed(), pathHtmlFile);
	}

	private static String getCheckedFlowInFinalResults(String flowNameResult) {
		if(flowNameResult.contains("FAILED")){
			return "flowFailed";
		}
		if(flowNameResult.contains("PASSED")){
			return "flowPassed";
		}
		return flowNameResult;
	}
	
	private static String checkClimate(Integer failureCount, Integer runCount) {
		int getFailed = failureCount;
		int getSuccess = runCount - getFailed;
		if (runCount == getSuccess){
			return SymbolPRM.DINGBAT_SUN_9788;
		} else {
			return SymbolPRM.DINGBAT_UMBRELLA_9730;
		}
	}

	private static void checkPercentIgnores(BigDecimal percentIgnores) {
		if(percentIgnores.doubleValue() > 0){
			ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(percentIgnores+"%",null,null,null,true),pathHtmlFile);
		}
	}
	private static void checkPercentFailures(BigDecimal percentIgnores,
			BigDecimal percentFails) {
		if(percentFails.doubleValue() > 0){
			ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(percentFails+"%",null,null,null,true),pathHtmlFile);
		}
	}
	private static void checkPercentSuccess(BigDecimal percentSuccess) {
		if(percentSuccess.doubleValue() > 0){
			ReportConfig.appendTag(fileHTML, ReportConfig.writePTag(percentSuccess+"%",null,null,null,true),pathHtmlFile);
		}
	}

	/**
	 * Insert bold text if status of Ignored tests exists
	 * 
	 * @param getIgnored
	 * @param getTotalTests
	 * @param percentIgnores
	 * @param percentFails
	 * @param percentSuccess
	 * @return
	 */
	private static String getBoldingIgnoreResults(int getIgnored,
			BigDecimal getTotalTests, BigDecimal percentIgnores,
			BigDecimal percentFails, BigDecimal percentSuccess) {
		if (percentIgnores.doubleValue() > percentFails.doubleValue()
				&& percentIgnores.doubleValue() > percentSuccess.doubleValue()){
			return "<b>Ignorou: "+percentIgnores+"% <br>("+getIgnored+"-"+getTotalTests+" Flow Test(s))</b>";
		} else {
			return "<i>" + SPAN_STYLE_FONTWEIGHT + "Ignorou: "+percentIgnores+"% <br>("+getIgnored+"-"+getTotalTests+" Flow Test(s))</i>";
		}
	}
	
	/**
	 * Insert bold text if status of Failed tests exists
	 * 
	 * @param getFailed
	 * @param getTotalTests
	 * @param percentIgnores
	 * @param percentFails
	 * @param percentSuccess
	 * @return
	 */
	private static String getBoldingFailResults(int getFailed,
			BigDecimal getTotalTests, BigDecimal percentIgnores,
			BigDecimal percentFails, BigDecimal percentSuccess) {
		if (percentFails.doubleValue() > percentIgnores.doubleValue()
				&& percentFails.doubleValue() > percentSuccess.doubleValue()){
			return "<b>Failed: "+percentFails+"% <br>("+getFailed+"-"+getTotalTests+" Flow Test(s))</b>";
		} else {
			return "<i>" + SPAN_STYLE_FONTWEIGHT + "Failed: "+percentFails+"% <br>("+getFailed+"-"+getTotalTests+" Flow Test(s))</i>";
		}
	}
	
	/**
	 * Insert bold text if status of Passed tests exists
	 * 
	 * @param getSuccess
	 * @param getTotalTests
	 * @param percentSuccess
	 * @param percentFails
	 * @param percentIgnores
	 * @return
	 */
	private static String getBoldingSuccessResults(int getSuccess,
			BigDecimal getTotalTests, BigDecimal percentSuccess,
			BigDecimal percentFails, BigDecimal percentIgnores) {
		if (percentSuccess.doubleValue() > percentFails.doubleValue()
				&& percentSuccess.doubleValue() > percentIgnores.doubleValue()){
			return "<b>Passed: "+percentSuccess+"% <br>("+getSuccess+"-"+getTotalTests+" Flow Test(s))</b>";
		} else {
			return "<i>" + SPAN_STYLE_FONTWEIGHT + "Passed: "+percentSuccess+"% <br>("+getSuccess+"-"+getTotalTests+" Flow Test(s))</i>";
		}
	}	
	
}