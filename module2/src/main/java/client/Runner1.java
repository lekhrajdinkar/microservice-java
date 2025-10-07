package client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Runner1 implements CommandLineRunner
{
    @Autowired private Service1Impl srv;

    public static void main(String[] args) {
        SpringApplication.run(Runner1.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(srv.m1());
        System.out.println(srv.m2());
    }
}

/*
java --module-path out --add-reads module2=ALL-UNNAMED --module module2/client.Runner1
*/
