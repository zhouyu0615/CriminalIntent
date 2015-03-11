package com.bignerd.criminalintent;

import java.util.ArrayList;
import java.util.List;

import com.bignerd.criminalintent.R.string;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends ListFragment {



	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;
	private CrimeAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		getActivity().setTitle(R.string.crime_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();

		adapter = new CrimeAdapter(getActivity(), 0,
				mCrimes);
		this.setListAdapter(adapter);

	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);

		Log.d(TAG, crime.getTitle() + " was clicked!");
		
		Intent intent=new Intent(getActivity(), CrimeActivity.class);
		intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
		this.startActivity(intent);
		
		
	}

	private class CrimeAdapter extends ArrayAdapter<Crime> {

		public CrimeAdapter(Context context, int resource,
				ArrayList<Crime> crimes) {
			super(getActivity(), 0, crimes);

		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			// return super.getView(position, convertView, parent);

			if (convertView == null) {
				convertView = getActivity().getLayoutInflater().inflate(
						R.layout.list_item_crime, null);

			}
			Crime crime = getItem(position);

			TextView titleTextView = (TextView) convertView
					.findViewById(R.id.tv_crime_list_item_title);
			titleTextView.setText(crime.getTitle());
			TextView dateTextView = (TextView) convertView
					.findViewById(R.id.tv_crime_list_item_date);
			dateTextView.setText(crime.getDate().toString());

			CheckBox slovedBox = (CheckBox) convertView
					.findViewById(R.id.cb_crime_list_item_slovedBOX);
			slovedBox.setChecked(crime.isSloved());

			return convertView;
		}

	}
	
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		adapter.notifyDataSetChanged();
		
	}
}
