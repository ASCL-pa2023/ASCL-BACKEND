package esgi.ascl.HelloWorld;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/hello")
public class HelloWorldController {

        @RequestMapping("/world")
        public String helloWorld() {
            System.out.println("Hello World!");
            return "Hello World!";
        }
}
