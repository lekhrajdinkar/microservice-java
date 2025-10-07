package client;

import server.Service1;
import org.springframework.stereotype.Service;

@Service
public class Service1Impl implements Service1
{
    @Override
    public String m1() {
        return "Service2 :: m1() - impl provided by module2";
    }

    @Override
    public String m2() {
        return "Service2 :: m2() - impl provided by module2";
    }
}
