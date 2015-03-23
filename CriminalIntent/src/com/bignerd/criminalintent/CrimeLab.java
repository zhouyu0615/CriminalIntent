package com.bignerd.criminalintent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;

import com.bignerd.criminalintent.R.string;

import android.content.Context;
import android.util.Log;

public class CrimeLab {

	private static final String TAG = "CrimeLab";
	private static final String FILENAME = "crimes.json";
	private CriminalIntentJSONSerializer mJsonSerializer;

	private static CrimeLab sCrimeLab;
	private Context mAppContext;

	private ArrayList<Crime> mCrimes;

	public CrimeLab(Context appContext) {
		mAppContext = appContext;
		
	
		
		mJsonSerializer = new CriminalIntentJSONSerializer(mAppContext,
				FILENAME);
		try {
			mCrimes = mJsonSerializer.loadCrimes();
			Log.d(TAG, "loadCrimes success!");
		} catch (Exception e) {
			Log.d(TAG, "loadCrimes failed!");
			mCrimes = new ArrayList<Crime>();
			// TODO: handle exception
		}

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

	public boolean saveCrimes() {
		try {
			mJsonSerializer.saveCrimes(mCrimes);
			Log.d(TAG, "Crimes save to file");
			return true;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.e(TAG, "Error saving crimes");
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}
	public void deleteCrime(Crime crime) {

		mCrimes.remove(crime);
	}

}
