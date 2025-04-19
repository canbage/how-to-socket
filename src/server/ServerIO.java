package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class ServerIO implements Runnable {
	
	Thread receiveThread;
	
	String clientID;
	
	Socket clientSocket;
	
	BufferedReader inputStream;
	
	ServerClientManager clientManager;
	
	public ServerIO(String clientID, Socket clientSocket, ServerClientManager clientManager) throws IOException {
		
		this.clientID = clientID;
		this.clientSocket = clientSocket;
		this.clientManager = clientManager;
		this.clientSocket.setSoTimeout(2000);
		this.inputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}
	
	public void startReceiveThread() {
		
		receiveThread = new Thread(this);
		receiveThread.start();
	}
	
	public void close() {
		
		try {
			if (inputStream != null) {
				inputStream.close();
			}
			if (clientSocket != null) {
				clientSocket.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		
		try {
			String inputLine;
			while (true) {
				try {
					inputLine = inputStream.readLine();
					if (inputLine == null) {
						break;
					}
					System.out.println("Received: " + inputLine + " from " + clientID);
				} catch (SocketTimeoutException e) {
					if (clientSocket.isClosed()) {
						break;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			clientManager.removeClient(clientID);
			close();
			System.out.println(clientID + " has disconnected");
		}
	}
	
	public void sendMessage(String message) {
		
		
	}
}
