package br.com.upperselenium.base.logger;

import br.com.upperselenium.base.constant.CmdPRM;
import br.com.upperselenium.base.constant.LogTracePRM;

/**
 * Classe com padrões de Logs a serem amostrados pelo Console e pelo arquivo "testlog.log". 
 *  
 * @author Hudson
 */
public class BaseLogger implements TestLogger{	
		
	public static void logInfoFrameworkTitle() {
		logInfoSimpleText("     __    __  _____   _____    _____   ______        _______   _____   ");
		logInfoSimpleText("    |  |  |  ||   _ \\ |   _ \\  / ___ \\ |   _  \\   _  /  _____] / ___ \\  ");
		logInfoSimpleText("    |  |  |  ||  |_) )|  |_) )| |___) )|  |_)  ) (_) \\____  \\ | |___) ) ");
		logInfoSimpleText("    |  |__|  ||  ___/ |  ___/ |  ____/ |  _   /      _____)  )|  ____/  ");
		logInfoSimpleText("     \\______/ |__|    |__|     \\_____\\ |__|\\__\\      [______/  \\_____\\  ");
		logInfoBlankLine();
	}
	
	public static void logInfoWithContinuousLine(Object parameterMarkerObject) {
		testLog.info(LogTracePRM.HIGHLIGHT_HEADER_START + parameterMarkerObject + LogTracePRM.HIGHLIGHT_HEADER_END);
	}
	
	public static void logInfoFirstOrLastHeaderTitle(Object parameterTitleObject) {
		testLog.info(LogTracePRM.HIGHLIGHT_HEADER_START + parameterTitleObject + LogTracePRM.HIGHLIGHT_HEADER_END);
	}
	
	public static void logInfoHeader(Object parameterTitleObject) {
		testLog.info(LogTracePRM.BLANK_LINE);
		testLog.info(LogTracePRM.HIGHLIGHT_HEADER_START + parameterTitleObject + LogTracePRM.HIGHLIGHT_HEADER_END);
		testLog.info(LogTracePRM.BLANK_LINE);
	}
	
	public static void logInfoHeaderSubtitle(Object parameterSubTitleObject, Object parameterDescriptionObject) {
		testLog.info(LogTracePRM.HIGHLIGHT_TEXT + parameterSubTitleObject + ": " + parameterDescriptionObject);
	}

	public static void logInfoBlankLine() {
		testLog.info(LogTracePRM.BLANK_LINE);
	}

	public static void logInfoSimpleText(Object parameterTextObject) {
		testLog.info(LogTracePRM.BLANK_LINE + " " + parameterTextObject);
	}
	
	public static void logInfoTextHighlight(Object parameterTextObject) {
		testLog.info(LogTracePRM.HIGHLIGHT_TEXT + parameterTextObject);
	}

	public static void logInfoEvents(Object parameterTextObject) {
		testLog.info(LogTracePRM.EVENTS_TEXT + parameterTextObject);
	}

	public static void logErrorSimpleTextHighlight(Object parameterTextObject) {
		testLog.error(LogTracePRM.HIGHLIGHT_TEXT + parameterTextObject);
	}
	
	public static void logErrorTextHighlight(Object parameterMessageErrorObject) {
		System.err.print(CmdPRM.BACKSLASH_N + LogTracePRM.CONTINUOUS_LINE + CmdPRM.BACKSLASH_N);
		testLog.error(LogTracePRM.HIGHLIGHT_TEXT + parameterMessageErrorObject + CmdPRM.BACKSLASH_N);
	}
	
	public static void logThrowables(Object parameterMessageThrowableObject, Throwable e) {
		System.err.print(CmdPRM.BACKSLASH_N + LogTracePRM.CONTINUOUS_LINE + CmdPRM.BACKSLASH_N);
		testLog.error(LogTracePRM.HIGHLIGHT_TEXT + parameterMessageThrowableObject + CmdPRM.BACKSLASH_N, e);
		testLog.info(LogTracePRM.BLANK_LINE);
	}
		
	public static String logException(Object parameterMessageErrorObject) {
		System.err.print(CmdPRM.BACKSLASH_N + LogTracePRM.CONTINUOUS_LINE + CmdPRM.BACKSLASH_N);
		return LogTracePRM.HIGHLIGHT_TEXT + parameterMessageErrorObject + CmdPRM.BACKSLASH_N;
	}
}