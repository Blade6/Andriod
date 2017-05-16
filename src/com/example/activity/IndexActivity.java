package com.example.activity;

import com.example.wechat.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class IndexActivity extends Activity {

	private Button signInButton;
	private Button signUpButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.index);
		signInButton = (Button) findViewById(R.id.SignIn_button);
		signUpButton = (Button) findViewById(R.id.SignUp_button);
		signInButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity.this, LoginActivity.class);
				startActivity(intent);
				finish();				
			}
		});
		signUpButton.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(IndexActivity.this, SignUpActivity.class);
				startActivity(intent);
				finish();			
			}
		});
	}
	
}
