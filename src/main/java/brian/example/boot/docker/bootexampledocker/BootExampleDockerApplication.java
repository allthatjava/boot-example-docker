package brian.example.boot.docker.bootexampledocker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class BootExampleDockerApplication {

    @GetMapping( value={"/", "/message"})
    public String getMessages(@Nullable @RequestParam("name") String name){
        log.debug("Welcome to dockerize example(Debug):"+name);
        log.info("Welcome to dockerize example(Info):"+name);
        return "Welcome to dockerize example : "+name;
    }

    public static void main(String[] args) {
        SpringApplication.run(BootExampleDockerApplication.class, args);
    }

}
