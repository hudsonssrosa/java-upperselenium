package br.com.upperselenium.base.constant;

/**
 * Interface de Constantes: As constantes não devem ser abreviadas e/ou devem possuir o mesmo nome correspondente ao seu valor,
 * ignorando-se apenas caracteres especiais (com possibilidades de nomenclatura por extenso para a constante).
 *
 * Ex: String SOBRE_NOS = "Sobre Nós";
 * Ex: String _200_GRAUS_CELSIUS = "200°C";
 * 
 * @author Hudson
 *
 */
public interface PatternPRM {

	String FORMAT_DATETIME_DDMMYYYY_HHMMSS = "dd/MM/yyyy - HH:mm:ss";
	String FORMAT_DATETIME_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
	String FORMAT_DATETIME_DDMMYYYY_AS_HHMMSS = "dd/MM/yyyy às HH:mm:ss";
	String FORMAT_DATETIME_DDMMYYYY_AT_HHMMSS = "dd/MM/yyyy at HH:mm:ss";
	String FORMAT_DATETIME_DD_MM_YYYY_HH_MM_SS = "dd-MM-yyyy_at_HH-mm-ss";
	String FORMAT_DATETIME_DDMMYYYYHHMMSSMILLIS = "ddMMyyyyHHmmssSSS";
	
	interface Date {
		
		String FORMAT_DATE_DD = "dd";		
		String FORMAT_DATE_DDMMYYYY = "dd/MM/yyyy";
		String FORMAT_DATE_MM = "MM";
		String FORMAT_DATE_MMMMM = "MMMMM";
		String FORMAT_DATE_MMYYYY = "MM/yyyy";
		String FORMAT_DATE_MMMMMYYYY = "MMMMM/yyyy";
		String FORMAT_DATE_YYYY = "yyyy";
				
	}
	
	interface Period {
		
		String FORMAT_TIME_DDMMYYYYHHMMSS = "dd/MM/yyyy HH:mm:ss";
		String FORMAT_TIME_HHMMSS = "HH:mm:ss";
		String FORMAT_TIME_HH = "HH";
		String FORMAT_TIME_MM = "mm";
		String FORMAT_TIME_SS = "ss";
		
	}
	
	interface Month {
		
		String JANUARY = "January";
		String FEBRUARY = "February";
		String MARCH = "March";
		String APRIL = "April";
		String MAY = "May";
		String JUNE = "June";
		String JULY = "July";
		String AUGUST = "August";
		String SEPTEMBER = "September";
		String OCTOBER = "October";
		String NOVEMBER = "November";
		String DECEMBER = "December";	
		
		String JAN_ = JANUARY.substring(0, 2);
		String FEB_ = FEBRUARY.substring(0, 2);
		String MAR_ = MARCH.substring(0, 2);
		String APR_ = APRIL.substring(0, 2);
		String MAY_ = MAY.substring(0, 2);
		String JUN_ = JUNE.substring(0, 2);
		String JUL_ = JULY.substring(0, 2);
		String AUG_ = AUGUST.substring(0, 2);
		String SEP_ = SEPTEMBER.substring(0, 2);
		String OCT_ = OCTOBER.substring(0, 2);
		String NOV_ = NOVEMBER.substring(0, 2);
		String DEC_ = DECEMBER.substring(0, 2);		
		
	}

}
