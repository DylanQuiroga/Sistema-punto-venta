package sistemapuntodeventa;

//librerias para el correcto funcionamiento (librerias de MySQL)
import com.mysql.jdbc.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Persona {

    //Atributos para Empleado y Administrador
    String rut, contra, telefono, nombres, apellidos, palabraClave;
    int admin;

    //Variables para conectarse con la base de datos
    private static Connection con;
    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String user = "root";
    private static final String pass = "root123";
    private static final String url = "jdbc:mysql://localhost:3306/personas?characterEncoding=latin1";

    Persona(int valor) {
        //Si se logra acceder a la base de datos, ejecuta una consulta para guardar los datos al empleado o al administrador
        if (conector()) {
            cargarUsuario(valor);
        }
    }

    protected boolean conector() {
        con = null;
        try {
            //Se conecta a la base de datos, si lo consigue, retorna un 'true'
            Class.forName(driver);
            con = (Connection) DriverManager.getConnection(url, user, pass);
            if (con != null) {
                return true;
            }
        } catch (ClassNotFoundException | SQLException e) {
            //si no lo logra, ya sea por que se ingreso mal un dato o por otro motivo, retorna un 'false'
            return false;
        }
        return false;
    }

    private void cargarUsuario(int valor) {
        //Aqui empiezan a cargar los valores de la base de datos a sus respectivas variables
        Statement stmt = null;
        ResultSet rs = null;
        try {
            //Aqui consulta a la base de datos, donde extrae los datos ya sea para el empleado o para el administrador
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM personas WHERE admin = " + valor);
            if (stmt.execute("SELECT * FROM personas WHERE admin = " + valor)) {
                rs = stmt.getResultSet();
                rs.next();
                //Los datos de la tabla se guardan en sus respectivas variables
                this.rut = rs.getString("rut");
                this.contra = rs.getString("contra");
                this.telefono = rs.getString("telefono");
                this.nombres = rs.getString("nombres");
                this.apellidos = rs.getString("apellidos");
                this.palabraClave = rs.getString("palabraClave");
                this.admin = valor;
                //System.out.println(rut + "\n" + contra + "\n" + telefono + "\n" + nombres + "\n" +apellidos+ "\n" +palabraClave+ "\n"+admin);
                rs.close();
            }
        } catch (SQLException ex) {
            //Si ocurre algun error, las variables retornan con el mensaje de error
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            this.rut = "Error";
            this.contra = "Error";
            this.telefono = "Error";
            this.nombres = "Error";
            this.apellidos = "Error";
            this.palabraClave = "Error";
            this.admin = -1;
        }
    }

    protected boolean cambiar(String rut, String contra, String nombre, String apellido, String telefono, String palabraClave, int i) {
        //Si el usuario realiza un cambio en las variables, la base de datos se actualiza 
        Statement stmt = null;
        ResultSet rs = null;
        String SQL = "UPDATE personas SET rut = '" + rut + "',contra = '" + contra + "',nombres = '" + nombre + "',apellidos = '" + apellido + "',telefono = '" + telefono + "',palabraClave = '" + palabraClave + "' WHERE admin = " + i;
        int res = 0;
        try {
            stmt = con.prepareStatement(SQL);
            res = stmt.executeUpdate(SQL);
            //Si la consulta se realiza de manera exitosa, retorna un 'true', el en caso contrario, retorna un 'false'
            if (res > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            //Si ocurre al gun error, retorna un 'false'
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
            return false;
        }
    }
}
