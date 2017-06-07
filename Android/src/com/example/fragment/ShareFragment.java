package com.example.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.adapter.ShareAdapter;
import com.example.common.LogUtil;
import com.example.common.MyURL;
import com.example.entity.Share;
import com.example.mychat.IndexActivity;
import com.example.mychat.LoginActivity;
import com.example.mychat.R;
import com.example.service.ShareService;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
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
import android.widget.Toast;

public class ShareFragment extends Fragment {
	
	protected static final int SUCCESS_GET_SHARE = 0;
	
	private ListView sharelist;
	private ImageView addshare;
	private LinearLayout share1;
	private LinearLayout share2;
	private ImageView share_returnview;
	private EditText sharetext;
	private ImageView shareimg;
	private Button sendMoment;
	private SharedPreferences sp;
	private Context context;
	private File cache;
	private ShareAdapter adapter;
	
	private Handler mHandler = new Handler(){  
        public void handleMessage(android.os.Message msg) {  
            if(msg.what == SUCCESS_GET_SHARE){  
                List<Share> shares = (List<Share>) msg.obj;  
                adapter = new ShareAdapter(context,shares,cache);  
                sharelist.setAdapter(adapter);  
            }  
        };  
    };
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//�������ǵĲ���
		return inflater.inflate(R.layout.share, container, false);
	}
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		sharelist = (ListView) view.findViewById(R.id.sharelist);
		addshare =  (ImageView) view.findViewById(R.id.addshare);
		context = this.getActivity().getApplicationContext();
		share1 = (LinearLayout) view.findViewById(R.id.share1);
		share2 = (LinearLayout) view.findViewById(R.id.share2);
		share_returnview = (ImageView) this.getActivity().findViewById(R.id.share_returnview);
		sharetext = (EditText) view.findViewById(R.id.sharetext);
		shareimg = (ImageView) view.findViewById(R.id.shareimg);
		sendMoment = (Button) view.findViewById(R.id.sendMoment);
		sp = this.getActivity().getSharedPreferences("userinfo", Context.MODE_WORLD_READABLE);
		List<Map<String, Object>>  result = new ArrayList<Map<String, Object>>();
		
		//��������Ŀ¼��ϵͳһ���о͵ô�������Ŀ¼�ģ�  
        cache = new File(Environment.getExternalStorageDirectory(), "cache");   
        if(!cache.exists()){  
            cache.mkdirs();  
        }
		
        AsyncHttpClient client = new AsyncHttpClient();
	    RequestParams params = new RequestParams();
	    String userid = sp.getString("USERID", "");
	    params.put("userid", userid);
	    client.post(MyURL.getShareURL, params, new JsonHttpResponseHandler(){
		   List<Share>  result = new ArrayList<Share>();
		   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
			   try { 
				    int returnCode =  (Integer) response.get("returnCode");
				    if (returnCode == 1) {
				    	JSONArray jsonArray = (JSONArray) response.get("data");
					    for(int i=0;i<jsonArray.length();i++){
					    	JSONObject ob = (JSONObject) jsonArray.get(i);
					    	Share s = new Share();
					    	if (!ob.getString("image").equals("null")) {
					    		s.setImgPath(ob.getString("image"));
					    	}
					    	if (!ob.getString("words").equals("null")) {
					    		s.setWords(ob.getString("words"));
					    	}
					    	s.setIcoPath(ob.getString("pic"));
					    	s.setUsername(ob.getString("username"));
					        result.add(s);
					    }
//					    ShareAdapter adapter = new ShareAdapter(context,result);
//					    sharelist.setAdapter(adapter);
					    
					  //��ȡ���ݣ���UI�߳��ǲ�������ʱ�����ģ������������߳�����  
				      new Thread(){  
				            public void run() {  
				                ShareService service = new ShareService();    
				                //���߳�ͨ��Message�����װ��Ϣ�������ó�ʼ���õģ�  
				                //Handler�����sendMessage()���������ݷ��͵����߳��У��Ӷ��ﵽ����UI���̵߳�Ŀ��  
				                Message msg = new Message();  
				                msg.what = SUCCESS_GET_SHARE;  
				                msg.obj = result;  
				                mHandler.sendMessage(msg);  
				            };  
				        }.start();
				    } else {
				    	Toast.makeText(context, "��̬��ȡʧ��",
							     Toast.LENGTH_SHORT).show();
				    }
			   } catch (JSONException e) {
				   e.printStackTrace();
			   }
			};
		});	
		
		addshare.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {	
				share1.setVisibility(View.GONE);
				share2.setVisibility(View.VISIBLE);  
				share_returnview.setVisibility(View.VISIBLE);
			}
		});
		
		share_returnview.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				//LogUtil.d("MainActivity", "you click me");
				//LogUtil.d("MainActivity", share1.getVisibility()+"");
				//LogUtil.d("MainActivity", share2.getVisibility()+"");
					share2.setVisibility(View.GONE);
					share1.setVisibility(View.VISIBLE);   
					share_returnview.setVisibility(View.INVISIBLE);
					
					AsyncHttpClient client = new AsyncHttpClient();
				    RequestParams params = new RequestParams();
				    String userid = sp.getString("USERID", "");
				    params.put("userid", userid);
				    client.post(MyURL.getShareURL, params, new JsonHttpResponseHandler(){
					   List<Share>  result = new ArrayList<Share>();
					   public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
						   try { 
							    int returnCode =  (Integer) response.get("returnCode");
							    if (returnCode == 1) {
							    	JSONArray jsonArray = (JSONArray) response.get("data");
								    for(int i=0;i<jsonArray.length();i++){
								    	JSONObject ob = (JSONObject) jsonArray.get(i);
								    	Share s = new Share();
								    	if (!ob.getString("image").equals("null")) {
								    		s.setImgPath(ob.getString("image"));
								    	}
								    	if (!ob.getString("words").equals("null")) {
								    		s.setWords(ob.getString("words"));
								    	}
								    	s.setIcoPath(ob.getString("pic"));
								    	s.setUsername(ob.getString("username"));
								        result.add(s);
								    }
								    
								  //��ȡ���ݣ���UI�߳��ǲ�������ʱ�����ģ������������߳�����  
							      new Thread(){  
							            public void run() {  
							                ShareService service = new ShareService();    
							                //���߳�ͨ��Message�����װ��Ϣ�������ó�ʼ���õģ�  
							                //Handler�����sendMessage()���������ݷ��͵����߳��У��Ӷ��ﵽ����UI���̵߳�Ŀ��  
							                Message msg = new Message();  
							                msg.what = SUCCESS_GET_SHARE;  
							                msg.obj = result;  
							                mHandler.sendMessage(msg);  
							            };  
							        }.start();
							    } else {
							    	Toast.makeText(context, "��̬��ȡʧ��",
										     Toast.LENGTH_SHORT).show();
							    }
						   } catch (JSONException e) {
							   e.printStackTrace();
						   }
						};
					});
			}
		});
		
		sendMoment.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				String words = sharetext.getText().toString().trim();
				AsyncHttpClient client = new AsyncHttpClient();
				RequestParams params = new RequestParams();
				params.put("userid", sp.getString("USERID", ""));
				params.put("words", words);
				
				client.post(MyURL.addShareURL, params, new JsonHttpResponseHandler(){
					public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, org.json.JSONObject response) {
						try {
							int returnCode =  (Integer) response.get("returnCode");
							if(returnCode == 1){
								Toast.makeText(context, "�����ɹ�",
									     Toast.LENGTH_SHORT).show();
								share_returnview.callOnClick();
							}else{
								Toast.makeText(context, "����ʧ��",
									     Toast.LENGTH_SHORT).show();
							}
						} catch (Exception e) {
							Toast.makeText(context, "������������ʧ�ܣ�����ȷ������������",
								     Toast.LENGTH_SHORT).show();
						}
						
					};
				});
				
			}
		});
		
		//shareimg2��share2.xml��������ͼƬ��imageview��id
//		shareimg2.setOnClickListener(new OnClickListener() {		
//			public void onClick(View v) {			
//				Intent intent = new Intent();  
//                /* ����Pictures����Type�趨Ϊimage */  
//                intent.setType("image/*");  
//                /* ʹ��Intent.ACTION_GET_CONTENT���Action */  
//                intent.setAction(Intent.ACTION_GET_CONTENT);   
//                /* ȡ����Ƭ�󷵻ر����� */  
//                getActivity().startActivityForResult(intent, 2);  
//			}
//		});
	}
    
}
