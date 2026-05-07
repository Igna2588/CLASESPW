package org.example.tiendafinal.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.tiendafinal.MainApplication;
import org.example.tiendafinal.dao.UsuarioDAO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField editCorreo, editPass;

    @FXML
    private Button btnLogin;

    private UsuarioDAO usuarioDAO;

    private Task<int[]> taskLogin;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instances();
        actions();
    }

    private void instances() {
        usuarioDAO = new UsuarioDAO();

    }

    private void actions() {
        btnLogin.setOnAction(event -> {
            // llamo al DAO para hacer el login -> el metodo del DAO tendra que darme algo
            /*
            ----------------------------- UI + Listener
                     XXXXXXXX
                     yyyyyyyy
                     yyyyyyyy
                     yyyyyyyy
                     yyyyyyyy
                     yyyyyyyy
                     yyyyyyyy
                     yyyyyyyy
                     yyyyyyyy
                     yyyyyyyy
             */
            // validar campos
            taskLogin = new Task<int[]>() {
                @Override
                protected int[] call() throws Exception {
                    int[] datos = usuarioDAO.getLogin(editCorreo.getText(), editPass.getText());
                    return datos;
                }
            };

            new Thread(taskLogin).start();
            taskLogin.setOnSucceeded(data->
            {
                int[] datos = taskLogin.getValue();
                if (datos != null) {
                    FXMLLoader loader = null;
                    String title = "";
                    switch (datos[1]) {
                        case 1 -> {
                            title = "Administracion de tienda";
                            loader = new FXMLLoader(MainApplication.class.getResource("admin-view.fxml"));
                        }
                        case 2 -> {
                            title = "Compras y carrito";
                            loader = new FXMLLoader(MainApplication.class.getResource("user-view.fxml"));
                        }
                    }
                    try {
                        Parent root = loader.load();
                        if(datos[1]==2){
                            UserController controller = loader.getController();
                            controller.setId(datos[0]);
                        }
                        Stage ventana = new Stage();
                        Scene scene = new Scene(root);
                        ventana.setScene(scene);
                        ventana.setTitle(title);
                        ventana.show();
                        ((Stage)btnLogin.getScene().getWindow()).close();

                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                        System.out.println("No se encuentra el fichero");
                    }
                } else {

                    System.out.println("No hay usuario");
                }
            });






        });
        // btnLogin.addEventHandler(MouseEvent.ANY, event->{});


    }
}
