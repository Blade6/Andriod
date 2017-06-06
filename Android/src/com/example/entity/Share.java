package com.example.entity;

public class Share {
	private String ico_path;// 头像
	private String username;//用户名
	private String words;//文字
	private String img_path;//图片
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getWords() {
		return words;
	}
	public void setWords(String words) {
		this.words = words;
	}
	public String getIcoPath() {
		return ico_path;
	}
	public void setIcoPath(String ico_path) {
		this.ico_path = ico_path;
	}
	public void setImgPath(String img_path) {
		this.img_path = img_path;
	}
	public String getImgPath() {
		return img_path;
	}
	
}
