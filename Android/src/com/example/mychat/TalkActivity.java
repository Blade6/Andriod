package com.example.mychat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MsgAdapter;
import com.example.entity.Msg;
import com.example.entity.User;
import com.example.common.LogUtil;
import com.example.common.MyURL;
import com.example.mychat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

public class TalkActivity extends Activity {

	private ImageButton quitRoom;
	
	private ListView msgListView;
	
	private EditText inputText;
	
	private Button send;
	
	private MsgAdapter adapter;
	
	private List<Msg> msgList = new ArrayList<Msg>();
	
	private Socket socket;
	
	private DataInputStream fromServer;
	
	private DataOutputStream toServer;
	
	//private String username = User.getUser().getUserName();
	private String username = "hjh";
	
	private Thread chat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.chat);
		quitRoom = (ImageButton) findViewById(R.id.quitRoom);
		adapter = new MsgAdapter(TalkActivity.this, R.layout.msg_item, msgList);
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		quitRoom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (socket != null) {
					try {
						toServer.writeUTF(username);
						toServer.writeUTF("exit");
						toServer.flush();
					} catch (IOException e) {
						LogUtil.d("MainActivity", "发送失败");
					}
				}
				
				Intent intent = new Intent(TalkActivity.this, IndexActivity.class);
				startActivity(intent);
				finish();
			}
		});
		send.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				String content = inputText.getText().toString();
				if (!"".equals(content)) {
					try {
						toServer.writeUTF(username);
						toServer.writeUTF(content);
						toServer.flush();
					} catch (IOException e) {
						LogUtil.d("TalkActivity", "发送失败");
					}
					
					Msg msg = new Msg(username, content, Msg.TYPE_SENT);
					msgList.add(msg);
					adapter.notifyDataSetChanged();//当有新消息时，刷新ListView中的显示
					msgListView.setSelection(msgList.size());//将ListView定位到最后一行
					inputText.setText("");//清空输入框中的内容
				}	
			}
		});
		
		// 开启新进程接收聊天室消息
		chat = new Thread(new chatTask());
		chat.start();
	}
	
	private class chatTask implements Runnable {
		@Override
		public void run() {
			try {
				//必须写清楚服务器ip，如果在本机测试，不能写localhost，因为后者指的是android的ip
				socket = new Socket(MyURL.talkServerIP, MyURL.talkServerPort);
				fromServer = new DataInputStream(
						socket.getInputStream());
				toServer = new DataOutputStream(
						socket.getOutputStream());
				toServer.writeUTF(username);
				toServer.flush();
				LogUtil.d("TalkActivity", "socket初始化成功");
			}
			catch (IOException e) {
				LogUtil.d("TalkActivity", "socket初始化失败");
			}
			while (true) {
				try {
					String fromWhom = fromServer.readUTF();
					String msgRec = fromServer.readUTF();
					Msg msg = new Msg(fromWhom, msgRec, Msg.TYPE_RECEIVED);
					msgList.add(msg);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();//当有新消息时，刷新ListView中的显示
							msgListView.setSelection(msgList.size());//将ListView定位到最后一行							
						}
					});
				}
				catch (IOException e) {
					LogUtil.d("TalkActivity", "接收失败");
				}
			}
		}		
	}
	
}
