package org.rolesp.game;

import org.rolesp.services.DAO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Usuario {

    private String nombre, username, permisos;
    private int id;

    public static ArrayList<Usuario> usuarios = new ArrayList<>();

    public static DAO dao = new DAO();

    public Usuario(String nombre, String username, String permisos, int id) {
        this.nombre = nombre;
        this.username = username;
        this.permisos = permisos;
        this.id = id;
    }

    public Usuario() {
    }

    public int getId() {
        return id;
    }

    public String getPermisos() {
        return permisos;
    }

    public void cargar() {
        try {
            Usuario usuario;
            String selectSQL = "SELECT * FROM rolesp.usuarios";
            PreparedStatement preparedStatement = dao.getConnection().prepareStatement(selectSQL);
            ResultSet rs = preparedStatement.executeQuery(selectSQL);
            while (rs.next()) {
                if (Usuario.usuarios.size() > 0) {
                    for (int i = 0; i < Usuario.usuarios.size(); i++) {
                        if (Usuario.usuarios.get(i).getId() != rs.getInt(1)) {
                            usuario = new Usuario(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(1));
                            usuarios.add(usuario);
                        }
                    }
                } else {
                    usuario = new Usuario(rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(1));
                    usuarios.add(usuario);
                }
            }
        } catch (
                SQLException ex) {
            System.out.println(ex);
        }
    }

    public boolean insertar(Usuario usuario) {
        try {
            PreparedStatement preparedStatement = dao.getConnection().prepareStatement("INSERT INTO rolesp.usuarios (id, nombre, username, permisos) VALUES (?, ?, ?, ?)");
            preparedStatement.setInt(1, usuario.id);
            preparedStatement.setString(2, usuario.nombre);
            preparedStatement.setString(3, usuario.username);
            preparedStatement.setString(4, usuario.permisos);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (SQLException ex) {
            System.out.println(ex);
            return false;
        }
        return true;
    }
}
