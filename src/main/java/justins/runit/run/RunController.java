/**
 * Controller class responsible for handling HTTP requests related to runs.
 * It defines endpoints for retrieving, creating, updating, and deleting runs,
 * as well as finding runs by location.
 */
package justins.runit.run;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/runs")
public class RunController {

    // Instance of RunRepository to handle database operations
    private final RunRepository runRepository;

    // Constructor to initialize the controller with a RunRepository instance
    public RunController(RunRepository runRepository) {
        this.runRepository = runRepository;
    }

    // Endpoint to fetch all runs
    @GetMapping("")
    List<Run> findAll() {
        return runRepository.findAll();
    }

    // Endpoint to fetch a run by its ID
    @GetMapping("/{id}")
    Run findById(@PathVariable Integer id) {
        Optional<Run> run = runRepository.findById(id);
        if(run.isEmpty()) {
            throw new RunNotFoundException(); // Throws an exception if run is not found
        }
        return run.get();
    }

    // Endpoint to create a new run
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Run run) {
        runRepository.save(run);
    }

    // Endpoint to update an existing run
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Run run, @PathVariable Integer id) {
        runRepository.save(run);
    }

    // Endpoint to delete a run by its ID
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void delete(@PathVariable Integer id) {
        runRepository.delete(runRepository.findById(id).get());
    }

    // Endpoint to fetch all runs by a specific location
    @GetMapping("/location/{location}")
    List<Run> findAllByLocation(@PathVariable String location) {
        return runRepository.findAllByLocation(location);
    }
}
