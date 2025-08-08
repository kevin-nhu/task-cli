package taskcli.gui;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import taskcli.Task;
import taskcli.TaskService;

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Controller {
    @FXML private TextField inputField;
    @FXML private DatePicker     duePicker;   
    @FXML private TextField      tagsField;   
    @FXML private TableView<Task> taskTable;
    @FXML private TableColumn<Task, Long>   colId;
    @FXML private TableColumn<Task, String> colTitle;
    @FXML private TableColumn<Task, String> colDue;
    @FXML private TableColumn<Task, String> colTags;
    @FXML private TableColumn<Task, String> colStatus;

    private TaskService service;
    private final ObservableList<Task> tasks = FXCollections.observableArrayList();

    public void init(TaskService service) {
        this.service = service;

        // Column bindings
        colId.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().id));
        colTitle.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().title));
        colDue.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(
            c.getValue().due != null
              ? c.getValue().due.format(DateTimeFormatter.ISO_LOCAL_DATE)
              : ""
        ));
        colTags.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(
            String.join(", ", c.getValue().tags)
        ));
        colStatus.setCellValueFactory(c -> new ReadOnlyObjectWrapper<>(c.getValue().status.toString()));

        taskTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        taskTable.setItems(tasks);

        taskTable.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(evt -> {
                if (row.isEmpty()) {
                    tv.getSelectionModel().clearSelection();
                }
            });
            return row;
        });
        refresh();
    }

    @FXML
    private void onAdd() {
        String title = inputField.getText().trim();
        if (title.isEmpty()) return;

        // 1. Read the due date (may be null)
        LocalDate due = duePicker.getValue();

        // 2. Read & parse the tags field
        String tagText = tagsField.getText().trim();
        List<String> tags = tagText.isEmpty()
            ? List.of()
            : Arrays.stream(tagText.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();

        // 3. Perform the add
        service.add(title, "", due, tags);

        // 4. Clear inputs
        inputField.clear();
        duePicker.setValue(null);
        tagsField.clear();

        // 5. Refresh the table
        refresh();
    }

    @FXML
    private void onMarkDone() {
        Task sel = taskTable.getSelectionModel().getSelectedItem();
        if (sel != null) {
            service.markDone(sel.id);
            refresh();
        }
    }

    @FXML
    private void onDelete() {
        Task sel = taskTable.getSelectionModel().getSelectedItem();
        if (sel != null) {
            service.delete(sel.id);
            refresh();
        }
    }

    private void refresh() {
        tasks.setAll(service.list(null, null, false));
    }
}
