package com.bignerd.criminalintent;


import java.util.Date;
import java.util.UUID;

public class Crime {

	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSloved;
	
	
	
	public Crime() {
		// TODO Auto-generated constructor stub
		 mId=UUID.randomUUID();
		 mDate=new Date();
	}
	
	 public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	public boolean isSloved() {
		return mSloved;
	}

	public void setSloved(boolean sloved) {
		mSloved = sloved;
	}



	public UUID getId() {
		return mId;
	}

	public void setId(UUID id) {
		mId = id;
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	};
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return mTitle;
	}
}
