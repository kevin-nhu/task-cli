package taskcli;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class JsonTaskRepository implements TaskRepository {
    private final Path path;
    private final ObjectMapper mapper;
    private final AtomicLong seq;
    private List<Task> cache;

    public JsonTaskRepository(Path path) {
        this.path = path;
        this.mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        this.cache = load();
        this.seq = new AtomicLong(cache.stream().mapToLong(t -> t.id).max().orElse(0));
    }

    private List<Task> load() {
        try {
            if (!Files.exists(path)) {
                Files.createDirectories(path.getParent());
                mapper.writeValue(path.toFile(), new ArrayList<Task>());
            }
            return mapper.readValue(path.toFile(), new TypeReference<>(){});
        } catch (IOException e) {
            throw new RuntimeException("Failed to load " + path + ": " + e.getMessage(), e);
        }
    }

    private void flush() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(path.toFile(), cache);
        } catch (IOException e) {
            throw new RuntimeException("Failed to save " + path + ": " + e.getMessage(), e);
        }
    }

    @Override public List<Task> findAll() { return new ArrayList<>(cache); }
    @Override public Optional<Task> findById(long id) { return cache.stream().filter(t -> t.id==id).findFirst(); }

    @Override public Task save(Task t) {
        if (t.id == 0) t.id = nextId();
        findById(t.id).ifPresentOrElse(
            old -> { cache.remove(old); cache.add(t); },
            () -> cache.add(t)
        );
        flush();
        return t;
    }

    @Override public void saveAll(List<Task> items) { cache = new ArrayList<>(items); flush(); }
    @Override public long nextId() { return seq.incrementAndGet(); }
}
