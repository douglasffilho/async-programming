package br.com.douglasffilho.asyncprogramming.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PizzaComponent {
    private Logger log = LoggerFactory.getLogger(PizzaComponent.class);

    private final long makingTime;

    public PizzaComponent(final long makingTime) {
        this.makingTime = makingTime;
    }

    public long getMakingTime() {
        return this.makingTime;
    }

    public void echo() {
        log.info("[{}] :: done in {}min", super.getClass().getSimpleName(), this.makingTime/100);
    }
}
