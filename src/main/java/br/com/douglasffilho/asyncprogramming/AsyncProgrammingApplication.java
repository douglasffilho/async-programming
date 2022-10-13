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

import java.util.List;

@SpringBootApplication
public class AsyncProgrammingApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(AsyncProgrammingApplication.class);

    @Autowired
    private List<MakingPizzaProvider> providers;

    public static void main(String[] args) {
        SpringApplication.run(AsyncProgrammingApplication.class, args);
    }

    @Override
    public void run(final String... args) throws Exception {
        this.providers.forEach(provider -> {
            log.info("Start making Pizza with: {}", provider.getClass().getSimpleName());
            var start = System.currentTimeMillis();

            final Pizza pizza = provider.makePizza();

            var execTime = System.currentTimeMillis() - start;

            pizza.getComponents().forEach(PizzaComponent::echo);

            log.info("Pizza done in {}min", execTime/100);
        });

        System.exit(0);
    }
}
