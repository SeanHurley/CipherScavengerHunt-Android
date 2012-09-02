package com.spiteful.cipher.service;

import net.minidev.json.JSONObject;

import com.spiteful.cipher.Constants;

public class GetMessageService extends BaseWebService {
	private String netid;
	private int teamPin;	
	
	public GetMessageService(WebActionCallback callback, String netid, int teamPin) {
		super(callback);
		this.teamPin = teamPin;
		this.netid = netid;
	}

	@Override
	public String getPath() {
		return Constants.GET_MESSAGE_PATH;
	}

	@Override
	public String getBody() {
		JSONObject json = new JSONObject();
		json.put(Constants.ID_KEY, netid);
		json.put(Constants.PIN_KEY, teamPin);
		
		return json.toJSONString();
	}
}
