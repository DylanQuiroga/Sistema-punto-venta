package sistemapuntodeventa;

public class Test3 {

    public static void main(String[] args) {
        Inventario inv = new Inventario();
        for (int i = 0; i < inv.totalArticulos; i++) {
            System.out.println(inv.ID[i] + " " + inv.producto[i] + " " + inv.precio[i]);
        }
    }

}
