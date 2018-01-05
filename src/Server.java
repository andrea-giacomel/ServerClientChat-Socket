// Server.java
import java.io.*;
import java.net.*;

public class Server {
	
	//indico la porta attraverso cui voglio comunicare
	public static final int PORT = 1050; // porta al di fuori del range 1-1024 !
	
	public static void main(String[] args) throws IOException {
		//creo il socket del server sulla porta impostata
		ServerSocket serverSocket = new ServerSocket(PORT);
		
		System.out.println("Server: Avviato. Socket info: " + serverSocket);
		
		//inizializzo il socket per il cilent
		Socket clientSocket = null;
		
		BufferedReader in = null;
		PrintWriter out = null;
		
		try {
			System.out.println("Server: Sono in attesa di un client");
			
			//attendo che un client richieda una connessione e mi collego
			clientSocket = serverSocket.accept();
			
			System.out.println("Connessione accettata a: "+ clientSocket);
			
			// creazione stream (canale) di input dal clientSocket
			InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
			in = new BufferedReader(isr);
			
			// creazione stream (canale) di output al clientSocket
			OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
			BufferedWriter bw = new BufferedWriter(osw);
			out = new PrintWriter(bw, true);

			System.out.println("Canale di I/O creato");
			System.out.println("E' ora possibile comunicare con il client\n");
			
			//ciclo di ricezione/trasmissione con il client
			while (true) {
				//leggo i messaggi inviati dal client
				String str = in.readLine();
				
				if (str.equals("END"))	//se la stringa del client è "END"
					break;				//interrompo il ciclo di comunicazione
				
				//stampo a schermo il messaggio ricevuto e indico la risposta
				System.out.println("Il client scrive: " + str);
				System.out.println("Server: rispondo con -> " + str);
				//rispondo al client con il suo stesso messaggio
				out.println(str);
			}
		}
		catch (IOException e) {
			System.err.println("Accettattazione fallita");
			System.exit(1);
		}
		
		// se il ciclo di comunicazione è stato interrotto,
		// eseguo la chiusura di stream e socket
		System.out.println("Server: Arresto in corso...");
		out.close();
		in.close();
		clientSocket.close();
		serverSocket.close();
		System.out.println("Server: Arresto completato");
	}
} 

// Server