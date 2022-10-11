package br.com.douglasffilho.asyncprogramming.services.impl;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.domain.Tomato;
import br.com.douglasffilho.asyncprogramming.services.MakingPizzaService;
import org.springframework.stereotype.Service;

@Service
public class MakePizzaTomatoesService implements MakingPizzaService {
    @Override
    public void addComponentToPizza(final Pizza pizza) {
        final Tomato component = new Tomato();
        this.addComponentToPizza(pizza, component);
    }
}
