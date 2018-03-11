package Cliente;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Cliente
{

	private static String ip = "";
	private static int puerto = 1337;

	private static int bufferSize;
	private static int tamanoFragmento;

	private boolean estadoConexion;
	private String estadoDescarga;
	public static final String[] ESTADOS_DESCARGA = { "Sin iniciar", "Descargando", "Completado", "Detenido", "Cancelado" };
	private ArrayList<String> archivosServidor, archivosCliente;
	private int archivoATransferir;
	private PrintWriter escritor;
	private Socket sc;
	private String fromUser;
	private BufferedReader stdIn;
	private static boolean transferenciaActiva;
	private Transferencia transferencia;

	public Cliente()
	{
		InetAddress IP = null;
		try
		{
			IP = InetAddress.getLocalHost();
		}
		catch (UnknownHostException e)
		{
			e.printStackTrace();
		}

		ip = IP.getHostAddress();

		archivosServidor = new ArrayList<>();
		archivosCliente = new ArrayList<>();
		estadoConexion = false;
		archivoATransferir = -1;
		transferenciaActiva = false;
		estadoDescarga = ESTADOS_DESCARGA[0];
	}

	public void cambiarEstadoConexion() throws Exception
	{
		if (!estadoConexion)
		{
			sc = null;

			escritor = null;
			BufferedReader lector = null;

			try
			{
				sc = new Socket(ip, puerto);

				escritor = new PrintWriter(sc.getOutputStream(), true);
				lector = new BufferedReader(new InputStreamReader(sc.getInputStream()));
				System.out.println("CLIENTE - Enviando peticiones al puerto " + puerto);

			}
			catch (Exception e)
			{
				System.err.println("Exception: " + e.getMessage());
				System.exit(1);
			}

			stdIn = new BufferedReader(new InputStreamReader(System.in));
			String fromServer;

			System.out.println("El Servidor debe informar que está conectado para proseguir.");
			System.out.println("Si no se logra conectar es porque se excedieron el numero de solicitudes posibles.");
			// Recibir CONECTADO AL SERVIDOR

			if ((fromServer = lector.readLine()) != null)
			{
				System.out.println(fromServer);
			}

			// Enviar HOLA.

//			fromUser = stdIn.readLine();
			fromUser = "HOLA";
			escritor.println(fromUser);

			// Recibir LISTA.
			boolean inicio = true;
			while ((fromServer = lector.readLine()) != null && !fromServer.equalsIgnoreCase("ya"))
			{
				System.out.println(fromServer);
				if (!inicio)
				{
					System.out.println(fromServer);
					archivosServidor.add(fromServer.split("%%%")[1] + " | " + fromServer.split("%%%")[2]);
				}
				inicio = false;

			}

			// Recibir BUFFERSIZE
			fromServer = lector.readLine();
			bufferSize = Integer.parseInt(fromServer);
			sc.setReceiveBufferSize(bufferSize);

			// Recibir TAMANOFRAGMENTO
			fromServer = lector.readLine();
			tamanoFragmento = Integer.parseInt(fromServer);

			estadoConexion = true;

		}
		else if (estadoConexion)
		{
			estadoConexion = false;
			bufferSize = 0;
			tamanoFragmento = 0;
			archivoATransferir = -1;
			archivosServidor = new ArrayList<>();
		}

	}


	public void prepararDecarga(int i)
	{
		archivoATransferir = i;
	}

	public String darArchivoADescargar()
	{
		return archivoATransferir >= 0 ? archivosServidor.get(archivoATransferir - 1) : "Ning�n archivo seleccionado";
	}

	public void iniciarTransferencia() throws Exception
	{
		transferencia = new Transferencia(escritor, tamanoFragmento, sc, stdIn, archivoATransferir - 1, darArchivoADescargar().split(" | ")[0]);
		transferencia.start();

	}

	public static void main(String[] args) throws IOException
	{
		Socket sc = null;

		PrintWriter escritor = null;
		BufferedReader lector = null;

		try
		{
			sc = new Socket(ip, puerto);

			escritor = new PrintWriter(sc.getOutputStream(), true);
			lector = new BufferedReader(new InputStreamReader(sc.getInputStream()));
			System.out.println("CLIENTE - Enviando peticiones al puerto " + puerto);

		}
		catch (Exception e)
		{
			System.err.println("Exception: " + e.getMessage());
			System.exit(1);

		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromServer;
		String fromUser;

		int estado = 0;

		while (estado < 3)
		{
			switch (estado)
			{
			case 0:
				System.out.println("El Servidor debe informar que está conectado para proseguir.");
				System.out.println("Si no se logra conectar es porque se excedieron el numero de solicitudes posibles.");
				// Recibir CONECTADO AL SERVIDOR

				if ((fromServer = lector.readLine()) != null)
				{
					System.out.println(fromServer);
				}

				// Enviar HOLA.

				fromUser = stdIn.readLine();
				escritor.println(fromUser);

				// Recibir LISTA.
				while ((fromServer = lector.readLine()) != null && !fromServer.equalsIgnoreCase("ya"))
				{
					System.out.println(fromServer);
				}

				// Recibir BUFFERSIZE
				fromServer = lector.readLine();
				bufferSize = Integer.parseInt(fromServer);
				sc.setReceiveBufferSize(bufferSize);

				// Recibir TAMANOFRAGMENTO
				fromServer = lector.readLine();
				tamanoFragmento = Integer.parseInt(fromServer);

				estado++;

				break;

			case 1:
				// Enviar Numero. Recibir Archivo.

				fromUser = stdIn.readLine();

				escritor.println(fromUser);

				String ruta = "./data/Archivo" + fromUser + ".pdf";

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

				estado = 7;

				break;

			default:
				estado = 0;
				break;

			}

		}

		// cierre PrintWriter y BufferedReader
		escritor.close();
		lector.close();

		// cierre el socket y la entrada est�ndar
		sc.close();
		stdIn.close();
	}

	public boolean darEstadoConexion()
	{
		return estadoConexion;
	}

	public ArrayList<String> darArchivosServidor()
	{
		for (String s : archivosServidor)
		{
			System.out.println(s);
		}

		return archivosServidor;
	}

	public ArrayList<String> darArchivosCliente()
	{

		File data = new File("./data");
		archivosCliente.clear();
		int i = 1;
		for (File archivo : data.listFiles())
		{
			if (!archivo.isDirectory())
			{
				String cadena = archivo.getName() + " | " + ((((double) archivo.length()) / 1048576L) + "     ").substring(0, 5) + "MB\n";
				i++;
				archivosCliente.add(cadena);
//				System.out.println(cadena);
			}
		}

		return archivosCliente;
	}

	public void cambiarEstadoDescarga(String pEstado)
	{
		estadoDescarga = pEstado;
	}

	public String darEstadoDescarga()
	{

		return transferencia != null ? ESTADOS_DESCARGA[transferencia.darEstado() + 1] : ESTADOS_DESCARGA[0];
	}

	public Transferencia darTransferencia()
	{
		return transferencia;
	}
}
