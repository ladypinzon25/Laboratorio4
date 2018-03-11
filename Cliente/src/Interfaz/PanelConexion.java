package Interfaz;


import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;



public class PanelConexion extends JPanel implements ActionListener 
{

	private JButton establecer;
	private JLabel eEstado,estado;
	private Interfaz interfaz;
	
	public PanelConexion(Interfaz pInterfaz)
	{
		interfaz=pInterfaz;
		
		setPreferredSize(new Dimension(500,50));
		setLayout(new GridLayout(1,2));
		setBorder(new TitledBorder("Conexión"));
		
		JPanel aux1= new JPanel();
		aux1.setPreferredSize(new Dimension(250,50));
		aux1.setLayout(new GridLayout(1,1));
		establecer=new JButton("Establecer Conexión");
		establecer.setActionCommand("c1");
		establecer.addActionListener(this);
		aux1.add(establecer);
		add(aux1);
		
		
		JPanel aux2= new JPanel();
		aux2.setPreferredSize(new Dimension(250,50));
		aux2.setLayout(new GridLayout(1,2));
		eEstado = new JLabel("Estado: ");
		aux2.add(eEstado);
		estado = new JLabel("Desconectado");
		aux2.add(estado);
		add(aux2);
		
	}

	
	public void actionPerformed(ActionEvent event) 
	{
		if(event.getActionCommand().equals(establecer.getActionCommand()))
		{
			interfaz.cambiarEstadoConexion();
		}
		
	}
	public void actualizar(boolean pEstado)
	{
		estado.setText(pEstado?"Conectado":"Desconectado");
		establecer.setText(pEstado?"Desconectar":"Establecer conexión");
	}
}
