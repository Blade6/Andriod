package com.example.activity;

import com.example.util.HttpCallbackListener;
import com.example.util.HttpUtil;
import com.example.util.JsonParser;
import com.example.util.LogUtil;
import com.example.util.MD5;
import com.example.wechat.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity {
	
	private EditText login_username;
	private EditText login_password;
	private Button login_button;
	private TextView loginState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		login_username = (EditText) findViewById(R.id.login_username);
		login_password = (EditText) findViewById(R.id.login_password);
		loginState = (TextView) findViewById(R.id.loginState);
		login_button = (Button) findViewById(R.id.login_button);
		login_button.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				login(LoginActivity.this, login_username.getText().toString().trim(),
						login_password.getText().toString().trim());
			}
		});
	}
	
	public void login(final Context context, String username, String pwd) {
		pwd = MD5.getMd5(pwd);
		String address = "wechat/index.php/Home/User/login/"
				+ "username/" + username + "/pwd/" + pwd;

		HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {
				String s = JsonParser.handleResponse(response);
				if (s.equals("failure") || s.equals("exception"))
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							loginState.setText("µ«¬Ω ß∞‹£¨’ ∫≈ªÚ√‹¬Î¥ÌŒÛ£°");						
						}
					});
				else {
					Intent intent = new Intent(context, MainActivity.class);
					startActivity(intent);
					finish();
				}
			}
			@Override
			public void onError(Exception e) {
				LogUtil.d("MainActivity", e.toString());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						loginState.setText("Õ¯¬Áπ ’œ£¨«Î…‘∫Û÷ÿ ‘£°");						
					}
				});				
			}
		});
	}
	
	
}
