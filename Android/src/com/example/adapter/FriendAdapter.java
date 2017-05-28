package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.adapter.ShareAdapter.ViewHolder;
import com.example.entity.Friend;
import com.example.mychat.R;

public class FriendAdapter extends BaseAdapter{

	private Context context;
	private ArrayList<Friend> data;
	public FriendAdapter(Context context,ArrayList<Friend> data){
		this.context = context;
		this.data = data;
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return data.get(arg0);
	}

	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	public View getView(int position, View convertView, ViewGroup arg2) {
		Friend f = data.get(position);
		ViewHolder holder = null;
		if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(context,
                    R.layout.frienditem, null);
            holder.img = (ImageView)convertView.findViewById(R.id.img);
            holder.name = (TextView)convertView.findViewById(R.id.name);
     
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        
        holder.img.setImageResource(f.getImg());
        holder.name.setText(f.getName());
        return convertView;
	}
	class ViewHolder{
		ImageView img;
		TextView name;
	}

}
