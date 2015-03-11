package com.bignerd.criminalintent;

import java.util.UUID;

import com.bignerd.criminalintent.R.string;

import android.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class CrimeFragment extends Fragment {
	public static final String EXTRA_CRIME_ID = "com.bignerd.criminalintent.crime_id";

	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSlovedCheckBox;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// mCrime = new Crime();
//		UUID mcrimeId = (UUID) getActivity().getIntent().getSerializableExtra(
//				EXTRA_CRIME_ID);
		
		UUID mcrimeId=(UUID) getArguments().
				getSerializable(EXTRA_CRIME_ID);
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
		mDateButton.setText(mCrime.getDate().toString());
		mDateButton.setEnabled(false);

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

}
