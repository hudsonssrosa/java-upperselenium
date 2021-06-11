package br.com.upperselenium.base.util;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import br.com.upperselenium.base.BaseSuite;
import br.com.upperselenium.base.WebDriverMaster;
import br.com.upperselenium.base.asset.ReportConfig;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;
/**
 * Helper class for performing PrintScreen for a ".PNG" image file. 
 * 
 * @author Hudson
 */
public class ScreenshotEvidencesUtil extends BaseLogger{
	
	private static File fileHTML = BaseSuite.getFileHTML();
	private static String pathFile = BaseSuite.getPathAndNameForIndexHTML();

	/**
	 * Method that prints the browser screen at the time of testing.
	 * As a return, you get the output file to be created and stored in the directory
	 * specified by parameter as well as WebDriver.
	 * 
	 * @param webDriver
	 * @param outputDir
	 * @return
	 */
	public static File takeScreenShot(WebDriver webDriver, File outputDir, String fileName) {
		File screenshot = ((TakesScreenshot) webDriver).getScreenshotAs(OutputType.FILE);
		File outputFile = new File(outputDir, fileName + "_Scr" + screenshot.getName().substring(10));
		try {
			FileUtils.copyFile(screenshot, outputFile);
			logInfoTextHighlight(MessagePRM.SCREENSHOT_OBTAINED);
		} catch (IOException e) {
			BaseLogger.logThrowables(MessagePRM.AsException.IMAGE_FILE_NOT_CREATED, e);
			return null;
		}
		return outputFile;
	}
	
	public static void takeDetachedScreenshot() {
		File outputEvidenceDirectory = new File(ReportConfig.getEvidenceDirectory());
		String fileEvidenceName = "DetachedPage";		
		File takeScreenShot = ScreenshotEvidencesUtil.takeScreenShot(WebDriverMaster.getWebDriver(), outputEvidenceDirectory, fileEvidenceName);
		ReportConfig.appendTag(fileHTML, ReportConfig.writeImgTag(ReportConfig.getAncestorDirectory() 
				+ takeScreenShot.getPath(), null, null, "screenshotEventFiring", "App Page"), pathFile);
	}
	
}