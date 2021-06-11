package br.com.upperselenium.base.asset;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import br.com.upperselenium.base.BaseContext;
import br.com.upperselenium.base.exception.DocumentException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.logger.HtmlFormatter;
import br.com.upperselenium.base.constant.CmdPRM;
import br.com.upperselenium.base.constant.ContextPRM;
import br.com.upperselenium.base.constant.FileDirPRM;
import br.com.upperselenium.base.constant.KeyPRM;
import br.com.upperselenium.base.constant.MessagePRM;
import br.com.upperselenium.base.util.FileRemoverUtil;
import br.com.upperselenium.base.util.PropertiesUtil;

public abstract class ReportConfig extends HtmlFormatter {

	public static void deleteOldReportFiles() {
		Properties reportProps = new Properties();
		try {
			PropertiesUtil.loadPropertiesFile(reportProps, FileDirPRM.File.TEST_CONFIG_PROPERTIES,
					FileDirPRM.Path.TEST_CONFIG_PROPERTIES);
			String enableDeleteReport = PropertiesUtil.getKeyTypeFromProperties(reportProps,
					KeyPRM.PropertyKey.DELETE_ALL_REPORTS);
			String reportDirectoryCustom = PropertiesUtil.getKeyTypeFromProperties(reportProps,
					KeyPRM.PropertyKey.REPORT_DIRECTORY);
			if (BaseContext.getListParameters(ContextPRM.FLAG_DELETE_REPORTS_LIST).size() == 2) {
				deleteAllEvidences(reportDirectoryCustom);
			} else if ("true".equals(enableDeleteReport)) {
				deleteAllEvidences(reportDirectoryCustom);
			}
		} catch (IOException e) {
			throw new DocumentException(
					BaseLogger.logException(MessagePRM.AsException.FILE_EXCLUSION_ERROR + FileDirPRM.REPORT), e);
		}
	}

	private static void deleteAllEvidences(String reportDir) {
		File dirEvidences = new File(reportDir + FileDirPRM.TEST_EVIDENCES);
		FileRemoverUtil.deleteOldDirAndSubdirs(dirEvidences);
		File dirResources = new File(reportDir + "/" + FileDirPRM.REPORT_RESOURCES);
		FileRemoverUtil.deleteOldDirAndSubdirs(dirResources);
		File dirReport = new File(reportDir);
		FileRemoverUtil.deleteAllFilesfromDir(dirReport);
	}

	public static String getReportDirectory() {
		Properties reportProps = new Properties();
		try {
			PropertiesUtil.loadPropertiesFile(reportProps, FileDirPRM.File.TEST_CONFIG_PROPERTIES,
					FileDirPRM.Path.TEST_CONFIG_PROPERTIES);
			String reportDirectoryCustom = PropertiesUtil.getKeyTypeFromProperties(reportProps,
					KeyPRM.PropertyKey.REPORT_DIRECTORY);
			return getValidReportDirectory(reportDirectoryCustom);
		} catch (IOException e) {
			throw new DocumentException(BaseLogger.logException(
					MessagePRM.AsException.FILE_EXCLUSION_ERROR), e);
		}
	}

	private static String getValidReportDirectory(String reportDirectoryCustom) {
		String hasSlash = reportDirectoryCustom.substring(reportDirectoryCustom.length() - 1);
		if (hasSlash.contains("\\") || hasSlash.contains(CmdPRM.Symbol.SLASH)) {
			return reportDirectoryCustom;
		} else {
			return reportDirectoryCustom + "\\";
		}
	}

	public static String getEvidenceDirectory() {
		if (getReportDirectory().equals(FileDirPRM.REPORT)) {
			String fixedEvidencesLocation = FileDirPRM.REPORT + FileDirPRM.TEST_EVIDENCES;
			return fixedEvidencesLocation;
		} else {
			String customEvidencesLocation = getReportDirectory() + FileDirPRM.TEST_EVIDENCES;
			return customEvidencesLocation;
		}
	}

	public static String getAncestorDirectory() {
		if (getReportDirectory().equals(FileDirPRM.REPORT)) {
			return "..\\";
		} else {
			return "";
		}
	}

}