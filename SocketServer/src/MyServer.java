import java.awt.BorderLayout;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MyServer extends JFrame {

	private JTextArea jta = new JTextArea();
	
	//                    上线的客户端编号        客户端socket
	private ConcurrentHashMap<String, Socket> sockets;
	
	public static void main(String[] args) {
		new MyServer();
	}
	
	public MyServer() {
		setLayout(new BorderLayout());
		add(new JScrollPane(jta), BorderLayout.CENTER);
		
		setTitle("Server");
		setSize(500, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		sockets = new ConcurrentHashMap<>();
		
		try {
			// Create a server socket
			ServerSocket serverSocket = new ServerSocket(8089);
			jta.append("Server started at " + new Date() + '\n');

			while (true) {
				// Listen for a connection request
				Socket socket = serverSocket.accept();
				// Create a new thread for the connection
				HandleAClient task = new HandleAClient(socket);
				
				// Start the new thread
				new Thread(task).start();			
			}
		}
		catch (IOException ex) {
			System.err.println(ex);
		}
	}
	
	class HandleAClient implements Runnable {
		private Socket socket;
		
		/** Construct a thread */
		public HandleAClient(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				DataInputStream inputFromClient = new DataInputStream(
						socket.getInputStream());
				String user_join_in = inputFromClient.readUTF();
				jta.append(user_join_in + "加入聊天室\n");
				jta.append("------------------------" + '\n');

				// 通知其他用户新用户上线
				Set<Map.Entry<String, Socket>> sets = sockets.entrySet();
				for (Entry<String, Socket> set : sets) {
					DataOutputStream outputToClient = new DataOutputStream(
						set.getValue().getOutputStream());
					outputToClient.writeUTF("管理员");
					outputToClient.writeUTF(user_join_in + "加入聊天室");
				}
				
				// 如果之前没有该socket客户端的话
				if (!sockets.containsKey(socket)) {
					// 保存socket客户端
					sockets.put(user_join_in, socket);
				}
				
				while (true) {					
					// Receive the client id
					String fromId = inputFromClient.readUTF();															
					// Receive the msg that client want to send
					String msg = inputFromClient.readUTF();
					
					// 有用户退出聊天室，管理该用户的线程结束
					if (msg.equals("exit")) {
						sockets.remove(fromId);
						break;
					}
					
					jta.append("from: " + fromId + '\n');
					jta.append("msg: " + msg + '\n');
					jta.append("------------------------" + '\n');
					
					sets = sockets.entrySet();
					for (Entry<String, Socket> set : sets) {
						if (!set.getKey().equals(fromId)) {
							DataOutputStream outputToClient = new DataOutputStream(
									set.getValue().getOutputStream());
							outputToClient.writeUTF(fromId);
							outputToClient.writeUTF(msg);
						}
					}
				}			
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}		
	}

}
