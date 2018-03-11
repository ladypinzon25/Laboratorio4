package Interfaz;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import Cliente.Cliente;

public class Interfaz extends JFrame 
{
	private PanelTransferencia panelTransferencia;
	
	private PanelArchivos panelArchivos;
	
	private PanelConexion panelConexion;
	
	private Cliente cliente;
	
	public Interfaz()
	{
		setSize(500,700);
		setTitle("Lab 5 Redes");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setVisible(true);
		setLocationRelativeTo(null);

		
		panelConexion = new PanelConexion(this);
		add(panelConexion,BorderLayout.NORTH);
		
		panelArchivos = new PanelArchivos(this);
		add(panelArchivos,BorderLayout.CENTER);
		
		panelTransferencia= new PanelTransferencia(this);
		add(panelTransferencia,BorderLayout.SOUTH);
		
		cliente = new Cliente();
		actualizar();
		validate();
	}
	
	public void cambiarEstadoConexion()
	{
		try
		{
			cliente.cambiarEstadoConexion();
			actualizar();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public void actualizar()
	{
		
		panelArchivos.actualizar(cliente.darArchivosServidor(), cliente.darArchivosCliente());
		panelConexion.actualizar(cliente.darEstadoConexion());
		panelTransferencia.actualizar(cliente.darArchivoADescargar(), cliente.darEstadoDescarga());
		validate();
	}
	public void prepararDescarga(int i)
	{
		cliente.prepararDecarga(i);
		actualizar();
	}
	public void iniciarTransferencia()
	{
		try
		{
			cliente.iniciarTransferencia();
			actualizar();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Interfaz();

	}

}

