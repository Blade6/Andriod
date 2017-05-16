package com.example.activity;

import com.example.entity.User;
import com.example.wechat.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageView pic;
	private TextView username;
	//private Button logout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		pic = (ImageView) findViewById(R.id.pic);
		username = (TextView) findViewById(R.id.username);
		//logout = (Button) findViewById(R.id.logout);
		User user = User.getUser();
		username.setText(user.getUserName() + "\n" + "µÇÂ½³É¹¦£¡");
	}
		
	
}
