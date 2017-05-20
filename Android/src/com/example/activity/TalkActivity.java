package com.example.activity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import com.example.adapter.MsgAdapter;
import com.example.entity.Msg;
import com.example.util.LogUtil;
import com.example.wechat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

public class TalkActivity extends Activity {

	private ListView msgListView;
	
	private EditText inputText;
	
	private Button send;
	
	private MsgAdapter adapter;
	
	private List<Msg> msgList = new ArrayList<Msg>();
	
	private Socket socket;
	
	private DataInputStream fromServer;
	
	private DataOutputStream toServer;
	
	//private String username = User.getUser().getUserName();
	
	private Thread chat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chat);
		adapter = new MsgAdapter(TalkActivity.this, R.layout.msg_item, msgList);
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		msgListView.setAdapter(adapter);
		send.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				String content = inputText.getText().toString();
				if (!"".equals(content)) {
					try {
						toServer.writeUTF("С��");
						toServer.writeUTF(content);
						toServer.flush();
					} catch (IOException e) {
						LogUtil.d("MainActivity", "����ʧ��");
					}
					
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					msgList.add(msg);
					adapter.notifyDataSetChanged();//��������Ϣʱ��ˢ��ListView�е���ʾ
					msgListView.setSelection(msgList.size());//��ListView��λ�����һ��
					inputText.setText("");//���������е�����
				}	
			}
		});
		
		// �����½��̽�����������Ϣ
		chat = new Thread(new chatTask());
		chat.start();
	}
	
	private class chatTask implements Runnable {
		@Override
		public void run() {
			try {
				//����д���������ip������ڱ������ԣ�����дlocalhost����Ϊ����ָ����android��ip
				socket = new Socket("192.168.201.80", 8089);
				fromServer = new DataInputStream(
						socket.getInputStream());
				toServer = new DataOutputStream(
						socket.getOutputStream());
				LogUtil.d("MainActivity", "socket��ʼ���ɹ�");
			}
			catch (IOException e) {
				LogUtil.d("MainActivity", "socket��ʼ��ʧ��");
			}
			
			while (true) {
				try {
					String fromWhom = fromServer.readUTF();
					String msgRec = fromServer.readUTF();
					LogUtil.d("MainActivity", "���ճɹ�");
					Msg msg = new Msg(msgRec, Msg.TYPE_RECEIVED);
					LogUtil.d("MainActivity", "��װ�ɹ�");
					msgList.add(msg);
					LogUtil.d("MainActivity", "��ӳɹ�");
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							adapter.notifyDataSetChanged();//��������Ϣʱ��ˢ��ListView�е���ʾ
							msgListView.setSelection(msgList.size());//��ListView��λ�����һ��							
						}
					});
				}
				catch (IOException e) {
					LogUtil.d("MainActivity", "����ʧ��");
				}
			}
		}		
	}
	
}
