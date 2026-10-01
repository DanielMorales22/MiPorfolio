package es.dam1.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import es.dam1.model.Jugador;

public class JugadorDAO {

    public List<Jugador> listarJugadores() {
        List<Jugador> lista = new ArrayList<>();
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        }
        try {
            Statement sentencia = con.createStatement();
            ResultSet registros = sentencia.executeQuery("SELECT * FROM jugadores");
            while (registros.next()) {
                int id = registros.getInt("id");
                int dorsal = registros.getInt("dorsal");
                String nombre = registros.getString("nombre");
                String posicion = registros.getString("posicion");
                String club = registros.getString("club");
                int edad = registros.getInt("edad");
                lista.add(new Jugador(id, dorsal, nombre, posicion, club, edad));
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

    public boolean aniadirJugador(Jugador j) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO jugadores (dorsal, nombre, posicion, club, edad) VALUES (?, ?, ?, ?, ?)");
            ps.setInt(1, j.getDorsal());
            ps.setString(2, j.getNombre());
            ps.setString(3, j.getPosicion());
            ps.setString(4, j.getClub());
            ps.setInt(5, j.getEdad());
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

    public boolean editarJugador(Jugador j) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE jugadores SET dorsal=?, nombre=?, posicion=?, club=?, edad=? WHERE id=?");
            ps.setInt(1, j.getDorsal());
            ps.setString(2, j.getNombre());
            ps.setString(3, j.getPosicion());
            ps.setString(4, j.getClub());
            ps.setInt(5, j.getEdad());
            ps.setInt(6, j.getId());
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

    public boolean eliminarJugador(int id) {
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        try {
            PreparedStatement ps = con.prepareStatement("DELETE FROM jugadores WHERE id=?");
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

    public List<Jugador> buscarPorNombre(String nombre) {
        List<Jugador> lista = new ArrayList<>();
        Connection con = null;
        try {
            con = Conexion.getConexion();
        } catch (Exception e) {
            e.printStackTrace();
            return lista;
        }
        try {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM jugadores WHERE nombre LIKE ?");
            ps.setString(1, "%" + nombre + "%");
            ResultSet registros = ps.executeQuery();
            while (registros.next()) {
                int id = registros.getInt("id");
                int dorsal = registros.getInt("dorsal");
                String nom = registros.getString("nombre");
                String posicion = registros.getString("posicion");
                String club = registros.getString("club");
                int edad = registros.getInt("edad");
                lista.add(new Jugador(id, dorsal, nom, posicion, club, edad));
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
}
