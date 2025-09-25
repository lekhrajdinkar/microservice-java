package microservice.courseApp.errorHandling;

public class MyException extends Exception{
    public MyException(String msg){
        super(msg);
    }
}