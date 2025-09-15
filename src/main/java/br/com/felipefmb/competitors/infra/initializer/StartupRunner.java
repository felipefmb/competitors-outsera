package br.com.felipefmb.competitors.infra.initializer;

import br.com.felipefmb.competitors.application.usecase.LoadMoviesFromCSVUseCase;
import br.com.felipefmb.competitors.domain.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
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
