package taskcli;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TaskServiceTest {
    @Test void addAndList() {
        var repo = new InMemRepo();
        var svc = new TaskService(repo);
        svc.add("Test", "", null, List.of());
        assertEquals(1, svc.list(null, null, false).size());
    }

    // tiny in-memory repo for tests
    static class InMemRepo implements TaskRepository {
        java.util.List<Task> items = new java.util.ArrayList<>();
        long seq=0;
        public java.util.List<Task> findAll(){ return new java.util.ArrayList<>(items);}
        public java.util.Optional<Task> findById(long id){ return items.stream().filter(t->t.id==id).findFirst();}
        public Task save(Task t){ if(t.id==0)t.id=++seq; findById(t.id).ifPresent(items::remove); items.add(t); return t;}
        public void saveAll(java.util.List<Task> x){ items = new java.util.ArrayList<>(x);}
        public long nextId(){ return ++seq;}
    }
}
