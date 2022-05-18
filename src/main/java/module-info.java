module com.example.jfxtest {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires com.fasterxml.jackson.core;
    requires org.apache.commons.codec;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;
    requires java.sql;
    requires java.desktop;
    requires com.fasterxml.jackson.datatype.jsr310;
    //requires commons.dbcp2;
    requires java.management;


    opens gui to javafx.fxml;
    exports gui;
    exports net;
    exports net.auth;
    exports net.codes;
    exports commands.dependencies;
    exports io;
    exports dragon;
    exports gui.controller;
    opens gui.controller to javafx.fxml;
}