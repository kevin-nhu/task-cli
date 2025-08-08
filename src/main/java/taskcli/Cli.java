package taskcli;

import java.time.LocalDate;
import java.util.*;

public class Cli {
    static void printHelp() {
        System.out.println("""
        Task CLI v0.1
          add --title "Pay rent" [--due 2025-09-01] [--tags home,bills]
          list [--status open|done] [--due-before YYYY-MM-DD] [--sort due]
          done --id <id>
        """);
    }

    // tiny arg parser helpers
    static Map<String,String> argsMap(String[] args) {
        Map<String,String> m = new HashMap<>();
        String lastKey = null;
        for (var a : args) {
            if (a.startsWith("--")) { lastKey = a.substring(2); m.putIfAbsent(lastKey,"true"); }
            else if (lastKey != null) { m.put(lastKey, a); lastKey = null; }
        }
        return m;
    }

    static LocalDate parseDate(String s) { return s == null ? null : LocalDate.parse(s); }
    static List<String> splitCsv(String s) { return (s==null||s.isBlank())? List.of() : List.of(s.split(",")); }
}
