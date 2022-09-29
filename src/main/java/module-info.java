module com.gama.library {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;


    exports com.gama.library to javafx.fxml;
    opens com.gama.library;
    opens com.gama.library.core;
    opens com.gama.library.database;
    opens com.gama.library.models;
    opens com.gama.library.controllers;
    exports com.gama.library.core to javafx.fxml;
    exports com.gama.library.controllers to javafx.fxml;

}