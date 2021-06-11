package br.com.upperselenium.base.listener;

import java.io.File;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.WebDriverEventListener;

import br.com.upperselenium.base.BaseSuite;
import br.com.upperselenium.base.asset.ReportConfig;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.SymbolPRM;
import br.com.upperselenium.base.util.ScreenshotEvidencesUtil;


@Aspect
public class EventsCaptureListener implements WebDriverEventListener{
	
	private static final String BR_MARGIN_SPACE = "<br><span style=\"margin-left:60px\">";
	private static final String SPAN_CLOSE = "</span>";
	private static final String EVENT = "event";
	private static WebElement arg0Performed;
	private static String pathHtmlFile = BaseSuite.getPathAndNameForIndexHTML();
	private static File fileHTML = BaseSuite.getFileHTML();
	
	private String replaceArrow(WebElement arg) {
		return arg.toString().replace("->", SymbolPRM.DINGBAT_RIGHT_ARROW_10140);
	}

	public void onException(Throwable arg0, WebDriver arg1) {}

	public void beforeChangeValueOf(WebElement arg0, WebDriver arg1) {}

	public void beforeClickOn(WebElement arg0, WebDriver arg1) {}
	
	public void beforeNavigateTo(String arg0, WebDriver arg1) {}

	public void beforeScript(String arg0, WebDriver arg1) {}
	
	public void afterScript(String arg0, WebDriver arg1) {}
	
	public void afterChangeValueOf(WebElement arg0, WebDriver arg1) {
		String message = "FILLED FIELD: ";	
		if (arg0 != null){
			initArgWhenIsNull(arg0, message);
			writeReportWhenArgsComparedAreDiff(arg0, message);
		}		
		arg0Performed = arg0;
	}

	private void writeReportWhenArgsComparedAreDiff(WebElement arg0,
			String message) {
		if (!arg0Performed.toString().equals(arg0.toString())){
			BaseLogger.logInfoEvents(message + arg0.toString());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + replaceArrow(arg0) + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}

	private void initArgWhenIsNull(WebElement arg0, String message) {
		if (arg0Performed == null){ 
			BaseLogger.logInfoEvents(message + arg0.toString());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + replaceArrow(arg0) + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
			arg0Performed = arg0;
		}
	}

	public void afterClickOn(WebElement arg0, WebDriver arg1) {
		String message = "Clicked on: ";
		if (arg0 != null){
			BaseLogger.logInfoEvents(message + arg0.toString());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + replaceArrow(arg0) + " " + SymbolPRM.DINGBAT_UP_ONCLICK_9757 + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}

	public void afterFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		String message = "Element found: ";
		if (arg0 != null && arg1 != null){
			BaseLogger.logInfoEvents(message + arg1.toString() + " a partir do método" + arg0.toString());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + replaceArrow(arg1) + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}

	public void afterNavigateBack(WebDriver arg0) {
		String message = "Redirected back to: ";
		if (arg0.getCurrentUrl() != null){
			BaseLogger.logInfoEvents(message + arg0.getCurrentUrl());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + arg0.getCurrentUrl() + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}

	public void afterNavigateForward(WebDriver arg0) {
		String message = "Redirected to: ";
		if (arg0.getCurrentUrl() != null){
			BaseLogger.logInfoEvents(message + arg0.getCurrentUrl());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + arg0.getCurrentUrl() + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}

	public void afterNavigateTo(String arg0, WebDriver arg1) {
		String message = "Went to: ";
		if (arg0 != null){
			BaseLogger.logInfoEvents(message + arg0);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + arg0 + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
			ScreenshotEvidencesUtil.takeDetachedScreenshot();			
		}
	}

	public void beforeFindBy(By arg0, WebElement arg1, WebDriver arg2) {
		String message = "Element found: ";
		if (arg1 != null){
			BaseLogger.logInfoEvents(message + arg1.toString());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + replaceArrow(arg1) + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}

	public void beforeNavigateBack(WebDriver arg0) {
		String message = "Prepared redirection back to: ";
		if (arg0.getCurrentUrl() != null){
			BaseLogger.logInfoEvents(message + arg0.getCurrentUrl());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + arg0.getCurrentUrl() + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}

	public void beforeNavigateForward(WebDriver arg0) {
		String message = "Prepared redirection forward to: ";
		if (arg0.getCurrentUrl() != null){
			BaseLogger.logInfoEvents(message + arg0.getCurrentUrl());
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(message + BR_MARGIN_SPACE + arg0.getCurrentUrl() + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}
	}		
	
		@Pointcut("  call(* org.junit.Assert.assertEquals(..)) && args(actual, expected) ")
		public void assertEqualsStringPC(String actual, String expected) {
			String actualMessage = "Current value: ";
			String expectedMessage = "Expected value: ";
			BaseLogger.logInfoEvents("Found: " + actual);
			BaseLogger.logInfoEvents("Expectd: " + expected);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(actualMessage + actual, null, null, EVENT, true), pathHtmlFile);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(expectedMessage + expected, null, null, EVENT, true), pathHtmlFile);
		}
		
		@Pointcut("  call(* org.junit.Assert.assertEquals(..)) && args(actual, expected, message) ")
		public void assertEqualsStringWithMessagePC(String actual, String expected, String message) {
			String actualMessage = "Current value: ";
			String expectedMessage = "Expected value: ";
			BaseLogger.logInfoEvents("Found: " + actual);
			BaseLogger.logInfoEvents("Expectd: " + expected);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(actualMessage + actual + BR_MARGIN_SPACE + message + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
			ReportConfig.appendTag(fileHTML, ReportConfig.writeLiTag(expectedMessage + expected + BR_MARGIN_SPACE + message + SPAN_CLOSE, null, null, EVENT, true), pathHtmlFile);
		}

		@Override
		public void afterNavigateRefresh(WebDriver arg0) {}

		@Override
		public void beforeNavigateRefresh(WebDriver arg0) {}
		
}
