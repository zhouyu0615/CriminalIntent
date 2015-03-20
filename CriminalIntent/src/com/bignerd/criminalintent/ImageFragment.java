package com.bignerd.criminalintent;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ImageFragment extends DialogFragment {
	
	public static final String EXTRA_IMAGE_PATH
	="com.bignerdranch.criminal.imagePath";
	
	public static ImageFragment newInstance(String imagePath){
		Bundle args=new Bundle();
		args.putSerializable(EXTRA_IMAGE_PATH, imagePath);
		
		ImageFragment fragment=new ImageFragment();
		fragment.setArguments(args);
		fragment.setStyle(DialogFragment.STYLE_NO_TITLE, 0);
		
		return fragment;
		
	}
	
	private ImageView mImageView;
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mImageView=new ImageView(getActivity());
		String path=(String) getArguments().getSerializable(EXTRA_IMAGE_PATH);
		BitmapDrawable image=PictureUtils.getScaledDrawable(getActivity(), path);
		mImageView.setImageDrawable(image);
		
		
		return mImageView;
	}
	
	@Override
	public void onDestroyView() {
		// TODO Auto-generated method stub
		
		super.onDestroyView();
		PictureUtils.cleanImageView(mImageView);
	}

}
