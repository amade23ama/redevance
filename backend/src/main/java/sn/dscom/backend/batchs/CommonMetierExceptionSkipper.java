package sn.dscom.backend.batchs;

import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import sn.dscom.backend.common.exception.CommonMetierException;

public class CommonMetierExceptionSkipper implements SkipPolicy {



    @Override
    public boolean shouldSkip(Throwable t, long skipCount) throws SkipLimitExceededException {
        return t instanceof CommonMetierException;
    }
}