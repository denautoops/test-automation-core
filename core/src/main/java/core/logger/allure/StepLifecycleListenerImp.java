package core.logger.allure;

import core.logger.Logger;
import io.qameta.allure.listener.StepLifecycleListener;
import io.qameta.allure.model.StepResult;

public class StepLifecycleListenerImp implements StepLifecycleListener {
    protected static final Logger LOGGER = Logger.getInstance(StepLifecycleListenerImp.class);

    @Override
    public void beforeStepStart(StepResult result) {
        LOGGER.info(String.format("Allure: %s", result.getName()));
    }

}
