package br.com.upperselenium.base.util;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.thoughtworks.selenium.webdriven.commands.WaitForPageToLoad;

import br.com.upperselenium.base.asset.WaitConfig;
import br.com.upperselenium.base.exception.WaitException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;

/**
 * Helper class for definition of wait (wait) on the element to be interacted with by WebDriver.
 * 
 * @author Hudson
 */
public class WaitElementUtil extends BaseLogger{
		
	private WaitElementUtil() {}
	
	/**
	 * Defines the implicit timeout of WebDriver when running the test.
	 * Time can be specified parameter.
	 * 
	 * @param webDriver
	 * @param timeWaitInSeconds
	 */
	public static final void setImplicitlyWait(WebDriver webDriver, long timeWaitInSeconds) {
		try {
			webDriver.manage().timeouts().implicitlyWait(timeWaitInSeconds, TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_IMPLICIT), e);
		}
	}

	/**
	 * Threads wait according to the time specified in the parameter.
	 * 
	 * @param timeWaitInSeconds
	 */
	public static final void waitForATime(long timeWaitInSeconds) {
		try {
			long timeInMillis = timeWaitInSeconds * 1000;
			Thread.sleep(timeInMillis);
		} catch (InterruptedException ie) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_FOR_A_TIME), ie);
		}
	}

	/**
	 * According to the timeout timeout specified by parameter, the
	 * WebDriver waits for the presence of any JavaScript alert on the screen.
	 * 
	 * @param webDriver
	 * @param timeWaitInSeconds
	 */
	public static final void waitForAPresentAlert(WebDriver webDriver, long timeWaitInSeconds) {
		try {
			new WebDriverWait(webDriver, timeWaitInSeconds).until(ExpectedConditions.alertIsPresent());
		} catch (Exception e) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_FOR_PRESENT_ALERT), e);
		}			
	}	

	/**
	 * If the element (mapped with By.by) is not yet in its clickable state, WebDriver waits for it
	 * according to the time specified by parameter.
	 * 
	 * @param webDriver
	 * @param by
	 * @param timeWaitInSeconds
	 * @return
	 */
	public static final WebElement waitForElementToBeClickable(WebDriver webDriver, By by, long timeWaitInSeconds) {
		try {
			return new WebDriverWait(webDriver, timeWaitInSeconds).until(ExpectedConditions.elementToBeClickable(by));
		} catch (Exception e) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_FOR_CLICKABLE_ELEMENT), e);
		}	
	}

	/**
	 * If the element (mapped with WebElement) is not yet in its clickable state, WebDriver waits for it
	 * according to the time specified by parameter.
	 * 
	 * @param webDriver
	 * @param webElement
	 * @param timeWaitInSeconds
	 * @return
	 */
	public static final WebElement waitForElementToBeClickable(WebDriver webDriver, WebElement webElement, long timeWaitInSeconds) {
		try {
			return new WebDriverWait(webDriver, timeWaitInSeconds).until(ExpectedConditions.elementToBeClickable(webElement));
		} catch (Exception e) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_FOR_CLICKABLE_ELEMENT), e);
		}	
	}

	/**
	 * If the element (mapped with By.by) is not yet present on the screen, WebDriver waits for it
	 * according to the time specified by parameter.
	 * 
	 * @param webDriver
	 * @param by
	 * @param timeWaitInSeconds
	 * @return
	 */
	public static final WebElement waitForPresenceOfElementLocated(WebDriver webDriver, By by, long timeWaitInSeconds) {
		try {
			return new WebDriverWait(webDriver, timeWaitInSeconds).until(ExpectedConditions.presenceOfElementLocated(by));
		} catch (Exception e) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_FOR_PRESENT_ELEMENT), e);
		}	
	}
	
	/**
	 * Wait for a page to load for a specified time, as specified by parameter.
	 * 
	 * @param webDriver
	 * @param timeWaitInSeconds
	 */
	public static final void waitForPageToLoad(WebDriver webDriver, long timeWaitInSeconds) {
		try {
			long timeOutInMillis = timeWaitInSeconds * 1000;
			new WaitForPageToLoad().apply(webDriver, new String[] { String.valueOf(timeOutInMillis) });
		} catch (Exception e) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_FOR_PRESENT_PAGE), e);
		}	
	}
	
	/**
	 * Wait for a page to load from the time defined in testconfig.properties.
	 * 
	 * @param webDriver
	 */
	public  static final void getTimeoutWhenPageIsLoading(WebDriver webDriver) {
		try {
			webDriver.manage().timeouts().pageLoadTimeout(WaitConfig.setTimeWaitingConfigDefault(), TimeUnit.SECONDS);
		} catch (Exception e) {
			throw new WaitException(logException(MessagePRM.AsException.WAIT_FOR_PRESENT_PAGE), e);
		}	
	}

	/**
	 * Returns TRUE or FALSE on element presence checking (mapped with By.by),
	 * according to the time specified by parameter.
	 * 
	 * @param webDriver
	 * @param by
	 * @param timeWaitInSeconds
	 * @return
	 */
	public static Boolean isElementPresent(WebDriver webDriver, By by, long timeWaitInSeconds) {
		try {
			waitForPresenceOfElementLocated(webDriver, by, timeWaitInSeconds);
			return Boolean.TRUE;
		} catch (WebDriverException e) {
			BaseLogger.logThrowables(MessagePRM.AsException.ELEMENT_NOT_PRESENT, e);
			return Boolean.FALSE;
		}
	}
		
}
		
