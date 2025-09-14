package br.com.felipefmb.competitors.domain.service;

import br.com.felipefmb.competitors.domain.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunnerService implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Log.info("Olá teste");
    }
}
