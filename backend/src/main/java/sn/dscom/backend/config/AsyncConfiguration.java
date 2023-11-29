package sn.dscom.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * Async Configuration
 * @author diome
 */
@Configuration
@EnableAsync
public class AsyncConfiguration  {

    /**
     * asyncExecutor
     * @return Executor
     */
    @Bean
    public Executor asyncExecutor() {
        return new ThreadPoolTaskExecutor();
    }
}
