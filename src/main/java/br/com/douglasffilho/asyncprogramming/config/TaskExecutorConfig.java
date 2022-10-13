package br.com.douglasffilho.asyncprogramming.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@Profile("custom-executor")
public class TaskExecutorConfig {
    private static final int AVAILABLE_THREADS = 3;
    private static final int AVAILABLE_THREADS_QUEUE_FACTOR = 10;


    @Bean("threadPoolTaskExecutor")
    public Executor asyncExecutor() {
        var executor = new ThreadPoolTaskExecutor();

        executor.setCorePoolSize(AVAILABLE_THREADS);
        executor.setMaxPoolSize(AVAILABLE_THREADS);
        executor.setQueueCapacity(AVAILABLE_THREADS_QUEUE_FACTOR * AVAILABLE_THREADS);
        executor.setThreadNamePrefix("AsyncThread");
        executor.initialize();

        return executor;
    }
}
