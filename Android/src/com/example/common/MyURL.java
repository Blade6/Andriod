package com.example.common;

public class MyURL {
	public static String IP = "192.168.201.80";
	public static String Address = "http://" + IP + ":8080";
	public static String URLHead = Address + "/wechat/index.php/Home/User/";
	
	public static final String LoginURL = URLHead + "login/";
	public static final String RegisterURL = URLHead + "register/";
	public static final String FriendsURL = URLHead + "getFriends/";
	public static final String FindfriendURL = URLHead + "search/";
	public static final String addfriendURL = URLHead + "addFriend/";
	public static final String getShareURL = URLHead + "getShare/";
	public static final String addShareURL = URLHead + "share/";
	public static final String LogoutURL = URLHead + "logout/";
	public static final String talkServerIP = IP;
	public static final int talkServerPort = 8089;
}
