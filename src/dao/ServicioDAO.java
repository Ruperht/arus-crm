package dao;

import modelo.Servicio;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {

    // CREATE
    public void insertar(Servicio servicio) {
        String sql = "INSERT INTO servicios (nombre, descripcion, precio_base, tipo) VALUES (?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecioBase());
            ps.setString(4, servicio.getTipo());

            ps.executeUpdate();
            System.out.println("Servicio insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar servicio: " + e.getMessage());
        }
    }

    // READ
    public List<Servicio> listar() {
        List<Servicio> servicios = new ArrayList<>();
        String sql = "SELECT * FROM servicios";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Servicio servicio = new Servicio(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripcion"),
                        rs.getDouble("precio_base"),
                        rs.getString("tipo")
                );
                servicios.add(servicio);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar servicios: " + e.getMessage());
        }

        return servicios;
    }

    // UPDATE
    public void actualizar(Servicio servicio) {
        String sql = "UPDATE servicios SET nombre=?, descripcion=?, precio_base=?, tipo=? WHERE id=?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, servicio.getNombre());
            ps.setString(2, servicio.getDescripcion());
            ps.setDouble(3, servicio.getPrecioBase());
            ps.setString(4, servicio.getTipo());
            ps.setInt(5, servicio.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Servicio actualizado correctamente.");
            } else {
                System.out.println("No se encontró el servicio con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar servicio: " + e.getMessage());
        }
    }

    // DELETE
    public void eliminar(int id) {
        String sql = "DELETE FROM servicios WHERE id=?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Servicio eliminado correctamente.");
            } else {
                System.out.println("No se encontró el servicio con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar servicio: " + e.getMessage());
        }
    }
}
