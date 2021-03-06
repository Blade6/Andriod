package com.example.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.FriendAdapter;
import com.example.common.LogUtil;
import com.example.common.MD5;
import com.example.common.MyURL;
import com.example.entity.Friend;
import com.example.mychat.LoginActivity;
import com.example.mychat.R;
import com.example.mychat.RegisterActivity;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;

public class FrdFragment extends Fragment {
	private ListView listView;
	private ImageView newfriend;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private SharedPreferences sp;
	private ImageView fri_returnview;
	private Button findnewfriend_button;
	private ImageView refresh;
	private ListView newfriendlist;
	private EditText findfriend_textview;
	private Context context;
	private String userid;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		View v = inflater.inflate(R.layout.friend, container, false);
		return v;
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		context = this.getActivity().getApplicationContext();
		listView=(ListView) view.findViewById(R.id.friends);
		newfriend = (ImageView) view.findViewById(R.id.newfriend);
		layout1 = (LinearLayout) view.findViewById(R.id.include1);  
		layout2 = (LinearLayout) view.findViewById(R.id.include2);
		LayoutInflater inflater =  LayoutInflater.from(this.getActivity());
		fri_returnview = (ImageView) this.getActivity().findViewById(R.id.fri_returnview);
		findnewfriend_button = (Button) view.findViewById(R.id.findnewfriend_button);
		refresh = (ImageView) view.findViewById(R.id.refresh);
		newfriendlist = (ListView) view.findViewById(R.id.newfriendlist);
		findfriend_textview = (EditText) view.findViewById(R.id.findfriend_textview);
		sp = this.getActivity().getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		userid = sp.getString("USERID", "");
		
		AsyncHttpClient client = new AsyncHttpClient();
		   RequestParams params = new RequestParams();
		   params.put("userid", userid);
		   client.post(MyURL.FriendsURL, params, new JsonHttpResponseHandler(){
			   ArrayList<Friend>  result = new ArrayList<Friend>();
			   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
				   try {
					    int returnCode =  (Integer) response.get("returnCode");
					    if (returnCode == 1) {
					    	JSONArray jsonArray = (JSONArray) response.get("data");
						    for(int i=0;i<jsonArray.length();i++){
						    	JSONObject ob = (JSONObject) jsonArray.get(i);
						    	Friend f = new Friend();
						    	f.setImg(R.drawable.tab_find_frd_normal);
						    	if (ob.has("frename")) {
						    		f.setName(ob.getString("frename"));
						    	} else {
						    		f.setName(ob.getString("username"));
						    	}
						        result.add(f);
						    }
					    }
					    FriendAdapter adapter = new FriendAdapter(context,result);
						listView.setAdapter(adapter);  
				} catch (JSONException e) {
					e.printStackTrace();
				}
				};
			});
		   
		newfriend.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				layout1.setVisibility(View.GONE);
				layout2.setVisibility(View.VISIBLE);  
				fri_returnview.setVisibility(View.VISIBLE);
			}
		});
		fri_returnview.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {	
				//LogUtil.d("MainActivity", "you click me");
				//LogUtil.d("MainActivity", layout1.getVisibility()+"");
				//LogUtil.d("MainActivity", layout2.getVisibility()+"");
				layout2.setVisibility(View.GONE); 
				layout1.setVisibility(View.VISIBLE);
				fri_returnview.setVisibility(View.INVISIBLE);
			}
		});
		
		findnewfriend_button.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {	
				
				String findName = findfriend_textview.getText().toString();
				//没有输入查找信息
				if(findName.isEmpty()||findName==null||findName==""){
					Toast.makeText(context, "请输入信息",
						     Toast.LENGTH_SHORT).show();
				}else{
					   AsyncHttpClient client = new AsyncHttpClient();
					   RequestParams params = new RequestParams();
					   params.put("userid", userid);
					   params.put("searchname", findName);
					   client.post(MyURL.FindfriendURL, params, new JsonHttpResponseHandler(){
						   ArrayList<Friend>  result = new ArrayList<Friend>();
						   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
							   try {
								    int returnCode =  (Integer) response.get("returnCode");
								    
								    String friendid = null;
								    if (returnCode == 1) {
								    	friendid = response.getString("data");
									    Friend f = new Friend();
									    f.setImg(R.drawable.tab_find_frd_normal);
									    f.setName(friendid);
									    result.add(f);
								    } else {
								    	Toast.makeText(context, "未搜索到任何结果",
											     Toast.LENGTH_SHORT).show();
								    }
								    
								    FriendAdapter adapter = new FriendAdapter(context, result);
									newfriendlist.setAdapter(adapter);
									
									newfriendlist.setOnItemClickListener(new OnItemClickListener() {
										@Override
										public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
											search(result.get(arg2).getName());											
										}
									});
									
							   } catch (JSONException e) {
								   e.printStackTrace();
							   }
							};
						});
				}
				 
			}
		});
		
		refresh.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AsyncHttpClient client = new AsyncHttpClient();
				   RequestParams params = new RequestParams();
				   params.put("userid", userid);
				   client.post(MyURL.FriendsURL, params, new JsonHttpResponseHandler(){
					   ArrayList<Friend>  result = new ArrayList<Friend>();
					   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
						   try {
							    int returnCode =  (Integer) response.get("returnCode");
							    if (returnCode == 1) {
							    	JSONArray jsonArray = (JSONArray) response.get("data");
								    for(int i=0;i<jsonArray.length();i++){
								    	JSONObject ob = (JSONObject) jsonArray.get(i);
								    	Friend f = new Friend();
								    	f.setImg(R.drawable.tab_find_frd_normal);
								    	if (ob.has("frename")) {
								    		f.setName(ob.getString("frename"));
								    	} else {
								    		f.setName(ob.getString("username"));
								    	}
								        result.add(f);
								    }
							    }
							    FriendAdapter adapter = new FriendAdapter(context,result);
								listView.setAdapter(adapter);  
						} catch (JSONException e) {
							e.printStackTrace();
						}
						};
					});				
			}
		});
		
	}
	
	public void search(String friendid) {
		AsyncHttpClient client = new AsyncHttpClient();
		RequestParams params = new RequestParams();
		params.put("userid", userid);
		params.put("friendid", friendid);
		
		client.post(MyURL.addfriendURL, params, new JsonHttpResponseHandler(){
			public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
				try {
					int returnCode =  (Integer) response.get("returnCode");
					if(returnCode == 1){
						Toast.makeText(context, "添加成功",
							     Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(context, "添加失败",
							     Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			};
		});
	}
	   
}
