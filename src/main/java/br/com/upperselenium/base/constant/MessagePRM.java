package br.com.upperselenium.base.constant;


public interface MessagePRM {

	String SCREENSHOT_OBTAINED = "Screenshot da tela foi obtido!";
	
	interface AsException {
		String ALERT_IS_NOT_PRESENT = "Alert is not present to be clicked!";
		String ALERT_NOT_EXPECTED = "An unexpected alert was displayed in the browser for not identifying a field expected by the test!\n"
		+ "This prevented subsequent tests from running.";
		String ELEMENT_NOT_DISPLAYABLE = "Element not found or not visible!";
		String ELEMENT_NOT_ENABLED = "Element not available!";
		String ELEMENT_NOT_INTERABLE = "Element not found or not interable while interpreting its Xpath.";
		String ELEMENT_NOT_PRESENT = "Element not present!";
		String ELEMENT_NOT_SELECTABLE = "Element is not selectable!";
		String ENCODING_NOT_SUPPORTED = "Encoding not supported when generating encryption!";
		String EXECUTION_SUITE_ERROR = "Error executing Suites! Possible failure in execution settings or incompatibility with Webdriver!";
		String DATAPROVIDER_EXCEPTION = "Could not get DataProvider file!";
		String DEFAULT_WAIT = "Unable to get the default Webdriver wait time through the set value!";
		String IMAGE_FILE_NOT_CREATED = "Cannot create browser screen image file.";
		String INSTANTIATION_PAGE_ERROR = "The specified Page Object object cannot be instantiated!";
		String IO_CAPABILITIES_ERROR = "Error setting Capabilities to WebDriver!";
		String IO_ERROR = "I/O error on file";
		String IO_FATAL_ERROR = "Fatal I/O error over file";
		String IO_REPORT_NOT_FOUND = "Error creating Html Report as the specified path could not be found!";
		String IO_WEBDRIVER_ERROR = "Error getting information to instantiate WebDriver!";
		String ISOLATED_SUITE_EXECUTION_ERROR = "Error trying to run the suite alone!";
		String FILE_EXCLUSION_ERROR = "Could not delete file(s) in: ";
		String FILE_NOT_UPDATED = "File has not been changed!";
		String GET_JSON_EXCEPTION = "Could not get Json file!";
		String MONTH_NOT_FOUND = "The month was not set correctly and/or was not found for date selection.";
		String OBSERVER_EXCEPTION = "Unable to execute the final results of the Suites of the class 'ObserverFinalResultsListener.java'!";
		String PERFORMANCE_RESULTS_NOT_CALCULATED = "Could not calculate division by zero when loading page!";
		String PROPERTIES_CKECKING_EXCEPTION = "An error occurred while verifying properties file data!";
		String PROBLEM_WHEN_ENCRYPTING = "An error occurred while encrypting the message given in the random data generator!";
		String RANDOM_DATA_PROBLEM = "Problem processing encryption algorithm!";
		String REFLECTION_PAGE_ERROR = "Could not reflexively access Page Object definitions (fields, methods, constructor, etc.)!";
		String RESULTS_NOT_CALCULATED = "Test results could not be obtained!";
		String SESSION_NOT_CLOSED = "WebDriver session was not closed properly!";
		String STAGE_EXCEPTION = "EXCEPTION THROWN ON THIS STAGE!";
		String TIMEOUT_EXCEPTION = "Timeout in test execution. Parameters 'timeWaitingDefault' timed out";
		String VALUE_NOT_FOUND = "The value was not found.";
		String WAIT_IMPLICIT = "Cannot wait for element implicitly!";
		String WAIT_FOR_A_TIME = "Unable to wait for element!";
		String WAIT_FOR_PRESENT_ALERT = "Can't wait for Alert. It doesn't seem to be present!";
		String WAIT_FOR_PRESENT_ELEMENT = "Element appears not to be present for use!";
		String WAIT_FOR_CLICKABLE_ELEMENT = "Element appears not to be present or visible to be clicked on!";
		String WAIT_FOR_PRESENT_PAGE = "Unable to wait for the referenced page. It doesn't seem to be loading!";
		String WRITE_JSON_EXCEPTION = "Unable to write to Json file!";
		String XPATH_ERROR = "The defined XPath cannot be interpreted correctly!";
	}

}
