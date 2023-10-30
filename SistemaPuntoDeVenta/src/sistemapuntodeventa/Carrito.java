package sistemapuntodeventa;

public class Carrito {
    int[] ID, cantidad,precio;
    String[] producto;
    int max = 999999;
    
    Carrito(){
        this.ID = new int[max];
        this.cantidad = new int[max];
        this.precio = new int[max];
        this.producto = new String[max];
    }
}
