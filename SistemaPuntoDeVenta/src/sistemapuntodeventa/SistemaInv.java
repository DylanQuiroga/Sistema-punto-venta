package sistemapuntodeventa;

//Librerias para el correcto funcionamiento (por lo general hay librerias de Swing)
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableModel;

public class SistemaInv extends JFrame {
    
    //se llama a inventario
    Inventario inv = new Inventario();
    private int i;
    String fuente = "Times New Roman";

    SistemaInv() {

        DefaultTableModel tablaDF = new DefaultTableModel();
        tablaDF.addColumn("ID");
        tablaDF.addColumn("Producto");
        tablaDF.addColumn("Precio");

        JTable tabla;
        JButton agregar, eliminar, modificar, actualizar;
        JMenuItem salir;
        JMenuBar menu;
        //String columna[] = {"Nombre", "Id", "Precio"};
        //String filaProductos[][] = {{"", "", ""}};
        tabla = new JTable();
        agregar = new JButton("Agregar producto");
        eliminar = new JButton("Eliminar producto");
        modificar = new JButton("Modificar producto");
        actualizar = new JButton("Actualizar tabla");
        salir = new JMenuItem("Cerrar sesión");
        menu = new JMenuBar();

        tabla.setFont(new Font(fuente, Font.PLAIN, 20));
        agregar.setFont(new Font(fuente, Font.PLAIN, 20));
        eliminar.setFont(new Font(fuente, Font.PLAIN, 20));
        modificar.setFont(new Font(fuente, Font.PLAIN, 20));
        actualizar.setFont(new Font(fuente, Font.PLAIN, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        setLayout(new GridBagLayout());

        for (i = 0; i < inv.totalArticulos; i++) {
            Object[] fila = new Object[3];
            fila[0] = inv.ID[i];
            fila[1] = inv.producto[i];
            fila[2] = inv.precio[i];
            tablaDF.addRow(fila);
        }

        tabla.setModel(tablaDF);
        tabla.setEnabled(false);
        tabla.setFillsViewportHeight(true);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        menu.add(salir);
        setJMenuBar(menu);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(10, 10, 0, 10);       // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(tabla, gbc);
        add(new JScrollPane(tabla), gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        agregar.setBackground(new Color(190, 235, 123));
        add(agregar, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        modificar.setBackground(new Color(116, 113, 179));
        add(modificar, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 0, 10);
        eliminar.setBackground(new Color(231, 97, 61));
        add(eliminar, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(0, 10, 0, 10);
        actualizar.setBackground(new Color(167, 195, 201));
        add(actualizar, gbc);

        agregar.addActionListener((ActionEvent e) -> {
            Agregar();
        });

        eliminar.addActionListener((ActionEvent e) -> {
            Eliminar();
        });

        modificar.addActionListener((ActionEvent e) -> {
            Modificar();
        });

        salir.addActionListener((ActionEvent e) -> {
            dispose();
            new LoginJFrame();
        });

        actualizar.addActionListener((ActionEvent) -> {
            dispose();
            new SistemaInv();
        });

        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(700, 600);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Sistema de inventario");

    }

    private void Agregar() {
        SpinnerModel value1 = new SpinnerNumberModel(1, 1, 999999999, 1);
        SpinnerModel value2 = new SpinnerNumberModel(1, 1, 999999999, 1);
        JFrame ag = new JFrame();
        JLabel titulo = new JLabel("Agregar");
        JLabel id = new JLabel("ID");
        JLabel prop = new JLabel("Producto");
        JLabel prec = new JLabel("Precio");
        JSpinner ingID = new JSpinner(value1);
        JTextField ingProp = new JTextField(5);
        JSpinner ingPrec = new JSpinner(value2);
        JButton can = new JButton("Cancelar");
        JButton acep = new JButton("Aceptar");

        ag.setLayout(null);

        titulo.setFont(new Font(fuente, Font.PLAIN, 20));
        id.setFont(new Font(fuente, Font.PLAIN, 20));
        prop.setFont(new Font(fuente, Font.PLAIN, 20));
        prec.setFont(new Font(fuente, Font.PLAIN, 20));
        ingID.setFont(new Font(fuente, Font.PLAIN, 15));
        ingProp.setFont(new Font(fuente, Font.PLAIN, 20));
        ingPrec.setFont(new Font(fuente, Font.PLAIN, 15));
        can.setFont(new Font(fuente, Font.PLAIN, 20));
        acep.setFont(new Font(fuente, Font.PLAIN, 20));

        titulo.setBounds(150, 0, 250, 100);
        ag.add(titulo);

        id.setBounds(20, 50, 60, 100);
        ag.add(id);

        ingID.setBounds(140, 90, 200, 20);
        ag.add(ingID);

        prop.setBounds(20, 100, 100, 100);
        ag.add(prop);

        ingProp.setBounds(140, 140, 200, 20);
        ag.add(ingProp);

        prec.setBounds(20, 190, 100, 20);
        ag.add(prec);

        ingPrec.setBounds(140, 190, 200, 20);
        ag.add(ingPrec);

        can.setBounds(30, 430, 130, 40);
        can.setBackground(new Color(167, 195, 201));
        ag.add(can);

        acep.setBounds(220, 430, 130, 40);
        acep.setBackground(new Color(167, 195, 201));
        ag.add(acep);

        can.addActionListener((ActionEvent e) -> {
            ag.dispose();
        });

        acep.addActionListener((ActionEvent e) -> {
            int precio = (Integer) ingPrec.getValue();
            int ID = (Integer) ingID.getValue();
            String Producto = ingProp.getText();

            if ("".equals(Producto)) {
                JOptionPane.showMessageDialog(null, "Ingrese el nombre del producto");
            } else if (inv.compExistencia(ID)) {
                JOptionPane.showMessageDialog(null, "Ya existe un producto con ese ID");
            } else if (!inv.compExistencia(ID)) {
                boolean consulta = inv.ingresarSQL(ID, Producto, precio);
                if (consulta) {
                    dispose();
                    ag.dispose();
                    JOptionPane.showMessageDialog(null, "Producto agregado exitosamente");
                    new SistemaInv();
                } else {
                    JOptionPane.showMessageDialog(null, "Error con la base de datos");
                }
            }

        });

        ag.setVisible(true);
        ag.setSize(400, 600);
        ag.setResizable(false);
        ag.setLocationRelativeTo(null);

    }

    private void Eliminar() {
        SpinnerModel value1 = new SpinnerNumberModel(1, 1, 999999999, 1);
        JFrame el = new JFrame();
        JLabel titulo = new JLabel("Eliminar");
        JLabel id = new JLabel("ID");
        JSpinner ingID = new JSpinner(value1);
        JButton can = new JButton("Cancelar");
        JButton acep = new JButton("Aceptar");

        el.setLayout(null);

        titulo.setFont(new Font(fuente, Font.PLAIN, 20));
        id.setFont(new Font(fuente, Font.PLAIN, 20));
        ingID.setFont(new Font(fuente, Font.PLAIN, 15));
        can.setFont(new Font(fuente, Font.PLAIN, 20));
        acep.setFont(new Font(fuente, Font.PLAIN, 20));

        titulo.setBounds(150, 0, 250, 100);
        el.add(titulo);

        id.setBounds(20, 50, 60, 100);
        el.add(id);

        ingID.setBounds(140, 90, 200, 20);
        el.add(ingID);
        
        can.setBounds(30, 430, 130, 40);
        can.setBackground(new Color(167, 195, 201));
        el.add(can);

        acep.setBounds(220, 430, 130, 40);
        acep.setBackground(new Color(167, 195, 201));
        el.add(acep);

        can.addActionListener((ActionEvent e) -> {
            el.dispose();
        });

        acep.addActionListener((ActionEvent e) -> {
            int ID = (Integer) ingID.getValue();

            if (!inv.compExistencia(ID)) {
                JOptionPane.showMessageDialog(null, "No existe la ID ingresada");
            } else if (inv.compExistencia(ID)) {
                boolean consulta = inv.eliminarSQL(ID);
                if (consulta) {
                    dispose();
                    el.dispose();
                    JOptionPane.showMessageDialog(null, "Producto eliminado exitosamente");
                    new SistemaInv();
                }
            }
        });

        el.setVisible(true);
        el.setSize(400, 600);
        el.setResizable(false);
        el.setLocationRelativeTo(null);

    }

    private void Modificar() {
        SpinnerModel value1 = new SpinnerNumberModel(1, 1, 999999999, 1);
        JFrame mod = new JFrame();
        JLabel titulo = new JLabel("Modificar");
        JLabel id = new JLabel("ID a modificar");
        JSpinner ingID = new JSpinner(value1);
        JButton can = new JButton("Cancelar");
        JButton acep = new JButton("Aceptar");

        mod.setLayout(null);

        titulo.setFont(new Font(fuente, Font.PLAIN, 20));
        id.setFont(new Font(fuente, Font.PLAIN, 20));
        ingID.setFont(new Font(fuente, Font.PLAIN, 15));
        can.setFont(new Font(fuente, Font.PLAIN, 20));
        acep.setFont(new Font(fuente, Font.PLAIN, 20));

        titulo.setBounds(150, 0, 250, 100);
        mod.add(titulo);

        id.setBounds(20, 50, 120, 100);
        mod.add(id);

        ingID.setBounds(160, 90, 200, 20);
        mod.add(ingID);
        
        can.setBounds(30, 430, 130, 40);
        can.setBackground(new Color(167, 195, 201));
        mod.add(can);

        acep.setBounds(220, 430, 130, 40);
        acep.setBackground(new Color(167, 195, 201));
        mod.add(acep);

        can.addActionListener((ActionEvent e) -> {
            mod.dispose();
        });

        acep.addActionListener((ActionEvent e) -> {
            int ID = (Integer) ingID.getValue();

            if (!inv.compExistencia(ID)) {
                JOptionPane.showMessageDialog(null, "No existe la ID ingresada");
            } else if (inv.compExistencia(ID)) {
                mod.dispose();
                modificacion(ID);
            }
        });

        mod.setVisible(true);
        mod.setSize(400, 600);
        mod.setResizable(false);
        mod.setLocationRelativeTo(null);

    }

    private void modificacion(int IDanterior) {
        String inventario[];
        inventario = inv.enviarDatosSQL(IDanterior);

        int auxID = Integer.parseInt(inventario[1]);
        int auxPrec = Integer.parseInt(inventario[2]);

        SpinnerModel value1 = new SpinnerNumberModel(auxID, 1, 999999999, 1);
        SpinnerModel value2 = new SpinnerNumberModel(auxPrec, 1, 999999999, 1);
        JFrame moding = new JFrame();
        JLabel titulo = new JLabel("Nuevos valores");
        JLabel id = new JLabel("ID");
        JLabel prop = new JLabel("Producto");
        JLabel prec = new JLabel("Precio");
        JSpinner ingID = new JSpinner(value1);
        JTextField ingProp = new JTextField(5);
        JSpinner ingPrec = new JSpinner(value2);
        JButton can = new JButton("Cancelar");
        JButton acep = new JButton("Aceptar");

        moding.setLayout(null);

        titulo.setFont(new Font(fuente, Font.PLAIN, 20));
        id.setFont(new Font(fuente, Font.PLAIN, 20));
        prop.setFont(new Font(fuente, Font.PLAIN, 20));
        prec.setFont(new Font(fuente, Font.PLAIN, 20));
        ingID.setFont(new Font(fuente, Font.PLAIN, 15));
        ingProp.setFont(new Font(fuente, Font.PLAIN, 20));
        ingPrec.setFont(new Font(fuente, Font.PLAIN, 15));
        can.setFont(new Font(fuente, Font.PLAIN, 20));
        acep.setFont(new Font(fuente, Font.PLAIN, 20));

        ingProp.setText(inventario[0]);

        titulo.setBounds(150, 0, 250, 100);
        moding.add(titulo);

        id.setBounds(20, 50, 60, 100);
        moding.add(id);

        ingID.setBounds(140, 90, 200, 20);
        moding.add(ingID);

        prop.setBounds(20, 100, 100, 100);
        moding.add(prop);

        ingProp.setBounds(140, 140, 200, 20);
        moding.add(ingProp);

        prec.setBounds(20, 190, 100, 20);
        moding.add(prec);

        ingPrec.setBounds(140, 190, 200, 20);
        moding.add(ingPrec);

        can.setBounds(30, 430, 130, 40);
        can.setBackground(new Color(167, 195, 201));
        moding.add(can);

        acep.setBounds(220, 430, 130, 40);
        acep.setBackground(new Color(167, 195, 201));
        moding.add(acep);

        can.addActionListener((ActionEvent e) -> {
            moding.dispose();
        });

        acep.addActionListener((ActionEvent e) -> {
            int IDnueva = (Integer) ingID.getValue();
            String produc = ingProp.getText();
            int precc = (Integer) ingPrec.getValue();

            if ("".equals(ingProp.getText())) {
                JOptionPane.showMessageDialog(null, "Ingrese un nombre para el producto");
            } else if (inv.compExistencia(IDnueva)) {
                if (IDnueva == IDanterior) {
                    boolean consulta = inv.modificar(IDanterior, IDnueva, produc, precc);
                    if (consulta) {
                        dispose();
                        moding.dispose();
                        JOptionPane.showMessageDialog(null, "Producto modificado exitosamente");
                        new SistemaInv();
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Ya existe un producto con esa ID");
                }
            } else if (!inv.compExistencia(IDnueva)) {
                boolean consulta1 = inv.modificar(IDanterior, IDnueva, produc, precc);
                if (consulta1) {
                    dispose();
                    moding.dispose();
                    JOptionPane.showMessageDialog(null, "Producto modificado exitosamente");
                    new SistemaInv();
                }
            }
        }
        );

        moding.setVisible(true);
        moding.setSize(400, 600);
        moding.setResizable(false);
        moding.setLocationRelativeTo(null);

    }

}
