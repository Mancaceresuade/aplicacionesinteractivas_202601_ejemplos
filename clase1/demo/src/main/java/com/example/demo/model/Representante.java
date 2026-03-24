package com.example.demo.model;
public class Representante extends Persona implements IComprable{
    public Representante(int id, String nombre) {
        super(id, nombre);
    }

    @Override
    public void comprar() {
        System.out.println("Comprando al representante, en zona "+this.nombre);
    }
    
}
