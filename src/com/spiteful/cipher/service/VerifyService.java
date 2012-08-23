package com.spiteful.cipher.service;

import net.minidev.json.JSONObject;

import com.spiteful.cipher.Constants;
import com.spiteful.cipher.model.Message;

public class VerifyService extends BaseWebService {
	private Message message;
	private int teamId;
	private int teamPin;	
	
	public VerifyService(WebActionCallback callback, Message message, int teamId, int teamPin) {
		super(callback);
		this.message = message;
		this.teamPin = teamPin;
		this.teamId = teamId;
	}

	@Override
	public String getPath() {
		return Constants.VERIFY_PATH;
	}

	@Override
	public String getBody() {
		JSONObject json = new JSONObject();
		json.put(Constants.DECODED_KEY, message.getDecoded());
		json.put(Constants.MESSAGEID_KEY, message.getId());
		json.put(Constants.TEAMID_KEY, teamId);
		json.put(Constants.PIN_KEY, teamPin);
		
		return json.toJSONString();
	}
}
