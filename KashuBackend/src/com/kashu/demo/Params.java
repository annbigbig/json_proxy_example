package com.kashu.demo;

public class Params {
	private String userid = null;
	private String passwd = null;
	private String messageid = null;
	
	//Constructor #0
	public Params() {
		userid = "";
		passwd = "";
		messageid = "";
	}
	
	//Constructor #3
	public Params(String userid, String passwd, String messageid) {
		this.userid = userid;
		this.passwd = passwd;
		this.messageid = messageid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getMessageid() {
		return messageid;
	}

	public void setMessageid(String messageid) {
		this.messageid = messageid;
	}
	
	public String toString(){
		String str = "##################\n";
			str += "userid = " + userid + "\n";
			str += "passwd = " + passwd + "\n";
			str += "messageid = " + messageid + "\n";
		return str;
	}
}
