package br.com.upperselenium.base;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import com.gargoylesoftware.htmlunit.ElementNotFoundException;

import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.constant.PatternPRM;
import br.com.upperselenium.base.constant.TimePRM;
import br.com.upperselenium.base.util.AssertUtil;
import br.com.upperselenium.base.util.FindElementUtil;
import br.com.upperselenium.base.util.WaitElementUtil;

/**
 * Base Class used in the execution of the WebDriver for Pages initialization
 * mapped in the context of Flow and its corresponding Stages.
 * 
 * @author Hudson
 */
public abstract class BasePage {

	private static final String INDEX_NAME_MARK = "%k%";
	private static final String UNDEFINED_INDEX_MARK = "[%x%]";
	private static final String COLLUMN_INDEX_MARK = "[%j%]";
	private static final String LINE_INDEX_MARK = "[%i%]";
	private static final String ANOM_INDEX_MARK = "%%";
	private static WebDriver webDriver = WebDriverMaster.getWebDriver();
	
	public BasePage() {		
		super();		
	}

	@SuppressWarnings("static-access")
	public BasePage(WebDriver webDriver) {
		this.setWebDriver(webDriver);
	}
	
	public static void setWebDriver(WebDriver webDriver) {
		BasePage.webDriver = webDriver;
	}
	
	public static WebDriver getWebDriver() {
		return webDriver;
	}	
	
	public String getTitle() {
		return getWebDriver().getTitle();
	}	
	
	public static String getCurrentPage(){
		return getWebDriver().getCurrentUrl();		
	}
	
	public void getTimeoutDefault() {
		WaitElementUtil.getTimeoutWhenPageIsLoading(getWebDriver());
	}
	
	public void initPageElements() {
		PageFactory.initElements(getWebDriver(), this);		
	}
	
    // DEFAULT WAITINGS
    public static WebElement waitForPresenceOfElementLocated(By elementId) {
    	waitForPageToLoadUntil10s();
    	return WaitElementUtil.waitForPresenceOfElementLocated(WebDriverMaster.getWebDriver(), elementId, TimePRM._10_SECS);
    }

    public static WebElement waitForPresenceOfElementLocated(String xpathElement) {
    	waitForPageToLoadUntil10s();
    	return WaitElementUtil.waitForPresenceOfElementLocated(WebDriverMaster.getWebDriver(), By.xpath(xpathElement), TimePRM._10_SECS);
    }
    
    public static WebElement waitForElementToBeClickable(By elementId) {
    	waitForPageToLoadUntil10s();
    	return WaitElementUtil.waitForElementToBeClickable(WebDriverMaster.getWebDriver(), elementId, TimePRM._10_SECS);
    }
    
    public static WebElement waitForElementToBeClickable(String xpathElement) {
    	waitForPageToLoadUntil10s();
    	return WaitElementUtil.waitForElementToBeClickable(WebDriverMaster.getWebDriver(), By.xpath(xpathElement), TimePRM._10_SECS);
    }
    
    public static WebElement waitForWebElementToBeClickable(WebElement elementId) {
    	waitForPageToLoadUntil10s();
    	return WaitElementUtil.waitForElementToBeClickable(WebDriverMaster.getWebDriver(), elementId, TimePRM._10_SECS);
    }
    
    public static WebElement waitForWebElementToBeClickable(String xpathElement) {
    	waitForPageToLoadUntil10s();
    	return WaitElementUtil.waitForElementToBeClickable(WebDriverMaster.getWebDriver(), By.xpath(xpathElement), TimePRM._10_SECS);
    }

    public static void waitForATime(int timeout) {
    	WaitElementUtil.waitForATime(timeout);
    }
    
    public static void waitForAPresentAlert() {
    	WaitElementUtil.waitForAPresentAlert(WebDriverMaster.getWebDriver(), TimePRM._15_SECS);
    }

    public static void waitForPageToLoad() {
    	WaitElementUtil.waitForPageToLoad(WebDriverMaster.getWebDriver(), TimePRM._5_SECS);
    }

    public static void waitForPageToLoad(long timeWaitInSeconds) {
    	WaitElementUtil.waitForPageToLoad(WebDriverMaster.getWebDriver(), timeWaitInSeconds);
    }
    
    public static void waitForPageToLoadUntil10s() {
    	WaitElementUtil.waitForPageToLoad(WebDriverMaster.getWebDriver(), TimePRM._10_SECS);
    }
    
    // DEFAULT LOCATION OF ELEMENTS
    public static Boolean isDisplayedElement(By byElement) {
    	return FindElementUtil.isDisplayedElement(byElement);
    }
    
    public static Boolean isDisplayedElement(String xpathElement) {
    	return FindElementUtil.isDisplayedElement(By.xpath(xpathElement));
    }
    
    public static Boolean isEnabledElement(By byElement) {
    	return FindElementUtil.isEnabledElement(byElement);
    }
    
    public static Boolean isEnabledElement(String xpathElement) {
    	return FindElementUtil.isEnabledElement(By.xpath(xpathElement));
    }
    
    public static Boolean isSelectedElement(By byElement) {
    	return FindElementUtil.isSelectedElement(byElement);
    }
    
    public static Boolean isSelectedElement(String xpathElement) {
    	return FindElementUtil.isSelectedElement(By.xpath(xpathElement));
    }
    
	// DEFAULT ELEMENT WITH TEXT
   	public String getValueFromElement(String xpath){
		return getWebDriver().findElement(By.xpath(xpath)).getText();
	}

	public String getValueFromElementUsingAttributeValue(String xpath){
		return getWebDriver().findElement(By.xpath(xpath)).getAttribute("value");
	}
	
	public Integer getGridSizeFromWebElement(String xpathElement) {
		List<WebElement> listItems = getWebElements(xpathElement);	
		return listItems.size();
	}
	
	// WEB ELEMENT DEFAULT
	public static WebElement getWebElement(String xpathElement) {
		try {
			return getWebDriver().findElement(By.xpath(xpathElement));
		} catch (Exception enp) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_PRESENT), enp);
		}
	}
	
	// WEB ELEMENT LIST DEFAULT
	public static List<WebElement> getWebElements(String xpathElement) {
		return getWebDriver().findElements(By.xpath(xpathElement));
	}
	
    // ALERT ACCEPTATION
    public static void acceptAlert() {
    	FindElementUtil.acceptAlert();
    }

    public static void cancelAlert() {
    	FindElementUtil.cancelAlert();
    }
	
	// CREATE SELECT COMBOS
	public static Select getNewSelectCombo(WebElement comboFound) {
		Select comboSelected = new Select(comboFound);
		return comboSelected;
	}
	
	// ACTIONS
	public static Actions getAction() {
		Actions action = new Actions(getWebDriver());   
		return action;
	}
	
    public static void getActionToElement(WebElement element) {
    	getAction().moveToElement(element).build().perform();
    }
    
    public static void getActionToElement(String xpathElement) {
    	WebElement fieldFound = getWebElement(xpathElement);
    	getAction().moveToElement(fieldFound).build().perform();
    }  
    
    // ASSERTS
    public static void getAssertEquals(String valueExpected, String labelResults) {
    	AssertUtil.assertEquals(valueExpected, labelResults);
    }
    
    // GRID ITERATIONS
	/**
	 * [%i%] : Replaces the row indexes of the FOR loop into the brackets of xpaths tags marked, for example, in "/tr[%i%]".
	 * [%j%] : Replaces FOR loop column indexes in the brackets of xpaths tags marked, for example, in "/td[%j%]".
	 * [%x%] : Replaces tag indexes with no row or column definition, for example in "/fieldset[%x%]".
	 * %k% : Can be used when index is part of id name and needs to be iterated in FOR loop
	 * %% : When there is a tag in which its first iteration does not have an index, that is, the index exists between square brackets only when > 1, it is possible to use this tag.
	 *
	 * @param xpathElement
	 * @param index
	 * @return
	 */	
	public static String replaceIndex(String xpathElement, int index1) {
		try {			
			if(xpathElement.contains(ANOM_INDEX_MARK) && index1 == 1){
				String xpathReplaced = xpathElement.replaceAll("%", "");
				return xpathReplaced;
			}
			if(xpathElement.contains(ANOM_INDEX_MARK) && index1 > 1){
				String xpathReplaced = xpathElement.replaceAll(ANOM_INDEX_MARK, "["+ index1 +"]");
				return xpathReplaced;
			}
			if(xpathElement.contains(LINE_INDEX_MARK)){
				String xpathLineReplaced = xpathElement.replaceAll("\\[%i%\\]", "["+ index1 +"]");				
				return xpathLineReplaced;
			}
			if(xpathElement.contains(COLLUMN_INDEX_MARK)){
				String xpathCollumnReplaced = xpathElement.replaceAll("\\[%j%\\]", "["+ index1 +"]");				
				return xpathCollumnReplaced;
			}
			if(xpathElement.contains(UNDEFINED_INDEX_MARK)){
				String xpathMarkReplaced = xpathElement.replaceAll("\\[%x%\\]", "["+ index1 +"]");				
				return xpathMarkReplaced;
			}
			if(xpathElement.contains(INDEX_NAME_MARK)){
				String xpathIndexNameReplaced = xpathElement.replaceAll(INDEX_NAME_MARK, ""+ index1);				
				return xpathIndexNameReplaced;
			}
		} catch (ElementNotFoundException enfe){
			BaseLogger.logThrowables(MessagePRM.AsException.XPATH_ERROR, enfe);
		}
		return xpathElement;
	}
	
	public static String replaceMultipleIndexes(String xpathElement, int indexLine, int indexCollumn, int index) {
		try {			
			if(xpathElement.contains(ANOM_INDEX_MARK) && indexLine == 1 && indexCollumn == 1){
				String xpathReplacedAnom = xpathElement.replaceAll(ANOM_INDEX_MARK, "");
				String xpathReplacedLine = xpathReplacedAnom.replaceAll("\\[%i%\\]", "");
				String xpathReplacedCol = xpathReplacedLine.replaceAll("\\[%j%\\]", "");
				return xpathReplacedCol;
			}
			if(xpathElement.contains(ANOM_INDEX_MARK) && indexLine > 1 && indexCollumn > 1){
				String xpathMarkReplaced = xpathElement.replaceAll(ANOM_INDEX_MARK, "["+ index +"]");
				String xpathLineReplaced = xpathMarkReplaced.replaceAll("\\[%i%\\]", "["+ indexLine +"]");				
				String xpathCollumnReplaced = xpathLineReplaced.replaceAll("\\[%j%\\]", "["+ indexCollumn +"]");	
				return xpathCollumnReplaced;
			}
			if(xpathElement.contains(LINE_INDEX_MARK) && xpathElement.contains(COLLUMN_INDEX_MARK)){
				String xpathLineReplaced = xpathElement.replaceAll("\\[%i%\\]", "["+ indexLine +"]");				
				String xpathCollumnReplaced = xpathLineReplaced.replaceAll("\\[%j%\\]", "["+ indexCollumn +"]");				
				return xpathCollumnReplaced;
			}
			if(xpathElement.contains(UNDEFINED_INDEX_MARK) && xpathElement.contains(LINE_INDEX_MARK) && xpathElement.contains(COLLUMN_INDEX_MARK)){
				String xpathMarkReplaced = xpathElement.replaceAll("\\[%x%\\]", "["+ index +"]");
				String xpathLineReplaced = xpathMarkReplaced.replaceAll("\\[%i%\\]", "["+ indexLine +"]");				
				String xpathCollumnReplaced = xpathLineReplaced.replaceAll("\\[%j%\\]", "["+ indexCollumn +"]");	
				return xpathCollumnReplaced;
			}
			if(xpathElement.contains(INDEX_NAME_MARK)){
				String xpathIndexNameReplaced = xpathElement.replaceAll(INDEX_NAME_MARK, ""+ index);
				String xpathLineReplaced = xpathIndexNameReplaced.replaceAll("\\[%i%\\]", "["+ indexLine +"]");				
				String xpathCollumnReplaced = xpathLineReplaced.replaceAll("\\[%j%\\]", "["+ indexCollumn +"]");	
				return xpathCollumnReplaced;
			}
		} catch (ElementNotFoundException enfe){
			BaseLogger.logThrowables(MessagePRM.AsException.XPATH_ERROR, enfe);
		}
		return xpathElement;
	}
	
	// MONTH CHECKS
	public static String getCustomMonthFromPicker(String month) {
		try {
			switch (month) {
			case "01":
				return PatternPRM.Month.JAN;
			case "02":
				return PatternPRM.Month.FEB;
			case "03":
				return PatternPRM.Month.MAR;
			case "04":
				return PatternPRM.Month.APR;
			case "05":
				return PatternPRM.Month.MAY;
			case "06":
				return PatternPRM.Month.JUN;
			case "07":
				return PatternPRM.Month.JUL;
			case "08":
				return PatternPRM.Month.AUG;
			case "09":
				return PatternPRM.Month.SEP;
			case "10":
				return PatternPRM.Month.OCT;
			case "11":
				return PatternPRM.Month.NOV;
			case "12":
				return PatternPRM.Month.DEC;
			default:
				return PatternPRM.Month.JAN;
			}
		} catch (Exception e) {
			BaseLogger.logThrowables(MessagePRM.AsException.MONTH_NOT_FOUND, e);
		}
		return null;
	}
}