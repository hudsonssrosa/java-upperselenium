package br.com.upperselenium.base.listener;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

import br.com.upperselenium.base.BaseContext;
import br.com.upperselenium.base.exception.RunningException;
import br.com.upperselenium.base.logger.BaseLogger;
import br.com.upperselenium.base.constant.EnumContextPRM;
import br.com.upperselenium.base.constant.MessagePRM;

public class ObserverFinalResultsListener extends BlockJUnit4ClassRunner {

    public ObserverFinalResultsListener(Class<?> klass) throws InitializationError {
        super(klass);
    }

    @Override public void run(RunNotifier notifier){
    	try {
    		if ("0".equals(BaseContext.getParameter(EnumContextPRM.FLAG_RUN_ALL.getValue()).toString())){
    			notifier.addListener(new FinalResultsListener());
    		}
    		super.run(notifier);
		} catch (Exception e) {
			throw new RunningException(BaseLogger.logException(MessagePRM.AsException.OBSERVER_EXCEPTION), e);
		}
		   	
    }
    
}