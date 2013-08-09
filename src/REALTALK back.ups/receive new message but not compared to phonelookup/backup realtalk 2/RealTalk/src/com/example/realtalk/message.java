package com.example.realtalk;

public class message {
	private String msgBody;
	private String msgAddress;
	private String msgThreadID;
	private String msgCount;
	private String msgsnippet;

	public message() {
		super();
	}

	public message(String address, String body) {
		super();
		this.msgAddress = address;
		this.msgBody = body;
	}

	public String getBody() {
		return this.msgBody;
	}

	public String getAddress() {

		return this.msgAddress;
	}
	
	public void setBody(String body) {
		this.msgBody = body;
	}

	public void setAddress(String address) {
		this.msgAddress = address;
	}
	public String getThreadID() {

		return this.msgThreadID;
	}
	
	public void setThreadID(String threadID) {
		this.msgThreadID = threadID;
	}
	public String getCountMsg() {
		return this.msgCount;
	}
	
	public void setCountMsg(String count) {
		this.msgCount = count;
	}
	public String getSnippet() {
		return this.msgsnippet;
	}
	
	public void setSnippet(String snippet) {
		this.msgsnippet = snippet;
	}
}
