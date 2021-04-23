package org.ciberfarma.app;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.TextArea;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.ciberfarma.modelo.Usuario;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class FrmUsuario extends JFrame {

	private JPanel contentPane;
	private JTextField txtNombre;
	private JTextField textApellido;
	private JTextField txtCodigo;

	EntityManagerFactory fabrica = Persistence.createEntityManagerFactory("jpa_sesion01");
	EntityManager em = fabrica.createEntityManager();
	private JTextArea textLista;
	private JScrollPane txtS;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmUsuario frame = new FrmUsuario();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 597, 494);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JButton btnRegistrar = new JButton("REGISTRAR");
		btnRegistrar.setBounds(372, 42, 102, 23);
		contentPane.add(btnRegistrar);

		JButton btnBuscar = new JButton("BUSCAR");
		btnBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		btnBuscar.setBounds(372, 76, 102, 23);
		contentPane.add(btnBuscar);

		JButton btnListar = new JButton("LISTADO");
		btnListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				listado();
			}
		});
		btnListar.setBounds(372, 110, 102, 23);
		contentPane.add(btnListar);

		txtNombre = new JTextField();
		txtNombre.setBounds(124, 73, 86, 20);
		contentPane.add(txtNombre);
		txtNombre.setColumns(10);

		textApellido = new JTextField();
		textApellido.setBounds(124, 115, 102, 20);
		contentPane.add(textApellido);
		textApellido.setColumns(10);

		JLabel lblNewLabel = new JLabel("Nombre");
		lblNewLabel.setBounds(10, 76, 60, 14);
		contentPane.add(lblNewLabel);

		JLabel lblApellido = new JLabel("Apellido");
		lblApellido.setBounds(10, 119, 60, 14);
		contentPane.add(lblApellido);

		JLabel lblCodigo = new JLabel("Codigo");
		lblCodigo.setBounds(10, 42, 60, 14);
		contentPane.add(lblCodigo);

		txtCodigo = new JTextField();
		txtCodigo.setColumns(10);
		txtCodigo.setBounds(124, 39, 86, 20);
		contentPane.add(txtCodigo);

		txtS = new JScrollPane();
		txtS.setBounds(10, 187, 561, 257);
		contentPane.add(txtS);

		textLista = new JTextArea();
		txtS.setViewportView(textLista);
	}

	protected void listado() {
		// TODO Auto-generated method stub
		//TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAll", Usuario.class);
		//List<Usuario> lstUsuarios = consulta.getResultList();

		TypedQuery<Usuario> consulta = em.createNamedQuery("Usuario.findAllWithType", Usuario.class);
		consulta.setParameter("xtipo", 1);
		List<Usuario> lstUsuarios = consulta.getResultList();
		em.close();

		// pasar el listado a txt,..

		for (Usuario u : lstUsuarios) {

			textLista.append(u.getCodigo() + "\t" + u.getNombre() +

					"\t" + u.getApellido() + "\n");

		}
	}

	private void buscar() {
		// TODO Auto-generated method stub
		// leer codigo
		int codigo = leerCodigo();
		;
		// buscar en la tabla para obtener el usuario

		Usuario u = em.find(Usuario.class, codigo);
		em.close();
		if (u == null) {
			aviso("Usuario " + codigo + " no existe");
		} else {
			txtNombre.setText(u.getNombre());
			textApellido.setText(u.getApellido());
		}

	}

	private void aviso(String mensaje) {
		// TODO Auto-generated method stub

		JOptionPane.showMessageDialog(this, mensaje, "Aviso del sistema", JOptionPane.WARNING_MESSAGE);
		// System.out.println(mensaje);

	}

	private int leerCodigo() {
		// TODO Auto-generated method stub
		return Integer.parseInt(txtCodigo.getText());
	}
}
