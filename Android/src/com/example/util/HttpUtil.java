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
					// ע�⣺ip���̶���ÿ������ǰ���ܶ�Ҫ�Ȳ�������ip
					// ���Դ����apk����������������
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
						// �ص�onFinish()����
						listener.onFinish(response.toString());
					}
					
				} catch (Exception e) {
					if (listener != null) {
						// �ص�onError()����
						// ע�⣬�����и��ǳ�����Ū��ĵط�
						// ���ص���onFinish()����ִ��ʱ����������г����ˣ�Ҳ�ǻ�ص�onError()�����ġ�
						// onFinish()��onError()����ƽ����ϵ�����߸�һ��
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
