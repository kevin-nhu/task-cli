package taskcli;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Task {
    public long id;
    public String title;
    public String notes;
    public LocalDate due;
    public TaskStatus status = TaskStatus.OPEN;
    public List<String> tags = List.of();

    public Task() {}
    public Task(long id, String title, String notes, LocalDate due, TaskStatus status, List<String> tags) {
        this.id = id;
        this.title = title;
        this.notes = notes;
        this.due = due;
        this.status = status;
        this.tags = tags;
    }

    @Override public String toString() {
        return "["+id+"] " + title + (due!=null? " (Due: "+due+")":"") + " - " + status;
    }
}
