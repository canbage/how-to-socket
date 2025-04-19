package client;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;

public class ClientIO {
	
public static void main(String[] args) throws IOException {
		
		String serverIP = "localhost";
		int port = 10630;
		
		Socket socket = new Socket(serverIP, port);
		System.out.println("Connected to server");
		
		PrintWriter outputStream = new PrintWriter(socket.getOutputStream(), true);
		
		JFrame frame = new JFrame("Remote Controller");
		frame.setSize(200, 200);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setFocusable(true);
		frame.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				outputStream.println("KEY_PRESSED:" + e.getKeyCode());
			}

			@Override
			public void keyReleased(KeyEvent e) {
				outputStream.println("KEY_RELEASED:" + e.getKeyCode());
			}

			@Override
			public void keyTyped(KeyEvent e) {}
		});
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				try {
					outputStream.close();
					socket.close();
				} catch (Exception ex){
					ex.printStackTrace();
				}
			}
		});
		
		frame.setVisible(true);
		frame.requestFocusInWindow();
	}
}
