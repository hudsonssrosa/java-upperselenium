package br.com.upperselenium.test.stage.helper;

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
import br.com.upperselenium.base.parameter.MessagePRM;
import br.com.upperselenium.base.parameter.TimePRM;
import br.com.upperselenium.base.util.ScreenshotEvidencesUtil;
import br.com.upperselenium.base.util.StringUtil;
import br.com.upperselenium.base.util.WaitElementUtil;


public abstract class BaseHelperPage extends BasePage {

	public BaseHelperPage() {
		super();
	}

	public void reportElement(Object textForEvidence) {
		BaseReport.reportContentOfElementEvent(textForEvidence);
	}

	public void reportElementTitle(Object textForEvidence) {
		BaseReport.reportContentOfElementTitle(textForEvidence);
	}

	public void useKey(String xpathElement, Keys key){
		waitForPageToLoadUntil10s();
		getWebDriver().findElement(By.xpath(xpathElement)).sendKeys(key);
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

	public String getToastMessage(String xpathElement){
		waitForPageToLoadUntil10s();
		if(!"".equalsIgnoreCase(getWebElement(xpathElement).getText())){
			waitForPresenceOfElementLocated(xpathElement);
			return getWebElement(xpathElement).getText();
		}
		return getWebElement(xpathElement).getText();
	}

	public void validateEqualsToValues(String dpoExpectedMessage, String pagePresentMessage){
		waitForPageToLoad();
		getAssertEquals(dpoExpectedMessage, pagePresentMessage);
	}

	public void validateMessageDefault(String dpoExpectedMessage){
		waitForPageToLoad();
		String xpathStatusAlert = "//*[contains(@class, 'alert alert-error') or contains(@class,'alert alert-success') or contains(@class,'alert alert-info')]";
		String xpathToastComponent = "//*[contains(@class, 'toast-message')]";
		String pagePresentMessage = "";
		pagePresentMessage = getFinalMessage(xpathToastComponent, xpathStatusAlert, pagePresentMessage);
		getAssertEquals(dpoExpectedMessage, pagePresentMessage);
	}

	private String getFinalMessage(String xpathToastComponent, String xpathStatusAlert, String pagePresentMessage) {
		pagePresentMessage = verifyAlertType(xpathToastComponent, xpathStatusAlert);
		return pagePresentMessage;
	}

	private String verifyAlertType(String xpathToastComponent, String xpathStatusAlert) {
		String finalMessage = "";
		try{
			finalMessage = getToastMessage(xpathToastComponent);
			getWebElement(xpathToastComponent).click();
		} catch (Exception ex){
			finalMessage = getAlertMessage(xpathStatusAlert);
		}
		return finalMessage;
	}

	// ##############################
	// ### DEFAULT FIELD ELEMENTS ###

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

	public void clickOnElement(String xpathElement, boolean screenshotTrueOrFalse){
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
			if (screenshotTrueOrFalse){
				ScreenshotEvidencesUtil.takeDetachedScreenshot();
			}
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

	// GET LABEL TEXT
	public String getLabel(String xpathElement, String value){
		waitForPageToLoadUntil10s();
		waitForPresenceOfElementLocated(xpathElement);
		reportElementTitle("LABEL CHECKED \"" + value.toUpperCase() + "\"");
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
				int sizeSplit = splitTerms.length;
				for (int i = 0; i < sizeSplit; i++) {
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
		reportElementTitle("GRID LABEL VERIFIED \"" + value.toUpperCase() + "\"");
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