package com.bignerd.criminalintent;

import java.util.UUID;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CrimeActivity extends SingleFragmentActivity {

	@Override
	protected android.support.v4.app.Fragment createFragment() {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	protected Fragment createFragment() {
//		// TODO Auto-generated method stub
//		UUID crimeId = (UUID) getIntent().getSerializableExtra(
//				CrimeFragment.EXTRA_CRIME_ID);
//
//		return CrimeFragment.newInstance(crimeId);
//	}
}
