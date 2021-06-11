package br.com.upperselenium.base;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.runner.RunWith;

import br.com.upperselenium.base.listener.ObserverFinalResultsListener;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.KeyPRM;
import br.com.upperselenium.base.util.PropertiesUtil;

/**
 * Base class used to store data throughout the execution of the WebDriver.
 * Variables can store data generated and used dynamically by the application
 * under test, for example.
 * 
 * @author HudsonRosa
 */
@RunWith(ObserverFinalResultsListener.class)
public abstract class BaseContext {
	private static List<Object> contextList;
	private static Map<String, Object> contextMap;
	private final static Map<String, Object> parameters = new HashMap<String, Object>();

	public static void setParameter(String parameterName, Object parameterValue) {
		parameters.put(parameterName, parameterValue);
		Boolean showContextOnLog = PropertiesUtil.getFlagFromProperties("true", FileDirPRM.File.TEST_CONFIG_PROPERTIES,
				FileDirPRM.Path.TEST_CONFIG_PROPERTIES, KeyPRM.PropertyKey.SHOW_CONTEXT_VARIABLES);
		if (showContextOnLog) {
			BaseLogger.logInfoEvents("Context @" + parameterName + " added: " + parameterValue);
		}
	}

	public static Object getParameter(String parameterName) {
		return parameters.get(parameterName);
	}

	@SuppressWarnings("unchecked")
	public static void addParameterOnList(String contextConstant, String valueToInsert) {
		contextList = (List<Object>) getParameter(contextConstant);
		if (contextList == null) {
			contextList = new ArrayList<Object>();
		}
		contextList.add(valueToInsert);
		setParameter(contextConstant, contextList);
	}

	@SuppressWarnings("unchecked")
	public static void addParameterOnList(String contextConstant, Object objectToInsert) {
		contextList = (List<Object>) getParameter(contextConstant);
		if (contextList == null) {
			contextList = new ArrayList<Object>();
		}
		contextList.add(objectToInsert);
		setParameter(contextConstant, contextList);
	}

	@SuppressWarnings("unchecked")
	public static void addAllParametersOnList(String contextConstant, Collection<? extends Object> objectToInsert) {
		contextList = (List<Object>) getParameter(contextConstant);
		if (contextList == null) {
			contextList = new ArrayList<Object>();
		}
		contextList.addAll(objectToInsert);
		setParameter(contextConstant, contextList);
	}

	@SuppressWarnings("unchecked")
	public static void addMapParameters(String contextConstant, String keyToInsert, Object valueToInsert) {
		contextMap = (Map<String, Object>) getParameter(contextConstant);
		if (contextMap == null) {
			contextMap = new LinkedHashMap<String, Object>();
		}
		contextMap.put(keyToInsert, valueToInsert);
		setParameter(contextConstant, contextMap);
	}

	@SuppressWarnings("unchecked")
	public static void removeMapParameters(String contextConstant, Object keyToInsert) {
		contextMap = (Map<String, Object>) getParameter(contextConstant);
		if (contextMap == null) {
			contextMap = new LinkedHashMap<String, Object>();
		}
		contextMap.remove(keyToInsert);
		setParameter(contextConstant, contextMap);
	}

	public static Map<String, Object> getContextMap(String contextConstant) {
		Map<String, Object> map = contextMap;
		return map;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getContextMap(String contextConstant, Object objectToInsert) {
		Map<String, Object> map = (Map<String, Object>) contextMap.get(objectToInsert);
		return map;
	}

	public static String getParameterOfList(String contextConstant, int index) {
		return contextList.get(index).toString();
	}

	public static List<Object> getListParameters(String contextConstant) {
		return contextList;
	}
}