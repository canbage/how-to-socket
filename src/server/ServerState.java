package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerState {
	
	ServerSocket serverSocket;
	
	ServerClientManager clientManager;
	
	ServerMain serverMain;
	
	int port;
	
	int clientCount = 1;
	
	public ServerState(ServerMain serverMain) {
		
		this.serverMain = serverMain;
	}
	
	public void killServer() {
		if ((serverSocket != null) && (!serverSocket.isClosed())) {
			try {
				serverSocket.close();
				if (clientManager != null) {
					clientManager.closeAllClients();
					clientManager = null;
				}
				clientCount = 1;
				System.out.println("Server on port: " + port + " has been closed");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void startServer(int port) {
		
		this.port = port;
		
		clientManager = new ServerClientManager();
		
		try {
			serverSocket = new ServerSocket(port);
			System.out.println("Server is starting on port: " + port + "...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void findClient() {
		
		Thread acceptThread = new Thread() {
			
			public void run() {
				try {
					System.out.println("Waiting for a client on port: " + port + "...");
					if ((serverSocket != null) && (clientManager != null)) {
						Socket clientSocket = serverSocket.accept();
						
						String clientID = "client " + clientCount;
						clientCount++;
						ServerIO clientIO = new ServerIO(clientID, clientSocket, clientManager);
						clientManager.addClient(clientID, clientIO);
						
						clientIO.startReceiveThread();
						
						System.out.println(clientID + " has connected");
						
						serverMain.clientFound();
					}
				} catch (IOException e) {
					if (serverSocket.isClosed()) {
						System.out.println("Server closed, stopping accept thread");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
		
		acceptThread.start();
	}
}
