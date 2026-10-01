package es.dam1.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import es.dam1.model.CuerpoTecnico;

public class CuerpoTecnicoDAO {

    public List<CuerpoTecnico> listarCuerpo() {
        List<CuerpoTecnico> lista = new ArrayList<>();
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        }
        try {
            Statement sentencia = con.createStatement();
            ResultSet registros = sentencia.executeQuery("SELECT * FROM cuerpo_tecnico");
            while (registros.next()) {
                int id = registros.getInt("id");
                String nombre = registros.getString("nombre");
                int edad = registros.getInt("edad");
                String funcion = registros.getString("funcion");
                lista.add(new CuerpoTecnico(id, nombre, edad, funcion));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean aniadirCuerpo(CuerpoTecnico c) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO cuerpo_tecnico (nombre, edad, funcion) VALUES (?, ?, ?)");
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getEdad());
            ps.setString(3, c.getFuncion());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean editarCuerpo(CuerpoTecnico c) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE cuerpo_tecnico SET nombre=?, edad=?, funcion=? WHERE id=?");
            ps.setString(1, c.getNombre());
            ps.setInt(2, c.getEdad());
            ps.setString(3, c.getFuncion());
            ps.setInt(4, c.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public boolean eliminarCuerpo(int id) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM cuerpo_tecnico WHERE id=?");
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        try {
            con.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}