module org.javaFX {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires org.apache.logging.log4j;

    opens org.javaFX to javafx.fxml;
    exports org.javaFX;
}