package com.bignerd.criminalintent;

import android.app.Fragment;

public class CrimeListActivity extends SingleFragmentActivity {

	@Override
	protected android.support.v4.app.Fragment createFragment() {
		// TODO Auto-generated method stub
		return new CrimeListFragment();
	}

}
