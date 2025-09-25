package microservice.courseApp.errorHandling;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionAdvisor
{
    @ExceptionHandler(value = {MyException.class})
    ResponseEntity<String> handleMyException(MyException e)
    {
        ResponseEntity<String> response = new ResponseEntity<>(
                "Business generic Exception. please verify request !" + e.getMessage(),
                HttpStatus.SERVICE_UNAVAILABLE);

        return response;
        // ResponseEntity res =  ResponseEntity.ok().BodyBuilder();
    }
}


