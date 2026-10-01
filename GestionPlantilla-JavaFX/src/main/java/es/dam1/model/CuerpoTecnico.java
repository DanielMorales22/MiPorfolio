package es.dam1.model;

public class CuerpoTecnico {

    private int id;
    private String nombre;
    private int edad;
    private String funcion;

    public CuerpoTecnico(int id, String nombre, int edad, String funcion) {
        this.id = id;
        this.nombre = nombre;
        this.edad = edad;
        this.funcion = funcion;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
    public String getFuncion() { return funcion; }
    public void setFuncion(String funcion) { this.funcion = funcion; }
}
