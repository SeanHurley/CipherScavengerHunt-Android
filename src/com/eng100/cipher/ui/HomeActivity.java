package com.eng100.cipher.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.eng100.cipher.Constants;
import com.spiteful.cipher.android.R;


public class HomeActivity extends Activity {
	private static final String tag = HomeActivity.class.toString();
	private Button scanButton;
	private Button viewMessageButton;
	private Button prefsButton;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		boolean firstRun = !prefs.contains(Constants.TEAMID_PREF) || !prefs.contains(Constants.TEAMPIN_PREF);

		if(firstRun) {
			Toast.makeText(HomeActivity.this, "Please enter credentials ", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(HomeActivity.this, CipherPreferencesActivity.class);
			startActivity(intent);
		}

		this.scanButton = (Button) this.findViewById(R.id.btn_scan);
		this.scanButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, ScanActivity.class);
				startActivity(intent);
			}
		});

		this.prefsButton = (Button) this.findViewById(R.id.btn_prefs);
		this.prefsButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, CipherPreferencesActivity.class);
				startActivity(intent);
			}
		});

		this.viewMessageButton = (Button) this.findViewById(R.id.btn_view);
		this.viewMessageButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(HomeActivity.this, ViewMessageActivity.class);
				startActivity(intent);
			}
		});
	}
}
