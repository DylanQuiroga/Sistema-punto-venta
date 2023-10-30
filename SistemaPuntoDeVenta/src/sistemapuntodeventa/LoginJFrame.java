package sistemapuntodeventa;

//librerias necesarias para el correcto funcionamiento
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class LoginJFrame extends JFrame {
    
    //Se cargan los datos de la base de datos para empleado y administrador
    Persona admin = new Administrador();
    Persona empleado = new Empleado();
    String fuente = "Times New Roman";

    LoginJFrame() {
        
        //Si logra establecer una conexion con la base de datos, empieza a ejecutar el codigo
        if (admin.conector()) {
            //Variables de componentes de la ventana (botones, textos, caja de textos, etc)
            JLabel textoRut, textoContra, textoTitulo, separacion, textoayuda1, textoayuda2;
            JTextField ingresarRut;
            JPasswordField ingresarContra;
            JButton boton, ayuda;
            
            //Se le establese un texto a cada componente
            textoRut = new JLabel("Rut");
            textoContra = new JLabel("Contraseña");
            textoTitulo = new JLabel("Inicio de sesión");
            separacion = new JLabel("---------------------------------------------------------------------------------------------------------------------------------------");
            textoayuda1 = new JLabel("¿Se le ha olvidado la contraseña?");
            textoayuda2 = new JLabel("Presioname");
            ingresarRut = new JTextField(5);
            ingresarContra = new JPasswordField(5);
            boton = new JButton("Ingresar");
            ayuda = new JButton("?");
            
            //funcion necesaria para usar el .setBounds();
            setLayout(null);
            
            //A las componentes se le establece una fuente
            textoRut.setFont(new Font(fuente, Font.PLAIN, 20));
            textoContra.setFont(new Font(fuente, Font.PLAIN, 20));
            textoTitulo.setFont(new Font(fuente, Font.PLAIN, 20));
            boton.setFont(new Font(fuente, Font.PLAIN, 20));
            ingresarRut.setFont(new Font(fuente, Font.PLAIN, 20));
            ingresarContra.setFont(new Font(fuente, Font.PLAIN, 20));
            textoayuda1.setFont(new Font(fuente, Font.PLAIN, 15));
            textoayuda2.setFont(new Font(fuente, Font.PLAIN, 15));
            
            //Al texto de 'presioname' se le da el color azul
            textoayuda2.setForeground(Color.blue);
            
            //Posicionamiento de cada componente y se le agrega a la ventana
            textoTitulo.setBounds(125, 0, 180, 100);
            add(textoTitulo);

            textoRut.setBounds(30, 50, 60, 100);
            add(textoRut);

            ingresarRut.setBounds(90, 90, 200, 20);
            add(ingresarRut);

            ayuda.setBounds(300, 80, 41, 40);
            ayuda.setBackground(new Color(167, 195, 201));
            add(ayuda);

            textoContra.setBounds(30, 120, 120, 100);
            add(textoContra);

            ingresarContra.setBounds(150, 160, 200, 20);
            add(ingresarContra);

            boton.setBounds(125, 230, 130, 40);
            boton.setBackground(new Color(167, 195, 201));
            add(boton);

            separacion.setBounds(0, 280, 600, 100);
            add(separacion);

            textoayuda1.setBounds(90, 310, 300, 100);
            add(textoayuda1);

            textoayuda2.setBounds(150, 330, 300, 100);
            add(textoayuda2);
            
            ///Al boton de 'Ingresar' se le da una accion, la cual hace una comparacion de lo ingresado con las variables que tiene el empleado y el administrador
            boton.addActionListener((ActionEvent e) -> {
                String rutIngresado = ingresarRut.getText();
                String contraIngresado = ingresarContra.getText();

                if ("".equals(rutIngresado)) {
                    JOptionPane.showMessageDialog(null, "Ingrese un rut");
                } else if ("".equals(contraIngresado)) {
                    JOptionPane.showMessageDialog(null, "Ingrese una contraseña");
                } else if (admin.rut.equals(rutIngresado) && admin.contra.equals(contraIngresado)) {
                    dispose();
                    new SistemaInv();
                } else if (empleado.rut.equals(rutIngresado) && empleado.contra.equals(contraIngresado)) {
                    dispose();
                    new ModuloVenta();
                } else if (!admin.rut.equals(rutIngresado) || !admin.contra.equals(contraIngresado) || !empleado.rut.equals(rutIngresado) || !empleado.contra.equals(contraIngresado)) {
                    JOptionPane.showMessageDialog(null, "Rut o contraseña incorrectos");
                }

            });
            
            //El boton ayuda puestra una pequenia ventana de como se tiene que ingresar el rut
            ayuda.addActionListener((ActionEvent e) -> {
                JOptionPane.showMessageDialog(null, "Ingrese un Rut con puntos y guion. \nEjemplo: 12.345.678-9");
            });
            
            //Configuracion que obtiene la ventana de login
            setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            setSize(400, 600);
            //setExtendedState(JFrame.MAXIMIZED_BOTH);
            setResizable(false);
            setLocationRelativeTo(null);
            setTitle("Login");
            
            //Texto presionable que ejecuta el formulario (Para modificar datos)
            textoayuda2.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    formulario();
                }
            });
        } else {    
            //Si la base de datos no existe o los valores estan mal ingresados, solo se ejecuta una ventana de error y el programa se detiene
            JOptionPane.showMessageDialog(null, "Error al vincular la base de datos");
        }
    }

    private void formulario() {
        //Se cierra la ventana de login y se ejecuta el formulario para validar de que se realizaran los cambios sin problemas
        dispose();
        JFrame formulario = new JFrame();
        JLabel titulo, rut, palabraClave;
        JButton validar;
        JRadioButton nombres, apellidos, telefono;
        ButtonGroup grupo = new ButtonGroup();
        JTextField ingRut, ingRB, ingPC;

        titulo = new JLabel("Recuperacion de contraseña");
        rut = new JLabel("Rut");
        palabraClave = new JLabel("Palabra clave");
        validar = new JButton("Validar");
        nombres = new JRadioButton("Nombres", true);
        apellidos = new JRadioButton("Apellidos", false);
        telefono = new JRadioButton("Teléfono", false);
        ingRut = new JTextField(5);
        ingRB = new JTextField(5);
        ingPC = new JTextField(5);

        JMenuItem volver;
        JMenuBar menu;
        volver = new JMenuItem("Volver");
        menu = new JMenuBar();

        menu.add(volver);
        formulario.setJMenuBar(menu);

        formulario.setLayout(null);
        grupo.add(nombres);
        grupo.add(apellidos);
        grupo.add(telefono);

        titulo.setFont(new Font(fuente, Font.PLAIN, 20));
        rut.setFont(new Font(fuente, Font.PLAIN, 20));
        palabraClave.setFont(new Font(fuente, Font.PLAIN, 17));
        validar.setFont(new Font(fuente, Font.PLAIN, 20));
        nombres.setFont(new Font(fuente, Font.PLAIN, 15));
        apellidos.setFont(new Font(fuente, Font.PLAIN, 15));
        telefono.setFont(new Font(fuente, Font.PLAIN, 15));
        ingRB.setFont(new Font(fuente, Font.PLAIN, 20));
        ingRut.setFont(new Font(fuente, Font.PLAIN, 20));
        ingPC.setFont(new Font(fuente, Font.PLAIN, 20));

        titulo.setBounds(80, 0, 250, 100);
        formulario.add(titulo);

        rut.setBounds(45, 50, 60, 100);
        formulario.add(rut);

        ingRut.setBounds(140, 90, 200, 20);
        formulario.add(ingRut);

        nombres.setBounds(25, 140, 100, 20);
        formulario.add(nombres);

        apellidos.setBounds(25, 160, 100, 20);
        formulario.add(apellidos);

        telefono.setBounds(25, 180, 100, 20);
        formulario.add(telefono);

        ingRB.setBounds(140, 160, 200, 20);
        formulario.add(ingRB);

        palabraClave.setBounds(30, 190, 160, 100);
        formulario.add(palabraClave);

        ingPC.setBounds(140, 230, 200, 20);
        formulario.add(ingPC);

        validar.setBounds(125, 320, 130, 40);
        validar.setBackground(new Color(167, 195, 201));
        formulario.add(validar);
        
        //Si se preciona volver, este volvera al login
        volver.addActionListener((ActionEvent e) -> {
            formulario.dispose();
            new LoginJFrame();
        });
        
        //Se valida lo ingresado para poder ingresar los nuevos valores
        validar.addActionListener((ActionEvent e) -> {
            if ("".equals(ingRut.getText())) {
                JOptionPane.showMessageDialog(null, "Ingrese un rut");
            } else if ("".equals(ingRB.getText()) && nombres.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingrese el nombre");
            } else if ("".equals(ingRB.getText()) && apellidos.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingrese el apellido");
            } else if ("".equals(ingRB.getText()) && telefono.isSelected()) {
                JOptionPane.showMessageDialog(null, "Ingrese el telefono");
            } else if ("".equals(ingPC.getText())) {
                JOptionPane.showMessageDialog(null, "Ingrese la palabra clave");
            } else if ((ingRut.getText()).equals(admin.rut)) {
                if (nombres.isSelected() && (ingRB.getText().equals(admin.nombres)) && (ingPC.getText()).equals(admin.palabraClave)) {
                    formulario.dispose();
                    llenar(1);
                } else if (apellidos.isSelected() && (ingRB.getText().equals(admin.apellidos)) && (ingPC.getText()).equals(admin.palabraClave)) {
                    formulario.dispose();
                    llenar(1);
                } else if (telefono.isSelected() && (ingRB.getText().equals(admin.telefono)) && (ingPC.getText()).equals(admin.palabraClave)) {
                    formulario.dispose();
                    llenar(1);
                } else {
                    JOptionPane.showMessageDialog(null, "Los datos no coinciden");
                }
            } else if ((ingRut.getText()).equals(empleado.rut)) {
                if (nombres.isSelected() && (ingRB.getText().equals(empleado.nombres)) && (ingPC.getText()).equals(empleado.palabraClave)) {
                    formulario.dispose();
                    llenar(0);
                } else if (apellidos.isSelected() && (ingRB.getText().equals(empleado.apellidos)) && (ingPC.getText()).equals(empleado.palabraClave)) {
                    formulario.dispose();
                    llenar(0);
                } else if (telefono.isSelected() && (ingRB.getText().equals(empleado.telefono)) && (ingPC.getText()).equals(empleado.palabraClave)) {
                    formulario.dispose();
                    llenar(0);
                } else {
                    JOptionPane.showMessageDialog(null, "Los datos no coinciden");
                }
            }
        }
        );

        formulario.setDefaultCloseOperation(EXIT_ON_CLOSE);
        formulario.setVisible(true);
        formulario.setSize(400, 600);
        formulario.setResizable(false);
        formulario.setLocationRelativeTo(null);
    }

    private void llenar(int i) {
        //Se ejecura una ventana para ingresar los nuevos valores que posteriormente van a ser actualizado en la base de datos
        JFrame llenar = new JFrame();
        JLabel titulo = new JLabel("Modificar Datos");
        JLabel rut = new JLabel("Rut");
        JLabel contra = new JLabel("Contraseña");
        JLabel nombres = new JLabel("Nombres");
        JLabel apellidos = new JLabel("Apellidos");
        JLabel fono = new JLabel("Telefono");
        JLabel PC = new JLabel("Palabra clave");
        JTextField ingRut = new JTextField(5);
        JTextField ingContra = new JTextField(5);
        JTextField ingNom = new JTextField(5);
        JTextField ingApe = new JTextField(5);
        JTextField ingFono = new JTextField(5);
        JTextField ingPC = new JTextField(5);
        JButton modificar = new JButton("Modificar");

        titulo.setFont(new Font(fuente, Font.PLAIN, 20));
        rut.setFont(new Font(fuente, Font.PLAIN, 20));
        contra.setFont(new Font(fuente, Font.PLAIN, 20));
        nombres.setFont(new Font(fuente, Font.PLAIN, 20));
        apellidos.setFont(new Font(fuente, Font.PLAIN, 20));
        fono.setFont(new Font(fuente, Font.PLAIN, 20));
        PC.setFont(new Font(fuente, Font.PLAIN, 20));
        modificar.setFont(new Font(fuente, Font.PLAIN, 20));
        ingRut.setFont(new Font(fuente, Font.PLAIN, 20));
        ingContra.setFont(new Font(fuente, Font.PLAIN, 20));
        ingNom.setFont(new Font(fuente, Font.PLAIN, 20));
        ingApe.setFont(new Font(fuente, Font.PLAIN, 20));
        ingFono.setFont(new Font(fuente, Font.PLAIN, 20));
        ingPC.setFont(new Font(fuente, Font.PLAIN, 20));

        llenar.setLayout(null);

        titulo.setBounds(120, 0, 250, 100);
        llenar.add(titulo);

        rut.setBounds(20, 50, 60, 100);
        llenar.add(rut);

        ingRut.setBounds(140, 90, 200, 20);
        llenar.add(ingRut);

        contra.setBounds(20, 100, 100, 100);
        llenar.add(contra);

        ingContra.setBounds(140, 140, 200, 20);
        llenar.add(ingContra);

        nombres.setBounds(20, 190, 100, 20);
        llenar.add(nombres);

        ingNom.setBounds(140, 190, 200, 20);
        llenar.add(ingNom);

        apellidos.setBounds(20, 200, 100, 100);
        llenar.add(apellidos);

        ingApe.setBounds(140, 240, 200, 20);
        llenar.add(ingApe);

        fono.setBounds(20, 250, 100, 100);
        llenar.add(fono);

        ingFono.setBounds(140, 290, 200, 20);
        llenar.add(ingFono);

        PC.setBounds(20, 300, 130, 100);
        llenar.add(PC);

        ingPC.setBounds(140, 340, 200, 20);
        llenar.add(ingPC);

        modificar.setBounds(125, 430, 130, 40);
        modificar.setBackground(new Color(167, 195, 201));
        llenar.add(modificar);

        if (i == 1) {
            //si se trata de un administrador, se posicionan los valores que tiene admin en cada caja de texto
            ingRut.setText(admin.rut);
            ingContra.setText(admin.contra);
            ingNom.setText(admin.nombres);
            ingApe.setText(admin.apellidos);
            ingFono.setText(admin.telefono);
            ingPC.setText(admin.palabraClave);
        } else if (i == 0) {
            //si se trata de un empleado, se posicionan los valores que tiene empleado en cada caja de texto
            ingRut.setText(empleado.rut);
            ingContra.setText(empleado.contra);
            ingNom.setText(empleado.nombres);
            ingApe.setText(empleado.apellidos);
            ingFono.setText(empleado.telefono);
            ingPC.setText(empleado.palabraClave);
        }
        
        //se toman los datos de la caja de texto y se modifica en la base de texto, para luego volver a ejecutar el login
        modificar.addActionListener((ActionEvent e) -> {
            if ("".equals(ingRut.getText()) || "".equals(ingContra.getText()) || "".equals(ingNom.getText()) || "".equals(ingApe.getText()) || "".equals(ingFono.getText()) || "".equals(ingPC.getText())) {
                JOptionPane.showMessageDialog(null, "Rellene los datos que faltan");
            } else if (i == 1 || i == 0) {
                boolean consulta = admin.cambiar(ingRut.getText(), ingContra.getText(), ingNom.getText(), ingApe.getText(), ingFono.getText(), ingPC.getText(), i);
                if (consulta) {
                    llenar.dispose();
                    JOptionPane.showMessageDialog(null, "Los datos han sido cambiados exitosamente");
                    new LoginJFrame();
                } else {
                    JOptionPane.showMessageDialog(null, "Error con la base de datos");
                }
            }
        });

        llenar.setDefaultCloseOperation(EXIT_ON_CLOSE);
        llenar.setVisible(true);
        llenar.setSize(400, 600);
        llenar.setResizable(false);
        llenar.setLocationRelativeTo(null);
    }

}
