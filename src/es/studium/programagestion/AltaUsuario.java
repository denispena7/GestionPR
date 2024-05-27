//ALta Usuario
package es.studium.programagestion;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class AltaUsuario implements WindowListener, ActionListener{
	Frame ventanaCompra= new Frame("Usuario");
	Label lblnomusu = new Label("Nombre Usuario");
	TextField txtnomusu = new TextField(15);
	Label lbltipousu= new Label("Tipo Usuario");
	TextField txttiposu = new TextField(15);
	Label lblpassword= new Label("Password");
	TextField txtpassword = new TextField(15);
	Button btnaceptar = new Button("Aceptar");
	Button btncancelar = new Button("Cancelar");
	
	public AltaUsuario ()
	{	//Añadimos los elementos a la ventana
		ventanaCompra.setLayout(new FlowLayout());
		ventanaCompra.setSize(250,160);
		ventanaCompra.setResizable(false);
		ventanaCompra.setVisible(true);
		ventanaCompra.addWindowListener(this);
		ventanaCompra.setLocationRelativeTo(null);

		//Añadimos los elementos a la ventana
		ventanaCompra.add(lblnomusu);
		ventanaCompra.add(txtnomusu);
		ventanaCompra.add(lbltipousu);
		ventanaCompra.add(txttiposu);
		ventanaCompra.add(lblpassword);
		ventanaCompra.add(txtpassword);
		ventanaCompra.add(btnaceptar);
		ventanaCompra.add(btncancelar);
		//Añadimos los botones
		btnaceptar.addActionListener(this);
		btncancelar.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}

