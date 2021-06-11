package br.com.upperselenium.base.util;

import java.util.Locale;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Interval;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

import br.com.upperselenium.base.constant.PatternPRM;

/**
 * Helper class for handling dates. (uses the JodaTime library)
 * 
 * @author Hudson
 */
public class JodaTimeUtil {

	private static final String EMPTY = "";
	private static final int ONE = 1;
	private static String CURRENT_YEAR = "CURRENT_YEAR";
	private static String NEXT_YEAR = "NEXT_YEAR";
	private static String LAST_YEAR = "LAST_YEAR";
	private static final int _MILIS = 1000;

	private JodaTimeUtil() {
	}
	
	/**
	 * Method that returns the difference between the start and end time interval, in milliseconds
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long diffDurationIntervalInMillis(DateTime start, DateTime end) {
		Interval interval = new Interval(start, end);
		Duration duration = interval.toDuration();
		return duration.getMillis(); 
	}
	
	/**
	 * Method that returns the difference between start and end time interval, in days
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long diffDurationIntervalInDays(DateTime start, DateTime end) {
		Interval interval = new Interval(start, end);
		Duration duration = interval.toDuration();
		return duration.getStandardDays();  
	}

	/**
	 * Method that returns the difference between start and end time interval, in hours
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long diffDurationIntervalInHours(DateTime start, DateTime end) {
		Interval interval = new Interval(start, end);
		Duration duration = interval.toDuration();
		return duration.getStandardHours();  
	}	
	
	/**
	 * Method that returns the difference between start and end time interval, in seconds
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long diffDurationIntervalInMinutes(DateTime start, DateTime end) {
		Interval interval = new Interval(start, end);
		Duration duration = interval.toDuration();
		return duration.getStandardMinutes();  
	}	
	
	/**
	 * Method that returns the difference between start and end time interval, in seconds
	 * 
	 * @param start
	 * @param end
	 * @return
	 */
	public static long diffDurationIntervalInSeconds(DateTime start, DateTime end) {
		Interval interval = new Interval(start, end);
		Duration duration = interval.toDuration();
		return duration.getStandardSeconds();  
	}	
	
	
	/**
	 * Method for converting Milliseconds to Seconds
	 * 
	 * @param result
	 * @return
	 */
	@SuppressWarnings("unused")
	private static int convertMilisInSecs(int number) {
		int seconds = (number / _MILIS) % 60 ;
		return seconds;
	}

	/**
	 * Method to return the duration time between two DateTime intervals. The return will be in days, hours, minutes and seconds
	 * 
	 * @param startTimeContext
	 * @param endTimeContext
	 * @return
	 */
	public static String getDurationTimeDDHHMMSS(String startTimeContext, String endTimeContext) {
		DateTime start = JodaTimeUtil.convertStringInDateTime(startTimeContext);
		DateTime end = JodaTimeUtil.convertStringInDateTime(endTimeContext);
		
		Duration duration = new Duration(start, end);
		PeriodFormatter formatter = new PeriodFormatterBuilder()
		     .appendDays().appendSuffix(" dia(s) ")
		     .appendHours().appendSuffix(" hora(s) ")
		     .appendMinutes().appendSuffix(" minuto(s) ")
		     .appendSeconds().appendSuffix(" segundo(s)")
		     .toFormatter();
		String timeDuration = formatter.print(duration.toPeriod());
		return timeDuration;
	}
	
	/**
	 * Method for converting DateTime to a String
	 * 
	 * @param dateTime
	 * @return
	 */
	public static String convertDateTimeInString(DateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormat.forPattern(PatternPRM.Period.FORMAT_TIME_DDMMYYYYHHMMSS);
		String dateFormatted = dateTime.toString(formatter);
		return dateFormatted;
	}
	
	/**
	 * Method for converting a String to a DateTime
	 * 
	 * @param dateTime
	 * @return
	 */
	public static DateTime convertStringInDateTime(String dateTime) {
		DateTime date = DateTime.parse(dateTime,DateTimeFormat.forPattern(PatternPRM.Period.FORMAT_TIME_DDMMYYYYHHMMSS));
		return date;
	}
	
	/**
	 * Method that increments the month according to the current date and returns "month_by_length/yyyy".
	 * ie: Current Date: 12/18/2015, with an addition of 5 months, return will be 05/2016
	 * 
	 * @param increment
	 * @return String
	 */
	public static String plusNumericMonthPatternMMYYYY(Integer increment) {
		DateTimeFormatter monthAndYearFormat = DateTimeFormat.forPattern(PatternPRM.Date.FORMAT_DATE_MMYYYY).withLocale(new Locale("pt", "BR"));
		String monthAndYear = EMPTY + DateTime.now().plusMonths(increment).toString(monthAndYearFormat);
		return monthAndYear;
	}

	/**
	 * Method that increments the month according to the current date and returns "month_by_length/yyyy".
	 * ie: Current Date: 12/18/2015, with an addition of 5 months, return will be May/2016
	 * 
	 * @param increment
	 * @return String
	 */
	public static String plusMonthNamePatternMMMMMYYYY(Integer increment) {
		DateTimeFormatter monthAndYearFormat = DateTimeFormat.forPattern(PatternPRM.Date.FORMAT_DATE_MMMMMYYYY).withLocale(new Locale("pt", "BR"));
		String monthAndYear = EMPTY + DateTime.now().plusMonths(increment).toString(monthAndYearFormat);
		return monthAndYear;
	}
	
	/**
	 * Converts Calendar Days to a specific date according to the parameter passed 
	 * 
	 * @param currentDaysInNumber
	 * @return
	 */
	public static String parseNumericCurrentDaysToDDMMYYYY(String currentDaysInNumber) {
		if(StringUtils.isBlank(currentDaysInNumber)){
			return null;
		}
		Integer parsedDaysToInteger = Integer.parseInt(currentDaysInNumber);		
		DateTime dateNow = DateTime.now();
		dateNow = dateNow.plusDays(parsedDaysToInteger);		
		DateTimeFormatter dataFormat = formatDatePatternDDMMYYYY();
		String dateInCurrentDays = EMPTY + dateNow.toString(dataFormat);
		return dateInCurrentDays;
	}	
	
	/**
	 * Method responsible for defining the year to be replaced in the data mass,
 	 * through the constants:
	 * CURRENT_YEAR, NEXT_YEAR, PREVIOUS_YEAR
	 * 
	 * @param date
	 * @return
	 */	
	public static String replaceYear(String date) {
		String year = date;
		if (date.toUpperCase().contains(CURRENT_YEAR)) {
			year = date.replace(CURRENT_YEAR, EMPTY + getCurrentYear());
		} else if (date.toUpperCase().contains(NEXT_YEAR)) {
			year = date.replace(NEXT_YEAR, EMPTY + getFormatedNextYear(formatYearPatternYYYY()));
		} else if (date.toUpperCase().contains(LAST_YEAR)){
			year = date.replace(LAST_YEAR, getFormatedLastYear(formatYearPatternYYYY()));
		}
		return year;
	}
	
	
	// RETURNS FORMATTED DATES
	
	/**
	 * Returns Current Year as DateTime
	 * 
	 * @return DateTime;
	 */
	public static int getCurrentYear() {
		return DateTime.now().getYear();
	}
	
	/**
	 * Returns Next Year formatted
	 * 
	 * @param yearValue
	 * @param now
	 * @return DateTime
	 */
	public static String getFormatedNextYear(DateTimeFormatter yearValue) {
		return DateTime.now().plusYears(ONE).toString(yearValue);
	}

	/**
	 * Returns the previous year formatted
	 * 
	 * @param yearValue
	 * @param now
	 * @return DateTime, DateTimeFormatter
	 */
	public static String getFormatedLastYear(DateTimeFormatter yearValue) {
		return DateTime.now().minusYears(ONE).toString(yearValue);
	}
	
	/**
	 * Returns Current Month formatted
	 * 
	 * @param yearValue
	 * @return DateTime, DateTimeFormatter
	 */
	public static String getFormatedCurrentMonthNumber(DateTimeFormatter monthValue) {
		return DateTime.now().toString(formatMonthPatternMM());
	}
	
	/**
	 * Returns Current Month formatted
	 * 
	 * @param yearValue
	 * @return DateTime, DateTimeFormatter
	 */
	public static String getFormatedCurrentMonthName() {
		return DateTime.now().toString(formatMonthPatternMMMMM());
	}
	
	/**
	 * Gets Current Date with default formatting
	 * 
	 * @return DateTime, DateTimeFormatter
	 */
	public static String getCurrentDatePatternDDMMYYYY() {
		return DateTime.now().toString(formatDatePatternDDMMYYYY());
	}

	/**
	 * Gets Current Day with default formatting
	 * @return DateTime, DateTimeFormatter
	 */
	public static String getCurrentDayPatternDD() {
		return DateTime.now().toString(formatDayPatternDD());
	}
	
	/**
	 * Gets Current Date with default formatting
	 * 
	 * @return DateTime, DateTimeFormatter
	 */
	public static String getCurrentDateWithCustomPattern(String patternDate) {
		return DateTime.now().toString(formatDatePtBrWithCustomPattern(patternDate));
	}
	
	// RETRIEVES DATES IN STRING FORMAT
	
	/**
	 * Method that returns the Current Year in "yyyy" format.
	 * 
	 * @return String
	 */
	public static String getStringCurrentYear() {
		String year = EMPTY;
		year = year + getCurrentYear();
		return year;
	}	
	
	/**
	 * Method that returns the Current Month in "MM" format.
	 * 
	 * @return String
	 */
	public static String getStringCurrentMonth() {
		String month = EMPTY;
		month = month + DateTime.now().getMonthOfYear();
		return month;
	}

	/**
	 * Method that returns the Current Day in "dd" format.
	 * 
	 * @return String
	 */
	public static String getStringCurrentDay() {
		String day = EMPTY;
		day = day + DateTime.now().getDayOfMonth();
		return day;
	}
	
	
	// DATE FORMATTERS DE DATA
	
	/**
	 * Sets the year formatting to the default "yyyy".
	 * 
	 * @return DateTimeFormatter
	 */
	public static DateTimeFormatter formatYearPatternYYYY() {
		DateTimeFormatter yearFormat = DateTimeFormat.forPattern(PatternPRM.Date.FORMAT_DATE_YYYY).withLocale(new Locale("pt", "PT"));
		return yearFormat;
	}
	
	/**
	 * Sets month formatting to default "MM".
	 * 
	 * @return DateTimeFormatter
	 */
	public static DateTimeFormatter formatMonthPatternMM() {
		DateTimeFormatter monthFormat = DateTimeFormat.forPattern(PatternPRM.Date.FORMAT_DATE_MM).withLocale(new Locale("pt", "PT"));
		return monthFormat;
	}
	
	/**
	 * Sets the month long formatting to the default "MMMMM".
	 * 
	 * @return DateTimeFormatter
	 */
	public static DateTimeFormatter formatMonthPatternMMMMM() {
		DateTimeFormatter monthFormat = DateTimeFormat.forPattern(PatternPRM.Date.FORMAT_DATE_MMMMM).withLocale(new Locale("pt", "PT"));
		return monthFormat;
	}
	
	/**
	 * Returns the current date formatted as dd/MM/yyyy.
	 * 
	 * @return DateTimeFormatter
	 */
	public static DateTimeFormatter formatDatePatternDDMMYYYY() {
		DateTimeFormatter dateFormat = DateTimeFormat.forPattern(PatternPRM.Date.FORMAT_DATE_DDMMYYYY).withLocale(new Locale("pt", "PT"));
		return dateFormat;
	}	

	/**
	 * Returns the current day formatted as dd.
	 * 
	 * @return DateTimeFormatter
	 */
	public static DateTimeFormatter formatDayPatternDD() {
		DateTimeFormatter dateFormat = DateTimeFormat.forPattern(PatternPRM.Date.FORMAT_DATE_DD).withLocale(new Locale("pt", "PT"));
		return dateFormat;
	}	
	
	/**
	 * Sets the locale to pt-BR relative to the date formatting pattern passed by the parameter.
	 * 
	 * @param patternDate
	 * @return DateTimeFormatter
	 */
	public static DateTimeFormatter formatDatePtBrWithCustomPattern(String patternDate) {
		return DateTimeFormat.forPattern(patternDate).withLocale(new Locale("pt", "BR"));
	}

	public static String getCurrentPeriodPatternHHMMSS() {
		return DateTime.now().toString(formatPeriodPatternHHMMSS());
	}

	public static String getCurrentPeriodPatternDDMMYYYYHHMMSS() {
		return DateTime.now().toString(formatPeriodPatternDDMMYYYYHHMMSS());
	}
	
	public static DateTimeFormatter formatPeriodPatternDDMMYYYYHHMMSS() {
		DateTimeFormatter timeFormat = DateTimeFormat.forPattern(PatternPRM.Period.FORMAT_TIME_DDMMYYYYHHMMSS).withLocale(new Locale("pt", "PT"));
		return timeFormat;
	}
	
	public static DateTimeFormatter formatPeriodPatternHHMMSS() {
		DateTimeFormatter timeFormat = DateTimeFormat.forPattern(PatternPRM.Period.FORMAT_TIME_HHMMSS).withLocale(new Locale("pt", "PT"));
		return timeFormat;
	}	
		
}
