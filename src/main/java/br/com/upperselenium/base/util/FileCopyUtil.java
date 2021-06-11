package br.com.upperselenium.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;

/**
 * Helper class for removing directories and files.
 * 
 * @author Hudson
 *
 */
public class FileCopyUtil extends BaseLogger {

	public static void copyDirsAndFiles(String originFolderStr, String destFolderStr) {
		File originFolder = new File(originFolderStr);
		File destFolder = new File(destFolderStr);

		if (!destFolder.exists()) {
			try {
				copyFolder(originFolder, destFolder);
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
		System.out.println("Resources for Reports created!");
	}
	
	public static void copyFolder(File originFolder, File destFolder) throws IOException {
		try {
			if (originFolder.isDirectory()) {
				if (!destFolder.exists()) {
					destFolder.mkdir();
					System.out.println("Directory was copied from " + originFolder + " to " + destFolder);
				}
				String files[] = originFolder.list();

				for (String file : files) {
					File srcFile = new File(originFolder, file);
					File destFile = new File(destFolder, file);
					// Cópia recursiva
					copyFolder(srcFile, destFile);
				}
			} else {
				InputStream in = new FileInputStream(originFolder);
				FileOutputStream out = new FileOutputStream(destFolder);
				byte[] buffer = new byte[1024];
				int length;
				while ((length = in.read(buffer)) > 0) {
					out.write(buffer, 0, length);
				}
				in.close();
				out.close();
				System.out.println("File copied from " + originFolder + " to " + destFolder);
			}
		} catch (IOException iox) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.IO_REPORT_NOT_FOUND), iox);
		}
	}

}
