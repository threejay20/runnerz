/**
 * Represents a record of a running activity.
 * It encapsulates information such as ID, title, start and end timestamps,
 * distance in miles, location, and version.
 */
package justins.runit.run;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.time.LocalDateTime;

public record Run(
        @Id Integer id, // Unique identifier for the run
        @NotEmpty String title, // Title of the run
        LocalDateTime startedOn, // Timestamp when the run started
        LocalDateTime completedOn, // Timestamp when the run completed
        @Positive Integer miles, // Distance covered in miles during the run
        Location location, // Location type (indoor or outdoor) of the run
        @Version Integer version // Version of the run for optimistic locking
) {

    // Constructor with validation logic to ensure the completion timestamp is after the start timestamp
    public Run {
        if (startedOn == null || completedOn == null || !completedOn.isAfter(startedOn)) {
            throw new IllegalArgumentException("Run must be completed after it has started");
        }
    }
}



