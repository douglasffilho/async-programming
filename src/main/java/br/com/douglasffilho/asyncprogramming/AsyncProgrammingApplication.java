package br.com.douglasffilho.asyncprogramming;

import br.com.douglasffilho.asyncprogramming.domain.Pizza;
import br.com.douglasffilho.asyncprogramming.domain.PizzaComponent;
import br.com.douglasffilho.asyncprogramming.providers.MakingPizzaProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AsyncProgrammingApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AsyncProgrammingApplication.class);

    @Autowired
    private MakingPizzaProvider provider;

    public static void main(String[] args) {
        SpringApplication.run(AsyncProgrammingApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        var start = System.currentTimeMillis();

        final Pizza pizza = this.provider.makePizza();

        var execTime = System.currentTimeMillis() - start;

        pizza.getComponents().forEach(PizzaComponent::echo);

        log.info("Pizza done in {}min", execTime/10);

        System.exit(0);
    }
}
