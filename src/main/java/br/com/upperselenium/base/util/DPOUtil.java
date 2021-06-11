package br.com.upperselenium.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import com.google.gson.Gson;

import br.com.upperselenium.base.exception.DocumentException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.constant.SymbolPRM;

/**
 * Helper class for converting Json files (dataProvider). 
 * 
 * @author Hudson
 */
public class DPOUtil {

	public <T> T getDataProvider(String pathDataProvider, Class<T> classDataProvider) {	
		BufferedReader reader = null;
		try {
			File file = new File(ClassLoader.getSystemResource(pathDataProvider).getFile());
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), SymbolPRM.UTF8));
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			throw new DocumentException(BaseLogger.logException(MessagePRM.AsException.DATAPROVIDER_EXCEPTION), e);
		}
		Gson gson = new Gson();
		return gson.fromJson(reader, classDataProvider);
	}
}