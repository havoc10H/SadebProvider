package com.my.sadebprovider.act.model.service.servicedetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ServiceDetailResponse{

	@SerializedName("result")
	public ResultItem result;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	public void setResult(ResultItem result){
		this.result = result;
	}

	public ResultItem getResult(){
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
			"ServiceDetailResponse{" + 
			"result = '" + result + '\'' + 
			",message = '" + message + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}