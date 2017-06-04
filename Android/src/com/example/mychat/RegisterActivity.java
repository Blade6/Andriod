package com.example.mychat;

import com.example.common.AtyContainer;
import com.example.common.MyURL;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import android.os.Build;

public class RegisterActivity extends Activity {
	private EditText username;
	private EditText password;
	private EditText password2;
	private EditText answer;
	private EditText answer2;
	private Spinner question;
	private Spinner question2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_register);
		AtyContainer.getInstance().addActivity(this);
		//获取控件
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		password2 = (EditText) findViewById(R.id.password2);
		answer = (EditText) findViewById(R.id.answer);
		answer2 = (EditText) findViewById(R.id.answer2);
		question = (Spinner) findViewById(R.id.question);
		question2 = (Spinner) findViewById(R.id.question2);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AtyContainer.getInstance().removeActivity(this);
	}
	public void register(View view){
		//获取数据
		String password_text = password.getText().toString();
		String password2_text = password2.getText().toString();
		String answer_text = answer.getText().toString();
		String answer2_text = answer2.getText().toString();
		String username_text = username.getText().toString();
		String question_text = question.getSelectedItem().toString();
		String question2_text = question2.getSelectedItem().toString();
		
		if (!password_text.equals(password2_text)) {
			Toast.makeText(getApplicationContext(), "密码不一致",
				     Toast.LENGTH_SHORT).show();
			return ;
		}
		
		AsyncHttpClient client = new AsyncHttpClient();
		//填充数据
		RequestParams params = new RequestParams();
		params.put("password", password_text);
		params.put("username", username_text);	
		params.put("questionOne", question_text);
		params.put("answerOne", answer_text);
		params.put("questionTwo", question2_text);
		params.put("answerTwo", answer2_text);
		//发送请求 
		client.post(MyURL.RegisterURL, params, new JsonHttpResponseHandler(){
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
				try {
					int returnCode =  (Integer) response.get("returnCode");
					if(returnCode == 1){
			            Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);  
			            RegisterActivity.this.startActivity(intent);  
					}else{
						Toast.makeText(getApplicationContext(), "注册失败",
							     Toast.LENGTH_SHORT).show();
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			};
		});
	}
	

}
