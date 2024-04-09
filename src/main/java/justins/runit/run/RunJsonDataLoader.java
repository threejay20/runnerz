/**
 * Component responsible for loading run data from JSON into the database on application startup.
 * It implements the CommandLineRunner interface to execute the loading logic.
 */
package justins.runit.run;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.asm.TypeReference;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class RunJsonDataLoader implements CommandLineRunner {

    // Logger for logging messages
    private static final Logger log = LoggerFactory.getLogger(RunJsonDataLoader.class);

    // Repository for database operations related to runs
    private final JdbcClientRunRepository runRepository;

    // ObjectMapper for JSON serialization/deserialization
    private final ObjectMapper objectMapper;

    // Constructor to initialize the data loader with a repository and ObjectMapper instance
    public RunJsonDataLoader(JdbcClientRunRepository runRepository, ObjectMapper objectMapper) {
        this.runRepository = runRepository;
        this.objectMapper = objectMapper;
    }

    // Method to load run data from JSON into the database
    @Override
    public void run(String... args) throws Exception {
        if(runRepository.count() == 0) { // Check if the database is empty
            try(InputStream inputStream = TypeReference.class.getResourceAsStream("/data/runs.json")) {
                Runs allRuns = objectMapper.readValue(inputStream, Runs.class); // Deserialize JSON into Runs object
                log.info("Reading {} runs from JSON data and saving it to a database", allRuns.runs().size());
                runRepository.saveAll(allRuns.runs()); // Save all runs to the database
            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e); // Throw exception if reading JSON fails
            }
        } else {
            log.info("Not loading runs from JSON data because the collection contains data.");
        }
    }
}
