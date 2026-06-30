package aruscrm;

import javax.swing.*;
import java.awt.*;
import dao.ServicioDAO;
import modelo.Servicio;

public class FormularioServicio extends JDialog {

    private JTextField txtNombre;
    private JTextField txtDescripcion;
    private JTextField txtPrecioBase;
    private JTextField txtTipo;
    private boolean servicioGuardado = false;

    private Servicio servicioExistente; // null = "Nuevo", con valor = "Editar"

    // Constructor para CREAR
    public FormularioServicio(JFrame parent) {
        this(parent, null);
    }

    // Constructor para EDITAR
    public FormularioServicio(JFrame parent, Servicio servicioAEditar) {
        super(parent, servicioAEditar == null ? "Nuevo Servicio" : "Editar Servicio", true);
        this.servicioExistente = servicioAEditar;

        setLayout(new GridLayout(6, 2, 5, 5));
        setSize(350, 250);
        setLocationRelativeTo(parent);

        add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        add(txtNombre);

        add(new JLabel("Descripción:"));
        txtDescripcion = new JTextField();
        add(txtDescripcion);

        add(new JLabel("Precio Base:"));
        txtPrecioBase = new JTextField();
        add(txtPrecioBase);

        add(new JLabel("Tipo:"));
        txtTipo = new JTextField();
        add(txtTipo);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");

        btnGuardar.addActionListener(e -> guardarServicio());
        btnCancelar.addActionListener(e -> dispose());

        add(btnGuardar);
        add(btnCancelar);

        // Si estamos editando, precargamos los campos
        if (servicioExistente != null) {
            txtNombre.setText(servicioExistente.getNombre());
            txtDescripcion.setText(servicioExistente.getDescripcion());
            txtPrecioBase.setText(String.valueOf(servicioExistente.getPrecioBase()));
            txtTipo.setText(servicioExistente.getTipo());
        }
    }

    private void guardarServicio() {
        String nombre = txtNombre.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String precioTexto = txtPrecioBase.getText().trim();
        String tipo = txtTipo.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nombre y Precio Base son obligatorios.",
                "Datos incompletos",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        double precioBase;
        try {
            precioBase = Double.parseDouble(precioTexto);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "El Precio Base debe ser un número (ej: 49.90).",
                "Valor inválido",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        ServicioDAO dao = new ServicioDAO();

        if (servicioExistente == null) {
            // Modo CREAR
            Servicio nuevoServicio = new Servicio(0, nombre, descripcion, precioBase, tipo);
            dao.insertar(nuevoServicio);
        } else {
            // Modo EDITAR: mantenemos el id original
            Servicio servicioActualizado = new Servicio(
                servicioExistente.getId(), nombre, descripcion, precioBase, tipo
            );
            dao.actualizar(servicioActualizado);
        }

        servicioGuardado = true;
        dispose();
    }

    public boolean isServicioGuardado() {
        return servicioGuardado;
    }
}