package com.bignerd.criminalintent;

import java.util.Date;
import java.util.UUID;

import org.json.JSONException;
import org.json.JSONObject;

import android.support.annotation.BoolRes;

public class Crime {

	private UUID mId;
	private String mTitle;
	private Date mDate;
	private boolean mSloved;
	private Photo mPhoto;

	private static final String JSON_ID = "id";
	private static final String JSON_TITLE = "title";
	private static final String JSON_SLOVED = "sloved";
	private static final String JSON_DATE = "date";
	private static final String JSON_PHOTO = "photo";

	public Crime() {
		// TODO Auto-generated constructor stub
		mId = UUID.randomUUID();
		mDate = new Date();
	}

	public Crime(JSONObject jsonObject) throws JSONException {

		mId = UUID.fromString(jsonObject.getString(JSON_ID));
		if (jsonObject.has(JSON_TITLE)) {
			mTitle = jsonObject.getString(JSON_TITLE);

		}
		mSloved = jsonObject.getBoolean(JSON_SLOVED);
		mDate = new Date(jsonObject.getLong(JSON_DATE));

		if (jsonObject.has(JSON_PHOTO)) {
			mPhoto = new Photo(jsonObject.getJSONObject(JSON_PHOTO));

		}
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

	public JSONObject toJSON() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put(JSON_ID, mId.toString());
		jsonObject.put(JSON_TITLE, mTitle);
		jsonObject.put(JSON_SLOVED, mSloved);
		jsonObject.put(JSON_DATE, mDate.getTime());

		if (mPhoto != null) {
			jsonObject.put(JSON_PHOTO, mPhoto.toJSON());

		}

		return jsonObject;

	}

	public Photo getPhoto() {
		return mPhoto;
	}

	public void setPhoto(Photo photo) {
		mPhoto = photo;
	}
	public Boolean hasAPhoto()
	{
		if (mPhoto!=null) {
			return true;
		}
		else {
			return false;
		}
		
	}

}
