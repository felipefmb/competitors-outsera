package br.com.felipefmb.competitors.domain.exceptions;

public class GoldenRaspberryAwardsException extends RuntimeException {

    public GoldenRaspberryAwardsException(String message) {
        super(message);
    }

    public GoldenRaspberryAwardsException(Throwable cause) {
        super(cause);
    }
}
