package server;

import java.util.concurrent.ConcurrentHashMap;

public class ServerClientManager {
	
	ConcurrentHashMap<String, ServerIO> clientMap = new ConcurrentHashMap<>();
	
	public void addClient(String id, ServerIO io) {
		
		clientMap.put(id, io);
	}
	
	public void removeClient(String id) {
		
		clientMap.remove(id);
	}
	
	public void closeAllClients() {
		
		for (ServerIO io : clientMap.values()) {
			io.close();
		}
		clientMap.clear();
	}
	
	public void testMessage(String message) {
		
		for (ServerIO io : clientMap.values()) {
			io.sendMessage(message);
		}
	}
}
