package justins.runit.run;

// Importing necessary packages and classes
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.List;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

// Repository class for managing database operations related to runs
@Repository
public class JdbcClientRunRepository {

    // Logger for logging messages
    private static final Logger log = LoggerFactory.getLogger(JdbcClientRunRepository.class);

    // JdbcClient instance for executing SQL queries
    private final JdbcClient jdbcClient;

    // Constructor to initialize JdbcClient instance
    public JdbcClientRunRepository(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    // Method to fetch all runs from the database
    public List<Run> findAll() {
        return jdbcClient.sql("select * from run")
                .query(Run.class)
                .list();
    }

    // Method to fetch a run by its ID from the database
    public Optional<Run> findById(Integer id) {
        return jdbcClient.sql("select id, title, started_on, completed_on, miles, location from run where id = :id")
                .param("id", id)
                .query(Run.class)
                .optional();
    }

    // Method to create a new run in the database
    public void create(Run run) {
        var updated = jdbcClient.sql("INSERT INTO Run(id,title,started_on,completed_on,miles,location) values(?,?,?,?,?,?)")
                .params(List.of(run.id(), run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString()))
                .update();

        Assert.state(updated == 1, "Failed to create run " + run.title());
    }

    // Method to delete a run from the database by its ID
    public void delete(Integer id) {
        var updated = jdbcClient.sql("delete from run where id = :id")
                .param("id", id)
                .update();
        Assert.state(updated == 1, "Failed to delete run" + id);
    }

    // Method to update a run in the database
    public void update(Run run, Integer id) {
        var updated = jdbcClient.sql("update run set title = ?, started_on = ?, completed_on = ?, miles = ?, location = ? where id = ?")
                .params(List.of(run.title(), run.startedOn(), run.completedOn(), run.miles(), run.location().toString(), id))
                .update();
        Assert.state(updated == 1, "Failed to update run with id " + id);
    }

    // Method to count the number of runs in the database
    public int count() {
        return jdbcClient.sql("select * from run")
                .query().listOfRows().size();
    }

    // Method to save a list of runs in the database
    public void saveAll(List<Run> runs) {
        runs.stream().forEach(this::create);
    }

    // Method to fetch runs by location from the database
    public List<Run> findByLocation(String location) {
        return jdbcClient.sql("select * from run where location = :location")
                .param("location", location)
                .query(Run.class)
                .list();
    }
}

