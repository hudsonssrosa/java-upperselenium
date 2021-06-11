package br.com.upperselenium.base.constant;

public enum EnumContextPRM {

	DAYS_IN_EXECUTION("daysInExecution"),
	FAILURE_COUNT("failureCount"),
	FLAG_RUN_ALL("flagRunAll"),
	FLOW_NUMBER("flowNumber"),
	FLOW_TIME_RESULT("flowTimeresult"),
	FINISHED_TIME_FOR_SUITE("finishedTimeForSuite"),
	HOURS_IN_EXECUTION("hoursInExecution"),
	IGNORE_COUNT("ignoreCount"),
	MINUTES_IN_EXECUTION("minutesInExecution"),
	PASSED_COUNT("passedCount"),
	RANDOM_NUMBER("randomNumber"),
	RANDOM_CRYPTED_STRING("randomCryptedString"),
	RANDOM_PURE_SHA1_STRING("randomPureSHA1String"),
	RUN_COUNT("runCount"),
	SECONDS_IN_EXECUTION("secondsInExecution"),
	START_TIME_TO_SUITE_EXECUTION("startTimeForSuite"),
	TOTAL_TIME_EXECUTION("totalTimeExecution"),
	PAGE_PERFORMANCE_STAGE("pagePerformanceStage"),
	PAGE_PERFORMANCE_FILE("pagePerformanceFile");
	
	private String value;

	private EnumContextPRM(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
}