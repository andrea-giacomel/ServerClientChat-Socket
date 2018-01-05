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
		
		BufferedReader in= null;		//buffer ingresso server
		BufferedReader stdIn = null;	//buffer tastiera
		PrintWriter out = null;			//buffer output client
		
		try {
			System.out.println("Server: Sono in attesa di un client");
			
			//attendo che un client richieda una connessione e mi collego
			clientSocket = serverSocket.accept();
			
			System.out.println("Connessione accettata a: "+ clientSocket);
			
			// creazione stream (canale) di input dal clientSocket
			InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
			in = new BufferedReader(isr);
			String clientInput = "";
			
			// creazione stream (canale) di output al clientSocket
			OutputStreamWriter osw = new OutputStreamWriter(clientSocket.getOutputStream());
			BufferedWriter bw = new BufferedWriter(osw);
			out = new PrintWriter(bw, true);
			
			// creazione stream di input da tastiera
			stdIn = new BufferedReader(new InputStreamReader(System.in));
			String userInput = "";

			System.out.println("Canale di I/O creato");
			System.out.println("E' ora possibile comunicare con il client\n");
			
			//ciclo di ricezione/trasmissione con il client
			while (true) {
				//se c'e' una stringa pronta da leggere nel buffer del client
				if( in.ready() ) {
					//leggo i messaggi inviati dal client
					clientInput = in.readLine();
					//stampo a schermo il messaggio ricevuto dal client
					System.out.println("Client: " + clientInput);
				}
				
				//controllo la richiesta d'arresto
				if(clientInput.equals("ESCI"))	//se la stringa del client è "ESCI"
					break;						//interrompo il ciclo di comunicazione
				
				//se c'e' una stringa nel buffer della tastiera
				if( stdIn.ready() ) {
					//leggo una stringa da tastiera
					userInput = stdIn.readLine();
					//inoltro la stringa al client
					out.println(userInput);
				}
				
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
		stdIn.close();
		clientSocket.close();
		serverSocket.close();
		System.out.println("Server: Arresto completato");
	}
} 

// Server