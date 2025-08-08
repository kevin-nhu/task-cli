package taskcli.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;                
import javafx.scene.Scene;
import javafx.stage.Stage;
import taskcli.Env;
import taskcli.TaskService;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // 1) Load FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("layout.fxml"));
        Parent root = loader.<Parent>load();

        // 2) Wire up your service + controller
        TaskService service = Env.defaultEnv().service();
        Controller controller = loader.getController();
        controller.init(service);

        // 3) Create the scene and attach the CSS
        Scene scene = new Scene(root, 450, 400);
        scene.getStylesheets().add(
            getClass().getResource("/taskcli/gui/style.css").toExternalForm()
        );

        // 4) Show the window
        stage.setTitle("Task Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
