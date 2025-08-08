package taskcli;

import java.time.LocalDate;
import java.util.Map;

public class ListCommand implements Command {
    private final TaskService svc; private final String[] args;
    public ListCommand(Env env, String[] args) { this.svc = env.service(); this.args = args; }

    @Override public void run() {
        Map<String,String> m = Cli.argsMap(args);
        var statusStr = m.get("status");
        TaskStatus status = statusStr == null ? null : TaskStatus.valueOf(statusStr.toUpperCase());
        LocalDate dueBefore = Cli.parseDate(m.get("due-before"));
        boolean sortByDue = "due".equalsIgnoreCase(m.get("sort"));

        var items = svc.list(status, dueBefore, sortByDue);
        if (items.isEmpty()) { System.out.println("(no tasks)"); return; }
        items.forEach(System.out::println);
    }
}
