package dao;

import modelo.Contrato;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ContratoDAO {

    // READ - Listar todos los contratos con nombre de cliente y servicio
    public List<Contrato> listar() {
        List<Contrato> contratos = new ArrayList<>();
        String sql = "SELECT c.id, c.cliente_id, c.servicio_id, c.fecha_inicio, c.fecha_fin, "
                + "c.estado, c.precio_final, c.facturado, c.numero_factura, "
                + "cl.nombre AS nombre_cliente, s.nombre AS nombre_servicio "
                + "FROM contratos c "
                + "INNER JOIN clientes cl ON c.cliente_id = cl.id "
                + "INNER JOIN servicios s ON c.servicio_id = s.id";

        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                LocalDate fechaFin = rs.getDate("fecha_fin") != null
                        ? rs.getDate("fecha_fin").toLocalDate() : null;

                Contrato contrato = new Contrato(
                        rs.getInt("id"),
                        rs.getInt("cliente_id"),
                        rs.getInt("servicio_id"),
                        rs.getDate("fecha_inicio").toLocalDate(),
                        fechaFin,
                        rs.getString("estado"),
                        rs.getDouble("precio_final"),
                        rs.getBoolean("facturado"),
                        rs.getString("numero_factura")
                );
                contrato.setNombreCliente(rs.getString("nombre_cliente"));
                contrato.setNombreServicio(rs.getString("nombre_servicio"));

                contratos.add(contrato);
            }
        } catch (SQLException e) {
            System.out.println("Error al listar contratos: " + e.getMessage());
        }
        return contratos;
    }

    // CREATE - Insertar nuevo contrato
    public void insertar(Contrato contrato) {
        String sql = "INSERT INTO contratos (cliente_id, servicio_id, fecha_inicio, fecha_fin, "
                + "estado, precio_final, facturado, numero_factura) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, contrato.getClienteId());
            ps.setInt(2, contrato.getServicioId());
            ps.setDate(3, Date.valueOf(contrato.getFechaInicio()));
            ps.setDate(4, contrato.getFechaFin() != null ? Date.valueOf(contrato.getFechaFin()) : null);
            ps.setString(5, contrato.getEstado());
            ps.setDouble(6, contrato.getPrecioFinal());
            ps.setBoolean(7, contrato.isFacturado());
            ps.setString(8, contrato.getNumeroFactura());

            ps.executeUpdate();
            System.out.println("Contrato insertado correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al insertar contrato: " + e.getMessage());
        }
    }

    // UPDATE - Actualizar contrato existente
    public void actualizar(Contrato contrato) {
        String sql = "UPDATE contratos SET cliente_id=?, servicio_id=?, fecha_inicio=?, fecha_fin=?, "
                + "estado=?, precio_final=?, facturado=?, numero_factura=? WHERE id=?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, contrato.getClienteId());
            ps.setInt(2, contrato.getServicioId());
            ps.setDate(3, Date.valueOf(contrato.getFechaInicio()));
            ps.setDate(4, contrato.getFechaFin() != null ? Date.valueOf(contrato.getFechaFin()) : null);
            ps.setString(5, contrato.getEstado());
            ps.setDouble(6, contrato.getPrecioFinal());
            ps.setBoolean(7, contrato.isFacturado());
            ps.setString(8, contrato.getNumeroFactura());
            ps.setInt(9, contrato.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("Contrato actualizado correctamente.");
            } else {
                System.out.println("No se encontró el contrato con ese ID.");
            }
        } catch (SQLException e) {
            System.out.println("Error al actualizar contrato: " + e.getMessage());
        }
    }

    // DELETE - Eliminar contrato por ID
    public void eliminar(int id) {
        String sql = "DELETE FROM contratos WHERE id=?";
        try (Connection con = ConexionBD.conectar(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar contrato: " + e.getMessage());
        }
    }
}
