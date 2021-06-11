package br.com.upperselenium.base.util;

import java.io.IOException;
import java.util.Properties;

import br.com.upperselenium.base.asset.CapabilityPropertyLoaderConfig;
import br.com.upperselenium.base.exception.DocumentException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.HtmlFormatter;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.MessagePRM;

public abstract class PropertiesUtil extends HtmlFormatter {

	/**
	 * Method to assist in verifying expected settings from a properties file
	 * 
	 * @param valueRequired
	 * @param fileWithExtension
	 * @param pathFile
	 * @param propertiesKey
	 * @return
	 */
	public static Boolean getFlagFromProperties(String valueRequired, String fileWithExtension, String pathFile, String propertiesKey) {
		Properties reportProps = new Properties();
		try {
			loadPropertiesFile(reportProps, fileWithExtension, pathFile);
			String propKey = getKeyTypeFromProperties(reportProps, propertiesKey);
			if (valueRequired.equals(propKey)){
				return true;
			} 
			return false;
		} catch (IOException e) {
			throw new DocumentException(BaseLogger.logException(MessagePRM.AsException.PROPERTIES_CKECKING_EXCEPTION + FileDirPRM.REPORT), e);
		}
	}

	public static void loadPropertiesFile(Properties reportProps, String fileWithExtension, String pathFile) throws IOException {
		reportProps.load(CapabilityPropertyLoaderConfig.class.getResourceAsStream(System.getProperty(fileWithExtension, pathFile)));
	}
	
	public static String getKeyTypeFromProperties(Properties propertiesInstance, String propertiesKey) {
		String propSet = propertiesInstance.getProperty(propertiesKey);
		return propSet;
	}
	
}