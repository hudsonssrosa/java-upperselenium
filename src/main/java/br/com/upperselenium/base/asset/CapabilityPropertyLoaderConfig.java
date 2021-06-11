package br.com.upperselenium.base.asset;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.KeyPRM;

/**
 * Classe utilitária para extração de dados dos arquivos ".properties".
 */
public class CapabilityPropertyLoaderConfig {

	public static Capabilities loadCapabilities() throws IOException {
		return loadCapabilities(
				System.getProperty(FileDirPRM.File.APPLICATION_PROPERTIES, FileDirPRM.Path.DEBUG_PROPERTIES));
	}

	public static Capabilities loadCapabilities(String fromResource) throws IOException {
		Properties props = new Properties();
		props.load(CapabilityPropertyLoaderConfig.class.getResourceAsStream(fromResource));
		String capabilitiesFile = props.getProperty(KeyPRM.PropertyValue.CAPABILITIES);

		Properties capsProps = new Properties();
		capsProps.load(CapabilityPropertyLoaderConfig.class.getResourceAsStream(capabilitiesFile));

		DesiredCapabilities capabilities = new DesiredCapabilities();
		for (String name : capsProps.stringPropertyNames()) {
			String value = capsProps.getProperty(name);
			if (value.toLowerCase().equals("true") || value.toLowerCase().equals("false")) {
				capabilities.setCapability(name, Boolean.valueOf(value));
			} else if (value.startsWith("file:")) {
				capabilities.setCapability(name,
						new File(".", value.substring(5)).getCanonicalFile().getAbsolutePath());
			} else {
				capabilities.setCapability(name, value);
			}
		}
		return capabilities;
	}

	public static String loadProperty(String name) throws IOException {
		return loadProperty(name,
				System.getProperty(FileDirPRM.File.APPLICATION_PROPERTIES, FileDirPRM.Path.DEBUG_PROPERTIES));
	}

	public static String loadProperty(String name, String fromResource) throws IOException {
		Properties props = new Properties();
		props.load(CapabilityPropertyLoaderConfig.class.getResourceAsStream(fromResource));
		return props.getProperty(name);
	}

}