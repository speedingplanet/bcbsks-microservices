package microservices.demos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
	
	public static final String STATUS_MESSAGE = "Hello, World!";

	@RequestMapping(produces = "text/plain", 
			method = RequestMethod.GET, 
			path = "/hello-world/as-text")
	public String sayHelloAsText() {
		return "[text/plain] " + STATUS_MESSAGE;
	}
	
	@GetMapping("/hello-world")
	public String sayHello() {
		return STATUS_MESSAGE;
	}

}
