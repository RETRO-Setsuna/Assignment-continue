import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppMain extends Application {

    @Override
    public void start(Stage primaryStage) {

        AppModel model = new AppModel();

        AppController controller = new AppController(model);

        AppView view = new AppView(controller, model, primaryStage);

        Scene scene = new Scene(view.asParent(), 500, 400);

        primaryStage.setTitle("HD Choco Shop");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}