package com.example.mychat;

import java.io.FileNotFoundException;

import com.example.common.AtyContainer;
import com.example.fragment.FrdFragment;
import com.example.fragment.MyFragment;
import com.example.fragment.ShareFragment;
import com.example.fragment.TalkFragment;

import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

	//我们使用的是android v4包下的fragment,这里必须要继承自FragmentActivity，而不是Activity
	public class IndexActivity extends FragmentActivity implements OnClickListener{
		//底部的4个导航控件
		private LinearLayout mTabWeixin;
		private LinearLayout mTabFrd;
		private LinearLayout mTabAddress;
		private LinearLayout mTabSetting;
		//底部4个导航控件中的图片按钮
		private ImageButton mImgWeixin;
		private ImageButton mImgFrd;
		private ImageButton mImgAddress;
		private ImageButton mImgSetting;
		//初始化4个Fragment
		private android.support.v4.app.Fragment tab01;
		private android.support.v4.app.Fragment tab02;
		private android.support.v4.app.Fragment tab03;
		private android.support.v4.app.Fragment tab04;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			setContentView(R.layout.activity_index);
			 AtyContainer.getInstance().addActivity(this);
			initView();//初始化所有的view
			initEvents();
			setSelect(0);//默认显示微信聊天界面
		}

		private void initEvents() {
			mTabWeixin.setOnClickListener(this);
			mTabFrd.setOnClickListener(this);
			mTabAddress.setOnClickListener(this);
			mTabSetting.setOnClickListener(this);
			
		}
		
		@Override
		protected void onDestroy() {
			super.onDestroy();
	        // 结束Activity&从栈中移除该Activity
	        AtyContainer.getInstance().removeActivity(this);
		}
   
	    //初始控件 
		private void initView() {
			mTabWeixin = (LinearLayout)findViewById(R.id.id_tab_weixin);
			mTabFrd = (LinearLayout)findViewById(R.id.id_tab_frd);
			mTabAddress = (LinearLayout)findViewById(R.id.id_tab_address);
			mTabSetting = (LinearLayout)findViewById(R.id.id_tab_setting);
			mImgWeixin = (ImageButton)findViewById(R.id.id_tab_weixin_img);
			mImgFrd = (ImageButton)findViewById(R.id.id_tab_frd_img);
			mImgAddress = (ImageButton)findViewById(R.id.id_tab_address_img);
			mImgSetting = (ImageButton)findViewById(R.id.id_tab_setting_img);
			
		}


		public void onClick(View v) {
			resetImg();
			switch (v.getId()) {
			case R.id.id_tab_weixin://当点击微信按钮时，切换图片为亮色，切换fragment为微信聊天界面
				setSelect(0);
				break;
			case R.id.id_tab_frd:
				setSelect(1);
				break;
			case R.id.id_tab_address:
				setSelect(2);
				break;
			case R.id.id_tab_setting:
				setSelect(3);
				break;

			default:
				break;
			}
			
		}

		/*
		 * 将图片设置为亮色的；切换显示内容的fragment
		 * */
		private void setSelect(int i) {
			FragmentManager fm = getSupportFragmentManager();
			FragmentTransaction transaction = fm.beginTransaction();//创建一个事务
			hideFragment(transaction);//我们先把所有的Fragment隐藏了，然后下面再开始处理具体要显示的Fragment
			switch (i) {
			case 0:
				if (tab01 == null) {
					tab01 = new TalkFragment();
					/*
					 * 将Fragment添加到活动中，public abstract FragmentTransaction add (int containerViewId, Fragment fragment)
					*containerViewId即为Optional identifier of the container this fragment is to be placed in. If 0, it will not be placed in a container.
					 * */
					transaction.add(R.id.id_content, tab01);//将微信聊天界面的Fragment添加到Activity中
				}else {
					transaction.show(tab01);
				}
				mImgWeixin.setImageResource(R.drawable.tab_weixin_pressed);
				break;
			case 1:
				if (tab02 == null) {
					tab02 = new FrdFragment();
					transaction.add(R.id.id_content, tab02);
				}else {
					transaction.show(tab02);
				}
				mImgFrd.setImageResource(R.drawable.tab_find_frd_pressed);
				break;
			case 2:
				if (tab03 == null) {
					tab03 = new ShareFragment();
					transaction.add(R.id.id_content, tab03);
				}else {
					transaction.show(tab03);
				}
				mImgAddress.setImageResource(R.drawable.tab_address_pressed);
				break;
			case 3:
				if (tab04 == null) {
					tab04 = new MyFragment();
					transaction.add(R.id.id_content, tab04);
				}else {
					transaction.show(tab04);
				}
				mImgSetting.setImageResource(R.drawable.tab_settings_pressed);
				break;

			default:
				break;
			}
			transaction.commit();//提交事务
		}

		/*
		 * 隐藏所有的Fragment
		 * */
		private void hideFragment(FragmentTransaction transaction) {
			if (tab01 != null) {
				transaction.hide(tab01);
			}
			if (tab02 != null) {
				transaction.hide(tab02);
			}
			if (tab03 != null) {
				transaction.hide(tab03);
			}
			if (tab04 != null) {
				transaction.hide(tab04);
			}
			
		}

		private void resetImg() {
			mImgWeixin.setImageResource(R.drawable.tab_weixin_normal);
			mImgFrd.setImageResource(R.drawable.tab_find_frd_normal);
			mImgAddress.setImageResource(R.drawable.tab_address_normal);
			mImgSetting.setImageResource(R.drawable.tab_settings_normal);
		}
		
		//返回键监听，退出程序确认
		  @Override  
		    public boolean onKeyDown(int keyCode, KeyEvent event)  
		    {  
		        if (keyCode == KeyEvent.KEYCODE_BACK )  
		        {  
		            // 创建退出对话框  
		            AlertDialog isExit = new AlertDialog.Builder(this).create();  
		            // 设置对话框标题  
		            isExit.setTitle("系统提示");  
		            // 设置对话框消息  
		            isExit.setMessage("确定要退出吗");  
		            // 添加选择按钮并注册监听  
		            isExit.setButton("确定", listener);  
		            isExit.setButton2("取消", listener);  
		            // 显示对话框  
		            isExit.show();  
		  
		        }  
		          
		        return false;  
		          
		    }  
		  //退出程序对话框的监听事件
		  DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()  
		    {  
		        public void onClick(DialogInterface dialog, int which)  
		        {  
		            switch (which)  
		            {  
		            case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序  
		            	AtyContainer.getInstance().finishAllActivity();
		                break;  
		            case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框  
		                break;  
		            default:  
		                break;  
		            }  
		        }  
		    }; 
		    
		//选择照片回调函数
		@Override  
	    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode == RESULT_OK) {  
	        	Log.e("uri", requestCode+""); 
	        	if(requestCode == 2){
	        		Uri uri = data.getData();  
		            Log.e("uri", uri.toString());  
		            ContentResolver cr = this.getContentResolver();  
		            try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));  
		                ImageView imageView = (ImageView) tab03.getView().findViewById(R.id.shareimg);
		                imageView.setImageBitmap(bitmap);  
		            } catch (FileNotFoundException e) {  
		                Log.e("Exception", e.getMessage(),e);  
		            }  
	        	}
	        	if(requestCode==1){
	        		Uri uri = data.getData();   
		            ContentResolver cr = this.getContentResolver();  
		            try {  
		                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri)); 
		               
		                ImageView imageView = (ImageView) tab04.getView().findViewById(R.id.ico);  
		                imageView.setImageBitmap(bitmap);  
		            } catch (FileNotFoundException e) {  
		                Log.e("Exception", e.getMessage(),e);  
		            }  
	        	}
	            
	        }
	    }  
	}
