package microservice.basicWebApp.jewelleryApp.controller;

import io.swagger.v3.oas.annotations.Operation;
import microservice.basicWebApp.jewelleryApp.errorHandler.MyResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/error-handler")
public class ErrorController
{
    @Operation(description = "Send ResponseEntity with error")
    @RequestMapping( method = RequestMethod.GET, value = "/ResponseEntity-with-error")
    private ResponseEntity<String> testException()    {
        return ResponseEntity.badRequest().body("Exception testing - body");
    }

    @Operation(description = "ResponseStatusException Demo")
    @RequestMapping( method = RequestMethod.GET, value = "/ResponseStatusException")
    @ResponseStatus(value = HttpStatus.BAD_REQUEST) //status 1 - Default response
    private ResponseEntity<String> testException3()  throws ResponseStatusException    {
        throw new  ResponseStatusException(HttpStatus.FORBIDDEN, " Spring 5 - Response Status Exception"); //status 2 - forbidden
    }

    // control-advise
    @Operation(description = "throws MyResourceNotFoundException, later handled by controller advise")
    @RequestMapping( method = RequestMethod.GET, value = "/control-advise")
    private ResponseEntity<String> testException2() throws MyResourceNotFoundException    {
        throw new MyResourceNotFoundException("Exception testing - body");
    }
}
