package com.spiteful.cipher.ui;

import net.minidev.json.JSONObject;
import net.minidev.json.JSONValue;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.spiteful.cipher.Constants;
import com.spiteful.cipher.Decoder;
import com.spiteful.cipher.android.R;
import com.spiteful.cipher.model.Message;
import com.spiteful.cipher.service.VerifyService;
import com.spiteful.cipher.service.WebActionCallback;

public class ScanActivity extends Activity implements WebActionCallback {
	private static final String tag = ScanActivity.class.toString();
	private Message message = new Message();
	private boolean success;
	private String reason;
	private ProgressDialog progressBar;
	private TextView levelText;
	private TextView encodedText;
	private TextView labelParity;
	private TextView parityText;
	private TextView decodedText;
	private TextView successText;
	private TextView reasonText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scanning);
		
		getUiElements();
		setupUi();
		
		IntentIntegrator integrator = new IntentIntegrator(this);
		integrator.initiateScan();
	}

	private void getUiElements() {
		this.levelText = (TextView) this.findViewById(R.id.tv_level);
		this.encodedText = (TextView) this.findViewById(R.id.tv_encoded);
		this.labelParity = (TextView) this.findViewById(R.id.label_parity);
		this.parityText = (TextView) this.findViewById(R.id.tv_parity);
		this.decodedText = (TextView) this.findViewById(R.id.tv_decoded);
		this.successText = (TextView) this.findViewById(R.id.tv_success);
		this.reasonText = (TextView) this.findViewById(R.id.tv_reason);
	}

	private void setupUi() {
		this.levelText.setText(message.getLevel() + "");
		this.encodedText.setText(message.getEncoded());
		this.decodedText.setText(message.getDecoded());
		if(message.getLevel() == 3) {
			this.parityText.setText(message.getParity());
			this.labelParity.setVisibility(View.VISIBLE);
			this.parityText.setVisibility(View.VISIBLE);
		} else {
			this.labelParity.setVisibility(View.GONE);
			this.parityText.setVisibility(View.GONE);
		}
		this.successText.setText(success ? "Yes!" : "NO...");
		if(success) {
			this.reasonText.setVisibility(View.GONE);
		} else {
			this.reasonText.setVisibility(View.VISIBLE);
			this.reasonText.setText(reason);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null && scanResult.getContents() != null) {
			Log.d(tag, scanResult.getContents());

			Object object = JSONValue.parse(scanResult.getContents());
			if (object instanceof JSONObject) {
				handleMessage((JSONObject) object);
			} else {
				// TODO Tell the user
				Toast.makeText(this, "Invalid QR Code", Toast.LENGTH_LONG).show();
				finish();
			}
		} else {
			finish();
		}
	}

	private void handleMessage(JSONObject json) {
		message = new Message();
		
		int level = (Integer)json.get(Constants.LEVEL_KEY);
		String data = (String) json.get(Constants.ENCODED_KEY);
		String decoded = "";
		message.setLevel(level);
		message.setEncoded(data);

		getUiElements();
		setupUi();

		switch (message.getLevel()) {
		case 1:
			decoded = Decoder.decodeLevel1(message.getEncoded());
			break;
		case 2:
			decoded = Decoder.decodeLevel2(message.getEncoded());
			break;
		case 3:
			String parity = (String) json.get(Constants.PARITY_KEY);
			message.setParity(parity);
			decoded = Decoder.decodeLevel3(message.getEncoded(), message.getParity());
			break;

		default:
			break;
		}
		
		message.setDecoded(decoded);
		
		
		if(json.containsKey(Constants.TEST_KEY)) {
			handleTestMessage(json);
			return;
		} else {
			int id = (Integer) json.get(Constants.ID_KEY);
			message.setId(id);
		}
		
		contactServer(message);
	}
	
	private void handleTestMessage(JSONObject json) {
		String solution = (String) json.get(Constants.SOLUTION_KEY);
		success = solution.equals(message.getDecoded());
			
		if(success) {
			Toast.makeText(this, "Congrats!", Toast.LENGTH_LONG).show();
		} else {
			reason = (String) json.get(Constants.REASON_KEY);
			Toast.makeText(this, "Failure.", Toast.LENGTH_LONG).show();
		}
		setupUi();
	}

	private void contactServer(Message message) {
		startSpinner();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String netid = prefs.getString(Constants.TEAMID_PREF, "0");
		int pin = Integer.parseInt(prefs.getString(Constants.TEAMPIN_PREF, "0"));

		VerifyService service = new VerifyService(this, message, netid, pin);
		service.performAction();
	}

	@Override
	public void onCompleted(JSONObject json) {
		stopSpinner();
		
		success = (Boolean) json.get(Constants.RESULT_KEY);
		if(success) {
			Toast.makeText(this, "Congrats!", Toast.LENGTH_LONG).show();
		} else {
			reason = (String) json.get(Constants.REASON_KEY);
			Toast.makeText(this, "Failure.", Toast.LENGTH_LONG).show();
		}
		setupUi();
	}
	
	
	private void startSpinner() {
		progressBar = new ProgressDialog(this);
		progressBar.setCancelable(false);
		progressBar.setMessage("Loading ...");
		progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressBar.show();
	}

	private void stopSpinner() {
		progressBar.cancel();
	}
}
