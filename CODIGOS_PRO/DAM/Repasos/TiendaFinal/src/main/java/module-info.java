module org.example.tiendafinal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires static lombok;
    requires java.net.http;
    requires org.json;
    requires com.google.gson;


    opens org.example.tiendafinal to javafx.fxml;
    exports org.example.tiendafinal;

    exports org.example.tiendafinal.controller;
    opens org.example.tiendafinal.controller to javafx.fxml, com.google.gson, org.json;

    exports org.example.tiendafinal.database;
    opens org.example.tiendafinal.database to java.sql, com.google.gson, org.json;

    exports org.example.tiendafinal.dao;
    opens org.example.tiendafinal.dao to java.sql,com.google.gson, org.json;

    exports org.example.tiendafinal.model;
    opens org.example.tiendafinal.model to java.sql,com.google.gson, org.json;
}