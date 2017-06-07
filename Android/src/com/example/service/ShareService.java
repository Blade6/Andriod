package com.example.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.example.common.LogUtil;

import android.net.Uri;

public class ShareService {
	public Uri getImageURI(String path, File cache, String type) throws Exception {  
        String name = type + "-" + path.substring(path.lastIndexOf("/")+1);
        File file = new File(cache, name);   
        // ���ͼƬ���ڱ��ػ���Ŀ¼����ȥ����������   
        if (file.exists()) {  
            return Uri.fromFile(file);//Uri.fromFile(path)��������ܵõ��ļ���URI  
        } else {  
            // �������ϻ�ȡͼƬ  
            URL url = new URL(path);  
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();  
            conn.setConnectTimeout(5000);  
            conn.setRequestMethod("GET");  
            conn.setDoInput(true); 
            if (conn.getResponseCode() == 200) {
                InputStream is = conn.getInputStream();
                FileOutputStream fos = new FileOutputStream(file);  
                byte[] buffer = new byte[1024];  
                int len = 0; 
                while ((len = is.read(buffer)) != -1) {  
                    fos.write(buffer, 0, len);  
                } 
                is.close();  
                fos.close();  
                // ����һ��URI����  
                return Uri.fromFile(file);  
            }
        }  
        return null;  
    }      
}
