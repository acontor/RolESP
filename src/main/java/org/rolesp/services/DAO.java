/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.rolesp.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author IES TRASSIERRA
 */
public class DAO {

    // Atributos y objetos de la clase DAO
    private String bd = "test";
    private String login = "root";
    private String password = "";
    private String url = "jdbc:mysql://localhost/" + bd;
    private Connection conexion = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet query = null;

    public DAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(url, login, password);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    public Connection getConnection() {
        return conexion;
    }

    public void desconectar() {
        conexion = null;
        System.out.println("\nLa conexion a la  base de datos " + bd + " ha terminado");
    }

}
