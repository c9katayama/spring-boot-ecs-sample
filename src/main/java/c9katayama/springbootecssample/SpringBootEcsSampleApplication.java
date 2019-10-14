package c9katayama.springbootecssample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class SpringBootEcsSampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootEcsSampleApplication.class, args);
	}

	@RestController
	public class IndexController {
		@GetMapping
		public String index() {
			return "OK2";
		}

		@GetMapping(path = "/hello")
		public String hello() {
			return "hello2";
		}
	}
}
