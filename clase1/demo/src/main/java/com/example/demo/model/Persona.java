package com.example.demo.model;
public abstract class Persona {
    protected int id;
    protected String nombre;
    public Persona(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }
    @Override
    public String toString() {
        return this.id+" "+this.nombre;
    }
}
