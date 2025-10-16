package br.com.felipefmb.competitors.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class GoldenRaspberryAwardsException extends RuntimeException {
    public GoldenRaspberryAwardsException(String message) {
        super(message);
    }
}
