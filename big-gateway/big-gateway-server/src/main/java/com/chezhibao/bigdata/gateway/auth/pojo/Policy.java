package com.chezhibao.bigdata.gateway.auth.pojo;

public class Policy {
	/**想要访问资源的用户 或者角色*/
	private String sub;
	
	/**将要访问的资源，可以使用  * 作为通配符，例如/user/* */
	private String obj;
	
	/**用户对资源执行的操作。HTTP方法，GET、POST、PUT、DELETE等，可以使用 * 作为通配符*/
	private String act;
 
	public Policy() {
		super();
	}
	
	/**
	 * 
	 * @param sub 想要访问资源的用户 或者角色
	 * @param obj 将要访问的资源，可以使用  * 作为通配符，例如/user/*
	 * @param act 用户对资源执行的操作。HTTP方法，GET、POST、PUT、DELETE等，可以使用 * 作为通配符
	 */
	public Policy(String sub, String obj, String act) {
		super();
		this.sub = sub;
		this.obj = obj;
		this.act = act;
	}
 
	public String getSub() {
		return sub;
	}
 
	public void setSub(String sub) {
		this.sub = sub;
	}
 
	public String getObj() {
		return obj;
	}
 
	public void setObj(String obj) {
		this.obj = obj;
	}
 
	public String getAct() {
		return act;
	}
 
	public void setAct(String act) {
		this.act = act;
	}
 
	@Override
	public String toString() {
		return "Policy [sub=" + sub + ", obj=" + obj + ", act=" + act + "]";
	}
	
}