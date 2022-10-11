package br.com.douglasffilho.asyncprogramming.services;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.domain.PizzaComponent;

public interface MakingPizzaService {
    void addComponentToPizza(final Pizza pizza);

    default void addComponentToPizza(final Pizza pizza, final PizzaComponent component) {
        try {
            Thread.sleep(component.getMakingTime());
            pizza.addComponent(component);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
