package org.thothlab.devilsvault.db.model;

public class Authorization {
	private int auth_id;
	private int internal_userID;
	private int external_userID;
	private String auth_Type;
	
	public int getAuth_id() {
		return auth_id;
	}
	public void setAuth_id(int auth_id) {
		this.auth_id = auth_id;
	}
	public int getInternal_userID() {
		return internal_userID;
	}
	public void setInternal_userID(int internal_userID) {
		this.internal_userID = internal_userID;
	}
	public int getExternal_userID() {
		return external_userID;
	}
	public void setExternal_userID(int external_userID) {
		this.external_userID = external_userID;
	}
	public String getAuth_Type() {
		return auth_Type;
	}
	public void setAuth_Type(String auth_Type) {
		this.auth_Type = auth_Type;
	}
	
	
}