package br.com.felipefmb.competitors.domain.exceptions;

public class FileReaderException extends RuntimeException {

    public FileReaderException(String message, Throwable cause) {
        super(message, cause);
    }
}
