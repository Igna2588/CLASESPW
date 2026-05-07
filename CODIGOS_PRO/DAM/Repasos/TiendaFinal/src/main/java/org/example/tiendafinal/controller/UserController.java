package org.example.tiendafinal.controller;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import org.example.tiendafinal.dao.UsuarioDAO;
import org.example.tiendafinal.model.Producto;
import org.example.tiendafinal.model.ProductoResponse;
import org.example.tiendafinal.model.Usuario;
import org.json.JSONObject;
import org.json.JSONWriter;


import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class UserController implements Initializable {


    @FXML
    private Button btnCarrito;

    @FXML
    private Button btnComprar;

    @FXML
    private Button btnDetalle;

    @FXML
    private Button btnVaciar;

    @FXML
    private Label labelCarrito;

    @FXML
    private Label labelUsuario;

    @FXML
    private ListView<Producto> listaProductos;
    private ObservableList<Producto> listaPr;
    private Usuario usuario;
    private UsuarioDAO usuarioDAO;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // "como el main de la scene"
        instancias();
        initGUI();
        Task taskJSON = new Task() {
            @Override
            protected Object call() throws Exception {
                leerJSON();
                return null;
            }
        };
        new Thread(taskJSON).start();

    }

    private void leerJSON() {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create("https://dummyjson.com/products"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JSONObject json = new JSONObject(response.body());
            Gson gson = new Gson();
            ProductoResponse productoResponse = gson.fromJson(json.toString(), ProductoResponse.class);
            // productoResponse.mostrarDatos();
            // System.out.println(productoResponse.getProducts().size());
            listaPr = FXCollections.observableArrayList(productoResponse.getProducts());
            listaProductos.setItems(listaPr);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void initGUI() {
        listaProductos.setItems(listaPr);
        labelUsuario.setText("Bienvenido maria@gmail.com");
    }

    private void instancias() {
        listaPr = FXCollections.observableArrayList();
    }

    public void setId(int id) {
        // con este id, a la base de datos a consultar cosas
        /*usuarioDAO = new UsuarioDAO();
        Usuario usuario1 = usuarioDAO.getUsuarioById(id);
        labelUsuario.setText("Bienvenido " + usuario1.getCorreo());*/
        System.out.println("Id pasado con valor de "+id);
        Task<Usuario> taskUsuarios = new Task<Usuario>() {
            @Override
            protected Usuario call() throws Exception {
                return usuarioDAO.getUsuarioById(id);
            }
        };

        new Thread(taskUsuarios).start();
        taskUsuarios.setOnSucceeded(data->{
            usuario = taskUsuarios.getValue();
            labelUsuario.setText("Bienvenido "+taskUsuarios.getValue().getCorreo());
        });
    }
}
