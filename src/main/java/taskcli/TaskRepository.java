package taskcli;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    Optional<Task> findById(long id);
    Task save(Task t);
    void saveAll(List<Task> items);
    long nextId();
}
