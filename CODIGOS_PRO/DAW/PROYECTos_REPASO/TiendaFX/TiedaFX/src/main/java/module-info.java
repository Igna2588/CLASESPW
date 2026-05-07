module org.example.tiedafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires static lombok;
    requires java.sql;
    requires java.desktop;
    requires java.net.http;
    requires org.json;
    requires com.google.gson;
    requires org.hibernate.orm.core;
    requires jakarta.persistence;
    requires java.naming;


    opens org.example.tiedafx to javafx.fxml, org.hibernate.orm.core, jakarta.persistence;
    exports org.example.tiedafx;

    exports org.example.tiedafx.controller;
    opens org.example.tiedafx.controller to javafx.fxml, org.json, com.google.gson, org.hibernate.orm.core, jakarta.persistence;

    opens org.example.tiedafx.dao to java.sql, org.hibernate.orm.core,jakarta.persistence;
    exports org.example.tiedafx.dao;

    opens org.example.tiedafx.model to java.sql, org.json, com.google.gson, org.hibernate.orm.core,jakarta.persistence;
    exports org.example.tiedafx.model;

    opens org.example.tiedafx.database to java.sql, org.hibernate.orm.core, jakarta.persistence;
    exports org.example.tiedafx.database;
}