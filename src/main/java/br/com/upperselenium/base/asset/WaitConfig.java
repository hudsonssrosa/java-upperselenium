package br.com.upperselenium.base.asset;

import java.io.IOException;
import java.util.Properties;

import br.com.upperselenium.base.exception.DocumentException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.HtmlFormatter;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.KeyPRM;
import br.com.upperselenium.base.constant.MessagePRM;

public abstract class WaitConfig extends HtmlFormatter {
	
	public static int setTimeWaitingConfigDefault() {
		Properties waitingProps = new Properties();
		try {
			getTimeWaitingParamFromFile(waitingProps);
			String defaultTimeWaiting = getTypeFromConfigReport(waitingProps);
			return Integer.parseInt(defaultTimeWaiting);
		} catch (IOException e) {
			throw new DocumentException(BaseLogger.logException(MessagePRM.AsException.TIMEOUT_EXCEPTION), e);
		}
	}
	
	private static void getTimeWaitingParamFromFile(Properties waitProps) throws IOException {
		waitProps.load(CapabilityPropertyLoaderConfig.class.getResourceAsStream(
				System.getProperty(FileDirPRM.File.TEST_CONFIG_PROPERTIES, FileDirPRM.Path.TEST_CONFIG_PROPERTIES
				)));
	}
	
	private static String getTypeFromConfigReport(Properties reportProps) {
		String timeWaitingSetted = reportProps.getProperty(KeyPRM.PropertyKey.TIME_WAITING_DEFAULT);
		return timeWaitingSetted;
	}
	
}