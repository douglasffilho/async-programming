package br.com.douglasffilho.asyncprogramming.domain;

import java.util.ArrayList;
import java.util.List;

public class Pizza {
    private final List<PizzaComponent> components;

    public Pizza() {
        this.components = new ArrayList<>();
    }

    public void addComponent(final PizzaComponent component) {
        this.components.add(component);
    }

    public List<PizzaComponent> getComponents() {
        return this.components;
    }
}
