package com.example.testgithub.model;


import com.google.gson.annotations.SerializedName;


public class Permissions{

	@SerializedName("pull")
	private boolean pull;

	@SerializedName("admin")
	private boolean admin;

	@SerializedName("push")
	private boolean push;

	public boolean isPull(){
		return pull;
	}

	public boolean isAdmin(){
		return admin;
	}

	public boolean isPush(){
		return push;
	}

	@Override
 	public String toString(){
		return 
			"Permissions{" + 
			"pull = '" + pull + '\'' + 
			",admin = '" + admin + '\'' + 
			",push = '" + push + '\'' + 
			"}";
		}
}