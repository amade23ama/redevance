package sn.dscom.backend.batchs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

public class MyStepListener implements StepExecutionListener {
    private static final Logger log= LoggerFactory.getLogger(MyStepListener .class);
    private StepExecution stepExecution;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        this.stepExecution = stepExecution;
        stepExecution.getJobExecution().getExecutionContext().putInt("totalChargement",0);
        stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementReDeposes",0);
        stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementDeposes",0);
        stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementDoublons",0);
        stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementDeposesSucces",0);
        stepExecution.getJobExecution().getExecutionContext().putInt("lNbChargementError",0);


    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return stepExecution.getExitStatus();
    }
}
