package taskcli;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public class AddCommand implements Command {
    private final TaskService svc;
    private final String[] args;

    public AddCommand(Env env, String[] args) { this.svc = env.service(); this.args = args; }

    @Override public void run() {
        Map<String,String> m = Cli.argsMap(args);
        var title = m.get("title");
        if (title == null || title.isBlank()) {
            System.out.println("Missing --title"); Cli.printHelp(); return;
        }
        LocalDate due = Cli.parseDate(m.get("due"));
        List<String> tags = Cli.splitCsv(m.get("tags"));
        var t = svc.add(title, m.getOrDefault("notes",""), due, tags);
        System.out.println("Task added: " + t);
    }
}
