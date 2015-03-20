package com.bignerd.criminalintent;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import com.bignerd.criminalintent.R.string;

import android.app.Fragment;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AbsListView.MultiChoiceModeListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class CrimeListFragment extends android.support.v4.app.ListFragment {

	private static final String TAG = "CrimeListFragment";
	private ArrayList<Crime> mCrimes;
	private CrimeAdapter adapter;
	private boolean mSubtitleVisible;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);

		setRetainInstance(true);
		mSubtitleVisible = false;

		getActivity().setTitle(R.string.crime_title);
		mCrimes = CrimeLab.get(getActivity()).getCrimes();

		adapter = new CrimeAdapter(getActivity(), 0, mCrimes);
		this.setListAdapter(adapter);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = super.onCreateView(inflater, container, savedInstanceState);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			if (mSubtitleVisible) {
				getActivity().getActionBar().setSubtitle(R.string.subtitle);
			}

		}

		ListView listView = (ListView) view.findViewById(android.R.id.list);
		if (Build.VERSION.SDK_INT<Build.VERSION_CODES.HONEYCOMB) {
			
			registerForContextMenu(listView);
		}else {
			listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
		}
		listView.setMultiChoiceModeListener(new MultiChoiceModeListener() {
			
			@Override
			public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				return true;
			}
			
			@Override
			public void onDestroyActionMode(ActionMode mode) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu) {
				// TODO Auto-generated method stub
				MenuInflater inflater=mode.getMenuInflater();
				
				inflater.inflate(R.menu.crime_list_item_context, menu);				
				return true;
			}
			
			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
				// TODO Auto-generated method stub
				switch (item.getItemId()) {
				case R.id.menu_item_delete_crime:
					CrimeAdapter adapter=(CrimeAdapter) getListAdapter();
					CrimeLab crimeLab=CrimeLab.get(getActivity());
					for (int i = adapter.getCount()-1; i >=0; i--) {
						if (getListView().isItemChecked(i)) {
							crimeLab.deleteCrime(adapter.getItem(i));
							
						}
						
					}
					crimeLab.saveCrimes();
					mode.finish();
					adapter.notifyDataSetChanged();

                 return true;
				default:
					return false;
				}


			}
			
			@Override
			public void onItemCheckedStateChanged(ActionMode mode, int position,
					long id, boolean checked) {
				// TODO Auto-generated method stub
				
			}
		});

		return view;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		Crime crime = ((CrimeAdapter) getListAdapter()).getItem(position);

		Log.d(TAG, crime.getTitle() + " was clicked!");

		// Intent intent=new Intent(getActivity(), CrimeActivity.class);
		Intent intent = new Intent(getActivity(), CrimePagerActivity.class);

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
			dateTextView.setText(SimpleDateFormat.getDateTimeInstance().format(crime.getDate()));

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

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.fragment_crime_list, menu);
		MenuItem showSubtitleItem = menu.findItem(R.id.menu_item_show_subtitle);
		if (mSubtitleVisible && showSubtitleItem != null) {
			showSubtitleItem.setTitle(R.string.hide_subtitle);

		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menu_item_new_crime:
			Crime crime = new Crime();
			CrimeLab.get(getActivity()).addCrime(crime);
			Intent intent = new Intent(getActivity(), CrimePagerActivity.class);
			intent.putExtra(CrimeFragment.EXTRA_CRIME_ID, crime.getId());
			startActivityForResult(intent, 0);
			return true;
		case R.id.menu_item_show_subtitle:
			if (getActivity().getActionBar().getSubtitle() == null) {

				getActivity().getActionBar().setSubtitle(R.string.subtitle);
				item.setTitle(R.string.hide_subtitle);
				mSubtitleVisible = true;
			} else {
				getActivity().getActionBar().setSubtitle(null);
				item.setTitle(R.string.show_subtitle);
				mSubtitleVisible = false;
			}

			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		getActivity().getMenuInflater().inflate(R.menu.crime_list_item_context,
				menu);

	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info =(AdapterContextMenuInfo) item.getMenuInfo();
		int position=info.position;
		CrimeAdapter adapter=(CrimeAdapter) getListAdapter();
		Crime crime=adapter.getItem(position);
		
		switch (item.getItemId()) {
		case R.id.menu_item_delete_crime:
			CrimeLab.get(getActivity()).deleteCrime(crime);
			adapter.notifyDataSetChanged();
			return true;
		
		}
		
		
		
		return super.onContextItemSelected(item);
	}
}
