public class Main {
    public static void main(String[] args) {
        //int[] numeros = {1, 2, 3, 4, 5};
        //imprimirArray(numeros);
        int[][] matriz = {{3,4,5},{3,2,1},{5,4,3}};
        imprimirMatriz(matriz);
    }
    private static void imprimirMatriz(int[][] matriz) {
        for (int i = 0; i < matriz.length; i++) { // 1+2n+n
            imprimirFila(matriz[i]);  // h(n) * n = (1+5n)*n = n+5n**2
            System.out.println(" ");  // n
        }
    } // f(n) = 1+2n+n+n+5n**2+n= 1+5n+5n**2
      // demostracion matematica
      // termino dominate, constante + 1
      // f(n) <= c g(n)
      // 1+5n+5n**2 <= 6n**2
      // 1/n**2+5n/n**2 <= 6n**2/n**2
      // 1/n**2+5/n <= 6
      // desde que valores de n0 se cumple ?
      // n0 = 2
      // demostramos que f(n) pertenece a O(n**2) para c=6 y n0=2

    private static void imprimirFila(int[] fila) {
        for (int j = 0; j < fila.length; j++) { // 1 + 2n + n
            System.out.println(fila+ " "); // 2n
        }
    } // h(n) = 1+5n


    public static void imprimirArray(int[] numeros) {
        for (int i = 0; i < numeros.length; i++) { // 1 + 2n + n
            System.out.println(numeros[i]); // 2n
        }
    } // f(n) = 1 + 2n + n + 2n = 1+5n => O(n)


    private static void pruebas() {
        System.out.println(Persona.cantidadCorazones);
        Persona persona = new Persona("Juan");
        System.out.println(persona.getNombre());
        int numero = 10;
        numero = 11;
        double num = 10.0;
        long numL = 10000000000L;
        String palabra = "Hola";
        palabra = " mundo";
        System.out.println(palabra);
        char letra = 'A';
        //System.out.println(letra == palabra.charAt(0));
        //System.out.println("Hello world!");

    }

}