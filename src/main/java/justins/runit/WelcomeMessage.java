/**
 * Component class responsible for generating a welcome message.
 * It provides a method to retrieve the welcome message.
 */
package justins.runit;

import org.springframework.stereotype.Component;

@Component
public class WelcomeMessage {

    // Method to retrieve the welcome message
    public String getWelcomeMessage() {
        return "Welcome to the Spring Boot application";
    }
}

