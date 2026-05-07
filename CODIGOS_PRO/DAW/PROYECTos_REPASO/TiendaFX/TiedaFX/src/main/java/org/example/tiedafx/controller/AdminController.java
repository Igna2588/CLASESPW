package org.example.tiedafx.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.tiedafx.dao.UserDAO;
import org.example.tiedafx.dao.UserDAOHibernate;
import org.example.tiedafx.model.User;
import org.example.tiedafx.model.UserHibernate;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AdminController implements Initializable {

    @FXML
    private Button btnRegistro;
    @FXML
    private TextField editNombre, editPass, editMail, editSaldo;
    private UserDAO userDAO;
    private UserDAOHibernate userDAOHibernate;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instances();
        actions();
    }

    private void instances() {
        userDAO = new UserDAO();
        userDAOHibernate = new UserDAOHibernate();
    }

    private void actions() {
        btnRegistro.setOnAction(evet -> {



            Alert alert = null;

            UserHibernate userHibernate = new UserHibernate(editNombre.getText(),
                    editMail.getText(),
                    editPass.getText(),
                    Integer.parseInt(editSaldo.getText()));

            userDAOHibernate.insertUser(userHibernate);

            /*try {
                userDAO.addUser(new User(editNombre.getText(),
                        editMail.getText(),
                        editPass.getText(),
                        2,
                        Integer.parseInt(editSaldo.getText())
                        ));
                // el usuario se ha insertado
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Confirmado!");
                alert.setContentText("Usuario con nombre "+ editNombre.getText() +" insertado correctamente");
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error!");
                alert.setContentText("Error en la insercion, correo duplicado");
            }
            alert.show();*/
        });
    }
}
