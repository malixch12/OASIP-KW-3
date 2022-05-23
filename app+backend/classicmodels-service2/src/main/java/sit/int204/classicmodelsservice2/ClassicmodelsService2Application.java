package sit.int204.classicmodelsservice2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import sit.int204.classicmodelsservice2.dtos.SimpleEventDTO;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@SpringBootApplication
public class ClassicmodelsService2Application {

    public static void main(String[] args) {
        SpringApplication.run(ClassicmodelsService2Application.class, args);

    }

}
