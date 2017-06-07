package com.example.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.common.LogUtil;
import com.example.common.MyURL;
import com.example.entity.Share;
import com.example.mychat.R;
import com.example.service.ShareService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;

public class ShareAdapter extends BaseAdapter {
	private Context context;
	private List<Share> data;
	private File cache;
    private LayoutInflater mInflater;
	
	public ShareAdapter(Context context,List<Share> data, File cache){
		this.context = context;
		this.data = data;
		this.cache = cache;

	    mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}
	
	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return data.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	 public View getView(int position, View convertView, ViewGroup parent) {
		 Share info = data.get(position);
		 ViewHolder holder = null;
	     if (convertView != null) {
	    	 holder = (ViewHolder) convertView.getTag();
	     } else {
	    	 holder = new ViewHolder();
	         convertView = View.inflate(context,
	        		 R.layout.shareitem, null);
	         holder.ico = (ImageView)convertView.findViewById(R.id.username_ico);
	         holder.username = (TextView)convertView.findViewById(R.id.username);
	         holder.words = (TextView)convertView.findViewById(R.id.title);
	         holder.shareimg = (ImageView)convertView.findViewById(R.id.shareimg);
	         if(info.getImgPath() == null){
	            	 holder.shareimg.setVisibility(View.GONE);
	         }
	       
	        convertView.setTag(holder);
	     } 
        
        holder.username.setText(info.getUsername());
        holder.words.setText(info.getWords());
        if (!info.getIcoPath().equals("/wechat/Public/Users/default.png")) {
        	asyncloadImage(holder.ico, MyURL.Address + info.getIcoPath(), "user");
        } else {
        	holder.ico.setImageResource(R.drawable.default_user_ico);
        }
        if (info.getImgPath() != null) {
        	asyncloadImage(holder.shareimg, MyURL.Address + info.getImgPath(), "moment");
        }
        return convertView;
	}
	 
	private void asyncloadImage(ImageView pic, String path, String type) {
	    ShareService service = new ShareService();
	    AsyncImageTask task = new AsyncImageTask(service, pic, type);
	    task.execute(path);
	}
	
	private final class AsyncImageTask extends AsyncTask<String, Integer, Uri> {

        private ShareService service;
        private ImageView pic;
        private String type;

        public AsyncImageTask(ShareService service, ImageView pic, String type) {
            this.service = service;
            this.pic = pic;
            this.type = type;
        }

        // 后台运行的子线程子线程
        @Override
        protected Uri doInBackground(String... params) {
            try {
                return service.getImageURI(params[0], cache, type);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        // 这个放在在ui线程中执行
        @Override
        protected void onPostExecute(Uri result) {
            super.onPostExecute(result); 
            // 完成图片的绑定
            if (pic != null && result != null) {
            	pic.setImageURI(result);
            }
        }
    }
           
	public class ViewHolder {
		ImageView ico;
	    TextView username;
	    TextView words;
	    ImageView shareimg;
	}
}