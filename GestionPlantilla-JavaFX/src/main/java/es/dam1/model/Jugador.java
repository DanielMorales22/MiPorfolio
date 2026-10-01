package es.dam1.model;

public class Jugador {

    private int id;
    private int dorsal;
    private String nombre;
    private String posicion;
    private String club;
    private int edad;

    public Jugador(int id, int dorsal, String nombre, String posicion, String club, int edad) {
        this.id = id;
        this.dorsal = dorsal;
        this.nombre = nombre;
        this.posicion = posicion;
        this.club = club;
        this.edad = edad;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getDorsal() { return dorsal; }
    public void setDorsal(int dorsal) { this.dorsal = dorsal; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getPosicion() { return posicion; }
    public void setPosicion(String posicion) { this.posicion = posicion; }
    public String getClub() { return club; }
    public void setClub(String club) { this.club = club; }
    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }
}
