package br.com.upperselenium.base.entity;

public class PageLoaded {

	private String currentStage;
	private String currentUrl;
	private double timePageLoadSec;

	public String getCurrentStage() {
		return currentStage;
	}

	public void setCurrentStage(String currentStage) {
		this.currentStage = currentStage;
	}

	public String getCurrentUrl() {
		return currentUrl;
	}

	public void setCurrentUrl(String currentUrl) {
		this.currentUrl = currentUrl;
	}

	public double getTimePageLoadSec() {
		return timePageLoadSec;
	}

	public void setTimePageLoadSec(double timePageLoadSec) {
		this.timePageLoadSec = timePageLoadSec;
	}

	@Override
	public String toString() {
		return "PageLoaded [currentStage=" + currentStage + ", currentUrl=" + currentUrl + ", timePageLoadSec="
				+ timePageLoadSec + "]";
	}

}
