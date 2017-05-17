package com.example.activity;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.util.HttpCallbackListener;
import com.example.util.HttpUtil;
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

public class SignUpActivity extends Activity {

	private EditText signup_username;
	private EditText signup_password;
	private Button zhuce_button;
	private TextView signupState;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.signup);
		signup_username = (EditText) findViewById(R.id.signup_username);
		signup_password = (EditText) findViewById(R.id.signup_password);
		zhuce_button = (Button) findViewById(R.id.zhuce_button);
		signupState = (TextView) findViewById(R.id.signupState);
		zhuce_button.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {
				signup(signup_username.getText().toString(), 
						signup_password.getText().toString());				
			}
		});
	}
	
	public void signup(String username, String pwd) {
		pwd = MD5.getMd5(pwd);
		String address = "wechat/index.php/Home/User/signup/"
				+ "username/" + username + "/pwd/" + pwd;
		HttpUtil.sendHttpRequest(address, "POST", new HttpCallbackListener() {		
			@Override
			public void onFinish(String response) {
				try {
					JSONObject jsonObject = new JSONObject(response.toString());
					String returnCode = jsonObject.getString("returnCode");
					final String msg = jsonObject.getString("msg");
					if (returnCode.equals("1")) {
						Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
						startActivity(intent);
						finish();
					} else {
						runOnUiThread(new Runnable() {							
							@Override
							public void run() {
								signupState.setText(msg);								
							}
						});
					}
				} catch (JSONException e) {
					runOnUiThread(new Runnable() {						
						@Override
						public void run() {
							signupState.setText("Ω‚ŒˆÕ¯¬Á«Î«Û≥ˆ¥Ì£¨«Î…‘∫Û÷ÿ ‘£°");							
						}
					});
				}
			}			
			@Override
			public void onError(Exception e) {
				LogUtil.d("MainActivity", e.toString());
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						signupState.setText("Õ¯¬Áπ ’œ£¨«Î…‘∫Û÷ÿ ‘£°");						
					}
				});				
			}
		});
	}
	
}
