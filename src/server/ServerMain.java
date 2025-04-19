package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class ServerMain {
	
	final int defaultPort = 10630;
	
	int currentPort = defaultPort;
	
	boolean clientFindingInProgress = false;
	
	public void clientFound() {
		clientFindingInProgress = false;
	}
	
	public static void main(String args[]) {
		
		ServerMain serverMain = new ServerMain();
		ServerState serverState = new ServerState(serverMain);
		
		serverMain.initialize(serverState);
	}
	
	public void initialize(ServerState serverState) {
		
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Server Interface");
		window.setSize(300, 150);		
		window.setLocationRelativeTo(null);
		window.setLayout(null);
		
		JButton startServer = new JButton("Start Server");
		startServer.setBounds(10, 20, 150, 20);
		startServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverState.startServer(currentPort);
			}
		});
		
		JLabel portLabel = new JLabel("Port: ");
		portLabel.setBounds(180, 20, 30, 20);
		
		JTextField portField = new JTextField("" + currentPort);
		portField.setBounds(210, 20, 70, 20);
		portField.setHorizontalAlignment(JTextField.CENTER);
		portField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					int portNumber = Integer.parseInt(e.getActionCommand());
					
					if ((portNumber >= 0) && (portNumber <= 65535)) {
						currentPort = portNumber;
						System.out.println("Changed port to: " + currentPort);
					} else {
						System.out.println("Invalid Port");
						portField.setText("" + currentPort);
					}
				} catch (NumberFormatException ex) {
					System.out.println("Invalid Port");
					portField.setText("" + currentPort);
				}
			}
		});
		
		JButton killServer = new JButton("Kill Server");
		killServer.setBounds(10, 50, 150, 20);
		killServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				serverState.killServer();
			}
		});
		
		JButton addClient = new JButton("Add Client");
		addClient.setBounds(170, 50, 110, 20);
		addClient.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!clientFindingInProgress) {
					serverState.findClient();
					clientFindingInProgress = true;
				} else {
					System.out.println("Already looking for client");
				}
			}
		});
		
		window.add(startServer);
		window.add(portLabel);
		window.add(portField);
		window.add(killServer);
		window.add(addClient);
		
		window.setVisible(true);
	}
}