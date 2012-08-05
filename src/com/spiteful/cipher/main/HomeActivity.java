package com.spiteful.cipher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.spiteful.cipher.android.R;
import com.spiteful.cipher.android.R.id;
import com.spiteful.cipher.android.R.layout;
import com.spiteful.cipher.android.R.menu;

public class HomeActivity extends Activity implements OnClickListener {
	private static final String tag = "HomeActivity";
	private Button scanButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		this.scanButton = (Button) this.findViewById(R.id.btn_scan);
		this.scanButton.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		if (v == scanButton) {
			IntentIntegrator integrator = new IntentIntegrator(this);
			integrator.initiateScan();
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		IntentResult scanResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
		if (scanResult != null) {
			Log.d(tag, scanResult.getContents());
		}
	}
}
