package com.bignerd.criminalintent;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import android.R.id;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

public class CrimeCameraFragment extends Fragment {

	private static final String TAG = "Crime Camera Fragment";
	public static final String EXTRA_PHOTO_FILENAME 
	= "android.bignerdrangch.criminalintent.photo_filename";

	private Camera mCamera;
	private SurfaceView mSurfaceView;
	private View mProgressContainerView;

	@SuppressWarnings("deprecation")
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_crime_camera, container,
				false);

		mProgressContainerView = view
				.findViewById(R.id.crime_camera_progressContainer);
		mProgressContainerView.setVisibility(View.INVISIBLE);

		Button takePictureButton = (Button) view
				.findViewById(R.id.crime_camera_takePictureButton);

		takePictureButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mCamera.takePicture(mShutterCallback, null, mPictureCallback);
			}
		});

		mSurfaceView = (SurfaceView) view
				.findViewById(R.id.crime_camera_surfaceView);
		SurfaceHolder holder = mSurfaceView.getHolder();
		holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		holder.addCallback(new SurfaceHolder.Callback() {

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				if (mCamera != null) {
					mCamera.stopPreview();
					Log.d(TAG, "StopPreview");
				}
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				try {
					if (mCamera != null) {
						mCamera.setPreviewDisplay(holder);
						Log.d(TAG, "setPreviewDisplay");

					}
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "Error setting up previewDisplay", e);
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				if (mCamera == null) {
					return;
				}

				Camera.Parameters parameters = mCamera.getParameters();
				Size size = getBestSupportedSize(
						parameters.getSupportedPreviewSizes(), width, height);
				parameters.setPreviewSize(size.width, size.height);
				
				size=getBestSupportedSize(
						parameters.getSupportedPictureSizes(), width, height);
				parameters.setPictureSize(size.width, size.height);
				
				mCamera.setParameters(parameters);
				try {
					mCamera.startPreview();
					Log.d(TAG, "startPreview!");
				} catch (Exception e) {
					// TODO: handle exception
					Log.e(TAG, "Could not start preview", e);
					mCamera.release();
					mCamera = null;
				}

			}
		});

		return view;
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mCamera = Camera.open(0);
		Log.d(TAG, "onResume Camera.open");
		super.onResume();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		if (mCamera != null) {
			mCamera.release();
			Log.d(TAG, "onResume Camera.release");
			mCamera = null;
		}
		super.onPause();
	}

	private Size getBestSupportedSize(List<Size> sizeList, int width, int height) {
		Size bestsSize = sizeList.get(0);
		int largestArea = bestsSize.height * bestsSize.width;
		for (Size s : sizeList) {
			int area = s.height * s.width;
			if (area > largestArea) {
				bestsSize = s;
				largestArea = area;
			}

		}

		return bestsSize;
	}

	private Camera.ShutterCallback mShutterCallback = new Camera.ShutterCallback() {
		@Override
		public void onShutter() {
			// TODO Auto-generated method stub
			mProgressContainerView.setVisibility(View.VISIBLE);
		}
	};

	private Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			String filename = UUID.randomUUID().toString() + ".jpg";

			FileOutputStream outputStream = null;
			boolean success = true;

			try {
				outputStream = getActivity().openFileOutput(filename,
						Context.MODE_PRIVATE);
				outputStream.write(data);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				success = false;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				success = false;
			} finally {

				try {
					if (outputStream != null) {
						outputStream.close();
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					success = false;
				}

			}
		if (success) {
			Log.i(TAG, "JPEG file save at "+filename);
			Intent intent=new Intent();
			intent.putExtra(EXTRA_PHOTO_FILENAME, filename);			
			getActivity().setResult(Activity.RESULT_OK, intent);
		}else {
			getActivity().setResult(Activity.RESULT_CANCELED);
			
		}
		
		
		getActivity().finish();
			
		}

	};
	
	

}
