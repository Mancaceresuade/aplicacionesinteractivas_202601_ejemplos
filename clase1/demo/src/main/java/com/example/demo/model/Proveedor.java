package com.example.demo.model;
public class Proveedor extends Persona implements IComprable{
    public Proveedor(int id, String nombre, String nroCuit) {
        super(id, nombre);
        this.nroProveedor = nroCuit;
    }

    private String nroProveedor;
    
    @Override
    public String toString() {
        return super.toString() + " " + this.nroProveedor;
    }    
    
    public String getNombre() {
        return this.nombre;
    }

    @Override
    public void comprar() {
        System.out.println("Comprando al proveedor "+this.nombre);
    }
}
