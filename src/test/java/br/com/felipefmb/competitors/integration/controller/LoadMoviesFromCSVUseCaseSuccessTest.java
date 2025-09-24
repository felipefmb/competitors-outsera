package br.com.felipefmb.competitors.integration.controller;

import br.com.felipefmb.competitors.adapters.out.csv.MovieCsvSource;
import br.com.felipefmb.competitors.application.usecase.LoadMoviesFromCSVUseCase;
import br.com.felipefmb.competitors.application.usecase.MovieUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoadMoviesFromCSVUseCaseSuccessTest {

    LoadMoviesFromCSVUseCase loadMoviesFromCSVUseCase;

    @Autowired
    MovieCsvSource movieCsvSource;

    @Autowired
    MovieUseCase movieUseCase;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        loadMoviesFromCSVUseCase = new LoadMoviesFromCSVUseCase(movieCsvSource, movieUseCase);
    }

    @Test
    void test() {
        loadMoviesFromCSVUseCase.execute("movielist");
    }


}
