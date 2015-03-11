package com.bignerd.criminalintent;

import java.util.ArrayList;
import java.util.UUID;

import android.content.Context;

public class CrimeLab {
	private static CrimeLab sCrimeLab;
	private Context mAppContext;

	private ArrayList<Crime> mCrimes;

	public CrimeLab(Context appContext) {
		mAppContext = appContext;
		mCrimes = new ArrayList<Crime>();
		for (int i = 0; i < 100; i++) {
			Crime tCrime =new Crime();
			tCrime.setTitle("Crime #"+i);
			tCrime.setSloved(i % 2==0);					
			mCrimes.add(tCrime);
		}


	}
	
	
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}

	public Crime getCrime(UUID id) {

		for (Crime c : mCrimes) {
			if (c.getId().equals(id)) {
				return c;
			}

		}
		return null;
	}



	public static CrimeLab get(Context c) {
		if (sCrimeLab == null) {
			sCrimeLab = new CrimeLab(c.getApplicationContext());
		}

		return sCrimeLab;
	}

}
