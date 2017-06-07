package com.example.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.ShareAdapter;
import com.example.common.AtyContainer;
import com.example.common.LogUtil;
import com.example.common.MD5;
import com.example.common.MyURL;
import com.example.entity.Share;
import com.example.mychat.R;
import com.example.mychat.R.id;
import com.example.mychat.R.layout;
import com.example.service.ShareService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MyFragment extends Fragment {
	protected static final int SUCCESS_GET_SHARE = 0;
	
	private Button logout_button;
	private LinearLayout myinfo_button;
	private TextView mygallery_button;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private LinearLayout layout3;
	private LinearLayout layout5;
	private SharedPreferences sp;
	private ImageView returnview;
	private ImageView ico;
	private TextView name;
	private TextView id;
	private ListView mygallerylist;
	private LinearLayout selectico;
	//private LinearLayout chengenanme;
	//private TextView textview;
	//private Button change;
	//private EditText edittext;
	private LinearLayout changePwd;
	private EditText oldpwd;
	private EditText newpwd;
	private Button change_pwd_btn;
	private Button changeinfo;
	private Context context;
	
	private ShareAdapter adapter;
	private File cache;
	
	private Handler mHandler = new Handler(){  
        public void handleMessage(android.os.Message msg) {  
            if(msg.what == SUCCESS_GET_SHARE){  
                List<Share> shares = (List<Share>) msg.obj;  
                adapter = new ShareAdapter(context,shares,cache);  
                mygallerylist.setAdapter(adapter);  
            }  
        };  
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		return inflater.inflate(R.layout.my, container, false);
	}

	public void onViewCreated(View view, Bundle savedInstanceState) {
		context = this.getActivity().getApplicationContext();
		sp = this.getActivity().getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		logout_button = (Button) view.findViewById(R.id.logout);
		myinfo_button = (LinearLayout) view.findViewById(R.id.myinfo);
		mygallery_button = (TextView) view.findViewById(R.id.mygallery);
		layout1 = (LinearLayout) view.findViewById(R.id.my1);  
		layout2 = (LinearLayout) view.findViewById(R.id.my2);
		layout3 = (LinearLayout) view.findViewById(R.id.my3);
		layout5 = (LinearLayout) view.findViewById(R.id.my5);
		returnview = (ImageView) this.getActivity().findViewById(R.id.returnview);
		ico = (ImageView) view.findViewById(R.id.ico);
		name = (TextView) view.findViewById(R.id.info_name);
		id = (TextView) view.findViewById(R.id.info_id);
		mygallerylist = (ListView) view.findViewById(R.id.mygallerylist);
		selectico = (LinearLayout) view.findViewById(R.id.selectico);
		//chengenanme = (LinearLayout) view.findViewById(R.id.chengenanme);
		//textview = (TextView) view.findViewById(R.id.textview);
		//change = (Button) view.findViewById(R.id.change);
		//edittext = (EditText) view.findViewById(R.id.edittext);
		changePwd = (LinearLayout) view.findViewById(R.id.changePwd);
		oldpwd = (EditText) view.findViewById(R.id.old_pwd);
		newpwd = (EditText) view.findViewById(R.id.new_pwd);
		change_pwd_btn = (Button) view.findViewById(R.id.change_pwd_btn);
		changeinfo = (Button) view.findViewById(R.id.changeinfo);
		
		//创建缓存目录，系统一运行就得创建缓存目录的，  
        cache = new File(Environment.getExternalStorageDirectory(), "cache");   
        if(!cache.exists()){  
            cache.mkdirs();  
        }
		
		logout_button.setOnClickListener(new OnClickListener() {
			//退出登录
			public void onClick(View v) {			
				String userid = sp.getString("USERID", "");
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("userid", userid);
				LogUtil.d("MainActivity", userid);
				client.post(MyURL.LogoutURL, params, new JsonHttpResponseHandler(){
					public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
						Editor editor = sp.edit();
						try {
							int returnCode =  (Integer) response.get("returnCode");
							// 登出成功
							if(returnCode == 1){
								// 清空原有的记住密码勾选
								editor.putString("USERNAME", "");
								editor.putString("PASSWORD", "");			
								editor.putBoolean("REMEMBER", false);
								AtyContainer.getInstance().finishAllActivity();
							}
						} catch (Exception e) {
							e.printStackTrace();
						}
						
					};
				});	
			}
			
		});
		//选择头像。回调函数在indexactivity中
		selectico.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                getActivity().startActivityForResult(intent, 1);  
				}
		});
		
//		//修改用户名
//		chengenanme.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {
//				textview.setText("用户名");	
//				layout2.setVisibility(View.GONE);
//				edittext.setText("");
//				layout4.setVisibility(View.VISIBLE);
//			}
//		});	
//		
//		//确认修改
//		change.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {	
//				String object = (String) textview.getText();
//				if(object == "用户名"){
//					name.setText(edittext.getText());				
//				}
//				layout4.setVisibility(View.GONE);
//				layout2.setVisibility(View.VISIBLE);
//			}
//		});	
		
		
		
		changePwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				layout2.setVisibility(View.GONE);
				layout5.setVisibility(View.VISIBLE);
			}
		});
		
		change_pwd_btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				String userid = sp.getString("USERID", "");
				params.put("userid", userid);
				params.put("oldpwd", MD5.getMd5(oldpwd.getText().toString().trim()));
				params.put("newpwd", MD5.getMd5(newpwd.getText().toString().trim()));
				client.post(MyURL.changeUserPwd, params, new JsonHttpResponseHandler(){
					   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
						   try {
							   int returnCode =  (Integer) response.get("returnCode");
							   if (returnCode == 1) {
								   Toast.makeText(context, "修改成功",
										Toast.LENGTH_SHORT).show();
							   } else {
								   Toast.makeText(context, "修改失败",
										Toast.LENGTH_SHORT).show();
							   }
						   } catch (Exception e) {
							   e.printStackTrace();
						   }
					   }
				});
			}
		});
		
		
		//个人信息获取
		myinfo_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				   AsyncHttpClient client = new AsyncHttpClient();
				   RequestParams params = new RequestParams();
				   String userid = sp.getString("USERID", "");
				   params.put("userid", userid);
				   client.post(MyURL.getInfoURL, params, new JsonHttpResponseHandler(){
					   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
						   try {
							    int returnCode =  (Integer) response.get("returnCode");
							    if (returnCode == 1) {
							    	JSONObject data = response.getJSONObject("data");
							    	name.setText(data.getString("username"));
									id.setText(data.getString("userid"));
							    	String ico_path = data.getString("pic");
							    	if (ico_path.equals("/wechat/Public/Users/default.png")) {
							    		ico.setImageResource(R.drawable.default_user_ico);
							    	} else {
							    		String name = "user" + "-" + ico_path.substring(ico_path.lastIndexOf("/")+1);
							    		File file = new File(cache, name);
							    		if (file.exists()) {  
							                ico.setImageURI(Uri.fromFile(file));  
							            } else {
							            	ico.setImageResource(R.drawable.default_user_ico);
							            }
							    	}
							    } else {
							    	Toast.makeText(context, "获取失败",
										     Toast.LENGTH_SHORT).show();
							    }
						   } catch (Exception e) {
							   e.printStackTrace();
							   LogUtil.d("MainActivity", "Error");
						   }
						};
					});
				layout1.setVisibility(View.GONE);	
				layout2.setVisibility(View.VISIBLE);
				returnview.setVisibility(View.VISIBLE);
				if (returnview.getVisibility() == View.VISIBLE) {
					LogUtil.d("MainActivity", "bingo");
				} else {
					LogUtil.d("MainActivity", "error");
				}
			}
		});	
		
		//返回按钮
		returnview.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
//				LogUtil.d("MainActivity", "you click me");
//				LogUtil.d("MainActivity", layout1.getVisibility()+"");
//				LogUtil.d("MainActivity", layout2.getVisibility()+"");
//				LogUtil.d("MainActivity", layout3.getVisibility()+"");
//				LogUtil.d("MainActivity", layout5.getVisibility()+"");
				layout1.setVisibility(View.VISIBLE);  
				layout2.setVisibility(View.GONE); 
				layout3.setVisibility(View.GONE);
				layout5.setVisibility(View.GONE);
				returnview.setVisibility(View.INVISIBLE);
				
			}
		});
		
		//提交修改的信息
//		changeinfo.setOnClickListener(new OnClickListener() {
//			public void onClick(View v) {	
//				Toast.makeText(context, "修改成功",
//					 Toast.LENGTH_SHORT).show();
//			}
//		});	
		
		
		//我的个人相册
		mygallery_button.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
			   AsyncHttpClient client = new AsyncHttpClient();
			   RequestParams params = new RequestParams();
			   String username_text = sp.getString("USERID", "");
			   params.put("userid", username_text);
			   client.post(MyURL.getUserShareURL, params, new JsonHttpResponseHandler(){
				   List<Share>  result = new ArrayList<Share>();
				   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
					   try { 
						   int returnCode =  (Integer) response.get("returnCode");
						    if (returnCode == 1) {
						    	JSONArray jsonArray = (JSONArray) response.get("data");
						    	String pic = response.getString("pic");
							    for(int i=0;i<jsonArray.length();i++){
							    	JSONObject ob = (JSONObject) jsonArray.get(i);
							    	Share s = new Share();
							    	if (!ob.getString("image").equals("null")) {
							    		s.setImgPath(ob.getString("image"));
							    	}

							    	if (!ob.getString("words").equals("null")) {
							    		s.setWords(ob.getString("words"));
							    	}
							    	s.setIcoPath(pic);
							    	s.setUsername(ob.getString("username"));
							        result.add(s);
							    }
							    
							  //获取数据，主UI线程是不能做耗时操作的，所以启动子线程来做  
						      new Thread(){  
						            public void run() {  
						                ShareService service = new ShareService();    
						                //子线程通过Message对象封装信息，并且用初始化好的，  
						                //Handler对象的sendMessage()方法把数据发送到主线程中，从而达到更新UI主线程的目的  
						                Message msg = new Message();  
						                msg.what = SUCCESS_GET_SHARE;  
						                msg.obj = result;  
						                mHandler.sendMessage(msg);  
						            };  
						        }.start();
						    } else {
						    	Toast.makeText(context, "动态获取失败",
									     Toast.LENGTH_SHORT).show();
						    }
					} catch (JSONException e) {
						e.printStackTrace();
					}
					};
				});		
				layout1.setVisibility(View.GONE);  
				layout3.setVisibility(View.VISIBLE);  
				returnview.setVisibility(View.VISIBLE);
			}
		});	
		
		
		
	}
}
