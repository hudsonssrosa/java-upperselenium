package br.com.upperselenium.base.util;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriverException;

import br.com.upperselenium.base.WebDriverMaster;
import br.com.upperselenium.base.constant.TimePRM;

/**
 * Helper class for validating manipulable elements.
 * 
 * @author Hudson
 */
public class FindElementUtil {
		
	private FindElementUtil() {}
	
	/**
	 * Defines whether the element passed by parameter is being displayed or not.
	 * 
	 * @param byElement
	 * @return
	 */
	public static Boolean isDisplayedElement(By byElement) {
		try {
			WebDriverMaster.getWebDriver().findElement(byElement).isDisplayed();
			return Boolean.TRUE;
		} catch (WebDriverException e) {
			return Boolean.FALSE;
		}
	}
	
	/**
	 * Defines whether the element is enabled for interaction or not.
	 * 
	 * @param byElement
	 * @return
	 */
	public static Boolean isEnabledElement(By byElement) {
		try {
			WebDriverMaster.getWebDriver().findElement(byElement).isEnabled();
			return Boolean.TRUE;
		} catch (WebDriverException e) {
			return Boolean.FALSE;
		}
	}

	/**
	 * Defines whether the element is selectable or not.
	 * 
	 * @param byElement
	 * @return
	 */
	public static Boolean isSelectedElement(By byElement) {
		try {
			WebDriverMaster.getWebDriver().findElement(byElement).isSelected();
			return Boolean.TRUE;
		} catch (WebDriverException e) {
			return Boolean.FALSE;
		}
	}

	public static void acceptAlert() {
		long waitForAlert = System.currentTimeMillis() + TimePRM._3_SECS;
		boolean boolFound = false;
		do {
			try {
				Alert alert = WebDriverMaster.getWebDriver().switchTo().alert();
				if (alert != null) {
					alert.accept();
					boolFound = true;
				}
			} catch (NoAlertPresentException enap) {}
		} while ((System.currentTimeMillis() < waitForAlert) && (!boolFound));
	}

	public static void cancelAlert() {
		long waitForAlert = System.currentTimeMillis() + TimePRM._3_SECS;
		boolean boolFound = false;
		do {
			try {
				Alert alert = WebDriverMaster.getWebDriver().switchTo().alert();
				if (alert != null) {
					alert.dismiss();
					boolFound = true;
				}
			} catch (NoAlertPresentException enap) {}
		} while ((System.currentTimeMillis() < waitForAlert) && (!boolFound));
	}
		
}
		
