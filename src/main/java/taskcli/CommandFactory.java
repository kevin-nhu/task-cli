package taskcli;

public class CommandFactory {
    public static Command parse(Env env, String[] args) {
        if (args.length == 0) return null;
        var verb = args[0];
        var rest = java.util.Arrays.copyOfRange(args, 1, args.length);

        return switch (verb) {
            case "add" -> new AddCommand(env, rest);
            case "list" -> new ListCommand(env, rest);
            case "done" -> new DoneCommand(env, rest);
            default -> null;
        };
    }
}
