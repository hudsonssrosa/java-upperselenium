package br.com.upperselenium.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface StageParams {
	
	/**
	 * Define Flow test goal.
	 * @return
	 */
	String goal() default "";
	
	/**
	 * Define Test Suites.
	 * @return
	 */
	Class<?> suiteClass() default Object.class;
} 
