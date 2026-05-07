package org.example.tiendafinal.dao;

import org.example.tiendafinal.database.DBConnection;
import org.example.tiendafinal.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    public UsuarioDAO(){
        connection = DBConnection.getConnection();
    }

    public int[] getLogin(String mail, String pass){
        int[] datos = null;

        String query = String.format("SELECT %s,%s FROM %s WHERE %s=? AND %s=?",
                "id","id_perfil","usuarios","correo","pass");
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1,mail);
            preparedStatement.setString(2,pass);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                datos = new int[2];
                datos[0] = resultSet.getInt("id");
                datos[1] = resultSet.getInt("id_perfil");
            }
        } catch (SQLException e) {
            System.out.println("Error en la query");
        }

        return datos;
    }
    public Usuario getUsuarioById(int id){
        Usuario usuario = null;
        String query = String.format("SELECT * FROM %s WHERE %s = ?", "usuarios", "id");
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1,id);
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String nombre = resultSet.getString("nombre");
                String correo = resultSet.getString("correo");
                String pass = resultSet.getString("pass");
                int salario = resultSet.getInt("salario");
                int perfil = resultSet.getInt("id_perfil");
                usuario = new Usuario(nombre, correo, salario,perfil);
            }
            return usuario;
        } catch (SQLException e) {
            System.out.println("Error en la base de datos");
            return usuario;
        }
    }
}
