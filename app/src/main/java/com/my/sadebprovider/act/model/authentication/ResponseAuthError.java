package com.my.sadebprovider.act.model.authentication;

import com.google.gson.annotations.SerializedName;

public class ResponseAuthError {

	@SerializedName("result")
	private String result;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setResult(String result){
		this.result = result;
	}

	public String getResult(){
		return result;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"ResponseAuthError{" + 
			"result = '" + result + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}