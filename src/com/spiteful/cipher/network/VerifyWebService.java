package com.spiteful.cipher.network;

import net.minidev.json.JSONObject;
import android.os.AsyncTask;

public class VerifyWebService extends AsyncTask<JSONObject, Void, JSONObject>{
	private WebActionCallback callback;
	
	public VerifyWebService(WebActionCallback callback) {
		this.callback = callback;
	}
	
	@Override
	protected JSONObject doInBackground(JSONObject... params) {
		try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		JSONObject json = new JSONObject();
		json.put("success", true);
		return json;
	}
	
	@Override
	protected void onPostExecute(JSONObject result) {
		callback.onCompleted(result);
	}
}
