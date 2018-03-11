package Interfaz;


import Cliente.Cliente;

public class Observador extends Thread
{
	private Cliente cliente;
	private Interfaz interfaz;
	public Observador(Cliente cliente,Interfaz pInterfaz)
	{
		this.cliente=cliente;
		interfaz=pInterfaz;
	}
	
	public void run()
	{
		int estado=0;
		while(estado<2)
		{
			interfaz.actualizar();
			try
			{
				Thread.sleep(200);
			}
			catch (InterruptedException e)
			{
				
				e.printStackTrace();
			}
			estado=cliente.darTransferencia().darEstado();
		}
	}
}
