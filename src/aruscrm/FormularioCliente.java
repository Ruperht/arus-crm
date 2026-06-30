package aruscrm;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import dao.ClienteDAO;
import modelo.Cliente;

public class FormularioCliente extends JDialog {

    private JTextField txtNombre;
    private JTextField txtEmail;
    private JTextField txtTelefono;
    private JTextField txtEmpresa;
    private JTextField txtOrigenLead;
    private boolean clienteGuardado = false;

    private Cliente clienteExistente; // null = modo "Nuevo", con valor = modo "Editar"

    // Constructor para CREAR (como ya tenías)
    public FormularioCliente(JFrame parent) {
        this(parent, null);
    }

    // Constructor para EDITAR (nuevo)
    public FormularioCliente(JFrame parent, Cliente clienteAEditar) {
        super(parent, clienteAEditar == null ? "Nuevo Cliente" : "Editar Cliente", true);
        this.clienteExistente = clienteAEditar;

        setLayout(new GridLayout(6, 2, 5, 5));
        setSize(350, 250);
        setLocationRelativeTo(parent);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Email:"));
        txtEmail = new JTextField();
        add(txtEmail);

        add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        add(txtTelefono);

        add(new JLabel("Empresa:"));
        txtEmpresa = new JTextField();
        add(txtEmpresa);

        add(new JLabel("Origen Lead:"));
        txtOrigenLead = new JTextField();
        add(txtOrigenLead);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarCliente());
        btnCancelar.addActionListener(e -> dispose());

        add(btnGuardar);
        add(btnCancelar);

        // Si estamos editando, precargamos los campos con los datos actuales
        if (clienteExistente != null) {
            txtNombre.setText(clienteExistente.getNombre());
            txtEmail.setText(clienteExistente.getEmail());
            txtTelefono.setText(clienteExistente.getTelefono());
            txtEmpresa.setText(clienteExistente.getEmpresa());
            txtOrigenLead.setText(clienteExistente.getOrigenLead());
        }
    }

    private void guardarCliente() {
        String nombre = txtNombre.getText().trim();
        String email = txtEmail.getText().trim();
        String telefono = txtTelefono.getText().trim();
        String empresa = txtEmpresa.getText().trim();
        String origenLead = txtOrigenLead.getText().trim();

        if (nombre.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nombre y Email son obligatorios.",
                "Datos incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        ClienteDAO dao = new ClienteDAO();

        if (clienteExistente == null) {
            // Modo CREAR
            Cliente nuevoCliente = new Cliente(0, nombre, email, telefono, empresa, origenLead, LocalDate.now());
            dao.insertar(nuevoCliente);
        } else {
            // Modo EDITAR: mantenemos el id y la fechaAlta originales
            Cliente clienteActualizado = new Cliente(
                clienteExistente.getId(),
                nombre, email, telefono, empresa, origenLead,
                clienteExistente.getFechaAlta()
            );
            dao.actualizar(clienteActualizado);
        }

        clienteGuardado = true;
        dispose();
    }

    public boolean isClienteGuardado() {
        return clienteGuardado;
    }
}