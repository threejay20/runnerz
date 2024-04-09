package justins.runit;

// Importing necessary packages and classes
import justins.runit.user.User;
import justins.runit.user.UserHttpClient;
import justins.runit.user.UserRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.util.List;

// Declaring the main Spring Boot application class
@SpringBootApplication
public class RunitApplication {

	// Logger for logging messages
	private static final Logger log = LoggerFactory.getLogger(RunitApplication.class);

	// Main method to run the Spring Boot application
	public static void main(String[] args) {
		SpringApplication.run(RunitApplication.class, args);
	}

	// Bean definition for UserHttpClient
	@Bean
	UserHttpClient userHttpClient() {
		// Creating RestClient and HttpServiceProxyFactory
		RestClient restClient = RestClient.create("Http://jsonplaceholder.typicode.com");
		HttpServiceProxyFactory factory =  HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient)).build();
		return factory.createClient(UserHttpClient.class); // Returning UserHttpClient instance
	}

	// CommandLineRunner bean definition
	@Bean
	CommandLineRunner runner(UserHttpClient client) {
		return args -> {
			// Fetching all users and printing them
			List<User> users = client.findAll();
			System.out.println(users);

			// Fetching a user by ID and printing it
			User user = client.findById(1);
			System.out.println(user);
		};
	}
}




