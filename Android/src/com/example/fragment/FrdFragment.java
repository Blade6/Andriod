package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.FriendAdapter;
import com.example.common.MyURL;
import com.example.entity.Friend;
import com.example.mychat.R;
import com.example.mychat.R.drawable;
import com.example.mychat.R.id;
import com.example.mychat.R.layout;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
public class FrdFragment extends Fragment {
	private ListView listView;
	private LinearLayout newfriend;
	private LinearLayout layout1;
	private LinearLayout layout2;
	private SharedPreferences sp;
	private TextView returnview;
	private Button findnewfriend_button;
	private ListView newfriendlist;
	private EditText findfriend_textview;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		View v = inflater.inflate(R.layout.friend, container, false);
		return v;
	}
	

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		context = this.getActivity().getApplicationContext();
		listView=(ListView) view.findViewById(R.id.friends);
		newfriend = (LinearLayout) view.findViewById(R.id.newfriend);
		layout1 = (LinearLayout) view.findViewById(R.id.include1);  
		layout2 = (LinearLayout) view.findViewById(R.id.include2);
		LayoutInflater inflater =  LayoutInflater.from(this.getActivity());
		returnview = (TextView) this.getActivity().findViewById(R.id.returnview);
		findnewfriend_button = (Button) view.findViewById(R.id.findnewfriend_button);
		newfriendlist = (ListView) view.findViewById(R.id.newfriendlist);
		findfriend_textview = (EditText) view.findViewById(R.id.findfriend_textview);
		sp = this.getActivity().getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		
		
//	   AsyncHttpClient client = new AsyncHttpClient();
//	   RequestParams params = new RequestParams();
//	   String userid = sp.getString("USERID", "");
//	   params.put("userid", userid);
//	   client.post(MyURL.FriendsURL, params, new JsonHttpResponseHandler(){
//		   ArrayList<Friend>  result = new ArrayList<Friend>();
//		   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
//			   try { 
//				   Friend f = new Friend();
//				    JSONArray jsonArray = (JSONArray) response.get("data");
//				    for(int i=0;i<jsonArray.length();i++){
//				    	JSONObject ob = (JSONObject) jsonArray.get(i);
//				    	f.setImg(ob.getInt("img"));
//				        f.setName(ob.getString("name"));
//				        result.add(f);
//				    }
//				    FriendAdapter adapter = new FriendAdapter(context,result);
//					listView.setAdapter(adapter);  
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			};
//		});

		//测试
		FriendAdapter adapter = new FriendAdapter(context,getData());
		listView.setAdapter(adapter); 
		
		newfriend.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {			
				layout1.setVisibility(View.GONE);  
				layout2.setVisibility(View.VISIBLE);  
				returnview.setVisibility(View.VISIBLE);
				
			}
		});
		returnview.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {			
				layout1.setVisibility(View.VISIBLE);  
				layout2.setVisibility(View.GONE);  
				returnview.setVisibility(View.GONE);
				
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
					
					//测试
					FriendAdapter adapter = new FriendAdapter(context,getData());
					newfriendlist.setAdapter(adapter); 
					
//					newfriendlist.setAdapter(adapter);  
//					AsyncHttpClient client = new AsyncHttpClient();
//					   RequestParams params = new RequestParams();
//					   String username_text = sp.getString("USERNAME", "");
//					   params.put("userid", username_text);
//					   params.put("username", findName);
//					   client.post(MyURL.FriendsURL, params, new JsonHttpResponseHandler(){
//						   ArrayList<Friend>  result = new ArrayList<Friend>();
//						   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
//							   try {
//								   
//								   Friend f = new Friend();
//								    JSONArray jsonArray = (JSONArray) response.get("data");
//								    for(int i=0;i<jsonArray.length();i++){
//								    	JSONObject ob = (JSONObject) jsonArray.get(i);
//								    	f.setImg(ob.getInt("img"));
//								        f.setName(ob.getString("name"));
//								        result.add(f);
//								    }
//								    FriendAdapter adapter = new FriendAdapter(context,result);
//									newfriendlist.setAdapter(adapter);  
//							} catch (JSONException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							}
//							};
//						});
				}
				 
			}
		});	
	}
	
	
	
	
//测试数据	
	   private ArrayList<Friend> getData() {
		   
		   //测试
		    ArrayList<Friend> list = new ArrayList<Friend>();
	        Friend f = new Friend();
	        f.setName("chen");
	        f.setImg(R.drawable.tab_find_frd_normal);
	        list.add(f);
	        Friend f2 = new Friend();
	        f2.setName("chen2");
	        f2.setImg(R.drawable.tab_find_frd_normal);
	        list.add(f2);	        
	        Friend f3 = new Friend();
	        f3.setName("chen3");
	        f3.setImg(R.drawable.tab_find_frd_normal);
	        list.add(f3);	         
	        return list;
	    }
	   

	   
}
