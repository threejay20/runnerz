/**
 * Exception class representing a "Run not found" error.
 * It is annotated with @ResponseStatus to return HTTP status 404 (NOT_FOUND) when thrown.
 */
package justins.runit.run;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RunNotFoundException extends RuntimeException {

    // Constructor to set the error message
    public RunNotFoundException() {
        super("Run not found");
    }
}
