package br.com.upperselenium.base.util;

import java.io.File;

import br.com.upperselenium.base.exception.DocumentException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.MessagePRM;

/**
 * Helper class for removing directories and files.
 * 
 * @author Hudson
 *
 */
public class FileRemoverUtil extends BaseLogger {

	public void deleteOldFile(String pathOfFileOnProject) {
		try {
			File file = new File(pathOfFileOnProject);
			if (file.delete()) {
				System.out.println(file.getName() + " was successfully removed!");
			} else {
				System.out.println(file.getName() + " not found!");
			}
		} catch (Exception e) {
			throw new DocumentException(BaseLogger.logException(MessagePRM.AsException.FILE_EXCLUSION_ERROR + pathOfFileOnProject), e);
		}
	}
	
    public static boolean deleteOldDirAndSubdirs(File dirName) {
        if (dirName.isDirectory()) {
            String[] children = dirName.list();
            for (int i=0; i<children.length; i++) { 
            	System.out.println(children[i] + " in directory '" + dirName.getName() + "' was successfully removed!");
                boolean deletedSuccess = deleteOldDirAndSubdirs(new File(dirName, children[i]));
                if (!deletedSuccess) {
                    return false;
                }
            }
        }
        return dirName.delete();
    }

	public static void deleteAllFilesfromDir(File f) {
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (File file : files) {
				file.delete();
			}
		}
	}
    
}
