package br.com.upperselenium.base.util;

import org.apache.commons.lang3.StringUtils;

import br.com.upperselenium.base.logger.HtmlFormatter;

public abstract class StringUtil extends HtmlFormatter {

	/**
	 * Validates if the parameter is NOT null or blank.
	 * 
	 * @param optionValue
	 * @return
	 */
	public static boolean isNotBlankOrNotNull(String value) {
		return StringUtils.isNotBlank(value) || StringUtils.isNotEmpty(value);
	}

	/**
	 * Validates if the parameter is null or blank.
	 * 
	 * @param optionValue
	 * @return
	 */
	public static boolean isBlankOrNull(String value) {
		return StringUtils.isBlank(value) || StringUtils.isEmpty(value);
	}

}