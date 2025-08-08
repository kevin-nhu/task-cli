package taskcli;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class TaskService {
    private final TaskRepository repo;
    public TaskService(TaskRepository repo) { this.repo = repo; }

    public Task add(String title, String notes, LocalDate due, java.util.List<String> tags) {
        var t = new Task(0, title, notes, due, TaskStatus.OPEN, tags==null? List.of(): tags);
        return repo.save(t);
    }

    public List<Task> list(TaskStatus status, LocalDate dueBefore, boolean sortByDue) {
        var items = repo.findAll().stream()
            .filter(t -> status == null || t.status == status)
            .filter(t -> dueBefore == null || (t.due != null && !t.due.isAfter(dueBefore)))
            .collect(Collectors.toList());
        
        if (sortByDue) {
            items.sort(Comparator.comparing((Task t) -> t.due == null ? LocalDate.MAX : t.due));
        }
        return items;
    }

    public boolean markDone(long id) {
        var opt = repo.findById(id);
        if (opt.isEmpty()) return false;
        var t = opt.get();
        t.status = TaskStatus.DONE;
        repo.save(t);
        return true;
    }
}
