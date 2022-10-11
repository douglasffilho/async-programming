package br.com.douglasffilho.asyncprogramming.services.impl;

import br.com.douglasffilho.asyncprogramming.domain.Cheese;
import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import org.springframework.stereotype.Service;

@Service
public class MakePizzaCheeseService implements MakingPizzaService {
    @Override
    public void addComponentToPizza(final Pizza pizza) {
        final Cheese component = new Cheese();
        this.addComponentToPizza(pizza, component);
    }
}
