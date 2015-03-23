package com.bignerd.criminalintent;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import com.bignerd.criminalintent.R.string;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts.Data;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;

public class CrimeFragment extends android.support.v4.app.Fragment {
	public static final String EXTRA_CRIME_ID = "com.bignerd.criminalintent.crime_id";

	private static final String TAG = "CrimeFragment";
	private static final String DIALOG_IMAGE = "image";
	private Crime mCrime;
	private EditText mTitleField;
	private Button mDateButton;
	private CheckBox mSlovedCheckBox;
	private ImageButton mPhotoButton;
	private ImageView mPhotoView;
	private Button mReportButton;
	private Button mSuspectButton;

	private final static String DIALOG_DATE = "date";
	private final static int REQUEST_DATE = 0;
	private final static int REQUEST_PHOTO = 1;
	private final static int REQUEST_CONTACT = 2;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		// mCrime = new Crime();
		// UUID mcrimeId = (UUID)
		// getActivity().getIntent().getSerializableExtra(
		// EXTRA_CRIME_ID);

		UUID mcrimeId = (UUID) getArguments()
				.getSerializable(EXTRA_CRIME_ID);
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

		mPhotoButton = (ImageButton) v.findViewById(R.id.crime_ImageButton);
		mPhotoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						CrimeCameraActivity.class);
				startActivityForResult(intent, REQUEST_PHOTO);
			}
		});

		PackageManager pm = getActivity().getPackageManager();
		boolean hasACamera = pm
				.hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
		if (!hasACamera) {
			mPhotoButton.setEnabled(false);
		}

		mPhotoView = (ImageView) v.findViewById(R.id.crime_imageview);
		mPhotoView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Photo photo = mCrime.getPhoto();
				if (photo == null) {
					return;
				}

				FragmentManager fm = getActivity().getSupportFragmentManager();
				String pathString = getActivity().getFileStreamPath(
						photo.getFilename()).getAbsolutePath();
				ImageFragment.newInstance(pathString).show(fm, DIALOG_IMAGE);

			}
		});

		mReportButton = (Button) v.findViewById(R.id.crime_reportButton);
		mReportButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Intent.ACTION_SEND);
				intent.setType("text/plain");
				intent.putExtra(Intent.EXTRA_TEXT, getCrimeReport());
				intent.putExtra(Intent.EXTRA_SUBJECT,
						getString(R.string.crime_report_subject));
				intent = Intent.createChooser(intent,
						getString(R.string.send_report));
				startActivity(intent);
			}
		});

		mSuspectButton = (Button) v.findViewById(R.id.crime_suspectButton);
		mSuspectButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK,
						ContactsContract.Contacts.CONTENT_URI);
				startActivityForResult(intent, REQUEST_CONTACT);
			}
		});

		if (mCrime.getSuspect() != null) {
			mSuspectButton.setText(mCrime.getSuspect());
		}
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
		System.out.println("--->requestcode= "+requestCode);
		
		if (requestCode == REQUEST_DATE) {
			Date date = (Date) data
					.getSerializableExtra(DatePickerFragment.EXTRA_DATE);

			mCrime.setDate(date);
			upViewDate();
		}
		if (requestCode == REQUEST_PHOTO) {
			String filename = data
					.getStringExtra(CrimeCameraFragment.EXTRA_PHOTO_FILENAME);
			if (filename != null) {
				Log.d(TAG, "filename:" + filename);

				if (mCrime.hasAPhoto()) {

					String path = getActivity().getFileStreamPath(
							mCrime.getPhoto().getFilename()).getAbsolutePath();
					Log.d(TAG, "crimefragment deletefilename: " + path);

					if (path != null) {

						File file = new File(path);
						if (file.exists()) {
							file.delete();
							mCrime.setPhoto(null);
						}
					}

				}

				Photo photo = new Photo(filename);
				mCrime.setPhoto(photo);
				showPhoto();

				Log.d(TAG, "Crime:" + mCrime.getTitle() + "has a photo");

			} 
			
		}
		if (requestCode == REQUEST_CONTACT) {
			Uri contactUri = data.getData();
			
			String[] queryFileds=new String[]{
					ContactsContract.Contacts.DISPLAY_NAME
					
			};
			Cursor cursor=getActivity().getContentResolver()
					.query(contactUri, 
							queryFileds, null, null, null);
			
			
			if (cursor.getCount()==0) {
				cursor.close();
				return;
			}
			
			cursor.moveToFirst();
			String suspect=cursor.getString(0);
			mCrime.setSuspect(suspect);
			mSuspectButton.setText(suspect);
			
			cursor.close();
		}
		// System.out.println("this is onActivityResult  RestulCode="+requestCode);

	}

	private void upViewDate() {

		// mDateButton.setText(mCrime.getDate().toString());
		mDateButton.setText(SimpleDateFormat.getDateTimeInstance().format(
				mCrime.getDate()));

	}

	private void showPhoto() {
		Photo photo = mCrime.getPhoto();
		BitmapDrawable bitmapDrawable = null;
		if (photo != null) {
			String path = getActivity().getFileStreamPath(photo.getFilename())
					.getAbsolutePath();
			bitmapDrawable = PictureUtils
					.getScaledDrawable(getActivity(), path);

		}
		mPhotoView.setImageDrawable(bitmapDrawable);

	}

	private String getCrimeReport() {
		String slovedString = null;
		if (mCrime.isSloved()) {
			slovedString = getString(R.string.crime_report_solved);
		} else {
			slovedString = getString(R.string.crime_report_unsolved);

		}

		String dateFormat = "EEE,MMM dd";
		String datsString = java.text.DateFormat.getDateTimeInstance().format(
				mCrime.getDate());

		String suspect = mCrime.getSuspect();
		if (suspect == null) {
			suspect = getString(R.string.crime_report_no_suspect);

		} else {
			suspect = getString(R.string.crime_report_suspect, suspect);
		}

		String reportString = getString(R.string.crime_report,
				mCrime.getTitle(), datsString, slovedString, suspect);
		return reportString;

	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub

		super.onPause();
		CrimeLab.get(getActivity()).saveCrimes();
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		showPhoto();
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		PictureUtils.cleanImageView(mPhotoView);
		super.onStop();

	}

}
