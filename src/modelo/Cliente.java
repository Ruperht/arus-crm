package modelo;

import java.time.LocalDate;

public class Cliente extends Persona {

    private int id;
    private String empresa;
    private String origenLead;
    private LocalDate fechaAlta;

    public Cliente(int id, String nombre, String email, String telefono,
                    String empresa, String origenLead, LocalDate fechaAlta) {
        super(nombre, email, telefono);
        this.id = id;
        this.empresa = empresa;
        this.origenLead = origenLead;
        this.fechaAlta = fechaAlta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getOrigenLead() {
        return origenLead;
    }

    public void setOrigenLead(String origenLead) {
        this.origenLead = origenLead;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    @Override
    public void mostrarInfo() {
        super.mostrarInfo();
        System.out.println("Empresa: " + empresa + " | Origen: " + origenLead + " | Alta: " + fechaAlta);
    }
}
