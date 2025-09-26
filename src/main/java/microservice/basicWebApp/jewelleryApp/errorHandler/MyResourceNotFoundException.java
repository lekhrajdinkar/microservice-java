package microservice.basicWebApp.jewelleryApp.errorHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class MyResourceNotFoundException extends Exception{
    public MyResourceNotFoundException(String message) {
        super(message);
    }
}
