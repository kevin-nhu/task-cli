package taskcli;

public class App {
    public static void main(String[] args) {
        try {
            var env = Env.defaultEnv();
            var cmd = CommandFactory.parse(env, args);
            if (cmd == null) {
                Cli.printHelp();
                return;
            }
            cmd.run();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            if (System.getenv("DEBUG") != null) e.printStackTrace();
        }
    }
}
