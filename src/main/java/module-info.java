module com.example.creditsfx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires mssql.jdbc;
    requires mysql.connector.j;

    exports com.example.creditsfx.Controllers;
    opens com.example.creditsfx.Controllers to javafx.fxml;

}