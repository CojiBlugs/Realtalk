package com.example.realtalk;

public class message {
	private String msgBody;
	private String msgAddress;
	private String msgThreadID;
	private String msgCount;
	private String msgsnippet;
	private String msgDate;
	private String msg_Id;
	private String msgType;

	public message() {
		super();
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
	public String getMsgDate() {
		return this.msgDate;
	}
	
	public void setMsgDate(String DATE) {
		this.msgDate = DATE;
	}
	public String get_id() {
		return this.msg_Id;
	}
	
	public void set_id(String ID) {
		this.msg_Id = ID;
	}
	public String getType() {
		return this.msgType;
	}
	
	public void setType(String type) {
		this.msgType = type;
	}
	public String getSnippet() {
		return this.msgsnippet;
	}
	
	public void setSnippet(String snippet) {
		this.msgsnippet = snippet;
	}
}
