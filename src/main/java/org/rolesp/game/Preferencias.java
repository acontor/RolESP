package org.rolesp.game;

import org.rolesp.services.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Preferencias {

    public static String nombre;

    public static String historia;

    public static String estado;

    public static DAO dao = new DAO();

    public static void setNombre(String nombre) {
        Preferencias.nombre = nombre;
    }

    public static void setHistoria(String historia) {
        Preferencias.historia = historia;
    }

    public static void setEstado(String estado) {
        Preferencias.estado = estado;
    }

    public void insertar() {
        try {
            PreparedStatement preparedStatement = dao.getConnection().prepareStatement("INSERT INTO rolesp.juego (nombre, historia, estado) VALUES (?, ?, ?)");
            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, historia);
            preparedStatement.setString(3, estado);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public boolean cargar() {
        try {
            Preferencias preferencias = new Preferencias();
            String selectSQL = "SELECT * FROM rolesp.juego";
            PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            if (!rs.next()) {
                return false;
            }
            rs.beforeFirst();
            while (rs.next()) {
                nombre = rs.getString(1);
                historia = rs.getString(2);
                estado = rs.getString(3);
            }
        } catch (
                SQLException ex) {
            System.out.println(ex);
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("\nNombre del juego: ").append(nombre).append("\n\nHistoria: ").append(historia).append("\n\nEstado: ").append(estado);
        String string = stringBuilder.toString();
        return string;
    }
}
