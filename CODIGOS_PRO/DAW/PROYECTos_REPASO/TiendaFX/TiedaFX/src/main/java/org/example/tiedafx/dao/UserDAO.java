package org.example.tiedafx.dao;

import javafx.scene.Scene;
import org.example.tiedafx.database.DBConnection;
import org.example.tiedafx.database.DBSchem;
import org.example.tiedafx.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    // acciones contra la base de datos
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UserDAO() {
        connection = DBConnection.getConnection();
    }

    public int[] getLogin(String mail, String pass) {

        int[] datos = null;

        String query = String.format("SELECT %s,%s FROM %s WHERE %s=? AND %s=?",
                DBSchem.COL_ID, DBSchem.COL_PROFILE,
                DBSchem.TAB_CLIENT,
                DBSchem.COL_MAIL, DBSchem.COL_PASS);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, mail);
            preparedStatement.setString(2, pass);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt(DBSchem.COL_ID);
                int profile = resultSet.getInt(DBSchem.COL_PROFILE);
                datos = new int[]{id, profile};
            }

        } catch (SQLException e) {
            System.out.println("Error en la sentencia QUERY");
            System.out.println(e.getMessage());
        }
        return datos;
    }

    public User getUser(int id) {
        User user = null;

        String query = String.format("SELECT * FROM %s WHERE %s=?", DBSchem.TAB_CLIENT, DBSchem.COL_ID);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println("Encontrado");
                int idUser = resultSet.getInt(DBSchem.COL_ID);
                String nombre = resultSet.getString(DBSchem.COL_NAME);
                String mail = resultSet.getString(DBSchem.COL_MAIL);
                int saldo = resultSet.getInt(DBSchem.COL_CASH);
                int perfil = resultSet.getInt(DBSchem.COL_PROFILE);
                user = new User(idUser,nombre, mail,perfil,saldo);
            }
            return user;
        } catch (SQLException e) {
            return null;
        }


    }

    public void addUser(User user) throws SQLException {
        String query = String.format("INSERT INTO %s (%s,%s,%s,%s,%s) VALUES (?,?,?,?,?)",
                DBSchem.TAB_CLIENT,
                DBSchem.COL_NAME, DBSchem.COL_MAIL, DBSchem.COL_PASS, DBSchem.COL_CASH, DBSchem.COL_PROFILE);
        preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getMail());
        preparedStatement.setString(3, user.getPass());
        preparedStatement.setInt(4, user.getCash());
        preparedStatement.setInt(5, user.getIdProfile());
        preparedStatement.execute();
    }
    public void buyProduct(long idClient, long idProduct){
        String query = String.format("INSERT INTO %s (%s, %s) VALUES (?,?)",
                DBSchem.TAB_SHOP,DBSchem.COL_ID_USER, DBSchem.COL_ID_PRODUCT);

        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(2,idProduct);
            preparedStatement.setLong(1,idClient);
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("Error en la query");
            System.out.println(e.getMessage());
        }

    }

    public int clearShop(long id){
        String query = String.format("DELETE FROM %s WHERE %s = ?", DBSchem.TAB_SHOP, DBSchem.COL_ID_USER);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en la query");
        }
        return 0;
    }

    public List<Long> getClientProducts(long id){
        List<Long> listaProductos = new ArrayList<>();
        String query = String.format("SELECT * FROM %s WHERE %s = ?", DBSchem.TAB_SHOP, DBSchem.COL_ID_USER);
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1,id);
            resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                listaProductos.add(resultSet.getLong(DBSchem.COL_ID_PRODUCT));
            }
        } catch (SQLException e) {
            System.out.println("Error en la query");
            System.out.println(e.getMessage());
        }
        return listaProductos;


    }
}
