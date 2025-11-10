package org.example.musicaapp;
import javafx.application.Application; import javafx.fxml.FXMLLoader; import javafx.scene.Scene; import javafx.stage.Stage;
public class Main extends Application {
    @Override public void start(Stage stage) throws Exception {
        FXMLLoader f = new FXMLLoader(Main.class.getResource("crud-view.fxml"));
        stage.setScene(new Scene(f.load(), 960, 600));
        stage.setTitle("Projeto 2 - CRUD Música");
        stage.show();
    }
    public static void main(String[] args){ launch(args); }
}
