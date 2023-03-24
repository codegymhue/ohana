package vn.ohana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"vn.ohana","saf"})
public class OhanaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OhanaApplication.class, args);
    }

}
