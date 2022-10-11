package br.com.douglasffilho.asyncprogramming.services.impl;

import br.com.douglasffilho.asyncprogramming.domain.Bread;
import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import org.springframework.stereotype.Service;

@Service
public class MakePizzaBreadService implements MakingPizzaService {
    @Override
    public void addComponentToPizza(final Pizza pizza) {
        final Bread component = new Bread();
        this.addComponentToPizza(pizza, component);
    }
}
