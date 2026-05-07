package org.example.tiedafx.controller;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import org.example.tiedafx.dao.UserDAO;
import org.example.tiedafx.model.Product;
import org.example.tiedafx.model.ProductResponse;
import org.example.tiedafx.model.User;
import org.json.JSONObject;


import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicReference;

public class UserController implements Initializable {
    @FXML
    private Button btnComprar;

    @FXML
    private Button btnSalir;

    @FXML
    private Button btnVaciar;

    @FXML
    private ListView<Product> listaProductos;
    private ObservableList<Product> listObserver;

    @FXML
    private Text textUser;

    @FXML
    private Text textoCarrito;

    private UserDAO userDAO;
    private Task<User> taskUser;
    private Task<ProductResponse> taskProducts;

    private User user;
    // private Task<>

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        instances();
        initGUI();
        actions();
    }

    private void actions() {
        btnComprar.setOnAction(evet -> {
            Product productSelected = listaProductos.getSelectionModel().getSelectedItem();
            if (productSelected != null) {
                // user.getId();
                // productSelected.getId();
                userDAO.buyProduct(user.getId(), productSelected.getId());
                updateCash();
            }
        });
        btnVaciar.setOnAction(evet -> {
            int rows = userDAO.clearShop(user.getId());
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Elementos del carrito borrados");
            alert.setContentText("Confirmados el borrado de " + rows + " productos");
            alert.show();
        });
        /*
        listaProductos.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Product>() {
            @Override
            public void changed(ObservableValue<? extends Product> observable, Product oldValue, Product newValue) {

            }
        });*/
    }

    private void initGUI() {

        System.out.println("Inicializando la interfaz");


    }

    private void instances() {
        userDAO = new UserDAO();
        listObserver = FXCollections.observableArrayList();
    }

    public void setId(int id) {


        taskUser = new Task<User>() {
            @Override
            protected User call() throws Exception {
                return userDAO.getUser(id);
            }
        };
        new Thread(taskUser).start();
        taskUser.setOnSucceeded(data -> {

            user = taskUser.getValue();
            System.out.println(id);
            textUser.setText("Bienvenido " + user.getName());
        });
        taskProducts = new Task<ProductResponse>() {
            @Override
            protected ProductResponse call() throws Exception {

                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .GET()
                        .uri(URI.create("https://dummyjson.com/products"))
                        .build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                String body = response.body();
                JSONObject object = new JSONObject(body);
                Gson gson = new Gson();
                ProductResponse productResponse = gson.fromJson(object.toString(), ProductResponse.class);
                return productResponse;
            }
        };


        new Thread(taskProducts).start();
        taskProducts.setOnSucceeded(data -> {

            listObserver = FXCollections.observableArrayList(taskProducts.getValue().getProducts());
            listaProductos.setItems(listObserver);
            updateCash();
        });

    }

    private void updateCash() {
        // todos los ids de los productos comprados
        List<Long> listaIds = userDAO.getClientProducts(user.getId());
        AtomicReference<Double> compra = new AtomicReference<>((double) 0);
        // recorre los ids de los productos compradps
        listaIds.forEach(id ->
                {
                    // 6
                    Product producto = listObserver.filtered(product -> product.getId() == id).getFirst();
                    compra.updateAndGet(v -> new Double((double) (v + producto.getPrice())));
                }
        );

        textoCarrito.setText(compra.get().toString());

    }
}
