package aruscrm;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import dao.ClienteDAO;
import dao.ServicioDAO;
import dao.ContratoDAO;
import modelo.Cliente;
import modelo.Servicio;
import modelo.Contrato;
import java.util.List;

public class FormularioContrato extends JDialog {

    private JComboBox<ComboItem> comboClientes;
    private JComboBox<ComboItem> comboServicios;
    private JTextField txtFechaInicio;
    private JTextField txtFechaFin;
    private JComboBox<String> comboEstado;
    private JTextField txtPrecioFinal;
    private JCheckBox chkFacturado;
    private JTextField txtNumeroFactura;

    private boolean contratoGuardado = false;
    private Contrato contratoExistente;

    // Constructor para CREAR
    public FormularioContrato(JFrame parent) {
        this(parent, null);
    }

    // Constructor para EDITAR
    public FormularioContrato(JFrame parent, Contrato contratoAEditar) {
        super(parent, contratoAEditar == null ? "Nuevo Contrato" : "Editar Contrato", true);
        this.contratoExistente = contratoAEditar;

        setLayout(new GridLayout(9, 2, 5, 5));
        setSize(400, 400);
        setLocationRelativeTo(parent);

        add(new JLabel("Cliente:"));
        comboClientes = new JComboBox<>();
        add(comboClientes);

        add(new JLabel("Servicio:"));
        comboServicios = new JComboBox<>();
        add(comboServicios);

        add(new JLabel("Fecha Inicio (dd/MM/yyyy):"));
        txtFechaInicio = new JTextField();
        add(txtFechaInicio);

        add(new JLabel("Fecha Fin (opcional):"));
        txtFechaFin = new JTextField();
        add(txtFechaFin);

        add(new JLabel("Estado:"));
        comboEstado = new JComboBox<>(new String[]{"Activo", "Finalizado", "Cancelado"});
        add(comboEstado);

        add(new JLabel("Precio Final:"));
        txtPrecioFinal = new JTextField();
        add(txtPrecioFinal);

        add(new JLabel("Facturado:"));
        chkFacturado = new JCheckBox();
        add(chkFacturado);

        add(new JLabel("Nº Factura:"));
        txtNumeroFactura = new JTextField();
        add(txtNumeroFactura);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        btnGuardar.addActionListener(e -> guardarContrato());
        btnCancelar.addActionListener(e -> dispose());
        add(btnGuardar);
        add(btnCancelar);

        cargarCombos();

        if (contratoExistente != null) {
            precargarDatos();
        }
    }

    private void cargarCombos() {
        ClienteDAO clienteDAO = new ClienteDAO();
        List<Cliente> clientes = clienteDAO.listar();
        for (Cliente c : clientes) {
            comboClientes.addItem(new ComboItem(c.getId(), c.getNombre()));
        }

        ServicioDAO servicioDAO = new ServicioDAO();
        List<Servicio> servicios = servicioDAO.listar();
        for (Servicio s : servicios) {
            comboServicios.addItem(new ComboItem(s.getId(), s.getNombre()));
        }
    }

    private void precargarDatos() {
        // Seleccionar el cliente y servicio correctos en los combos
        for (int i = 0; i < comboClientes.getItemCount(); i++) {
            if (comboClientes.getItemAt(i).getId() == contratoExistente.getClienteId()) {
                comboClientes.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < comboServicios.getItemCount(); i++) {
            if (comboServicios.getItemAt(i).getId() == contratoExistente.getServicioId()) {
                comboServicios.setSelectedIndex(i);
                break;
            }
        }

        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        txtFechaInicio.setText(contratoExistente.getFechaInicio().format(formato));
        txtFechaFin.setText(contratoExistente.getFechaFin() != null
                ? contratoExistente.getFechaFin().format(formato) : "");
        comboEstado.setSelectedItem(contratoExistente.getEstado());
        txtPrecioFinal.setText(String.valueOf(contratoExistente.getPrecioFinal()));
        chkFacturado.setSelected(contratoExistente.isFacturado());
        txtNumeroFactura.setText(contratoExistente.getNumeroFactura() != null
                ? contratoExistente.getNumeroFactura() : "");
    }

    private void guardarContrato() {
        ComboItem clienteSeleccionado = (ComboItem) comboClientes.getSelectedItem();
        ComboItem servicioSeleccionado = (ComboItem) comboServicios.getSelectedItem();

        if (clienteSeleccionado == null || servicioSeleccionado == null) {
            JOptionPane.showMessageDialog(this,
                "Debes seleccionar un cliente y un servicio.",
                "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String fechaInicioTexto = txtFechaInicio.getText().trim();
        String fechaFinTexto = txtFechaFin.getText().trim();
        String precioTexto = txtPrecioFinal.getText().trim();

        if (fechaInicioTexto.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Fecha Inicio y Precio Final son obligatorios.",
                "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate fechaInicio;
        LocalDate fechaFin = null;
        double precioFinal;

        try {
            fechaInicio = LocalDate.parse(fechaInicioTexto, formato);
            if (!fechaFinTexto.isEmpty()) {
                fechaFin = LocalDate.parse(fechaFinTexto, formato);
            }
            precioFinal = Double.parseDouble(precioTexto);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Revisa el formato de las fechas (dd/MM/yyyy) y que el precio sea un número.",
                "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String estado = (String) comboEstado.getSelectedItem();
        boolean facturado = chkFacturado.isSelected();
        String numeroFactura = txtNumeroFactura.getText().trim();

        ContratoDAO dao = new ContratoDAO();

        if (contratoExistente == null) {
            Contrato nuevo = new Contrato(0, clienteSeleccionado.getId(), servicioSeleccionado.getId(),
                    fechaInicio, fechaFin, estado, precioFinal, facturado, numeroFactura);
            dao.insertar(nuevo);
        } else {
            Contrato actualizado = new Contrato(contratoExistente.getId(), clienteSeleccionado.getId(),
                    servicioSeleccionado.getId(), fechaInicio, fechaFin, estado, precioFinal,
                    facturado, numeroFactura);
            dao.actualizar(actualizado);
        }

        contratoGuardado = true;
        dispose();
    }

    public boolean isContratoGuardado() {
        return contratoGuardado;
    }
}
