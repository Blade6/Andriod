package com.example.entity;

public class Msg {
	public static final int TYPE_RECEIVED = 0;
	
	public static final int TYPE_SENT = 1;
	
	private String fromWhom;
	
	private String content;
	
	private int type;
	
	public Msg(String fromWhom, String content, int type){
		this.fromWhom = fromWhom;
		this.content = content;
		this.type = type;
	}
	
	public String getSender() {
		return fromWhom;
	}
	
	public String getContent(){
		return content;
	}
	
	public int getType(){
		return type;
	}
}
