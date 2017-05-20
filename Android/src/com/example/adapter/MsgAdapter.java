package com.example.adapter;

import java.util.List;

import com.example.entity.Msg;
import com.example.wechat.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<Msg> {
	
	private int resourceId;
	
	public MsgAdapter(Context context, int textViewResourceId, List<Msg> objects) {
		super(context, textViewResourceId, objects);
		resourceId = textViewResourceId;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Msg msg = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) {
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.leftLayout = (LinearLayout) view.findViewById(R.id.left_layout);
			viewHolder.rightLayout = (LinearLayout) view.findViewById(R.id.right_layout);
			viewHolder.leftUser = (TextView) view.findViewById(R.id.there);
			viewHolder.rightUser = (TextView) view.findViewById(R.id.here);
			viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		if (msg.getType() == Msg.TYPE_RECEIVED) {
			//如果是收到的消息，则显示左边的消息布局，将右边的消息布局隐藏
			viewHolder.leftLayout.setVisibility(View.VISIBLE);
			viewHolder.rightLayout.setVisibility(View.GONE);
			viewHolder.leftUser.setText(msg.getSender());
			viewHolder.leftMsg.setText(msg.getContent());
		} else {
			viewHolder.rightLayout.setVisibility(View.VISIBLE);
			viewHolder.leftLayout.setVisibility(View.GONE);
			viewHolder.rightUser.setText(msg.getSender());
			viewHolder.rightMsg.setText(msg.getContent());
		}
		return view;
	}
	
	class ViewHolder {
		
		LinearLayout leftLayout;
		
		LinearLayout rightLayout;
		
		TextView leftUser;
		
		TextView rightUser;
		
		TextView leftMsg;
		
		TextView rightMsg;
		
	}
	
}
