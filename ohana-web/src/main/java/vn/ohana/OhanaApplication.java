package vn.ohana;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"vn.ohana", "vn.rananu"})
@EntityScan("vn.ohana.entities")
public class OhanaApplication extends SpringBootServletInitializer {
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(OhanaApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(OhanaApplication.class, args);
    }

}
