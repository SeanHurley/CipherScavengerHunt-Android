package com.spiteful.cipher.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.spiteful.cipher.android.R;

public class HomeActivity extends Activity implements OnClickListener {
	private static final String tag = HomeActivity.class.toString();
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
			Intent intent = new Intent(this, ScanActivity.class);
			startActivity(intent);
		}
	}

}
