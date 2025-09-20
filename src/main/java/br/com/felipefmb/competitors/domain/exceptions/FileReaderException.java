package br.com.felipefmb.competitors.domain.exceptions;

public class FileReaderException extends RuntimeException {

    public FileReaderException(String message) {
        super(message);
    }

    public FileReaderException(Throwable cause) {
        super(cause);
    }
}
