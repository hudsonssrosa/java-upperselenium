package br.com.upperselenium.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface FlowParams {
	
	/**
	 * Define an ID to test Flow.
	 * @return
	 */
	String idTest() default "";

	/**
	 * Define the dataprovider path used to get the application page: "src/test/resources/.".
	 * @return
	 */
	String loginDirPath() default "";
	
	/**
	 * Define the dataprovider path on level of files "**DP.json" into "src/test/resources/.".
	 * @return
	 */
	String testDirPath() default "";
	
	/**
	 * Define Flow test goal.
	 * @return
	 */
	String goal() default "";
	
	/**
	 * Define Test Suites onto the Flow.
	 * @return
	 */
	Class<?> suiteClass() default Object.class;
} 
