package taskcli;

import java.util.Map;

public class DoneCommand implements Command {
    private final TaskService svc; private final String[] args;
    public DoneCommand(Env env, String[] args) { this.svc = env.service(); this.args = args; }

    @Override public void run() {
        Map<String, String> m = Cli.argsMap(args);
        var idStr = m.get("id");
        if (idStr == null) { System.out.println("Missing --id"); return; }
        long id = Long.parseLong(idStr);
        boolean ok = svc.markDone(id);
        System.out.println(ok ? "Marked done: " + id : "Task not found: " + id);
    }
}
