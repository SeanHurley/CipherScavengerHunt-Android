package com.spiteful.cipher.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.spiteful.cipher.android.R;

public class CipherPreferencesActivity extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {     
	    super.onCreate(savedInstanceState);        
	    addPreferencesFromResource(R.xml.preferences);        
	}

}