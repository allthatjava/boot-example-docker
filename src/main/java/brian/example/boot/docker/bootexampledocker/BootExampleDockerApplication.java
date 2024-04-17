package brian.example.boot.docker.bootexampledocker;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@Slf4j
public class BootExampleDockerApplication {

    @GetMapping("/message")
    public String getMessages(@RequestParam("name") String name){
        log.debug("Welcome to dockerize example:"+name);
        return "Welcome to dockerize example 2";
    }

    public static void main(String[] args) {
        SpringApplication.run(BootExampleDockerApplication.class, args);
    }

}
