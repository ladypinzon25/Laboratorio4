package Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;

public class PanelTransferencia extends JPanel implements ActionListener {

	private JLabel eArchivo, archivo, porcentaje,estadoDescarga;
	private JButton iniciar, pausar, cancelar;
	private Interfaz interfaz; 
	
	public PanelTransferencia(Interfaz pI)
	{
		interfaz=pI;
		setPreferredSize(new Dimension(500, 200));
		setLayout(new BorderLayout());
		setBorder(new TitledBorder("Transferencia"));

		JPanel aux1 = new JPanel();
		aux1.setPreferredSize(new Dimension(500, 50));
		aux1.setLayout(new GridLayout(1, 2));
		eArchivo = new JLabel("Archivo: ");
		aux1.add(eArchivo);
		archivo = new JLabel("Ningún Archivo seleccionado");
		aux1.add(archivo);
		add(aux1, BorderLayout.NORTH);

		JPanel aux2 = new JPanel();
		aux2.setPreferredSize(new Dimension(500, 50));
		aux2.setLayout(new GridLayout(1, 3));
		iniciar = new JButton("Iniciar");
		iniciar.setActionCommand("c1");
		iniciar.addActionListener(this);
		aux2.add(iniciar);
		pausar = new JButton("Pausar");
		pausar.setActionCommand("c2");
		pausar.addActionListener(this);
		aux2.add(pausar);
		cancelar = new JButton("Cancelar");
		cancelar.setActionCommand("c3");
		cancelar.addActionListener(this);
		aux2.add(cancelar);
		add(aux2, BorderLayout.CENTER);
		
		JPanel aux3 = new JPanel();
		aux3.setPreferredSize(new Dimension(500, 100));
		aux3.setLayout(new GridLayout(1, 3));
		aux3.add(new JLabel());
		
		estadoDescarga = new JLabel("Sin iniciar",SwingConstants.CENTER);
		estadoDescarga.setFont(new Font("Serif", Font.PLAIN, 25));
		aux3.add(estadoDescarga);
		aux3.add(new JLabel());
		
		
		add(aux3, BorderLayout.SOUTH);

	}
	public void actualizar(String pArchivo,String pEstadoDescarga)
	{
		archivo.setText(pArchivo);
		estadoDescarga.setText(pEstadoDescarga);
	}

	public void actionPerformed(ActionEvent evento) 
	{
		String comando = evento.getActionCommand();
		
		if(comando.equals(iniciar.getActionCommand()))
		{
			interfaz.iniciarTransferencia();
		}
	}

}

