// Client.java

import java.net.*;
import java.io.*;

public class Client {
	
	public static void main(String[] args) throws IOException {
	
		//indirizzo del server
		InetAddress addr = InetAddress.getByName(null);	//server sullo stesso pc
		
		Socket socket=null;				//socket
		
		BufferedReader in= null;		//buffer ingresso server
		BufferedReader stdIn = null;	//buffer tastiera
		PrintWriter out = null;			//buffer output client
		
		try {
			// creazione socket
			socket = new Socket(addr, Server.PORT);
			System.out.println("Client: Avviato. Socket info: "+ socket);
			
			// creazione stream di input da socket
			InputStreamReader isr = new InputStreamReader( socket.getInputStream());
			in = new BufferedReader(isr);
			String serverInput = null;
			
			// creazione stream di output su socket
			OutputStreamWriter osw = new OutputStreamWriter( socket.getOutputStream());
			BufferedWriter bw = new BufferedWriter(osw);
			out = new PrintWriter(bw, true);
			
			// creazione stream di input da tastiera
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput = null;
			
			System.out.println("Canale di I/O creato");
			System.out.println("E' ora comunicare con il server.\n");
			
			//ciclo di ricezione/trasmissione con il server
			while (true){
				//leggo una stringa da tastiera
				userInput = stdIn.readLine();
				//inoltro la stringa al server
				out.println(userInput);
				
				//controllo la richiesta d'arresto
				if (userInput.equals("ESCI"))	//se la stringa da tastiera è "ESCI"
					break;						//interrompo il ciclo di comunicazione
				
				//leggo i messaggi inviati dal server
				serverInput = in.readLine();
				//stampo a schermo il messaggio ricevuto dal server
				System.out.println("Server: " + serverInput);
			}
		}
		catch (UnknownHostException e) {
			System.err.println("Impossibile connettersi al server indicato: "+ addr);
			System.exit(1);
		} 
		catch (IOException e) {
			System.err.println("Impossibile ottenere I/O dal server: " + addr);
			System.exit(1);
		}
		
		// se il ciclo di comunicazione è stato interrotto,
		// eseguo la chiusura di stream e socket
		System.out.println("Client: Arresto in corso...");
		out.close();
		in.close();
		stdIn.close();
		socket.close();
		System.out.println("Client: Arresto completato");
	}
}

//Client