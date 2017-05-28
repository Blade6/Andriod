package com.example.entity;

public class User {

	private static User user; 
	
	private String userid;
	private String username;
	private String pwd;
	private String pic;
	
	public static void setUser(User user1) {
		user = user1;
	}
	
	// 除了登录外，其他请求User的地方都应该调用getUser()方法
	public static User getUser() {
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
