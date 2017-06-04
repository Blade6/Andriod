package com.example.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.FriendAdapter;
import com.example.adapter.ShareAdapter;
import com.example.common.MyURL;
import com.example.entity.Friend;
import com.example.entity.Share;
import com.example.mychat.R;
import com.example.mychat.R.id;
import com.example.mychat.R.layout;
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
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ShareFragment extends Fragment {
	private ListView sharelist;
	private TextView addshare;
	private LinearLayout share1;
	private LinearLayout share2;
	private TextView returnview;
	private EditText sharetext;
	private ImageView shareimg;
	private SharedPreferences sp;
	private Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		return inflater.inflate(R.layout.share, container, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		sharelist = (ListView) view.findViewById(R.id.sharelist);
		addshare =  (TextView) view.findViewById(R.id.addshare);
		context = this.getActivity().getApplicationContext();
		share1 = (LinearLayout) view.findViewById(R.id.share1);
		share2 = (LinearLayout) view.findViewById(R.id.share2);
		returnview = (TextView) this.getActivity().findViewById(R.id.returnview);
		sharetext = (EditText) view.findViewById(R.id.sharetext);
		shareimg = (ImageView) view.findViewById(R.id.shareimg);
		sp = this.getActivity().getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
//		List<Map<String, Object>>  result = new ArrayList<Map<String, Object>>();
//		   AsyncHttpClient client = new AsyncHttpClient();
//		   RequestParams params = new RequestParams();
//		   String userid = sp.getString("USERID", "");
//		   params.put("userid", userid);
//		   client.post(MyURL.LoginURL, params, new JsonHttpResponseHandler(){
//			   ArrayList<Share>  result = new ArrayList<Share>();
//			   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
//				   try { 
//					   Share s = new Share();
//					    JSONArray jsonArray = (JSONArray) response.get("data");
//					    for(int i=0;i<jsonArray.length();i++){
//					    	JSONObject ob = (JSONObject) jsonArray.get(i);
//					    	s.setIco(ob.getInt("ico"));
//					    	s.setImg(ob.getInt("img"));
//					    	s.setTitle(ob.getString("title"));
//					    	s.setUsername(ob.getString("name"));
//					        result.add(s);
//					    }
//					    ShareAdapter adapter = new ShareAdapter(context,result);
//					    sharelist.setAdapter(adapter);  
//				} catch (JSONException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				};
//			});
		
		
		//测试
		
		
		ArrayList<Share> data= getData();
		ShareAdapter adapter = new ShareAdapter(getActivity(), data);
		sharelist.setAdapter(adapter);
		
		
		addshare.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {			
				share1.setVisibility(View.GONE);  
				share2.setVisibility(View.VISIBLE);  
				returnview.setVisibility(View.VISIBLE);
				
			}
		});
		
		returnview.setOnClickListener(new OnClickListener() {		
		public void onClick(View v) {			
			share1.setVisibility(View.VISIBLE);  
			share2.setVisibility(View.GONE);  
			returnview.setVisibility(View.GONE);
			
		}
	});
		
		
		shareimg.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {			
				Intent intent = new Intent();  
                /* 开启Pictures画面Type设定为image */  
                intent.setType("image/*");  
                /* 使用Intent.ACTION_GET_CONTENT这个Action */  
                intent.setAction(Intent.ACTION_GET_CONTENT);   
                /* 取得相片后返回本画面 */  
                getActivity().startActivityForResult(intent, 2);  
			}
		});
	}
	
	
	
	
	
	private ArrayList<Share> getData() {
		   
	   
	   //测试
	   ArrayList<Share> list = new ArrayList<Share>();
        Share share = new Share();
        share.setTitle("123");
        share.setUsername("34455");
        list.add(share);
        Share share2 = new Share();
        share2.setTitle("123");
        share2.setUsername("34455");
        share2.setImg(2130837518);
        list.add(share2);
        return list;
    }
}
