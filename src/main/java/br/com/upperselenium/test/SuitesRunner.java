package br.com.upperselenium.test;

import java.util.List;

import br.com.upperselenium.base.BaseSuitesExecutor;
import br.com.upperselenium.base.sample.test.suite.S000SampleSuite;

/**
 * Define todas as Suites para execução.
 * @author Hudson
 *
 */
public class SuitesRunner extends BaseSuitesExecutor {

	public static void main(String args[]) {
		runAll(null);
	}

	public static List<Class<?>> runSuites() {
		suites.add(SampleSuite.class);
		return suites;
	}

}