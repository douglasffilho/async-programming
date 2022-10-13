package br.com.douglasffilho.asyncprogramming.providers.impl;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.providers.MakingPizzaProvider;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;
import java.util.stream.Stream;

/**
 * Take a look at the fact that define a Task Executor configuration is not
 * required because Spring Boot already do this by default.
 * But you can configure a Bean for TaskExecutor and customize it to get dedicated threads
 * for your process. Look at config.TaskExecutorConfig
 */

@Component
@Profile("async")
public class MakePizzaWithAsyncTasks implements MakingPizzaProvider {
    private final List<MakingPizzaService> services;

    public MakePizzaWithAsyncTasks(final List<MakingPizzaService> services) {
        this.services = services;
    }

    @Override
    public Pizza makePizza() {
        final Pizza pizza = new Pizza();

        final Stream<Future<Pizza>> tasks = this.services
                .parallelStream()
                .map(service -> service.asyncAddComponentToPizza(pizza));

        return tasks.flatMap(task -> {
                    try {
                        return Stream.of(task.get());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .findAny()
                .orElse(pizza);
    }
}
