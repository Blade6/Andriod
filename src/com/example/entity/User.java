package com.example.entity;

public class User {

	private static User user; 
	private String userid;
	private String username;
	private String pwd;
	private String Q1;
	private String A1;
	private String Q2;
	private String A2;
	private String pic;
	
	private User() {}
	
	public static void initUser(String userid, String username, String pic) {
		user = new User();
		user.setUserId(userid);
		user.setUserName(username);
		user.setPic(pic);
	}
	
	public static User getInstance() {
		if (user == null)
			throw new NullPointerException();
		return user;
	}
	
	public void setUserId(String userid) {
		this.userid = userid;
	}
	
	public String getUserId() {
		return this.userid;
	}
	
	public void setUserName(String username) {
		this.username = username;
	}
	
	public String getUserName() {
		return this.username;
	}
	
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	
	public void setPic(String pic) {
		this.pic = pic;
	}
	
	public String pic() {
		return this.pic;
	}
	
}
