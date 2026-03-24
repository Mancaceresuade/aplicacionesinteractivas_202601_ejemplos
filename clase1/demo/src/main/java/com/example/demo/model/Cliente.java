package com.example.demo.model;
public class Cliente extends Persona{
    public Cliente(int id, String nombre, String nroCuit) {
        super(id, nombre);
        this.nroCuit = nroCuit;
    }

    private String nroCuit;
    
    @Override
    public String toString() {
        return super.toString() + " " + this.nroCuit;
    }

    public String getNombre() {
        return this.nombre;
    }
}
