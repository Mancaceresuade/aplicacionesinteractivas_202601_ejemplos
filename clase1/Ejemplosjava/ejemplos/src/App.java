import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        proceso();
    }    
    public static void proceso() {
        // Persona persona = new Persona(10,"Carlos");
        Cliente cliente = new Cliente(10, "Carlos", "23-3452345-4");
        Cliente cliente1 = new Cliente(10, "Juan", "23-3452345-4");
        Cliente cliente2 = new Cliente(10, "Pedro", "23-3452345-4");
        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(cliente);
        clientes.add(cliente1);
        clientes.add(cliente2);
        // clientes.forEach(c -> System.out.println(c));
        clientes.stream().filter(c -> c.getNombre().equals("Carlos"))
            .forEach(c -> System.out.println(c));

        ArrayList<IComprable> comprables = new ArrayList<>();
        Proveedor proveedor = new Proveedor(20, "Proveedor1", "23-3452345-4");
        Representante   representante = new Representante(30, "Zona1");
        comprables.add(proveedor);
        comprables.add(representante);
        comprables.forEach(c -> c.comprar()); // polimorfismo, el mismo mensaje se comporta de diferente manera dependiendo del objeto que lo reciba

    }        
}