import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(this.getClass().getResource("/views/startView.fxml"));
        AnchorPane root = loader.load();

        MainControler controller = loader.getController();
        Scene scene = new Scene(root);
        controller.setMain(scene);

        controller.addSceene("main", root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Grades Application");

        primaryStage.show();


    }
}
