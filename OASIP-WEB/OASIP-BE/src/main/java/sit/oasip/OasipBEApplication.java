package sit.oasip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class OasipBEApplication {

    public static void main(String[] args) {
        SpringApplication.run(OasipBEApplication.class, args);


    }

}
