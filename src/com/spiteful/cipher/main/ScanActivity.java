package com.spiteful.cipher.main;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.spiteful.cipher.android.R;
import com.spiteful.cipher.model.Message;
import com.spiteful.cipher.network.VerifyWebService;
import com.spiteful.cipher.network.WebActionCallback;
import com.spiteful.cipher.util.Constants;
import com.spiteful.cipher.util.Decoder;

public class ScanActivity extends Activity implements WebActionCallback {
	private static final String tag = ScanActivity.class.toString();
	private View spinner;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanning);
		
		this.spinner = this.findViewById(R.id.spinner);
		
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
		Message message = new Message();
		int level = (Integer)json.get(Constants.LEVEL_KEY);
		int id = (Integer) json.get(Constants.ID_KEY);
		String data = (String) json.get(Constants.ENCODED_KEY);
		String decoded = "";
		message.setId(id);
		message.setLevel(level);
		message.setEncoded(data);

		switch (message.getLevel()) {
		case 1:
			decoded = Decoder.decodeLevel1(message.getEncoded());
			break;
		case 2:
			decoded = Decoder.decodeLevel2(message.getEncoded());
			break;
		case 3:
			decoded = Decoder.decodeLevel3(message.getEncoded());
			break;

		default:
			break;
		}
		message.setDecoded(decoded);
		contactServer(message);
	}

	private void contactServer(Message message) {
		startSpinner();
		VerifyWebService service = new VerifyWebService(this);
		service.execute(new Message[] {message});
	}

	@Override
	public void onCompleted(JSONObject json) {
		stopSpinner();
		
		boolean success = (Boolean) json.get(Constants.RESULT_KEY);
		if(success) {
			Toast.makeText(this, "Congrats bro!", Toast.LENGTH_LONG).show();
		} else {
			Toast.makeText(this, "Dude, you suck", Toast.LENGTH_LONG).show();
		}
	}
	
	
	private void startSpinner() {
		this.spinner.setVisibility(View.VISIBLE);
	}
	
	private void stopSpinner() {
		this.spinner.setVisibility(View.GONE);
	}
}
