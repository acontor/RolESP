package org.rolesp.game;

import org.rolesp.admin.commands.CrearReglas;
import org.rolesp.services.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Reglas {

    public static String reglas;

    public static DAO dao = new DAO();

    public static void setReglas(String reglas) {
        Reglas.reglas = reglas;
    }

    @Override
    public String toString() {
        return reglas;
    }

    public void insertar() {
        try {
            PreparedStatement preparedStatement = dao.getConnection().prepareStatement("INSERT INTO rolesp.reglas (juego, reglas) VALUES (?, ?)");
            preparedStatement.setString(1, Preferencias.nombre);
            preparedStatement.setString(2, reglas);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        CrearReglas.crearReglas = 0;
    }

    public void cargar() {
        try {
            Preferencias preferencias = new Preferencias();
            String selectSQL = "SELECT reglas FROM rolesp.reglas";
            PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while (rs.next()) {
                reglas = rs.getString(1);
            }
        } catch (
                SQLException ex) {
            System.out.println(ex);
        }
    }
}
