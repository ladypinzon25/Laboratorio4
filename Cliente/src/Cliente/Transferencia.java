package Cliente;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;


public class Transferencia extends Thread
{

	private PrintWriter escritor;
	private Socket sc;
	private int tamanoFragmento;
	private int archivo;
	private String nombreArchivo;
	private int estado;

	public Transferencia(PrintWriter pEscritor, int t, Socket pSc, BufferedReader pStdIn, int pArchivo, String pNombreArchivo)
	{
		escritor = pEscritor;
		tamanoFragmento = t;
		sc = pSc;
		archivo = pArchivo;
		nombreArchivo = pNombreArchivo;
		estado=0;
	}

	public void run()
	{
		System.out.println("runnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn");
		try
		{
//			String fromUser = stdIn.readLine();

			escritor.println(archivo);

			String ruta = "./data/" + nombreArchivo;
			System.out.println("runnnnnnnnnnn "+nombreArchivo);
			System.out.println("ruta: " + ruta);

			// ////////////////////////////////////////////////////////////////////////
			// RECIBIR ARCHIVO - INICIO
			// ////////////////////////////////////////////////////////////////////////

			// Initialize contents
			byte[] contents = new byte[tamanoFragmento];

			// Initialize the FileOutputStream to the output file's full
			// path.
			FileOutputStream fos = new FileOutputStream(ruta);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			InputStream is = sc.getInputStream();

			// No of bytes read in one read() call
			int bytesRead = 0;
			estado=1;
			while ((bytesRead = is.read(contents)) != -1)
			{

				bos.write(contents, 0, bytesRead);
				bos.flush();

			}
			bos.flush();
			System.out.println("File saved successfully!");
			System.out.println("CLIENTE DESCONECTADO");

			// ////////////////////////////////////////////////////////////////////////
			// RECIBIR ARCHIVO - FIN
			// ////////////////////////////////////////////////////////////////////////
			bos.close();
			fos.close();
			is.close();
			estado=2;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	public int darEstado()
	{
		return estado;
	}
}
