package dao;

import modelo.Cliente;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {

    // CREATE - Insertar nuevo cliente
    public void insertar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nombre, email, telefono, empresa, origen_lead, fecha_alta) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmpresa());
            ps.setString(5, cliente.getOrigenLead());
            ps.setDate(6, Date.valueOf(cliente.getFechaAlta()));

            ps.executeUpdate();
            System.out.println("Cliente insertado correctamente.");

        } catch (SQLException e) {
            System.out.println("Error al insertar cliente: " + e.getMessage());
        }
    }

    // READ - Listar todos los clientes
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getString("telefono"),
                        rs.getString("empresa"),
                        rs.getString("origen_lead"),
                        rs.getDate("fecha_alta").toLocalDate()
                );
                clientes.add(cliente);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar clientes: " + e.getMessage());
        }

        return clientes;
    }

    // UPDATE - Actualizar cliente existente
    public void actualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre=?, email=?, telefono=?, empresa=?, origen_lead=? WHERE id=?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getEmail());
            ps.setString(3, cliente.getTelefono());
            ps.setString(4, cliente.getEmpresa());
            ps.setString(5, cliente.getOrigenLead());
            ps.setInt(6, cliente.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Cliente actualizado correctamente.");
            } else {
                System.out.println("No se encontró el cliente con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al actualizar cliente: " + e.getMessage());
        }
    }

    // DELETE - Eliminar cliente por ID
    public void eliminar(int id) {
        String sql = "DELETE FROM clientes WHERE id=?";

        try (Connection con = ConexionBD.conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                System.out.println("Cliente eliminado correctamente.");
            } else {
                System.out.println("No se encontró el cliente con ese ID.");
            }

        } catch (SQLException e) {
            System.out.println("Error al eliminar cliente: " + e.getMessage());
        }
    }
}
