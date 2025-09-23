package br.com.felipefmb.competitors.infra.initializer;

import br.com.felipefmb.competitors.application.usecase.LoadMoviesFromCSVUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile("!test")
public class StartupRunner implements CommandLineRunner {

    private final LoadMoviesFromCSVUseCase loadMoviesFromCSVUseCase;

    public StartupRunner(LoadMoviesFromCSVUseCase loadMoviesFromCSVUseCase) {
        this.loadMoviesFromCSVUseCase = loadMoviesFromCSVUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        loadMoviesFromCSVUseCase.execute();
    }
}
