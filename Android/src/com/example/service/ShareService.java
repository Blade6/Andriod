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
        // 如果图片存在本地缓存目录，则不去服务器下载   
        if (file.exists()) {  
            return Uri.fromFile(file);//Uri.fromFile(path)这个方法能得到文件的URI  
        } else {  
            // 从网络上获取图片  
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
                // 返回一个URI对象  
                return Uri.fromFile(file);  
            }
        }  
        return null;  
    }      
}
