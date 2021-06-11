package br.com.upperselenium.base.util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import br.com.upperselenium.base.exception.DocumentException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;

/**
 * Helper class for converting Json files (dataProvider)
 * 
 * @author Hudson
 */
public class JSONUtil {

	public static <T> T getJSONData(String pathDataProvider, Class<T> classToInsertData) {
		Gson gson = new Gson();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(pathDataProvider));
		} catch (FileNotFoundException e) {
			e.getStackTrace();
			throw new DocumentException(BaseLogger.logException(MessagePRM.AsException.GET_JSON_EXCEPTION), e);
		}
		return gson.fromJson(reader, classToInsertData);
	}

	public static void createJSON(String pathDataProvider, Object object) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String json = gson.toJson(object);
		try {
			FileWriter writer = new FileWriter(pathDataProvider);
			writer.write(json);
			writer.close();
		} catch (IOException e) {
			throw new DocumentException(BaseLogger.logException(MessagePRM.AsException.WRITE_JSON_EXCEPTION), e);
		}
		BaseLogger.logInfoTextHighlight("DATA ADDED TO JSON FILE:");
		BaseLogger.logInfoSimpleText(json);
	}

}