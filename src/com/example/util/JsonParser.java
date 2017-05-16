package com.example.util;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.entity.User;
import com.google.gson.Gson;
import com.google.gson.JsonElement;

import android.util.Log;

public class JsonParser {

	public static String handleResponse(String response) {
		String message = null;
		try {
			JSONObject jsonObject = new JSONObject(response.toString());
			String returnCode = jsonObject.getString("returnCode");
			if (returnCode.equals("1")) {
				String datatype = jsonObject.getString("datatype");
				String data = jsonObject.getString("data");
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

	private static void buildUser(String data) {
		Gson gson = new Gson();
		User.setUser(gson.fromJson(data, User.class));
	}
	
}
