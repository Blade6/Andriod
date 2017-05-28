import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Client extends JFrame {

	private JTextField jtf = new JTextField();
	private JTextArea jta = new JTextArea();
	
	private DataOutputStream toServer;
	private DataInputStream fromServer;
	
	private String me;
	
	private String msgSend;
	private boolean flag = false;
	
	public static void main(String[] args) {
		new Client(args[0]);
	}
	
	public Client(String me) {
		this.me = me;
		
		JPanel p = new JPanel();
		p.setLayout(new BorderLayout());
		p.add(new JLabel("Enter message"), BorderLayout.WEST);
		p.add(jtf, BorderLayout.CENTER);
		jtf.setHorizontalAlignment(JTextField.RIGHT);
		
		setLayout(new BorderLayout());
		add(p, BorderLayout.NORTH);
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		jtf.addActionListener(new TextFieldListener());
		
		setTitle(me);
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		try {
			// Create a socket to connect to the server
			Socket socket = new Socket("localhost", 8089);
			fromServer = new DataInputStream(
				socket.getInputStream());
			toServer = new DataOutputStream(
				socket.getOutputStream());
			toServer.writeUTF(me);
			toServer.flush();
			
			Thread recMsgThread = new Thread(new recMsg());
			recMsgThread.start();
		}
		catch (IOException ex) {
			jta.append(ex.toString() + '\n');
		}
	}
	
	private class TextFieldListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				// Get the message from the text field
				msgSend = jtf.getText();
				
				// Sent the message to the server
				toServer.writeUTF(me);
				toServer.writeUTF(msgSend);
				toServer.flush();
				
				jta.append(me + " says:\n" + msgSend + "\n");
			}
			catch (IOException ex) {
				System.err.println(ex);
			}
		}
	}
	
	private class recMsg implements Runnable {
		@Override
		public void run() {
			while (true) {
				try {
					// Get message from the server
					String fromWhom = fromServer.readUTF();
					String msgRec = fromServer.readUTF();
					jta.append(fromWhom + " says:\n" + msgRec + "\n");
				} catch (IOException e) {
					e.printStackTrace();
				}				
			}
		}		
	}
}
