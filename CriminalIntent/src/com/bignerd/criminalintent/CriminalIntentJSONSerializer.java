package com.bignerd.criminalintent;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import android.content.Context;

public class CriminalIntentJSONSerializer {
	private Context mContext;
	private String mFilename;

	public CriminalIntentJSONSerializer(Context context, String string) {
		mContext = context;
		mFilename = string;
	}

	public void saveCrimes(ArrayList<Crime> crimes) throws JSONException,
			IOException {

		JSONArray array = new JSONArray();
		for (Crime crime : crimes) {
			array.put(crime.toJSON());

		}
		Writer writer = null;
		try {
			OutputStream outputStream = mContext.openFileOutput(mFilename,
					Context.MODE_PRIVATE);
			writer = new OutputStreamWriter(outputStream);

			writer.write(array.toString());
		} finally {

			if (writer!=null) {
				
				writer.close();
			}

		
		}
	}

	public ArrayList<Crime> loadCrimes() throws IOException, JSONException {
		ArrayList<Crime> crimes = new ArrayList<Crime>();
		BufferedReader reader = null;

		try {
			InputStream inputStream = mContext.openFileInput(mFilename);
			reader = new BufferedReader(new InputStreamReader(inputStream));

			StringBuilder jsonString = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {

				jsonString.append(line);
			}

			JSONArray array = (JSONArray) new JSONTokener(
					jsonString.toString()).nextValue();
			for (int i = 0; i < array.length(); i++) {
				crimes.add(new Crime(array.getJSONObject(i)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			if (reader!=null) {
				reader.close();
			}

		}

		return crimes;

	}

}
