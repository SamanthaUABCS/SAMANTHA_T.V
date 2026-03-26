package Main;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class EXAMEN extends JFrame {

    private JTextField Documento, Nombre, Direccion, Telefono;
    private JTextField Producto, Cantidad, Valor;
    private JLabel Subtotal, Descuento, IVA, Total;

    private DefaultTableModel modelo;
    private JTable tabla;

    public EXAMEN() {
        setTitle("Factura");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // PANEL SUPERIOR (DATOS)
        JPanel panelDatos = new JPanel(new GridLayout(2, 1));

        JPanel cliente = new JPanel(new GridLayout(2, 4));
        cliente.setBorder(BorderFactory.createTitledBorder("Datos del cliente"));

        Documento = new JTextField();
        Nombre = new JTextField();
        Direccion = new JTextField();
        Telefono = new JTextField();

        cliente.add(new JLabel("Documento:"));
        cliente.add(Documento);
        cliente.add(new JLabel("Nombre:"));
        cliente.add(Nombre);

        cliente.add(new JLabel("Dirección:"));
        cliente.add(Direccion);
        cliente.add(new JLabel("Teléfono:"));
        cliente.add(Telefono);

        panelDatos.add(cliente);

        add(panelDatos, BorderLayout.NORTH);

        // TABLA
        modelo = new DefaultTableModel(new Object[]{"Producto", "Cantidad", "Valor", "Subtotal"}, 0);
        tabla = new JTable(modelo);

        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // PANEL INFERIOR
        JPanel panelInferior = new JPanel(new BorderLayout());

        JPanel entrada = new JPanel();
        Producto = new JTextField(8);
        Cantidad = new JTextField(5);
        Valor = new JTextField(5);

        JButton btnAgregar = new JButton("Añadir");
        JButton btnEliminar = new JButton("Eliminar");

        entrada.add(new JLabel("Producto:"));
        entrada.add(Producto);
        entrada.add(new JLabel("Cantidad:"));
        entrada.add(Cantidad);
        entrada.add(new JLabel("Valor:"));
        entrada.add(Valor);
        entrada.add(btnAgregar);
        entrada.add(btnEliminar);

        panelInferior.add(entrada, BorderLayout.NORTH);

        // TOTALES
        JPanel totales = new JPanel(new GridLayout(4, 2));

        Subtotal = new JLabel("0");
        Descuento = new JLabel("0");
        IVA = new JLabel("0");
        Total = new JLabel("0");

        totales.add(new JLabel("Subtotal:"));
        totales.add(Subtotal);
        totales.add(new JLabel("Descuento (5%):"));
        totales.add(Descuento);
        totales.add(new JLabel("IVA (19%):"));
        totales.add(IVA);
        totales.add(new JLabel("Total:"));
        totales.add(Total);

        panelInferior.add(totales, BorderLayout.CENTER);

        add(panelInferior, BorderLayout.SOUTH);

        // EVENTOS

        btnAgregar.addActionListener(e -> agregarProducto());
        btnEliminar.addActionListener(e -> eliminarProducto());
    }

    private void agregarProducto() {
        try {
            String producto = Producto.getText();
            int cantidad = Integer.parseInt(Cantidad.getText());
            double valor = Double.parseDouble(Valor.getText());

            double subtotal = cantidad * valor;

            modelo.addRow(new Object[]{producto, cantidad, valor, subtotal});

            calcularTotales();

            Producto.setText("");
            Cantidad.setText("");
            Valor.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Datos inválidos");
        }
    }

    private void eliminarProducto() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            modelo.removeRow(fila);
            calcularTotales();
        }
    }

    private void calcularTotales() {
        double subtotal = 0;

        for (int i = 0; i < modelo.getRowCount(); i++) {
            subtotal += (double) modelo.getValueAt(i, 3);
        }

        double descuento = subtotal * 0.05;
        double iva = (subtotal - descuento) * 0.19;
        double total = subtotal - descuento + iva;

        Subtotal.setText(String.valueOf(subtotal));
        Descuento.setText(String.valueOf(descuento));
        IVA.setText(String.valueOf(iva));
        Total.setText(String.valueOf(total));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EXAMEN().setVisible(true));
    }
}