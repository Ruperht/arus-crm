package aruscrm;

public class ComboItem {
    private int id;
    private String texto;

    public ComboItem(int id, String texto) {
        this.id = id;
        this.texto = texto;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return texto; // esto es lo que se ve en el JComboBox
    }
}
