package br.com.upperselenium.base.sample.test.suite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import br.com.upperselenium.base.BaseSuite;
import br.com.upperselenium.base.annotation.SuiteParams;
import br.com.upperselenium.base.sample.test.flow.T0000SignInFlow;

@RunWith(Suite.class)

@SuiteClasses({
	T0000SignInFlow.class,
	T0002RestApiPostmanFlow.class
	})

@SuiteParams(description="Test Cases de exemplo para UI, Performance e Rest API")
public class S000SampleSuite extends BaseSuite {}
