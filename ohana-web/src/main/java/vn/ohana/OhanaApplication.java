package vn.ohana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = {"vn.ohana","vn.rananu"})
@EntityScan("vn.ohana.entities")
public class OhanaApplication {
    public static void main(String[] args) {
        SpringApplication.run(OhanaApplication.class, args);
    }

}
