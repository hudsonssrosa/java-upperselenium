package br.com.upperselenium.base.constant;


public interface FileDirPRM {

	String CAPABILITIES_DIR = "/capabilities/";
	String RES_BROWSER_LOGO_CHROME = "browser_logo_chrome.png";
	String RES_BROWSER_LOGO_FIREFOX = "browser_logo_firefox.png";
	String RES_BROWSER_LOGO_PHANTOMJS = "browser_logo_phantomjs.png";
	String RES_BROWSER_LOGO_IE = "browser_logo_ie.png";
	String RES_BROWSER_LOGO_EDGE = "browser_logo_edge.png";
	String RES_COMPANY_LOGO = "YouCompanyLogo.png";
	String REPORT_RESOURCES = "report-resources/";
	String REPORT = "report/";
	String TEST_EVIDENCES = "/test-evidences";
	
	String WEBDRIVERS_FOR_AUTOMATION = "C://webdrivers_for_automation";
	
	interface File {
		
		String APPLICATION_PROPERTIES = "application.properties";
		String CONFIG_BROWSER_PROPERTIES = "config_browser.properties";
		String CONFIG_REPORT_PROPERTIES = "config_report.properties";
		String TEST_CONFIG_PROPERTIES = "testconfig.properties";
		
	}
	
	interface Path {
		
		String CHROME_DRIVER_EXE = "/chromedriver.exe";
		String CONFIG_BROWSER_PROPERTIES = "/config_browser.properties";
		String CONFIG_REPORT_PROPERTIES = "/config_report.properties";
		String DEBUG_PROPERTIES = "/debug.properties";
		String EDGE_DRIVER_EXE = "/MicrosoftWebDriver.exe";
		String GECKO_DRIVER_EXE = "/geckodriver.exe";
		String IE_DRIVER_EXE = "/IEDriverServer.exe";
		String OPERA_DRIVER_EXE = "/operadriver.exe";
		String PHANTOMJS_DRIVER_EXE = "/phantomjs-2.1.1-windows/bin/phantomjs.exe";
		String TEST_CONFIG_PROPERTIES = "/testconfig.properties";
		
	}
	
	interface Extension {
		
		String CAPABILITIES_FILE = ".capabilities";
		String HTML_FILE = ".html";
		String JAVA_FILE = ".java";
		String JSON_FILE = ".json";
		String XHTML_FILE = ".xhtml";
		
	}

}
