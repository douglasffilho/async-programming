package br.com.douglasffilho.asyncprogramming.providers.impl;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.providers.MakingPizzaProvider;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Stream;

@Component
@Profile("threads")
public class MakePizzaWithThreads implements MakingPizzaProvider {
    private final List<MakingPizzaService> services;

    public MakePizzaWithThreads(final List<MakingPizzaService> services) {
        this.services = services;
    }

    @Override
    public Pizza makePizza() {
        final Pizza pizza = new Pizza();

        final Stream<Thread> servicesThreads = this.services
                .parallelStream()
                .map(service -> new Thread(() -> service.addComponentToPizza(pizza)));

        return servicesThreads
                .flatMap(thread -> {
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    return Stream.of(pizza);
                })
                .findAny()
                .orElse(pizza);
    }
}
