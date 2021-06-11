package br.com.upperselenium.base;

import static org.openqa.selenium.remote.CapabilityType.BROWSER_NAME;
import static org.openqa.selenium.remote.CapabilityType.PLATFORM;
import static org.openqa.selenium.remote.CapabilityType.VERSION;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.MarionetteDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import br.com.upperselenium.base.asset.CapabilityPropertyLoaderConfig;
import br.com.upperselenium.base.asset.WaitConfig;
import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.listener.EventsCaptureListener;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.TestLogger;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.KeyPRM;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.constant.TimePRM;
import br.com.upperselenium.base.util.WaitElementUtil;

/**
 * Base Class that performs the creation of the WebDriver, reading the "properties" files
 * and setting WebDriver Capabities in the context of SuiteClasses.
 * 
 * @author HudsonRosa
 */
public final class WebDriverMaster extends BaseLogger implements TestLogger {

	private static final String IE_DRIVER_EXE = FileDirPRM.Path.IE_DRIVER_EXE;
	private static final String EDGE_DRIVER_EXE = FileDirPRM.Path.EDGE_DRIVER_EXE;
	private static final String PHANTOMJS_DRIVER_EXE = FileDirPRM.Path.PHANTOMJS_DRIVER_EXE;
	private static final String CHROME_DRIVER_EXE = FileDirPRM.Path.CHROME_DRIVER_EXE;
	private static final String GECKO_DRIVER_EXE = FileDirPRM.Path.GECKO_DRIVER_EXE;
	private static final String OPERA_DRIVER_EXE = FileDirPRM.Path.OPERA_DRIVER_EXE;
	private static final String BROWSER_PROPERTIES_PATH = FileDirPRM.Path.TEST_CONFIG_PROPERTIES;
	private static final String BROWSER_PROPERTIES_FILE = FileDirPRM.File.TEST_CONFIG_PROPERTIES;
	private static final String CAPABILITIES_FILE_EXTENSION = FileDirPRM.Extension.CAPABILITIES_FILE;
	private static final String CAPABILITIES_PATH = FileDirPRM.CAPABILITIES_DIR;
	private static final String DRIVERS_PATH = FileDirPRM.WEBDRIVERS_FOR_AUTOMATION;
	private static final int WAIT_DEFAULT = WaitConfig.setTimeWaitingConfigDefault();
	protected static String gridHubUrl;
	protected static String baseUrl;
	private static WebDriver WEBDRIVER;
	private static Capabilities capabilities;
	
	private WebDriverMaster(){}

	/**
	 * Method to instantiate WebDriver and Profiles
	 */
	public static void initWebDriverSession(){
		if (WEBDRIVER == null) {
			WEBDRIVER = null;
			WebDriverMaster.prepareWebDriver();
		}
		WebDriverMaster.getWebDriver();
		logToInitWebDriver();
	}
	
	private static void logToInitWebDriver() {
		logInfoFrameworkTitle();		
		logInfoWithContinuousLine("\\_________________________/");
		logInfoFirstOrLastHeaderTitle("/ INITIALISING WEBDRIVER \\");
		logInfoBlankLine();
		logInfoTextHighlight("Executing with: " + WebDriverMaster.getWebDriverName().getClass().getSimpleName());
		logInfoBlankLine();
	}	
	
	/**
	 * Method to destroy WebDriver session after execution
	 */
	public static void destroyWebDriverSession() {
		if (WEBDRIVER != null){
			WEBDRIVER.quit();
			try {
				Thread.sleep(TimePRM._1_SEC);
			} catch (InterruptedException e) {
				throw new RunningException(BaseLogger.logException(MessagePRM.AsException.SESSION_NOT_CLOSED), e);
			}
		}
		logInfoHeader(WebDriverMaster.getWebDriverName().getClass().getSimpleName().toUpperCase() + " PARADO!");			
	}
	
	/**
	 * Method that returns WebDriver loaded in context with Event Listener configured.
	 * 
	 * @return
	 */
	public static WebDriver getWebDriver() {
		EventsCaptureListener eventHandle = new EventsCaptureListener();
		return new EventFiringWebDriver(WEBDRIVER).register(eventHandle);
	}

	/**
	 * Method that returns the loaded WebDriver in context.
	 * 
	 * @return
	 */
	public static WebDriver getWebDriverName() {
		return WEBDRIVER;
	}

	/**
	 * Method to adjust Browser startup after preparing WebDriver for execution.
     * Receives the WebDriver of the current browser as a parameter.
	 * 
	 * @param activeWebDriver
	 */
	private static void setDefaultWebDriverProperties(WebDriver activeWebDriver){
		WaitElementUtil.setImplicitlyWait(activeWebDriver, WAIT_DEFAULT);
		System.out.println(WAIT_DEFAULT);
		activeWebDriver.manage().window().maximize();
	}
	
	/**
	 * Method that loads Selenium Grid-related initial environment variables
	 * 
	 * @throws IOException
	 */
	private static void loadInitialEnvironmentVariables() throws IOException {
		baseUrl = CapabilityPropertyLoaderConfig.loadProperty(KeyPRM.PropertyKey.SITE_URL);
		gridHubUrl = CapabilityPropertyLoaderConfig.loadProperty(KeyPRM.PropertyKey.GRID2_HUB);
		baseUrl = CapabilityPropertyLoaderConfig.loadProperty(KeyPRM.PropertyKey.SITE_URL);
		gridHubUrl = CapabilityPropertyLoaderConfig.loadProperty(KeyPRM.PropertyKey.GRID_HUB);
		if ("".equals(gridHubUrl)) {
			gridHubUrl = null;
		}
		setCapabilities(CapabilityPropertyLoaderConfig.loadCapabilities());
	}
	
	/**
	 * Method to load existing property values into '.capabilities' format files.
     * Receives information from the previously defined browser as a parameter.
	 * 
	 * @param browserType
	 * @return
	 * @throws IOException
	 */
	public static Capabilities loadCapabilitiesFromFile(String browserType)
			throws IOException {
		Properties capsProps = new Properties();
		getCapabilityFileValues(browserType, capsProps);
		DesiredCapabilities capabilities = new DesiredCapabilities();
		for (String name : capsProps.stringPropertyNames()) {
			String valueOfCapability = capsProps.getProperty(name);
			if (valueOfCapability.toLowerCase().equals("true") || valueOfCapability.toLowerCase().equals("false")) {
				capabilities.setCapability(name, Boolean.valueOf(valueOfCapability));
			} else if (valueOfCapability.startsWith("file:")) {
				capabilities.setCapability(name, new File(".", valueOfCapability.substring(5)).getCanonicalFile().getAbsolutePath());
			} else {
				capabilities.setCapability(name, valueOfCapability);
			}
		}
		return capabilities;
	}

	/**
	 * Method that retrieves Capabilities file in context.
     * Receives as parameter the information from the previously defined browser and a properties object to load the data from the file.
	 * 
	 * @param browserName
	 * @param capsProps
	 * @throws IOException
	 */
	private static void getCapabilityFileValues(String browserName, Properties capsProps) throws IOException {
		capsProps.load(CapabilityPropertyLoaderConfig.class.getResourceAsStream(
				System.getProperty(
						browserName + CAPABILITIES_FILE_EXTENSION, CAPABILITIES_PATH + browserName + CAPABILITIES_FILE_EXTENSION
						)));
	}
	
	/**
	 * Method that retrieves the Properties file of the defined Browser type.
     * Receives a properties object as a parameter to load data from the file.
	 * 
	 * @param browserProps
	 * @throws IOException
	 */
	private static void getTypeBrowserFileValues(Properties browserProps) throws IOException {
		browserProps.load(CapabilityPropertyLoaderConfig.class.getResourceAsStream(System.getProperty(BROWSER_PROPERTIES_FILE, BROWSER_PROPERTIES_PATH)));
	}
	
	/**
	 * Method that returns a String with the defined Browser type.
	 * 
	 * @param browserProps
	 * @return
	 */
	private static String getBrowserType(Properties browserProps) {
		String browserType = browserProps.getProperty(KeyPRM.PropertyKey.BROWSER_TYPE);
		return browserType;
	}
	
	/**
	 * Responsible for loading the Browser type, Capabilities and checking the WebDriver to be initialized.
	 * Returns a WebDriver initialized in context.
	 * 
	 * @return
	 */
	public static WebDriver prepareWebDriver() {
		Properties browserProps = new Properties();
		try {
			loadInitialEnvironmentVariables();
			getTypeBrowserFileValues(browserProps);
			String browserTypeString = getBrowserType(browserProps);
			loadCapabilitiesFromFile(browserTypeString);
		} catch (IOException e) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.IO_WEBDRIVER_ERROR), e);
		}
		return openBrowserWithCapabilites(getBrowserType(browserProps));
	}
	
	/**
	 * Responsible for opening a Browser defining the WebDriver to be executed, according to information on the type of Browser and its Desired Capabilities.
     * Receives a String referring to the type of Browser as a parameter and returns a WebDriver.
	 * 
	 * @param browser
	 * @return
	 */
	private static WebDriver openBrowserWithCapabilites(String browser) {
		Capabilities capabilities;
		try {
			capabilities = loadCapabilitiesFromFile(browser);
			String getBrowserByCaps = capabilities.getBrowserName();
			switch (getBrowserByCaps) {
			case BrowserType.CHROME:
				return openChrome(capabilities);
			case BrowserType.EDGE:
				return openEdge(capabilities);
			case BrowserType.FIREFOX:
				return openFirefox(capabilities);
			case BrowserType.IE:
				return openIE(capabilities);
			case BrowserType.OPERA_BLINK:
				return openOpera(capabilities);
			case BrowserType.PHANTOMJS:
				return openPhantomJs(capabilities);
			default:
				return openChrome(capabilities);
			}
		} catch (IOException e) {
			BaseLogger.logThrowables(MessagePRM.AsException.IO_CAPABILITIES_ERROR, e);
		}
		return WEBDRIVER;
	}
	
	/**
	 * Opens Chrome Browser with Desired Capabilities set.
     * Receives Capabilities values by parameter and returns an initialized WebDriver.
	 * 
	 * @param capabilities
	 * @return
	 */
	protected static WebDriver openChrome(Capabilities capabilities) {		
		System.setProperty("webdriver.chrome.driver", DRIVERS_PATH + CHROME_DRIVER_EXE);
		
		((DesiredCapabilities) capabilities).setCapability("chrome.binary", DRIVERS_PATH + CHROME_DRIVER_EXE);
		((DesiredCapabilities) capabilities).setCapability(BROWSER_NAME, BrowserType.CHROME);
		((DesiredCapabilities) capabilities).setCapability(VERSION, "");
		((DesiredCapabilities) capabilities).setCapability(PLATFORM, Platform.ANY);
		
		ChromeOptions options = defineChromeOptions();
		((DesiredCapabilities) capabilities).setCapability(ChromeOptions.CAPABILITY, options);
		
		WEBDRIVER = new ChromeDriver(capabilities);
		setDefaultWebDriverProperties(WEBDRIVER);
		
		WEBDRIVER.manage().timeouts().pageLoadTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		WEBDRIVER.manage().timeouts().implicitlyWait(WAIT_DEFAULT, TimeUnit.SECONDS);
		return WEBDRIVER;
	}	
	
	/**
	 * Chrome Browser Profile.
     * Returns profile options defined for the Browser.
	 * 
	 * @return
	 */
	private static ChromeOptions defineChromeOptions() {
		ChromeOptions options = new ChromeOptions();

		Map<String, Object> prefs = new HashMap<String, Object>();
		prefs.put("download.prompt_for_download", "false");

		Map<String, Object> contentSetting = new HashMap<String, Object>();
		contentSetting.put("multiple-automatic-downloads", 1);
		prefs.put("profile.default_content_settings", contentSetting);

		options.setExperimentalOption("prefs", prefs);

		options.addArguments("start-maximized");
		
		options.addArguments("--test-type"); 
		options.addArguments("--enable-npapi");
		options.addArguments("--action-box");
		options.addArguments("--allow-no-sandbox-job");
		options.addArguments("--disable-answers-in-suggest");
		options.addArguments("--disable-confirmation");
		options.addArguments("--disable-desktop-notifications");		
		options.addArguments("--disable-infobars");
		options.addArguments("--multiple-automatic-downloads");

		return options;
	}
	
	/**
	 * Opens Firefox Browser with Profiles defined.
     * Receives Capabilities values by parameter and returns an initialized WebDriver.
	 * 
	 * @param capabilities
	 * @return
	 */
	protected static WebDriver openFirefox(Capabilities capabilities) {
		System.setProperty("webdriver.gecko.driver", DRIVERS_PATH + GECKO_DRIVER_EXE);
		capabilities = DesiredCapabilities.firefox();		
		((DesiredCapabilities)capabilities).setCapability("marionette", true);
		((DesiredCapabilities)capabilities).setBrowserName("firefox");		
		WebDriver firefoxDriver = new MarionetteDriver(((DesiredCapabilities)capabilities));
		firefoxDriver.manage().window().maximize();
		
		firefoxDriver.manage().timeouts().pageLoadTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		firefoxDriver.manage().timeouts().implicitlyWait(WAIT_DEFAULT, TimeUnit.SECONDS);
		firefoxDriver.manage().timeouts().setScriptTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		
		WEBDRIVER = firefoxDriver;
		setDefaultWebDriverProperties(WEBDRIVER);
		
		return firefoxDriver;
	}
	
	/**
	 * Firefox Browser Profile
	 * Returns profile options defined for the Browser.
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	@Deprecated
	private static FirefoxProfile defineFirefoxProfile() {
		FirefoxProfile profile = new FirefoxProfile();

		profile.setPreference("app.update.enabled", false);

		final int userPassLength = 255;
		profile.setPreference("network.http.phishy-userpass-length", userPassLength);

		profile.setPreference("browser.download.folderList", 2);
		profile.setPreference("browser.download.dir", System.getProperty("java.io.tmpdir"));
		profile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/plain, application/vnd.ms-excel, application/xls, application/pdf");

		profile.setPreference("browser.translation.detectLanguage", false);
		profile.setPreference("pref.browser.language.disable_button.remove", false);
		profile.setPreference("services.sync.prefs.sync.intl.accept_languages", true);

		return profile;
	}
	
	/**
	 * Opens IE Browser with Desired Capabilities set.
	 * Receives Capabilities values by parameter and returns an initialized WebDriver.
	 * 
	 * @param capabilities
	 * @return
	 */
	protected static WebDriver openIE(Capabilities capabilities) {
		System.setProperty("webdriver.ie.driver", DRIVERS_PATH + IE_DRIVER_EXE);
		if(capabilities == null) {
			capabilities = new DesiredCapabilities();
		}		
		((DesiredCapabilities)capabilities).setBrowserName(BrowserType.IE);
		((DesiredCapabilities)capabilities).setPlatform(Platform.WINDOWS);
		
		((DesiredCapabilities)capabilities).setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		((DesiredCapabilities)capabilities).setCapability("logLevel", "DEBUG");
		((DesiredCapabilities)capabilities).setCapability("ie.ensureCleanSession", true);
		((DesiredCapabilities)capabilities).setCapability("nativeEvents", false);
		((DesiredCapabilities)capabilities).setCapability("ie.forceCreateProcessApi", true);
		
		InternetExplorerDriver ieDriver = new InternetExplorerDriver((DesiredCapabilities)capabilities);
		ieDriver.manage().timeouts().pageLoadTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		ieDriver.manage().timeouts().implicitlyWait(WAIT_DEFAULT, TimeUnit.SECONDS);
		ieDriver.manage().timeouts().setScriptTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		
		WEBDRIVER = ieDriver;
		setDefaultWebDriverProperties(WEBDRIVER);
		
		return WEBDRIVER;
	}
	
	/**
	 * Opens IE Browser with Desired Capabilities set.
	 * Receives Capabilities values by parameter and returns an initialized WebDriver.
	 * 
	 * @param capabilities
	 * @return
	 */
	protected static WebDriver openEdge(Capabilities capabilities) {
		System.setProperty("webdriver.edge.driver", DRIVERS_PATH + EDGE_DRIVER_EXE);
		if(capabilities == null) {
			capabilities = new DesiredCapabilities();
		}		
		((DesiredCapabilities)capabilities).setBrowserName(BrowserType.EDGE);
		((DesiredCapabilities)capabilities).setPlatform(Platform.WINDOWS);
		
		WebDriver edgeDriver = new EdgeDriver((DesiredCapabilities)capabilities);
		edgeDriver.manage().timeouts().pageLoadTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		edgeDriver.manage().timeouts().implicitlyWait(WAIT_DEFAULT, TimeUnit.SECONDS);
		edgeDriver.manage().timeouts().setScriptTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		
		WEBDRIVER = edgeDriver;
		setDefaultWebDriverProperties(WEBDRIVER);
		
		return WEBDRIVER;
	}
	
	/**
	 * Opens Opera Browser with Desired Capabilities set.
	 * Receives Capabilities values by parameter and returns an initialized WebDriver.
	 * 
	 * @param capabilities
	 * @return
	 */
	protected static WebDriver openOpera(Capabilities capabilities) {
		
		System.setProperty("webdriver.opera.driver", DRIVERS_PATH + OPERA_DRIVER_EXE);
		if(capabilities == null) {
			capabilities = new DesiredCapabilities();
		}		
		((DesiredCapabilities)capabilities).setBrowserName(BrowserType.OPERA_BLINK);
		((DesiredCapabilities)capabilities).setPlatform(Platform.WINDOWS);
		
		((DesiredCapabilities)capabilities).setCapability("opera.binary", DRIVERS_PATH + OPERA_DRIVER_EXE);
		((DesiredCapabilities)capabilities).setCapability("opera.opera.guess_binary_path", DRIVERS_PATH + OPERA_DRIVER_EXE);
		((DesiredCapabilities)capabilities).setCapability("opera.logging.level", Level.CONFIG);
		((DesiredCapabilities)capabilities).setCapability("opera.display", 8);
		
		WebDriver operaDriver = new OperaDriver((DesiredCapabilities)capabilities);
		operaDriver.manage().timeouts().pageLoadTimeout(WAIT_DEFAULT, TimeUnit.SECONDS);
		operaDriver.manage().timeouts().implicitlyWait(WAIT_DEFAULT, TimeUnit.SECONDS);

		WEBDRIVER = operaDriver;
		setDefaultWebDriverProperties(WEBDRIVER);
		
		return WEBDRIVER;
	}
	
	/**
     * Boots with PhantonJS WebDriver with Desired Capabilities set.
     * Receives Capabilities values by parameter and returns an initialized WebDriver.
	 * 
	 * @param capabilities
	 * @return
	 */
	public static WebDriver openPhantomJs(Capabilities capabilities) {
		DesiredCapabilities dc = new DesiredCapabilities();
		dc.setCapability("phantomjs.binary.path ", DRIVERS_PATH + PHANTOMJS_DRIVER_EXE);
		PhantomJSDriverService service = new PhantomJSDriverService.Builder()
				.usingPhantomJSExecutable(new File(DRIVERS_PATH + PHANTOMJS_DRIVER_EXE))
				.usingAnyFreePort().usingCommandLineArguments(new String[] {"--ignore-ssl-errors=yes", "--webdriver-loglevel=WARN"})
				.withLogFile(new File(DRIVERS_PATH + "/phantomjs.log"))
				.build();
		PhantomJSDriver phantomJSDriver = new PhantomJSDriver(service, dc);

		phantomJSDriver.manage().timeouts().implicitlyWait(WAIT_DEFAULT, TimeUnit.SECONDS);
		WEBDRIVER = phantomJSDriver;
		
		return phantomJSDriver;
	}

	public static Capabilities getCapabilities() {
		return capabilities;
	}

	public static void setCapabilities(Capabilities capabilities) {
		WebDriverMaster.capabilities = capabilities;
	}
	
	
	
}
