package entities;

import java.io.Serializable;

public class Modulo implements Serializable {

    private static final long SerialVersionUID = 2L;

    // Declaraciones
    private int id;
    private String nombre;
    private String codigo;

    public Modulo(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "MODULO\n" +
                "id: " + id + '\n' +
                "Nombre: " + nombre + '\n' +
                "Código: " + codigo;
    }
}
