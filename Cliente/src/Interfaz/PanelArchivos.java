package Interfaz;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

//import com.sun.org.apache.bcel.internal.generic.LLOAD;

public class PanelArchivos extends JPanel implements ActionListener 
{
	private Interfaz interfaz;
	
	private ArrayList<JPanel> archivosCliente,archivosServidor;
	
	private JButton refrescar1, refrescar2;
	
	private JPanel aux1,aux2;
	
	public PanelArchivos(Interfaz pI)
	{
		interfaz = pI;
		setPreferredSize(new Dimension(500,400));
		setLayout(new BorderLayout());
		archivosCliente = new ArrayList<>();
		archivosServidor = new ArrayList<>();
		
		aux1= new JPanel();
		aux1.setBorder(new TitledBorder("Archivos Cliente"));
		aux1.setPreferredSize(new Dimension(250,400));
		aux1.setLayout(new GridLayout(9,1));
		add(aux1,BorderLayout.CENTER);
		
		aux2= new JPanel();
		aux2.setBorder(new TitledBorder("Archivos Servidor"));
		aux2.setPreferredSize(new Dimension(250,400));
		aux2.setLayout(new GridLayout(9,1));
		add(aux2,BorderLayout.WEST);
		
		JPanel aux3= new JPanel();
		aux3.setLayout(new GridLayout(1,4));
		aux3.add(new JLabel());
		refrescar1= new JButton("Refrescar");
		refrescar1.addActionListener(this);
		refrescar1.setActionCommand("c1");
		aux3.add(refrescar1);
		aux3.add(new JLabel());
		refrescar2= new JButton("Refrescar");
		refrescar2.addActionListener(this);
		refrescar2.setActionCommand("c2");
		aux3.add(refrescar2);
		add(aux3,BorderLayout.SOUTH);
	}
	public void actualizar(ArrayList<String> aServidor, ArrayList<String> aCliente)
	{
		aux1.removeAll();
		aux2.removeAll();
		
		for (int i = 0; i < aCliente.size(); i++)
		{
			JPanel auxArchivo = new JPanel();
			auxArchivo.setLayout(new BorderLayout());
			auxArchivo.setPreferredSize(new Dimension(250,400/9));
			JLabel lblArchivo = new JLabel(aCliente.get(i));
			lblArchivo.setPreferredSize(new Dimension(250,400/9));
			auxArchivo.add(lblArchivo, BorderLayout.CENTER);
//			JButton descargar= new JButton(new ImageIcon("./images/download.png"));
//			descargar.setPreferredSize(new Dimension(50, 0));
//			descargar.setActionCommand("dC:"+i);
//			descargar.addActionListener(this);
//			auxArchivo.add(descargar,BorderLayout.EAST);
			archivosCliente.add(auxArchivo);
			aux1.add(auxArchivo);
		}
		for (int i = 0; i < aServidor.size(); i++)
		{
			System.out.println("Entro al servidor");
			JPanel auxArchivo = new JPanel();
			auxArchivo.setLayout(new BorderLayout());
			auxArchivo.setPreferredSize(new Dimension(250,400/9));
			JLabel lblArchivo = new JLabel(aServidor.get(i));
			lblArchivo.setPreferredSize(new Dimension(200,400/9));
			auxArchivo.add(lblArchivo, BorderLayout.CENTER);
			JButton descargar= new JButton(new ImageIcon("./images/download.png"));
			descargar.setPreferredSize(new Dimension(50, 0));
			descargar.setActionCommand("dS:"+(i+1));
			descargar.addActionListener(this);
			auxArchivo.add(descargar,BorderLayout.EAST);
			archivosServidor.add(auxArchivo);
			aux2.add(auxArchivo);
		}
		
		
	}
	
	public void actionPerformed(ActionEvent event) 
	{
		String comando = event.getActionCommand();
		if(event.getActionCommand().equals(refrescar1.getActionCommand())|| event.getActionCommand().equals(refrescar2.getActionCommand()))
		{
			interfaz.actualizar();
		}
		else
		{
			interfaz.prepararDescarga(Integer.parseInt(comando.split(":")[1]));
		}
		
	}

}

