package com.example.activity;

import com.example.entity.User;
import com.example.wechat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private ImageView pic;
	private TextView username;
	private Button talk;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_info);
		pic = (ImageView) findViewById(R.id.pic);
		username = (TextView) findViewById(R.id.username);
		talk = (Button) findViewById(R.id.talk);
		User user = User.getUser();
		username.setText(user.getUserName() + "\n" + "µÇÂ½³É¹¦£¡");
		talk.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MainActivity.this, TalkActivity.class);
				startActivity(intent);
				finish();				
			}
		});
	}
		
	
}
