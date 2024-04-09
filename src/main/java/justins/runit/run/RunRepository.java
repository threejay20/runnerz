/**
 * Interface defining operations for accessing and manipulating run data in the database.
 * It extends ListCrudRepository, which provides CRUD (Create, Read, Update, Delete) operations.
 */
package justins.runit.run;

import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

public interface RunRepository extends ListCrudRepository<Run, Integer> {

    // Method to find all runs by location
    List<Run> findAllByLocation(String location);
}
