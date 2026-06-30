package modelo;

import java.time.LocalDate;

public class Contrato {
    private int id;
    private int clienteId;
    private int servicioId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String estado;
    private double precioFinal;
    private boolean facturado;
    private String numeroFactura;

    // Campos extra solo para mostrar en la tabla (no existen en la BD tal cual)
    private String nombreCliente;
    private String nombreServicio;

    public Contrato(int id, int clienteId, int servicioId, LocalDate fechaInicio,
                     LocalDate fechaFin, String estado, double precioFinal,
                     boolean facturado, String numeroFactura) {
        this.id = id;
        this.clienteId = clienteId;
        this.servicioId = servicioId;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
        this.precioFinal = precioFinal;
        this.facturado = facturado;
        this.numeroFactura = numeroFactura;
    }

    public int getId() { return id; }
    public int getClienteId() { return clienteId; }
    public int getServicioId() { return servicioId; }
    public LocalDate getFechaInicio() { return fechaInicio; }
    public LocalDate getFechaFin() { return fechaFin; }
    public String getEstado() { return estado; }
    public double getPrecioFinal() { return precioFinal; }
    public boolean isFacturado() { return facturado; }
    public String getNumeroFactura() { return numeroFactura; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }
}
