package br.com.felipefmb.competitors.application.service;

import br.com.felipefmb.competitors.application.Log;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunnerService implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        Log.info("Olá teste");
    }
}
