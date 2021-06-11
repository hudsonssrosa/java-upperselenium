package br.com.upperselenium.base.sample.test.stage.helper;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

import br.com.upperselenium.base.BasePage;
import br.com.upperselenium.base.BaseReport;
import br.com.upperselenium.base.BaseStage;
import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.constant.TimePRM;
import br.com.upperselenium.base.util.ScreenshotEvidencesUtil;
import br.com.upperselenium.base.util.StringUtil;
import br.com.upperselenium.base.util.WaitElementUtil;

/**
 * Base Class used in the execution of the WebDriver for Pages initialization
 * mapped in the context of Flow and its corresponding Stages.
 * 
 * @author Hudson
 */
public abstract class BaseSampleHelperPage extends BasePage {
	
	public BaseSampleHelperPage() {		
		super();		
	}
	
	/**
	 * Sends information to a report about the value filled by WebDriver in an element on the screen.
	 * 
	 * @param textForEvidence
	 */
	public void reportElement(Object textForEvidence) {
		BaseReport.reportContentOfElementEvent(textForEvidence);
	}
	
	/**
	 * Sends information to a report with the title referring to the value filled by WebDriver in an element on the screen.
	 * 
	 * @param textForEvidence
	 */
	public void reportElementTitle(Object textForEvidence) {
		BaseReport.reportContentOfElementTitle(textForEvidence);
	}
    
	// USING KEYS
	public void useKey(String xpathElement, Keys key){
		waitForPageToLoadUntil10s();
		getWebDriver().findElement(By.xpath(xpathElement)).sendKeys(key);
	}
	
	// GET BREADCRUMBS TEXT (DIRECTLY MAPPED)
	public String getBreadcrumbsText(String value){
		waitForPageToLoadUntil10s();
		String xpathBreadcrumbLevel = "";
		String splitTerms[];
		if (StringUtil.isNotBlankOrNotNull(value)) {
			if (value.contains("/")) {
				splitTerms = value.split("/");
				int sizeSplited = splitTerms.length;
				for (int i = 1; i <= sizeSplited; i++) {
					xpathBreadcrumbLevel = "//*[@class='breadcrumb']/li[" + i + "]/a";
					WebElement labelBreadcrumb = getWebElement(xpathBreadcrumbLevel);
					if (splitTerms[i-1].equals(labelBreadcrumb.getText())) {
						String label = labelBreadcrumb.getText();
						Integer level = i;
						reportElementTitle("NÍVEL "+ level +" DO BREADCRUMB OBTIDO"); 
						getAssertEquals(splitTerms[i-1], label);
						return label;
					}
				}
			}
		}
		return value;
	}
	
	// GET BLOCK OVERLAY
	public void getBlockOverlay(){
		String xpathBlock = "//*[contains(@class, 'blockUI blockOverlay')]";
		if (isDisplayedElement(By.xpath(xpathBlock))){
			waitForPageToLoad(TimePRM._30_SECS);
			reportElementTitle("AGUARDOU BLOCK OVERLAY"); 
		}
	}
	
	// GET TOOLTIP TEXT
	public String getTooltip(String xpathElement, String value){
		waitForPageToLoadUntil10s();		
		String xpathElementContent = "//*[@class='popover-content']";
		WebElement iconFound = getWebElement(xpathElement);
		if (StringUtil.isNotBlankOrNotNull(value)){
			iconFound.click();
			WebElement messageFound = getWebElement(xpathElementContent);
			String label = messageFound.getText();
			getAssertEquals(value, label);
			iconFound.click();
			return label;
		}
		reportElement(value);
		return null;
	}	
		
	// GET ALERT MESSAGE DEFAULT
	public String getAlertMessage(String xpathElement){
		waitForPageToLoadUntil10s();   	
		String xpathElementError = "//*[contains(@class, 'alert alert-error')]";
		String xpathElementSuccess = "//*[contains(@class, 'alert alert-success')]";
		String xpathElementInfo = "//*[contains(@class, 'alert alert-info')]";
		if(!"".equalsIgnoreCase(getWebElement(xpathElementError).getText())){
			waitForPresenceOfElementLocated(xpathElementError);
			return getWebElement(xpathElementError).getText();   		
		}
		if(!"".equalsIgnoreCase(getWebElement(xpathElementSuccess).getText())){
			waitForPresenceOfElementLocated(xpathElementSuccess);
			return getWebElement(xpathElementSuccess).getText();   		
		}
		if(!"".equalsIgnoreCase(getWebElement(xpathElementInfo).getText())){
			waitForPresenceOfElementLocated(xpathElementInfo);
			return getWebElement(xpathElementInfo).getText();   		
		}
		return getWebElement(xpathElement).getText();
	}
	
	// GET TOAST MESSAGE DEFAULT
	public String getToastMessage(String xpathElement){
		waitForPageToLoadUntil10s(); 	
		if(!"".equalsIgnoreCase(getWebElement(xpathElement).getText())){
			waitForPresenceOfElementLocated(xpathElement);
			return getWebElement(xpathElement).getText();   		
		}
		return getWebElement(xpathElement).getText();
	}

	// GET CUSTOM VALIDATION MESSAGE (DIRECTLY MAPPED)
	public void validateEqualsToValues(String dpoExpectedMessage, String pagePresentMessage){
		waitForPageToLoad();
		getAssertEquals(dpoExpectedMessage, pagePresentMessage);
	}
	
	// GET VALIDATION MESSAGE OPERATION DEFAULT (DIRECTLY MAPPED)
	public void validateMessageDefault(String xpath, String dpoExpectedMessage){
		waitForPageToLoad();
		String xpathStatusAlert = xpath;		
		String pagePresentMessage = getWebElement(xpathStatusAlert).getText();
		getAssertEquals(dpoExpectedMessage, pagePresentMessage);
	}

	// ##############################
	// ### DEFAULT FIELD ELEMENTS ###

	// CLICK BUTTON WITH ACTION PERFORM
	public void clickActionOnElement(String xpathElement){
		waitForPageToLoad();
		waitForPresenceOfElementLocated(xpathElement);
		WebElement fieldFound = getWebElement(xpathElement);
		try {
			if(isDisplayedElement(xpathElement)){
				waitForATime(TimePRM._2_SECS);
				getActionToElement(fieldFound);
				getWebDriver().findElement(By.xpath(xpathElement)).click();
				getWebDriver().findElement(By.xpath(xpathElement)).click();
				acceptAlert();
				BaseStage.calcPageLoadPerformanceDegradation();
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			ScreenshotEvidencesUtil.takeDetachedScreenshot();
		}
	}
	
	// CLICK BUTTON
	public void clickOnElement(String xpathElement){
		waitForPageToLoad();
		waitForPresenceOfElementLocated(xpathElement);
		WebElement fieldFound = getWebElement(xpathElement);
		try {
			if(isDisplayedElement(xpathElement)){
				fieldFound.click();
				acceptAlert();
				BaseStage.calcPageLoadPerformanceDegradation();
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			ScreenshotEvidencesUtil.takeDetachedScreenshot();
		}
	}

	public void clickOnElementByCondition(String xpathElement, String value){
		if (StringUtil.isNotBlankOrNotNull(value)) {
			waitForPageToLoad();
			waitForPresenceOfElementLocated(xpathElement);
			WebElement fieldFound = getWebElement(xpathElement);
			try {
				if(isDisplayedElement(xpathElement)){
					fieldFound.click();
					acceptAlert();
					BaseStage.calcPageLoadPerformanceDegradation();
				}
			} catch (Exception t) {
				cancelAlert();
				throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
			} finally {
				ScreenshotEvidencesUtil.takeDetachedScreenshot();
			}
		}
	}

	// CLICK BREADCRUMBS (DIRECTLY MAPPED)
	public void clickUpToBreadcrumbs(String value){
		waitForPageToLoadUntil10s();
		String xpathBreadcrumbLevel = "";
		String splitTerms[];
		try {
			if (StringUtil.isNotBlankOrNotNull(value)) {
				splitTerms = value.split(",");
				String level = splitTerms[0].trim();
				String labelLink = splitTerms[1].trim();
				xpathBreadcrumbLevel = "//*[@class='breadcrumb']/li[" + level + "]/a[contains(text(),'"+ labelLink +"')]";
				waitForPresenceOfElementLocated(xpathBreadcrumbLevel);
				String element = getValueFromElement(xpathBreadcrumbLevel);
				clickOnElement(xpathBreadcrumbLevel);
				reportElementTitle("CLICOU NO NÍVEL "+ level + " DO BREADCRUMB"); 
				getAssertEquals(labelLink, element);
			} 
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} 
	}
	
	// GET WELCOME LABEL TEXT (DIRECTLY MAPPED)
	public String getWelcomeTitle(String value){
		waitForPageToLoadUntil10s();
		if(StringUtil.isNotBlankOrNotNull(value)){
			String xpathWelcome = "//*[@class='well']/h2[contains(text(),'"+ value +"')]";
			reportElementTitle("ACESSOU PAINEL DE \"WELCOME\"");
			return getLabel(xpathWelcome, value);
		}
		return null;
	}	
	
	// GET LABEL TEXT
	public String getLabel(String xpathElement, String value){
		waitForPageToLoadUntil10s();
		waitForPresenceOfElementLocated(xpathElement);
		reportElementTitle("VALIDOU LABEL \"" + value.toUpperCase() + "\"");
		WebElement labelFound = getWebElement(xpathElement);
		acceptAlert();
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				if(value.equals(labelFound.getText())){
					String label = labelFound.getText();
					getAssertEquals(value, label);
					return label;
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
		return null;
	}
	
	// TYPE CHECK BY LABEL NAME
	public void typeCheckOptionByLabel(String xpathElement, String optionValue){
		waitForPageToLoadUntil10s();
		try {
			if (StringUtil.isNotBlankOrNotNull(optionValue)){
				waitForPresenceOfElementLocated(xpathElement);
				WebElement fieldCheck = getWebElement("//label[contains(text(), '"+ optionValue +"')]"); 
				fieldCheck.click();
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(optionValue);
		}
	}
	
    // TYPE TEXT
	public void typeText(WebElement field, String value){
		waitForPageToLoadUntil10s();
		acceptAlert();
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				field.clear();
				field.sendKeys(value);
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}
	
	public void typeText(String xpathElement, String value){		
		waitForPresenceOfElementLocated(xpathElement);
		waitForPageToLoadUntil10s();
		WebElement inputFound = getWebElement(xpathElement);
		acceptAlert();
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				inputFound.clear();
				inputFound.sendKeys(value);
				cancelAlert();
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}	

	// TYPE TEXT WITH AUTOCOMPLETE COMPONENT
	public void typeTextAutoCompleteSelect(String xpathElement, String value){
		waitForPresenceOfElementLocated(xpathElement);
		waitForPageToLoadUntil10s();
		WebElement inputFound = getWebElement(xpathElement);
		acceptAlert();
		if (StringUtil.isNotBlankOrNotNull(value)){
			inputFound.clear();
			inputFound.sendKeys(value);
			getBlockOverlay();
			String xpathAutocomplete = "//*[@class='autocomplete-suggestions']";
			waitForPageToLoadUntil10s();
			waitForATime(TimePRM._3_SECS);
			waitForPresenceOfElementLocated(xpathAutocomplete);
			waitForPageToLoadUntil10s();
			if(getWebElement(xpathAutocomplete) != null){
				try {
					WebElement autocompleteFound = getWebElement(xpathAutocomplete + "/div[@class='autocomplete-suggestion']");
					waitForPresenceOfElementLocated(xpathAutocomplete + "/div[@class='autocomplete-suggestion']");
					autocompleteFound.click();
				} catch (Exception e){
					inputFound.sendKeys(Keys.ARROW_DOWN);
					inputFound.sendKeys(Keys.SPACE);
				}
			}
		}
		reportElement(value);
	}	
	
	// TYPE TEXT WITH AUTOCOMPLETE COMPONENT
	public void typeTextAutoCompleteWithPaste(String xpathElement, String value){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		StringSelection str = new StringSelection(value);
		clipboard.setContents(str, null);
		waitForPresenceOfElementLocated(xpathElement);
		waitForPageToLoadUntil10s();
		WebElement inputFound = getWebElement(xpathElement);
		acceptAlert();	
		if (StringUtil.isNotBlankOrNotNull(value)){
			inputFound.clear();
			inputFound.sendKeys(Keys.chord(Keys.LEFT_CONTROL, "v"),"");
			inputFound.sendKeys(Keys.TAB);
			waitForElementToBeClickable(xpathElement);
		}
		reportElement(value);
	}		
	
	// TYPE TEXT WITH AUTOCOMPLETE COMPONENT
	public void typeTextAutoCompleteFilter(String xpathElement, String value){
		waitForPresenceOfElementLocated(xpathElement);
		waitForPageToLoadUntil10s();
		WebElement inputFound = getWebElement(xpathElement);
		acceptAlert();
		if (StringUtil.isNotBlankOrNotNull(value)){
			inputFound.clear();
			inputFound.sendKeys(value);
		}
		reportElement(value);
	}	

	public void typeTextMultipleAutocomplete(String xpathElement, String value) {
		waitForPresenceOfElementLocated(xpathElement);
		WebElement inputFound = getWebElement(xpathElement);
		acceptAlert();
		try {
			String splitTerms[];
			inputFound.sendKeys(Keys.BACK_SPACE);
			inputFound.sendKeys(Keys.BACK_SPACE);
			if (value.contains(",")) {
				splitTerms = value.split(",");
				int sizeSplited = splitTerms.length;
				for (int i = 0; i < sizeSplited; i++) {
					if (StringUtil.isNotBlankOrNotNull(value)) {
						waitForPageToLoadUntil10s();
						inputFound.click();
						waitForPageToLoadUntil10s();
						inputFound.sendKeys(splitTerms[i].toString().trim());
						inputFound.sendKeys(Keys.ENTER);
						waitForPageToLoadUntil10s();
					}
					reportElement(value);
				}
			} else if (StringUtil.isNotBlankOrNotNull(value)) {
				waitForPageToLoadUntil10s();
				inputFound.click();
				waitForPageToLoadUntil10s();
				inputFound.sendKeys(value);
				inputFound.sendKeys(Keys.ENTER);
				waitForPageToLoadUntil10s();
				reportElement(value);
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		}
	}
	
	// TYPE SELECT COMBO
	public void typeSelectComboOption(String xpathElement, String optionOrPropValue){
		waitForPresenceOfElementLocated(xpathElement);
		WebElement comboFound = getWebElement(xpathElement);
		acceptAlert();
		try {
			if (StringUtil.isNotBlankOrNotNull(optionOrPropValue)){
				getNewSelectCombo(comboFound).selectByVisibleText(optionOrPropValue);
			}
		} catch (Exception t) {
			comboFound.sendKeys(Keys.TAB);
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(optionOrPropValue);
		}
	}
	
	public void typeSelectComboOption(String xpathElement, int optionIndex){
		waitForPresenceOfElementLocated(xpathElement);
		WebElement comboFound = getWebElement(xpathElement);
		acceptAlert();
		try {
			if (optionIndex >= 0){
				getNewSelectCombo(comboFound).selectByIndex(optionIndex);
			}
		} catch (Exception t) {
			comboFound.sendKeys(Keys.TAB);
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(""+optionIndex);
		}
	}
	
	// TYPE RADIO 
	public void typeRadioListOption(String xpathElement, String value){
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				waitForPresenceOfElementLocated(xpathElement);
				List<WebElement> listRadio = getWebElements(xpathElement);
				acceptAlert();
				for (WebElement element : listRadio){
					waitForWebElementToBeClickable(element);
					element.click();
					break;
				}
			}				
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}

	// TYPE CHECK
	public void typeCheckOption(String xpathElement, String value) {
		acceptAlert();
		try {
			if (StringUtil.isNotBlankOrNotNull(value)) {
				clickElementInstanced(xpathElement);
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}
	
	// TYPE GROUP CHECK
	public void typeGroupCheckOption(String xpathElement, String value, int index){		
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				String newLineXpath = replaceIndex(xpathElement, index);		
				waitForPresenceOfElementLocated(newLineXpath);
				WebElement fieldCheck = getWebElement(newLineXpath);
				if(!fieldCheck.isSelected()){
					fieldCheck.click();
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}
	
	// TYPE GROUP CHECKWITH MULTIPLE INDEXES
	public void typeGroupCheckOption(String xpathElement, String value, int indexLine, int indexCollumn, int index){		
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);		
				waitForPresenceOfElementLocated(newLineXpath);
				WebElement fieldCheck = getWebElement(newLineXpath);
				if(!fieldCheck.isSelected()){
					fieldCheck.click();
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}
			
		
	// #############################
	// ### GRID (TABLE) ELEMENTS ###
	
	// CLICK GRID BUTTON
	public void clickGridButtonOnElement(String xpathElement, String value, int index){
		waitForPageToLoad();
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				String newLineXpath = replaceIndex(xpathElement, index);
				WebElement buttonOrCheck = getWebElement(newLineXpath);
				if(!buttonOrCheck.isSelected() || StringUtil.isBlankOrNull(value)){
					buttonOrCheck.click();
					acceptAlert();
					BaseStage.calcPageLoadPerformanceDegradation();
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			ScreenshotEvidencesUtil.takeDetachedScreenshot();
		}
	}

	// CLICK GRID BUTTON WITH MULTIPLE INDEXES
	public void clickGridButtonOnElement(String xpathElement, String value, int indexLine, int indexCollumn, int index){
		waitForPageToLoad();
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);	
				WebElement buttonOrCheck = getWebElement(newLineXpath);
				if(!buttonOrCheck.isSelected() || StringUtil.isBlankOrNull(value)){
					buttonOrCheck.click();
					acceptAlert();
					BaseStage.calcPageLoadPerformanceDegradation();
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			ScreenshotEvidencesUtil.takeDetachedScreenshot();
		}
	}
		
	// GET GRID TEXT
	public String getGridLabel(String xpathElement, String value, int index){
		waitForPageToLoadUntil10s();
		reportElementTitle("VALIDOU GRID LABEL \"" + value.toUpperCase() + "\"");
		String newLineXpath = replaceIndex(xpathElement, index);		
		WebElement labelFound = getWebElement(newLineXpath);
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				if(value.equals(labelFound.getText())){
					String label = labelFound.getText();
					getAssertEquals(value, label);
					return label;
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
		return null;
	}
	
	// GET GRID TEXT WITH MULTIPLE INDEXES
	public String getGridLabel(String xpathElement, String value, int indexLine, int indexCollumn, int index){
		waitForPageToLoadUntil10s();
		reportElementTitle("VALIDOU GRID LABEL \"" + value.toUpperCase() + "\"");
		String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);		
		WebElement labelFound = getWebElement(newLineXpath);
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				if(value.equals(labelFound.getText())){
					String label = labelFound.getText();
					getAssertEquals(value, label);
					return label;
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
		return null;
	}
	
	// GET GRID TEXT IN FIRST LINE
	public String getGridFirstLineLabel(String xpathElement) {
		waitForPageToLoadUntil10s();
		WebElement valueFound = getWebElement(xpathElement);
		try {
			valueFound.getText();
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(valueFound.getText());
		}
		return valueFound.getText();
	}
	
	// TYPE GRID TEXT
	public void typeGridText(String xpathElement, String value, int index){
		waitForPageToLoadUntil10s();
		String newLineXpath = replaceIndex(xpathElement, index);		
		WebElement inputFound = getWebElement(newLineXpath);
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				inputFound.clear();
				inputFound.sendKeys(value);
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}

	// TYPE GRID TEXT WITH MULTIPLE INDEXES
	public void typeGridText(String xpathElement, String value, int indexLine, int indexCollumn, int index){
		waitForPageToLoadUntil10s();
		String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);		
		WebElement inputFound = getWebElement(newLineXpath);
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				inputFound.clear();
				inputFound.sendKeys(value);
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}
	
	// TYPE TEXT WITH AUTOCOMPLETE COMPONENT
	public void typeGridTextAutoCompleteSelect(String xpathElement, String value, int index){
		waitForPresenceOfElementLocated(xpathElement);
		waitForPageToLoadUntil10s();
		String newLineXpath = replaceIndex(xpathElement, index);		
		WebElement inputFound = getWebElement(newLineXpath);
		if (StringUtil.isNotBlankOrNotNull(value)){
			inputFound.clear();
			inputFound.sendKeys(value);
			getBlockOverlay();
			String xpathAutocomplete = "//*[@class='autocomplete-suggestions']";
			waitForATime(TimePRM._1_SEC);
			waitForPresenceOfElementLocated(xpathAutocomplete);
			if(getWebElement(xpathAutocomplete) != null){
				WebElement autocompleteFound = getWebElement(xpathAutocomplete + "/div[@class='autocomplete-suggestion']");
				waitForPresenceOfElementLocated(xpathAutocomplete + "/div[@class='autocomplete-suggestion']");
				autocompleteFound.click();
				try {
					autocompleteFound.click();
				} catch (Exception e){
					inputFound.sendKeys(Keys.ARROW_DOWN);
					inputFound.sendKeys(Keys.SPACE);
				}
			}
		}
		reportElement(value);
	}	
	
	// TYPE TEXT WITH AUTOCOMPLETE COMPONENT WITH MULTIPLE INDEXES	
	public void typeGridTextAutoCompleteSelect(String xpathElement, String value, int indexLine, int indexCollumn, int index){
		waitForPresenceOfElementLocated(xpathElement);
		waitForPageToLoadUntil10s();
		String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);		
		WebElement inputFound = getWebElement(newLineXpath);
		if (StringUtil.isNotBlankOrNotNull(value)){
			inputFound.clear();
			inputFound.sendKeys(value);
			getBlockOverlay();
			String xpathAutocomplete = "//*[@class='autocomplete-suggestions']";
			waitForATime(TimePRM._1_SEC);
			waitForPresenceOfElementLocated(xpathAutocomplete);
			if(getWebElement(xpathAutocomplete) != null){
				WebElement autocompleteFound = getWebElement(xpathAutocomplete + "/div[@class='autocomplete-suggestion']");
				waitForPresenceOfElementLocated(xpathAutocomplete + "/div[@class='autocomplete-suggestion']");
				autocompleteFound.click();
				try {
					autocompleteFound.click();
				} catch (Exception e){
					inputFound.sendKeys(Keys.ARROW_DOWN);
					inputFound.sendKeys(Keys.SPACE);
				}
			}
		}
		reportElement(value);
	}			
			
	// TYPE GRID SELECT COMBO
	public void typeGridSelectComboOption(String xpathElement, String value, int index) {
		if (StringUtil.isNotBlankOrNotNull(value)) {
			String newLineXpath = replaceIndex(xpathElement, index);
			waitForPresenceOfElementLocated(newLineXpath);
			WebElement comboFound = getWebElement(newLineXpath);
			try {
				getNewSelectCombo(comboFound).selectByVisibleText(value);
			} catch (Exception e) {
				comboFound.sendKeys(Keys.TAB);
			}
		}
		reportElement(value);
	}
	
	// TYPE GRID SELECT COMBO WITH MULTIPLE INDEXES
	public void typeGridSelectComboOption(String xpathElement, String value, int indexLine, int indexCollumn, int index) {
		if (StringUtil.isNotBlankOrNotNull(value)) {
			String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);
			waitForPresenceOfElementLocated(newLineXpath);
			WebElement comboFound = getWebElement(newLineXpath);
			try {
				getNewSelectCombo(comboFound).selectByVisibleText(value);
			} catch (Exception e) {
				comboFound.sendKeys(Keys.TAB);
			}
		}
		reportElement(value);
	}
	
	// TYPE GRID RADIO DEFAULT
	public void typeGridRadioListOption(String xpathElement, String value,int index){
		if (StringUtil.isNotBlankOrNotNull(value)){
			String newLineXpath = replaceIndex(xpathElement, index);		
			waitForPresenceOfElementLocated(newLineXpath);
			List<WebElement> listRadio = getWebElements(newLineXpath);		
			for (WebElement element : listRadio){
				waitForWebElementToBeClickable(element);
				element.click();
				break;
			}
		}				
		reportElement(value);
	}	

	// TYPE GRID RADIO DEFAULT WITH MULTIPLE INDEXES
	public void typeGridRadioListOption(String xpathElement, String value, int indexLine, int indexCollumn, int index){
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);;		
				waitForPresenceOfElementLocated(newLineXpath);
				List<WebElement> listRadio = getWebElements(newLineXpath);		
				for (WebElement element : listRadio){
					waitForWebElementToBeClickable(element);
					element.click();
					break;
				}
			}				
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}	
	
	// TYPE GRID CHECK
	public void typeGridCheckOption(String xpathElement, String value, int index){		
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				String newLineXpath = replaceIndex(xpathElement, index);		
				waitForPresenceOfElementLocated(newLineXpath);
				WebElement fieldCheck = getWebElement(newLineXpath);
				if(!fieldCheck.isSelected()){
					fieldCheck.click();
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}
	
	// TYPE GRID CHECK WITH MULTIPLE INDEXES
	public void typeGridCheckOption(String xpathElement, String value, int indexLine, int indexCollumn, int index){		
		try {
			if (StringUtil.isNotBlankOrNotNull(value)){
				String newLineXpath = replaceMultipleIndexes(xpathElement, indexLine, indexCollumn, index);			
				waitForPresenceOfElementLocated(newLineXpath);
				WebElement fieldCheck = getWebElement(newLineXpath);
				if(!fieldCheck.isSelected()){
					fieldCheck.click();
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}
	
	// GET GRID FOR CHECK IN FIRST LINE
	public void typeGridCheckFirstFilteredItem(String xpathElement, String value){
		WaitElementUtil.waitForATime(TimePRM._5_SECS);
		waitForPresenceOfElementLocated(By.xpath(xpathElement));
		WebElement fieldCheck = getWebElement(xpathElement);
		try {
			if (getWebElement(".//tr//td[contains(text(), '"+ value +"')]").getText().contains(value)){
				if(!fieldCheck.isSelected()){
					fieldCheck.click();
				}
			}
		} catch (Exception t) {
			cancelAlert();
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.ELEMENT_NOT_INTERABLE), t);
		} finally {
			reportElement(value);
		}
	}

	// ##########################################
	// ### TYPE DATE PICKER DEFAULT (DYNAMIC) ###
	public void typeDatePickerDefault(String xpathDateField, String value){
		waitForPageToLoadUntil10s();
		String snippetDatePicker = "contains(@class, 'datepicker datepicker-dropdown dropdown-menu')"; //div[4]
		String snippetDatetimePicker = "contains(@class, 'datetimepicker datetimepicker-dropdown-bottom-right dropdown-menu')"; //div[4]
		String snippetDatePickerYears = "contains(@class, 'datepicker-years')"; //div[3]
		String snippetDatetimePickerYears = "contains(@class, 'datetimepicker-years')"; //div[3]
		String snippetDatePickerMonths = "contains(@class, 'datepicker-months')"; //div[2]
		String snippetDatetimePickerMonths = "contains(@class, 'datetimepicker-months')"; //div[2]
		String snippetDatePickerDays = "contains(@class, 'datepicker-days')"; //div[1]		
		String snippetDatetimePickerDays = "contains(@class, 'datetimepicker-days')"; //div[1]		
		clickElementInstanced(xpathDateField);				
		waitForPageToLoad();
		if(StringUtil.isNotBlankOrNotNull(value) 
				&& value.contains("/") 
				&& !value.contains(":")){
			String[] splitedDate = value.split("/");			
			String day = splitedDate[0];
			String month = splitedDate[1];
			String year = splitedDate[2];
			
			if(month == null && year == null){
				String xpathDay = ".//td[@class='day' and contains(text(), '"+day+"')]";			
				clickElementInstanced(xpathDay);
			}
			if(year == null){
				String xpathSwitch = ".//th[@class='switch']";			
				String xpathMonth = ".//td/span[@class='month' and contains(text(), '"+getCustomMonthFromPicker(month)+"']";
				String xpathDay = ".//td[@class='day' and contains(text(), '"+day+"')]";			
				clickElementInstanced(xpathSwitch);				
				clickElementInstanced(xpathMonth);
				clickElementInstanced(xpathDay);
			} else {
				String xpathSwitch = ".//div["+snippetDatePicker+"]/div["+snippetDatePickerDays+"]/table/thead/tr[1]/th[2]";
				String xpathReturnToDefault = ".//div["+snippetDatePicker+"]/div["+snippetDatePickerMonths+"]/table/thead/tr/th[2]";
				String xpathYear = ".//div["+snippetDatePicker+"]/div["+snippetDatePickerYears+"]/table/tbody/tr/td/span[contains(text(), '"+year.trim()+"')]";
				String xpathMonth = "/html/body/div["+snippetDatePicker+"]/div["+snippetDatePickerMonths+"]/table/tbody/tr/td/span[contains(text(), '"+getCustomMonthFromPicker(month)+"')]";
				String xpathDaySnippet = "/html/body/div["+snippetDatePicker+"]/div["+snippetDatePickerDays+"]/table/tbody/tr[";
				clickElementInstancedTwice(xpathSwitch, xpathReturnToDefault);
				clickElementInstanced(xpathYear);
				clickElementInstanced(xpathMonth);				
				getCustomDayFromPicker(day, xpathDaySnippet);
			}
		}			
		if(StringUtil.isNotBlankOrNotNull(value) 
				&& value.contains("/") 
				&& value.contains(":")){
			String[] splitedDate = value.split("/");			
			String day = splitedDate[0];
			String month = splitedDate[1];
			String yearToSplit = splitedDate[2];
			String[] yearSplited = yearToSplit.split(" ");
			String year = yearSplited[0];
			String[] splitedYearByHour = value.split(" ");			
			String time = splitedYearByHour[1];
			String[] splitedHourAndMinute = time.split(":"); 
			String hourString = splitedHourAndMinute[0];
			int hourInt = Integer.parseInt(hourString);			
			String minute = splitedHourAndMinute[1];			
			
			if(month == null && year == null){
				String xpathDay = ".//td[@class='day' and contains(text(), '"+day+"')]";			
				clickElementInstanced(xpathDay);
			}
			if(year == null){
				String xpathSwitch = ".//th[@class='switch']";			
				String xpathMonth = ".//td/span[@class='month' and contains(text(), '"+getCustomMonthFromPicker(month)+"']";
				String xpathDay = ".//td[@class='day' and contains(text(), '"+day+"')]";			
				clickElementInstanced(xpathSwitch);				
				clickElementInstanced(xpathMonth);
				clickElementInstanced(xpathDay);
			} else {
				String xpathSwitch = ".//div["+snippetDatetimePicker+"]/div["+snippetDatetimePickerDays+"]/table/thead/tr[1]/th[2]";		
				String xpathReturnToDefault = ".//div["+snippetDatetimePicker+"]/div["+snippetDatetimePickerMonths+"]/table/thead/tr/th[2]";		
				String xpathYear = ".//div["+snippetDatetimePicker+"]/div["+snippetDatetimePickerYears+"]/table/tbody/tr/td/span[contains(text(), '"+year.trim()+"')]";
				String xpathMonth = "/html/body/div["+snippetDatetimePicker+"]/div["+snippetDatetimePickerMonths+"]/table/tbody/tr/td/span[contains(text(), '"+getCustomMonthFromPicker(month)+"')]";
				String xpathDaySnippet = "/html/body/div["+snippetDatetimePicker+"]/div["+snippetDatetimePickerDays+"]/table/tbody/tr[";
				clickElementInstancedTwice(xpathSwitch, xpathReturnToDefault);
				clickElementInstanced(xpathYear);
				clickElementInstanced(xpathMonth);				
				getCustomDayFromPicker(day, xpathDaySnippet);
				getCustomFullHourFromPicker(hourInt, snippetDatetimePicker, snippetDatetimePickerMonths, snippetDatetimePickerYears, snippetDatetimePickerDays);
				getCustomTimeFromPicker(hourInt, minute, snippetDatetimePicker, snippetDatetimePickerMonths, snippetDatetimePickerYears, snippetDatetimePickerDays);
			}
		}			
		if (StringUtil.isNotBlankOrNotNull(value)
				&& !"atual".equalsIgnoreCase(value)
				&& !value.contains("/")
				&& !value.contains(":")){			
			getOnlyCustomDayFromPicker(value);
		}
		if (StringUtil.isNotBlankOrNotNull(value) 
				&& "atual".equalsIgnoreCase(value)
				&& !value.contains("/")
				&& !value.contains(":")){			
			getOnlyActualDayFromPicker();			
		}
		reportElement(value);
	}
		
	private void getCustomFullHourFromPicker(int hourInt, String snippetDatepicker, 
			String snippetDatepickerMonths, String snippetDatepickerYears, String snippetDatepickerDays) {
		int hoursQuantity = 24;
		String hourExpected = "" + hourInt + ":00";
		String hourPosition = "";
		String xpathHour = "";
		String snippetDatetimePickerHours = "contains(@class,'datetimepicker-hours')";
		WebElement element = null;
		boolean isElementFound = false;

		for(int i=1; i<=hoursQuantity; i++){
			xpathHour = "/html/body/div["+snippetDatepicker+"]/div["+snippetDatetimePickerHours+"]/table/tbody/tr/td/span[contains(@class, 'hour') and contains(text(), '"+hourInt+":00"+"')]";
			element = getWebElement(xpathHour);
			hourPosition = element.getText();
			if(hourExpected.equalsIgnoreCase(hourPosition)){
				isElementFound = hasPresentElement(xpathHour);
				break;
			}
			if(isElementFound){
				break;
			}
		}
		if(element != null){
			element.click();
		}						
	}
	
	private void getCustomTimeFromPicker(int hourInt, String minute, String snippetDatepicker, 
			String snippetDatepickerMonths, String snippetDatepickerYears, String snippetDatepickerDays) {
		int minutesInstantInHourQuantity = 12;
		String timeExpected = hourInt+":"+minute;
		String timePosition = "";
		String xpathCustomTime = "";
		String snippetDatetimePickerMinutes = "contains(@class,'datetimepicker-minutes')";
		WebElement element = null;
		boolean isElementFound = false;
		
		for(int i=1; i<=minutesInstantInHourQuantity; i++){
			xpathCustomTime = "/html/body/div["+snippetDatepicker+"]/div["+snippetDatetimePickerMinutes+"]/table/tbody/tr/td/span[contains(@class, 'minute') and contains(text(), '"+timeExpected+"')]";
			element = getWebElement(xpathCustomTime);
			timePosition = element.getText();
			if(timeExpected.equalsIgnoreCase(timePosition)){
				isElementFound = hasPresentElement(xpathCustomTime);
				break;
			}
			if(isElementFound){
				break;
			}
		}
		if(element != null){
			element.click();
		}						
	}
	
	private void getCustomDayFromPicker(String day, String xpathDaySnippet) {
		int trLine = 6;
		int tdCol = 7;
		int dayExpected = Integer.parseInt(day);
		int dayPosition = 0;
		String xpathDay = "";
		WebElement element = null;
		boolean isElementFound = false;
		
		for(int i=1; i<=trLine; i++){
			for(int j=1; j<=tdCol; j++){				
				xpathDay = xpathDaySnippet + i +"]/td["+ j +"]";
				element = getWebElement(xpathDay);
				dayPosition = Integer.parseInt(element.getText());
				if(dayExpected == dayPosition){
					boolean hasDayLessThan10inFirstLine = i == 1 && dayExpected <= 10;
					boolean hasDayGreaterThan24inLastLine = i == 6 && dayExpected >=24;
					boolean hasDayBetween1and31inSecondToFifthLines = i > 1 && i < 6 && dayExpected >= 1 && dayExpected <= 31;
					if(hasDayLessThan10inFirstLine){
						isElementFound = hasPresentElement(xpathDay);
						break;
					}
					if(hasDayGreaterThan24inLastLine){
						isElementFound = hasPresentElement(xpathDay);
						break;
					}
					if (hasDayBetween1and31inSecondToFifthLines){
						isElementFound = hasPresentElement(xpathDay);
						break;
					}
				}
			}
			if(isElementFound){
				break;
			}
		}
		if(element != null){
			element.click();
		}						
	}
	
	private void getOnlyCustomDayFromPicker(String optionValue) {
		String xpathDay = ".//td[@class='day' and contains(text(), '"+Integer.parseInt(optionValue)+"')]";
		clickElementInstanced(xpathDay);
	}

	private void getOnlyActualDayFromPicker() {
		String xpathDay1 = ".//td[contains(@class,'day active') or contains(@class,'day today active')]";
		waitForPageToLoad();
		waitForPresenceOfElementLocated(xpathDay1);
		waitForWebElementToBeClickable(xpathDay1);
		WebElement dayElement = getWebElement(xpathDay1);
		dayElement.click();
	}
		
	private void clickElementInstanced(String xpath) {
		waitForPresenceOfElementLocated(xpath);
		WebElement element = getWebElement(xpath);
		waitForElementToBeClickable(xpath);
		element.click();		
	}

	private void clickElementInstancedTwice(String xpath, String xpathDefault) {
		waitForPresenceOfElementLocated(xpath);
		WebElement element = getWebElement(xpath);
		waitForElementToBeClickable(xpath);
		element.click();		
		getWebElement(xpathDefault);
		WebElement elementDefault = getWebElement(xpathDefault);
		waitForElementToBeClickable(xpathDefault);
		elementDefault.click();		
	}
		
	private boolean hasPresentElement(String xpathPresent) {
		boolean isElementFound;
		waitForElementToBeClickable(xpathPresent);
		isElementFound = true;
		return isElementFound;
	}
	
}