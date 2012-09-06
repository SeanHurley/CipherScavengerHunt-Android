package com.eng100.cipher.service;

import net.minidev.json.JSONObject;

import com.eng100.cipher.Constants;
import com.eng100.cipher.model.Message;

public class VerifyService extends BaseWebService {
	private Message message;
	private String netid;
	private int teamPin;	
	
	public VerifyService(WebActionCallback callback, Message message, String netid, int teamPin) {
		super(callback);
		this.message = message;
		this.teamPin = teamPin;
		this.netid = netid;
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
		json.put(Constants.TEAMID_KEY, netid);
		json.put(Constants.PIN_KEY, teamPin);
		
		return json.toJSONString();
	}
}
