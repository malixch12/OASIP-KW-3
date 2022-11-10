package sit.oasip;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.file.Path;
import java.nio.file.Paths;

@SpringBootApplication
public class OasipBEApplication {

    public static void main(String[] args) {
//        System.out.println("System property: " + System.getProperty("user.dir"));
//        Path path = Paths.get("/grade2_2/INT221/OASIP-KW-3/OASIP-WEB/db");
//        System.out.println(path);

        SpringApplication.run(OasipBEApplication.class, args);


    }

}
