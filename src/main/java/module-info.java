module org.example.musicaapp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.example.musicaapp to javafx.fxml;
    exports org.example.musicaapp;
}
