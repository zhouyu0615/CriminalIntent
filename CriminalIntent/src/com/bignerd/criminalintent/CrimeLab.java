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

	}
	
	public ArrayList<Crime> getCrimes() {
		return mCrimes;
	}

	public void addCrime(Crime c) {
		mCrimes.add(c);

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
