package com.bignerd.criminalintent;

import java.util.Date;
import java.util.UUID;

import com.bignerd.criminalintent.R.string;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;

public class CrimeFragment extends android.support.v4.app.Fragment {
	public static final String EXTRA_CRIME_ID = "com.bignerd.criminalintent.crime_id";

	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSlovedCheckBox;
	private final static String DIALOG_DATE = "date";
	private final static int REQUEST_DATE = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// mCrime = new Crime();
		// UUID mcrimeId = (UUID)
		// getActivity().getIntent().getSerializableExtra(
		// EXTRA_CRIME_ID);

		UUID mcrimeId = (UUID) getArguments().getSerializable(EXTRA_CRIME_ID);
		mCrime = CrimeLab.get(getActivity()).getCrime(mcrimeId);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		View v = inflater.inflate(R.layout.fragment_crime, container, false);


		mTitleField = (EditText) v.findViewById(R.id.ed_crime_title);
		mTitleField.setText(mCrime.getTitle());
		mTitleField.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				mCrime.setTitle(s.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

			}

			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		mDateButton = (Button) v.findViewById(R.id.btn_crime_date);
		upViewDate();
		mDateButton.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				FragmentManager fmManager = getActivity()
						.getSupportFragmentManager();
				DatePickerFragment dialog = DatePickerFragment
						.newInstance(mCrime.getDate());
				dialog.setTargetFragment(CrimeFragment.this, REQUEST_DATE);

				dialog.show(fmManager, DIALOG_DATE);

			}
		});

		mSlovedCheckBox = (CheckBox) v.findViewById(R.id.cb_crime_solved);
		mSlovedCheckBox.setChecked(mCrime.isSloved());
		mSlovedCheckBox
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						// TODO Auto-generated method stub
						mCrime.setSloved(isChecked);
					}
				});

		return v;

	}

	public static CrimeFragment newInstance(UUID crimeId) {
		Bundle args = new Bundle();
		args.putSerializable(EXTRA_CRIME_ID, crimeId);

		CrimeFragment crimeFragment = new CrimeFragment();
		crimeFragment.setArguments(args);

		return crimeFragment;

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != Activity.RESULT_OK) {
			return;

		}
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

			mCrime.setDate(date);
			upViewDate();

		}
		// System.out.println("this is onActivityResult  RestulCode="+requestCode);

	}

	private void upViewDate() {

		mDateButton.setText(mCrime.getDate().toString());

	}



}
