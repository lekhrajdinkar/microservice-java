package client;

import server.Service1;
import org.springframework.stereotype.Service;

@Service
public class Service1Impl implements Service1
{
    @Override
    public String m1() {
        return "module1(Server)'s interface Service1 :: m1() - impl provided by module2's Service1Impl (this)";
    }

    @Override
    public String m2() {
        return "module1(Server)'s interface Service1 :: m2() - impl provided by module2's Service1Impl (this)";
    }
}
