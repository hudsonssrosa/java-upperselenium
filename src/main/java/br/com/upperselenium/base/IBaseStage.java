package br.com.upperselenium.base;

public interface IBaseStage {
	
	<D> D loadDataProviderFile(Class<D> dpoClass, String dataProviderPathFile);
	
	void initMappedPages();
	
	void runStage();
	
	void runValidations();
	
	void runCompleteStage();
	
	void getCurrentStage();
	
}
