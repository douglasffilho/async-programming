package br.com.douglasffilho.asyncprogramming.providers.impl;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.providers.MakingPizzaProvider;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.concurrent.Executors;

@Component
@Profile("virtual-webflux")
public class MakePizzaWithVirtualWebFlux implements MakingPizzaProvider {
    private static final Scheduler scheduler = Schedulers.fromExecutor(Executors.newVirtualThreadPerTaskExecutor());

    private final List<MakingPizzaService> services;

    public MakePizzaWithVirtualWebFlux(final List<MakingPizzaService> services) {
        this.services = services;
    }

    @Override
    public Pizza makePizza() {
        final Pizza pizza = new Pizza();

        var observablesOfServices = this.services
                .stream()
                .map(service -> Mono.just(service).subscribeOn(scheduler));

        return Flux
                .fromStream(observablesOfServices)
                .flatMap(serviceObservable -> serviceObservable.map(service -> {
                    service.addComponentToPizza(pizza);
                    return pizza;
                }))
                .blockLast();
    }
}
