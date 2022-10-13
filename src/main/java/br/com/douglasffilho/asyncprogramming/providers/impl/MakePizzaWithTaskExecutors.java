package br.com.douglasffilho.asyncprogramming.providers.impl;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.providers.MakingPizzaProvider;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Stream;

@Component
@Profile("future")
public class MakePizzaWithTaskExecutors implements MakingPizzaProvider {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(3);

    private final List<MakingPizzaService> services;

    public MakePizzaWithTaskExecutors(final List<MakingPizzaService> services) {
        this.services = services;
    }

    @Override
    public Pizza makePizza() {
        final Pizza pizza = new Pizza();

        final Stream<Future<Pizza>> tasks = this.services
                .parallelStream()
                .map(service -> EXECUTOR.submit(() -> {
                    service.addComponentToPizza(pizza);
                    return pizza;
                }));

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
