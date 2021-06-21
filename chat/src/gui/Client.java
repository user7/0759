package gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static java.lang.System.out;

public class Client extends Application {

    public static String address;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("Чат");
        primaryStage.setScene(new Scene(root, 500, 700));
        primaryStage.show();
        Controller controller = loader.getController();
        primaryStage.setOnHidden(e -> controller.shutdown());
    }

    public static void main(String[] args) {
        address = args.length > 0 ? args[0] : "";
        launch(args);
    }

    public static void logError(String msg, Exception e) {
        out.println(msg + e.toString());
        e.printStackTrace();
    }
}