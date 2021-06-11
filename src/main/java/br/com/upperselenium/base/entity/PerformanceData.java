package br.com.upperselenium.base.entity;

import java.util.List;

public class PerformanceData {

	private List<PageLoaded> degradationList;

	public List<PageLoaded> getDegradationList() {
		return degradationList;
	}

	public void setDegradationList(List<PageLoaded> degradationList) {
		this.degradationList = degradationList;
	}

	@Override
	public String toString() {
		return "PerformanceData [degradationList=" + degradationList + "]";
	}

}
