package com.my.sadebprovider.act.model.user;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AllUserListResponse{

	@SerializedName("result")
	private List<ResultItem> result;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setResult(List<ResultItem> result){
		this.result = result;
	}

	public List<ResultItem> getResult(){
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
			"AllUserListResponse{" + 
			"result = '" + result + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}