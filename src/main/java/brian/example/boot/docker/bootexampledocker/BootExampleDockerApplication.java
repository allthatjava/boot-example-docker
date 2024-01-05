package brian.example.boot.docker.bootexampledocker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class BootExampleDockerApplication {

    @GetMapping("/message")
    public String getMessages(){
        return "Welcome to dockerize example";
    }

    public static void main(String[] args) {
        SpringApplication.run(BootExampleDockerApplication.class, args);
    }

}
