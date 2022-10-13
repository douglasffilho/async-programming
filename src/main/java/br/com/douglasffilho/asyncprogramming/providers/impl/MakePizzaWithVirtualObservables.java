package br.com.douglasffilho.asyncprogramming.providers.impl;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.providers.MakingPizzaProvider;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.schedulers.Schedulers;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * To run correctly pick Java JDK 19 with language level Preview
 */

@Component
@Profile("virtual-observables")
public class MakePizzaWithVirtualObservables implements MakingPizzaProvider {
    private static final Scheduler scheduler = Schedulers.from(Executors.newVirtualThreadPerTaskExecutor());

    private final List<MakingPizzaService> services;

    public MakePizzaWithVirtualObservables(final List<MakingPizzaService> services) {
        this.services = services;
    }

    @Override
    public Pizza makePizza() {
        final Pizza pizza = new Pizza();

        var observablesOfServices = this.services
                .stream()
                .map(service -> Observable.just(service).subscribeOn(scheduler));

        return Observable
                .fromStream(observablesOfServices)
                .flatMap(serviceObservable -> serviceObservable.map(service -> {
                    service.addComponentToPizza(pizza);
                    return pizza;
                }))
                .blockingLast();
    }
}
