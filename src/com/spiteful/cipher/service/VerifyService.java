package com.spiteful.cipher.service;

import net.minidev.json.JSONObject;

import com.spiteful.cipher.Constants;
import com.spiteful.cipher.model.Message;

public class VerifyService extends BaseWebService {
	private Message message;	
	
	public VerifyService(WebActionCallback callback, Message message) {
		super(callback);
		this.message = message;
	}

	@Override
	public String getPath() {
		return Constants.VERIFY_PATH;
	}

	@Override
	public String getBody() {
		JSONObject json = new JSONObject();
		json.put(Constants.DECODED_KEY, message.getDecoded());
		json.put(Constants.ID_KEY, message.getId());
		
		return json.toJSONString();
	}
}
