package sistemapuntodeventa;

//Librerias de MySQL
import com.mysql.jdbc.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Inventario {

    //atributos para el inventario
    int[] ID, precio;
    String[] producto;
    int totalArticulos = 0;
    private int max = 99999999;

    //variables para conectarse con MySQL
    private static Connection con;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "root123";
    private static final String url = "jdbc:mysql://localhost:3306/personas?characterEncoding=latin1";

    Inventario() {
        //Como los atributos son de tipo array, se le da un valor maximo para poder guardar datos sin problemas
        this.ID = new int[max];
        this.precio = new int[max];
        this.producto = new String[max];
        //Si se logra la conexion con la base de datos, se empieza a cargar el inventario
        if (conector()) {
            cargarInv();
        }
    }

    private boolean conector() {
        //Comprueba si se puede conectar a la base de datos, si se logra conectar, retorna un 'true', sino, retorna un 'false'
        con = null;
        try {
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, user, pass);
            if (con != null) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            return false;
        }
        return false;
    }

    private void cargarInv() {
        //empieza a cargar los datos de la base de datos a cada atributo de inventario
        Statement stmt = null;
        ResultSet rs = null;

        try {
            int i = 0;
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM inventario");
            if (stmt.execute("SELECT * FROM inventario")) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    this.producto[i] = rs.getString("producto");
                    this.ID[i] = rs.getInt("ID");
                    this.precio[i] = rs.getInt("Precio");
                    totalArticulos++;
                    //System.out.println(producto[i] + "\n" + ID[i] + "\n" + precio[i]);
                    i++;
                }
                rs.close();
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }
    }

    protected boolean compExistencia(int ident) {
        //comprueba si existe un ID ingresado, si existe, retorna un 'true', sino retorna un 'false'
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM inventario WHERE ID = " + ident);
            if (stmt.execute("SELECT * FROM inventario WHERE ID = " + ident)) {
                rs = stmt.getResultSet();
                rs.next();
                if (rs.getInt("ID") == ident) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
        return true;
    }

    protected boolean ingresarSQL(int ID, String Producto, int Precio) {
        //Ingresa un nuevo producto a la base de datos, si logra ingresarlo retorna un 'true' sino un 'false'
        Statement stmt = null;
        ResultSet rs = null;
        String SQL = "INSERT INTO inventario (producto,ID,Precio) VALUES ('" + Producto + "'," + ID + "," + Precio + ")";
        int res = 0;
        try {
            stmt = con.prepareStatement(SQL);
            res = stmt.executeUpdate(SQL);
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

    }

    protected boolean eliminarSQL(int ID) {
        //Se elimina un producto ingresando un ID
        Statement stmt = null;
        ResultSet rs = null;
        String SQL = "DELETE FROM inventario WHERE ID = " + ID;
        int res = 0;

        try {
            stmt = con.prepareStatement(SQL);
            res = stmt.executeUpdate(SQL);
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }

    }

    protected String[] enviarDatosSQL(int ident) {
        //retorna un String[] para mostrarlo en la modificacion
        Statement stmt = null;
        ResultSet rs = null;
        String retorno[] = new String[3];

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM inventario WHERE ID = " + ident);
            if (stmt.execute("SELECT * FROM inventario WHERE ID = " + ident)) {
                rs = stmt.getResultSet();
                rs.next();
                retorno[0] = rs.getString("producto");
                retorno[1] = Integer.toString(rs.getInt("ID"));
                retorno[2] = Integer.toString(rs.getInt("Precio"));
                return retorno;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            retorno[0] = "Error";
            retorno[1] = "Error";
            retorno[2] = "Error";
            return retorno;
        }
        retorno[0] = "Error";
        retorno[1] = "Error";
        retorno[2] = "Error";
        return retorno;
    }

    protected boolean modificar(int ID, int id, String prod, int prec) {
        //Se modifica la base de datos para el inventario
        Statement stmt = null;
        ResultSet rs = null;
        String SQL = "UPDATE inventario SET producto = '" + prod + "',Precio =  " + prec + ",ID = " + id + " WHERE ID = " + ID;

        int res = 0;
        try {
            stmt = con.prepareStatement(SQL);
            res = stmt.executeUpdate(SQL);
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }

    protected int consultarPrecio(String producto) {
        //Retorna el precio consultando el producto
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM inventario WHERE producto = '" + producto + "'");
            if (stmt.execute("SELECT * FROM inventario WHERE producto = '" + producto + "'")) {
                rs = stmt.getResultSet();
                rs.next();
                if (rs.getString("producto").equals(producto)) {
                    return rs.getInt("Precio");
                } else {
                    return -1;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return -1;
        }
        return -1;
    }

    protected int consultarID(String producto) {
        //Retorna la ID consultando el producto
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM inventario WHERE producto = '" + producto + "'");
            if (stmt.execute("SELECT * FROM inventario WHERE producto = '" + producto + "'")) {
                rs = stmt.getResultSet();
                rs.next();
                if (rs.getString("producto").equals(producto)) {
                    return rs.getInt("ID");
                } else {
                    return -1;
                }
            }
        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return -1;
        }
        return -1;
    }

    protected String[] buscarProducto(String producto) {
        //Retoran un String[] para entregar los productos que tengan coincidencia con el producto ingresado
        Statement stmt = null;
        ResultSet rs = null;
        String retorno[] = null;
        int i = 0;

        try {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM inventario WHERE producto LIKE '%" + producto + "%'");
            if (stmt.execute("SELECT * FROM inventario WHERE producto LIKE '%" + producto + "%'")) {
                rs = stmt.getResultSet();
                while (rs.next()) {
                    i++;
                }
            }

            if (i > 0) {
                retorno = new String[i];
                i = 0;
                stmt = con.createStatement();
                rs = stmt.executeQuery("SELECT * FROM inventario WHERE producto LIKE '%" + producto + "%'");
                if (stmt.execute("SELECT * FROM inventario WHERE producto LIKE '%" + producto + "%'")) {
                    rs = stmt.getResultSet();
                    while (rs.next()) {
                        retorno[i] = rs.getString("producto");
                        i++;
                    }
                }
                return retorno;
            }

        } catch (SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return retorno;
        }
        return retorno;
    }

}
