package br.com.upperselenium.base.entity;

import java.util.List;

public class GraphData {

	private List<GraphParameter> dataList;

	public List<GraphParameter> getDataList() {
		return dataList;
	}

	public void setDataList(List<GraphParameter> dataList) {
		this.dataList = dataList;
	}

	@Override
	public String toString() {
		return "GraphData [dataList=" + dataList + "]";
	}

}
