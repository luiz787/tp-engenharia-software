package br.ufmg.dcc.saracura.exception.handler;

import br.ufmg.dcc.saracura.exception.BusinessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<Object> handleBusinessException(final BusinessException ex, final WebRequest request) {
        final var corpoResposta = ex.getMessage();
        return handleExceptionInternal(ex, corpoResposta,
                new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request);
    }

}
