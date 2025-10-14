package br.com.felipefmb.competitors.configs;

import br.com.felipefmb.competitors.adapters.in.web.mapper.DataMapper;
import br.com.felipefmb.competitors.adapters.in.web.response.Response;
import br.com.felipefmb.competitors.domain.Log;
import br.com.felipefmb.competitors.domain.exceptions.GoldenRaspberryAwardsException;
import br.com.felipefmb.competitors.domain.exceptions.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Response> handleResourceNotFound() {
        Log.info("Not found");
        return ResponseEntity.status(204).build();
    }

    @ExceptionHandler(GoldenRaspberryAwardsException.class)
    public ResponseEntity<Response> handleValidationErrors(GoldenRaspberryAwardsException e) {
        Log.error(e.getMessage(), e.fillInStackTrace());
        var dataResponse = DataMapper.toData(e.getMessage());
        return ResponseEntity.badRequest().body(dataResponse);
    }

}
