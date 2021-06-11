     __    __  _____   _____    _____   ______        _______   _____  
    |  |  |  ||   _ \ |   _ \  / ___ \ |   _  \   _  /  _____] / ___ \ 
    |  |  |  ||  |_) )|  |_) )| |___) )|  |_)  ) (_) \____  \ | |___) )
    |  |__|  ||  ___/ |  ___/ |  ____/ |  _   /      _____)  )|  ____/ 
     \______/ |__|    |__|     \_____\ |__|\__\      [______/  \_____\ 

	                          R E A D  M E
	   
                    Developed by: Hudson S. S. Rosa
					   
Go ahead and try:

```
$ git clone git@github.com:hudsonssrosa/upperselenium.git
```

### 1. UpperSelenium Test Automation ###
With this TDD framework you can:
#### 1.1. Choose different browsers/webdrivers to execute your tests: **Firefox, Chrome, PhantomJS and Edge**.
#### 1.2. Execute all tests by **Suite**.
#### 1.3. Generate results with detailed logs on console and complete **HTML Report**. 
#### 1.4. The HTML Report contains screenshot from failed stages web page.
#### 1.5. View all suite results in a general **bar graph**.
#### 1.6. Implement tests using concepts as **Suite, Flow, Stages and Page Objects**.
#### 1.7. Create Data Mass using JSon files - This makes it easier to reuse Stages.

### 2. Test Classes Structure ###

```
#!java
PACKAGES:
src/main/java
  |___br.com.upperselenium
      |__test
         |"SuitesExecutor.java"
         |__flow
         |     SampleFlow.java
         |__stage
         |  |  <app functionality name>
         |  |__dpo
         |  |  SampleDPO.java
         |  |__page
         |     SamplePage.java
         |__suite
               SampleSuite.java

FOLDERS:
src/main/resources
  |___dataprovider
      |__sampleFlow
               SampleDP.json

```

### 3. How do I implement tests? ###
* 3.1. Create Page Object class and mapping all the web elements using in the test. Just extract XPaths from the page. Invoke element actions methods implemented in superclass and pass parameter value to use in the test.

```
#!java

public class LoginSamplePage extends BasePage{
	private static final String BUTTON_ENTER = ".//div[2]/div/form/div/button";	
	private static final String TEXT_USER = "//*[@id='UserName']";	
	private static final String TEXT_PASS = "//*[@id='Password']";	
		
	public void clickEnter(){
		clickOnElement(BUTTON_ENTER);
	}

	public void typeTextUser(String value){
		typeTextField(TEXT_USER, value);
	}

	public void typeTextPassword(String value){
		typeTextField(TEXT_PASS, value);
	}		
}
```

* 3.2. Create data mass with a JSon file locating in "src\main\resources\dataprovider\...\SampleDP.json"


LoginSampleDP.json - Consumed by "LoginStage.java"
```
#!json

{
    "url": "http://localhost/app",
    "user": "theUserName",
    "pass": "me123456",
	"message": "User connected successfully"
}
```
SampleDP.json - Consumed by "SampleStage.java"
```
#!json

{
	"name": "Hudson Rosa",
	"occupation": "Quality Engineer"
}
```

* 3.3. Create a DPO class (Data Provider Object). This is a POJO file used to transfer information from JSon file to an object during the test execution. For example, if you need to create a class with the name "LoginSampleDPO.java", do this basically.

Note: To create the Constructor, Getters, Setters and ToString method you can set shortcuts to make it easier your class implementation.

```
#!java

public class LoginSampleDPO {

    private String url;
    private String user;
    private String pass;
    private String message;

    public LoginSampleDPO(String url, String user, String pass, String message) {
        super();
        this.url = url;
        this.user = user;
        this.pass = pass;
        this.message = message;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message= message;
    }

    @Override
    public String toString() {
        return "LoginSampleDPO [url=" + url 
						   + ", user=" + user 
						   + ", pass=" + pass 
						   + ", message=" + message + "]";
    }
}
```

* 3.4. Create a Stage class. This class will aggregate actions semantically of application with Page Object and DPO class instantiation. Is required implement these flollowing methods:

```
#!java

public class LoginStage extends BaseStage {
	private LoginSampleDPO loginSampleDPO;
	private LoginSamplePage loginSamplePage;

	public LoginStage(String dpSample) {
		loginSampleDPO = loadDataProviderFile(LoginSampleDPO.class, dpSample);
	}

	@Override
	public void initMappedPages() {
		loginSamplePage = initElementsFromPage(LoginSamplePage.class);    
	}

	@Override
	public void runStage() {
		WebDriverMaster.getWebDriver().get(loginSampleDPO.getUrl());			
		waitForPageToLoad(TimePRM._10_SECS);
		loginSamplePage.typeTextUser(loginSampleDPO.getUser());
        loginSamplePage.typeTextPassword(loginSampleDPO.getPass());
		loginSamplePage.clickEnter();
	}

	@Override
	public void runValidations() {
		validateField();
		//... Add new validations here
	}

	private void validateField() {             
		String expectedMessage = loginSampleDPO.getMessage();
		String actualMessage = loginSamplePage.getMessage();
		AssertUtil.assertEquals(expectedMessage, actualMessage);
	}
}
```

* 3.5. Create a Flow class invoking the Stage and set test parameters. Attribute the "SampleDPs.json" path in a String constant.

```
#!java

@FlowParams
(idTest = "T0001_Sample",
testDirPath = "dataprovider/sample/Sample",
loginDirPath = "dataprovider/sample/Login",
goal = "Access the application and validate Home Page",
suiteClass = SampleSuite.class)

public class T0000SampleFlow extends BaseFlow {

	private String dpSample = getDPFileName("SampleDP");
	private String dpLoginSample = getLoginDPFileName("LoginSampleDP");

	@Override
	protected void addFlowStages() {
		addStage(new LoginStage(dpLoginSample));
		addStage(new SampleStage(dpSample));
		//... Add new Stages here
	}	
}
```

* 3.6. Create a Suite class to organize Flows semantically and in order for execution.

```
#!java

@RunWith(Suite.class)

            @SuiteClasses({
                SampleFlow.class,
                //...AnotherSampleFlow.class                 
            })

            @SuiteParams(description="Tests About Sample Application")
            public class SampleSuite extends SuiteBase {}
```


### 4. How do I run tests? ###

* 4.1. Set the following parameter of "test_config.properties":

```
#!java
    browserType=chrome
    deleteAllReports=true
    reportDirectory=report/
    timeWaitingDefault=5
    showContextVariables=false
```

* 4.2. In Eclipse (developer mode), execute the file "SuitesExecutor.java" after entering the desired class suite.

```
#!java

            public static List<Class<?>> runSuites() {
                suites.add(SampleSuite.class);
                return suites;
            }
```

* 4.3. When all the tests are completed, collect the report in folder specified in "test_config.properties". For example, consider the path "report/" and the HTML file created.

```
#!java

upperselenium-automation
  |___report
      |"SuiteReport_01-01-2017_at_00-00-00.html"
```

### 5. Who do I talk to? ###

* Author: Hudson Steffanni Soares Rosa (hudsonssrosa@gmail.com)

Have fun!