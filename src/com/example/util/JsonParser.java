package com.example.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.User;

import android.util.Log;

public class JsonParser {

	public static String handleResponse(String response) {
		String message = null;
		try {
			JSONObject jsonObject = new JSONObject(response.toString());
			String returnCode = jsonObject.getString("returnCode");
			if (returnCode.equals("1")) {
				String datatype = jsonObject.getString("datatype");
				JSONObject data = jsonObject.getJSONObject("data");
				if (datatype.equals("userInfo")) buildUser(data);
				message = "success";
			}
			else {
				message = "failure";
			}
		} catch (JSONException e) {
			message = "exception";
		} finally {
			return message;
		}
	}
	
	public static void buildUser(JSONObject data) throws JSONException {
		String userid = data.getString("userid");
		String username = data.getString("username");
		String pic = data.getString("pic");
		
		User.initUser(userid, username, pic);
		User user = User.getInstance();
		user.setUserId(userid);
		user.setUserName(username);
		user.setPic(pic);
	}
	
}
