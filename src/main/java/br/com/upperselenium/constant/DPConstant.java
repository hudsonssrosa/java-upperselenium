package br.com.upperselenium.constant;


/**
 * Constants Interface: Constants must not be abbreviated and/or must have the same name corresponding to their value,
 * ignoring only special characters (with full naming possibilities for the constant).
 *
 * Ex: String ABOUT_US = "About Us";
 * Ex: String _200_CELSIUS_DEGREES = "200°C";
 * 
 * @author Hudson
 *
 */
public interface DPConstant {
	
	/**
 	 * Set the .json file path from "src/test/resources/."
	 * That is, consider the module constants from "dataprovider/...".
	 * 
	 * @author HudsonRosa
	 */
	interface Path {
		
		String PROJECT_PACKAGE = "br.com.upperselenium.";
		String DATAPROVIDER_FOLDER = "dataprovider/";
		
		// Directory settings of the DP files from the Example tests
		String SAMPLE_DP_FOLDER = DATAPROVIDER_FOLDER + "sample/";
		String LOGIN_SAMPLE_DP_FOLDER = DATAPROVIDER_FOLDER + "sample/";
		
	}

}
