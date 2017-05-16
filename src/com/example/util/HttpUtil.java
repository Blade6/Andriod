package com.example.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.util.Log;

public class HttpUtil {

	public static void sendHttpRequest(final String address, final String method, 
			final HttpCallbackListener listener) {
		new Thread(new Runnable() {			
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					// 注意：ip不固定，每次运行前可能都要先查查服务器ip
					// 所以打包成apk反而不能正常运行
					//String ip = "192.168.22.2:8080";
					String ip = "192.168.201.80:8080";
					String realAddress = "http://" + ip + "/" + address;
					URL url = new URL(realAddress);
					connection = (HttpURLConnection) url.openConnection();
					connection.setRequestMethod(method);
					connection.setConnectTimeout(8000);
					connection.setReadTimeout(8000);
					connection.setDoInput(true);
					
					LogUtil.d("MainActivity", "connection ok");
					
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					
					LogUtil.d("MainActivity", "InputStream ok");
					
					if (listener != null) {
						// 回调onFinish()方法
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					if (listener != null) {
						// 回调onError()方法
						// 注意，这里有个非常容易弄错的地方
						// 当回调了onFinish()方法执行时，如果运行中出错了，也是会回调onError()方法的。
						// onFinish()和onError()不是平级关系，后者高一级
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
		}).start();
	}
	
}
