package taskcli;

import java.nio.file.Path;

public record Env(TaskService service) {
    public static Env defaultEnv() {
        var dataPath = Path.of("data", "tasks.json");
        var repo = new JsonTaskRepository(dataPath);
        var svc = new TaskService(repo);
        return new Env(svc);
    }
}
