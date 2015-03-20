package com.bignerd.criminalintent;

import android.app.Fragment;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class CrimeCameraActivity extends SingleFragmentActivity {

	@Override
	protected android.support.v4.app.Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeCameraFragment();
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		
		super.onCreate(savedInstanceState);
	}

}  
