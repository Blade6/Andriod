package com.example.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.example.entity.Share;
import com.example.mychat.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	private ArrayList<Share> data;
	public ShareAdapter(Context context,ArrayList<Share> data){
		   this.context = context;
		   this.data = data;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	 public View getView(int position, View convertView, ViewGroup arg2) {
		Share info = data.get(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context,
                    R.layout.shareitem, null);
            holder.ico = (ImageView)convertView.findViewById(R.id.username_ico);
            holder.username = (TextView)convertView.findViewById(R.id.username);
            holder.title = (TextView)convertView.findViewById(R.id.title);
            holder.shareimg = (ImageView)convertView.findViewById(R.id.shareimg);
            if(info.getImg() == 0){
            	holder.shareimg.setVisibility(View.GONE);
            }
       
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.username.setText(info.getUsername());
        holder.title.setText(info.getTitle());
        holder.ico.setImageResource(info.getIco());
        if(!(info.getImg() ==0)){
        	holder.shareimg.setImageResource(info.getImg());
        }
        return convertView;
	}
           
	 public class ViewHolder {
		 	ImageView ico;
	        TextView username;
	        TextView title;
	        ImageView shareimg;
	    }
}