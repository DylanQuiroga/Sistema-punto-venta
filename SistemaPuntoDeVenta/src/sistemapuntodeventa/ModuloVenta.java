package sistemapuntodeventa;

//Librerias para Swing y guardar un archivo
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ModuloVenta extends JFrame implements ActionListener {
    
    //componentes de Swing y de archivos
    Inventario inv = new Inventario();
    DefaultTableModel tablaDF = new DefaultTableModel();
    JComboBox productos;
    JLabel labelProductos, labelCantidad, labelPrecio, labelTotal, labelBuscar;
    JButton agregar, eliminar, generar;
    JTextField buscar, precio, total;
    JSpinner cantidad;
    JTable tabla;
    JMenuItem salir;
    JMenuBar menu;
    private int totalReal = 0;
    private final String fuente = "Times New Roman";
    JFileChooser selec;
    FileOutputStream salida = null;
    static File archivo;

    public ModuloVenta() {
        //caracteristicas del modulo de venta
        setTitle("MODULO DE VENTA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setMinimumSize(new Dimension(800, 900));
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        selec = new JFileChooser();

        //Salir
        salir = new JMenuItem("Cerrar sesión");
        menu = new JMenuBar();
        menu.add(salir);
        setJMenuBar(menu);

        //Etiquetas
        labelProductos = new JLabel("Producto");
        labelProductos.setFont(new Font(fuente, Font.PLAIN, 20));

        labelCantidad = new JLabel("Cantidad");
        labelCantidad.setFont(new Font(fuente, Font.PLAIN, 20));

        labelPrecio = new JLabel("Precio: $");
        labelPrecio.setFont(new Font(fuente, Font.PLAIN, 20));

        labelTotal = new JLabel("Total: $");
        labelTotal.setFont(new Font(fuente, Font.PLAIN, 20));

        labelBuscar = new JLabel("Buscar");
        labelBuscar.setFont(new Font(fuente, Font.PLAIN, 20));

        //Spinner 
        SpinnerModel value = new SpinnerNumberModel(1, 1, 999, 1);
        cantidad = new JSpinner(value);

        //Tabla
        tablaDF.addColumn("ID");
        tablaDF.addColumn("Producto");
        tablaDF.addColumn("Cantidad");
        tablaDF.addColumn("Precio unitario");
        tablaDF.addColumn("Precio Final");

        tabla = new JTable();
        tabla.setModel(tablaDF);
        tabla.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setReorderingAllowed(false);
        tabla.setFillsViewportHeight(true);
        tabla.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        tabla.setDefaultEditor(Object.class, null);

        // JcomboBox (posible cambio)
        /*
        Inventario inventario; // <- clase a implementar?
        productos = new JComboBox(inventario.obtenerProductos());
         */
        productos = new JComboBox();
        productos.addItem("Seleccione producto");
        for (int i = 0; i < inv.totalArticulos; i++) {
            productos.addItem(inv.producto[i]);
        }

        //TextField
        buscar = new JTextField(5);
        buscar.setFont(new Font("Comic Sans", Font.PLAIN, 20));

        precio = new JTextField(5);
        precio.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        precio.setOpaque(false);
        precio.setEditable(false);

        total = new JTextField(5);
        total.setFont(new Font("Comic Sans", Font.PLAIN, 20));
        total.setOpaque(false);
        total.setEditable(false);

        //Botones
        agregar = new JButton("Agregar al carrito");
        agregar.setBackground(new Color(167, 195, 201));
        agregar.setForeground(Color.white);
        agregar.setFont(new Font(fuente, Font.PLAIN, 20));
        agregar.setEnabled(false);

        eliminar = new JButton("Eliminar producto marcado");
        eliminar.setBackground(Color.lightGray);
        eliminar.setFont(new Font(fuente, Font.PLAIN, 20));

        generar = new JButton("Generar boleta");
        generar.setBackground(new Color(190, 235, 123));
        generar.setFont(new Font(fuente, Font.PLAIN, 20));

        //Restriccciones LABEL "Buscar"
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 10, 0, 0); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(labelBuscar, gbc);

        //Restricciones TextField "buscar"
        gbc.insets = new Insets(0, 90, 0, 100); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(buscar, gbc);

        //Restriccciones LABEL "Productos"
        gbc.insets = new Insets(90, 10, 0, 0); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(labelProductos, gbc);

        //Restriccciones ComboBox
        gbc.insets = new Insets(90, 90, 0, 100);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(productos, gbc);

        //Restriccciones LABEL "Cantidad"
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 10, 0, 0); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(labelCantidad, gbc);

        //Restricciones Jspinner
        gbc.insets = new Insets(0, 100, 0, 50); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.HORIZONTAL;

        add(cantidad, gbc);

        //Restriccciones BOTÓN "agregar"
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.insets = new Insets(70, 10, 70, 10); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(agregar, gbc);

        //Restriccciones LABEL "Precio"
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(labelPrecio, gbc);

        //Restricciones TextField "precio"
        gbc.insets = new Insets(0, 75, 0, 50); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(precio, gbc);

        //Restriccciones tabla
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 0.5;
        gbc.weighty = 2.0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = GridBagConstraints.BOTH;
        add(tabla, gbc);
        add(new JScrollPane(tabla), gbc);

        //Restriccciones LABEL "total"
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.fill = GridBagConstraints.BOTH;
        add(labelTotal, gbc);

        //Restricciones TextField "total"
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(0, 75, 0, 50); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(total, gbc);

        //Restriccciones BOTÓN "eliminar"
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(70, 10, 70, 10); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(eliminar, gbc);

        //Restriccciones BOTÓN "generar"
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.insets = new Insets(70, 10, 70, 10); // Insets(int top, int left, int bottom, int right)
        gbc.fill = GridBagConstraints.BOTH;
        add(generar, gbc);
        
        //Escuchadores para cada boton
        buscar.addActionListener(this);
        productos.addActionListener(this);
        agregar.addActionListener(this);
        eliminar.addActionListener(this);
        generar.addActionListener(this);
        salir.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        //si se le da a 'Enter' a la barra de buscar, este hace una consulta a todos los productos que tengan relacion con el producto ingresado
        if (e.getSource() == buscar) {
            String productoEscrito = buscar.getText();
            String totalBuscado[];
            totalBuscado = inv.buscarProducto(productoEscrito);

            productos.removeAllItems();
            productos.addItem("Seleccione producto");

            try {
                for (int i = 0; i < totalBuscado.length; i++) {
                    productos.addItem(totalBuscado[i]);
                }
            } catch (NullPointerException npe) {
            }

            agregar.setEnabled(false);
            
            //
        } else if (e.getSource() == productos) {
            if (productos.getSelectedIndex() != 0) {
                String producto = (String) productos.getSelectedItem();
                int precioAux = inv.consultarPrecio(producto);
                precio.setText(String.valueOf(precioAux));
                agregar.setEnabled(true);
            } else {
                precio.setText("");
                agregar.setEnabled(false);
            }
        } else if (e.getSource() == agregar) {
            Object[] fila = new Object[5];
            int Nproductos = (Integer) cantidad.getValue();
            String ProductoObt = (String) productos.getSelectedItem();
            int precioObt = inv.consultarPrecio(ProductoObt);
            int IDObt = inv.consultarID(ProductoObt);

            fila[0] = IDObt;
            fila[1] = ProductoObt;
            fila[2] = Nproductos;
            fila[3] = precioObt;
            fila[4] = precioObt * Nproductos;

            totalReal = totalReal + precioObt * Nproductos;
            
            agregar.setEnabled(false);
            productos.setSelectedIndex(0);

            tablaDF.addRow(fila);
            total.setText("" + totalReal);
            cantidad.setValue(1);

        } else if (e.getSource() == eliminar) {
            if (tabla.getSelectedRow() != -1) {
                int indice = tabla.getSelectedRow();
                int resta = (Integer) tablaDF.getValueAt(indice, 4);
                totalReal = totalReal - resta;
                tablaDF.removeRow(tabla.getSelectedRow());
                total.setText("" + totalReal);
            }

        } else if (e.getSource() == salir) {
            //agregar.removeActionListener(this);
            dispose();
            new LoginJFrame();

        } else if (e.getSource() == generar) {
            JFrame salir1 = new JFrame("Boleta");
            salir1.setSize(500, 800);
            salir1.setLayout(new BorderLayout());

            JLabel titulo = new JLabel("Cuenta");
            titulo.setFont(new Font(fuente, Font.PLAIN, 20));

            JTextArea area = new JTextArea();

            JButton guardar = new JButton("Guardar como .txt");
            guardar.setBackground(new Color(167, 195, 201));

            salir1.add(titulo, (BorderLayout.NORTH));
            titulo.setHorizontalAlignment(JLabel.CENTER);
            salir1.add(guardar, (BorderLayout.SOUTH));

            salir1.add(area, (BorderLayout.CENTER));
            salir1.add(new JScrollPane(area),(BorderLayout.CENTER));
            area.setText("..........................................PRODUCTOS..........................................\n");
            for (int i = 0; i < tablaDF.getRowCount(); i++) {
                area.append("ID: " + String.valueOf(tablaDF.getValueAt(i, 0)) + "\nProducto: " + String.valueOf(tablaDF.getValueAt(i, 1)) + "\nCantidad: " + String.valueOf(tablaDF.getValueAt(i, 2)) + "\nPrecio unitario: " + String.valueOf(tablaDF.getValueAt(i, 3)) + "\nPrecio final: " + String.valueOf(tablaDF.getValueAt(i, 4)) + "\n..........................................................................................................\n");
            }
            int totalSinIva = (int) (totalReal / 1.19);
            int iva = (int) (totalReal - totalSinIva);
            area.append("TOTAL: $" + String.valueOf(totalReal) + "\nIVA: $" + String.valueOf(iva) + " (Ya incluido)");
            area.setEditable(false);

            guardar.addActionListener((ActionEvent a) -> {
                if(selec.showDialog(null, "Guardar") == JFileChooser.APPROVE_OPTION){   //si se selecciona un archivo
                archivo = selec.getSelectedFile();
                if(archivo.getName().endsWith("txt")){      //si el archivo termina en txt
                    String doc = area.getText();
                    String mensaje = GuardarArchivo(archivo,doc,salida);
                    if(mensaje !=null){
                        JOptionPane.showMessageDialog(null, mensaje);
                    }else{
                        JOptionPane.showMessageDialog(null, "Archivo no compatible");
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Documento guardado");
                }
            }
            });

            salir1.setResizable(false);
            //salir1.setDefaultCloseOperation(HIDE_ON_CLOSE);
            salir1.setVisible(true);
            salir1.setLocationRelativeTo(null);
        }

    }
    
    private static String GuardarArchivo(File archivo, String documento, FileOutputStream salida){                                       //funcion para guardar un archivo
        String mensaje = null;
        try {
            salida = new FileOutputStream(archivo);
            byte[] bytxt = documento.getBytes();
            salida.write(bytxt);
            mensaje = "Archivo guardado";
        } catch (IOException e){
            
     }
    return mensaje;
   }
    
}
