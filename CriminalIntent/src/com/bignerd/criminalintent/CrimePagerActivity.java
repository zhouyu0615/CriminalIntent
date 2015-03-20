package com.bignerd.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;

import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;

public class CrimePagerActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private ArrayList<Crime> mCrimes;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);

		mCrimes = CrimeLab.get(this).getCrimes();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {

			if (NavUtils.getParentActivityName(this) != null) {

				this.getActionBar().setDisplayHomeAsUpEnabled(true);

			}
		}

		android.support.v4.app.FragmentManager frManager = getSupportFragmentManager();

		mViewPager.setAdapter(new FragmentStatePagerAdapter(frManager) {

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mCrimes.size();
			}

			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				Crime crime = mCrimes.get(arg0);
				return CrimeFragment.newInstance(crime.getId());
			}
		});

		UUID crimeId = (UUID) getIntent().getSerializableExtra(
				CrimeFragment.EXTRA_CRIME_ID);
		for (int i = 0; i < mCrimes.size(); i++) {
			if (mCrimes.get(i).getId().equals(crimeId)) {
				mViewPager.setCurrentItem(i);
				setTitle(mCrimes.get(i).getTitle());
				break;

			}

		}

		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub

				Crime crime = mCrimes.get(arg0);
				if (crime.getTitle() != null) {
					setTitle(crime.getTitle());
				}

			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		System.out.println("onOptionsItemSelected!");
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(this) != null) {
				NavUtils.navigateUpFromSameTask(this);
			}
			return true;

		default:
			return super.onOptionsItemSelected(item);

		}

	}

}
