package com.spiteful.cipher.main;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.spiteful.cipher.util.Constants;
import com.spiteful.cipher.util.Decoder;

public class ScanActivity extends Activity {
	private static final String tag = ScanActivity.class.toString();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			Log.d(tag, scanResult.getContents());

			Object object = JSONValue.parse(scanResult.getContents());
			if (object instanceof JSONObject) {
				handleMessage((JSONObject) object);
			} else {
				// TODO Tell the user
			}
		}
	}

	private void handleMessage(JSONObject json) {
		int level = Integer.parseInt((String) json.get(Constants.LEVEL_KEY));
		String data = (String) json.get(Constants.DATA_KEY);
		String decoded = "";

		switch (level) {
		case 1:
			decoded = Decoder.decodeLevel1(data);
			break;
		case 2:
			decoded = Decoder.decodeLevel2(data);
			break;
		case 3:
			decoded = Decoder.decodeLevel3(data);
			break;

		default:
			break;
		}

		contactServer(null, decoded);
	}

	private void contactServer(String id, String decoded) {

	}
}
