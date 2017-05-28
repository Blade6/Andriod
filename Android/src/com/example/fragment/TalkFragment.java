package com.example.fragment;

import com.example.mychat.R;
import com.example.mychat.TalkActivity;
import com.example.mychat.R.id;
import com.example.mychat.R.layout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class TalkFragment extends Fragment {
	private Context context;
	private Button enterRoom;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//引入我们的布局
		return inflater.inflate(R.layout.tab01, container, false);
	}
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		context = this.getActivity().getApplicationContext();
		enterRoom = (Button) view.findViewById(R.id.enterRoom);
		enterRoom.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), TalkActivity.class);
				getActivity().startActivity(intent);
				getActivity().finish();
			}
		});
	}
}
