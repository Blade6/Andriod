package com.example.mychat;

import org.json.JSONObject;

import com.example.common.AtyContainer;
import com.example.common.LogUtil;
import com.example.common.MD5;
import com.example.common.MyURL;
import com.loopj.android.http.*;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText username;
	private EditText password;
	private CheckBox remember;
	private String username_text;
	private String password_text;
	private boolean remember_text;
	private SharedPreferences sp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//取消标题栏
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login);
		AtyContainer.getInstance().addActivity(this);
		//获取控件 
	    username = (EditText) findViewById(R.id.username);
	    password = (EditText) findViewById(R.id.password);
	    remember = (CheckBox) findViewById(R.id.remember);
	    //获取sharePreference
		sp = this.getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		//如果已经记住密码
		if(sp.getBoolean("REMEMBER", false)){
			username.setText(sp.getString("USERNAME", ""));
			password.setText(sp.getString("PASSWORD", ""));
			remember.setChecked(true);
            Intent intent = new Intent(LoginActivity.this,IndexActivity.class);
            LoginActivity.this.startActivity(intent);  
		}
		
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AtyContainer.getInstance().removeActivity(this);
	}
	//注册事件处理
	public void register(View view){
        Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);  
        LoginActivity.this.startActivity(intent);  		
	}
	//登录事件处理
	public void login(View view){
	    username_text = username.getText().toString();
	    password_text = password.getText().toString();
	    remember_text = remember.isChecked();
		//http post请求
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("username", username_text);
		params.put("pwd", MD5.getMd5(password_text));
		
		//这个是测试的而已
		//Intent intent = new Intent(LoginActivity.this,IndexActivity.class);  
        //LoginActivity.this.startActivity(intent); 
        
        //发送请求
		client.post(MyURL.LoginURL, params, new JsonHttpResponseHandler(){
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
				Editor editor = sp.edit();
				try {
					int returnCode =  (Integer) response.get("returnCode");
					//登录成功
					if(returnCode == 1){
						JSONObject data = response.getJSONObject("data");
						String userid = data.getString("userid");
						//记住密码勾选
						editor.putString("USERID", userid);
						editor.putString("USERNAME", username_text);
						editor.putString("PASSWORD", MD5.getMd5(password_text));
						if(remember_text){				
							editor.putBoolean("REMEMBER", true);
						}
						editor.commit();
			            Intent intent = new Intent(LoginActivity.this,IndexActivity.class);  
			            LoginActivity.this.startActivity(intent);  
					}else{

						Toast.makeText(getApplicationContext(), "登录失败",
							     Toast.LENGTH_SHORT).show();
					}
					
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			};
		});
		
	}
}

