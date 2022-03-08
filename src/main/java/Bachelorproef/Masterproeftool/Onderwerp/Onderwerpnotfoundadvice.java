package Bachelorproef.Masterproeftool.Onderwerp;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
class Onderwerpnotfoundadvice {

    @ResponseBody
    @ExceptionHandler(Onderwerpnotfoundexception.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String employeeNotFoundHandler(Onderwerpnotfoundexception ex) {
        return ex.getMessage();
    }
}