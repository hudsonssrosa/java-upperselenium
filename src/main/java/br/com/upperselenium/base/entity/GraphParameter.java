package br.com.upperselenium.base.entity;

public class GraphParameter {

	private String stage;
	private String name;
	private double value;

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "GraphParameter [stage=" + stage + ", name=" + name + ", value=" + value + "]";
	}

}
