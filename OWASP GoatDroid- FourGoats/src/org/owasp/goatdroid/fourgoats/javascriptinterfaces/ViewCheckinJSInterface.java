/**
 * OWASP GoatDroid Project
 * 
 * This file is part of the Open Web Application Security Project (OWASP)
 * GoatDroid project. For details, please see
 * https://www.owasp.org/index.php/Projects/OWASP_GoatDroid_Project
 *
 * Copyright (c) 2012 - The OWASP Foundation
 * 
 * GoatDroid is published by OWASP under the GPLv3 license. You should read and accept the
 * LICENSE before you use, modify, and/or redistribute this software.
 * 
 * @author Jack Mannino (Jack.Mannino@owasp.org https://www.owasp.org/index.php/User:Jack_Mannino)
 * @author Walter Tighzert
 * @created 2012
 */
package org.owasp.goatdroid.fourgoats.javascriptinterfaces;

import org.owasp.goatdroid.fourgoats.activities.DoCommentActivity;
import org.owasp.goatdroid.fourgoats.activities.ViewCheckinActivity;
import org.owasp.goatdroid.fourgoats.misc.Constants;
import org.owasp.goatdroid.fourgoats.misc.Utils;
import org.owasp.goatdroid.fourgoats.request.CommentsRequest;
import org.owasp.goatdroid.fourgoats.responseobjects.GenericResponseObject;
import org.owasp.goatdroid.fourgoats.responseobjects.ResponseObject;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ViewCheckinJSInterface {

	Context mContext;

	public ViewCheckinJSInterface(Context context) {

		mContext = context;
	}

	public void launchViewCheckinActivity(String venueName,
			String venueWebsite, String dateTime, String latitude,
			String longitude, String checkinID) {

		Intent intent = new Intent(mContext, ViewCheckinActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("venueName", venueName);
		bundle.putString("venueWebsite", venueWebsite);
		bundle.putString("dateTime", dateTime);
		bundle.putString("latitude", latitude);
		bundle.putString("longitude", longitude);
		bundle.putString("checkinID", checkinID);
		intent.putExtras(bundle);
		mContext.startActivity(intent);
	}

	public void launchDoCommentActivity(String venueName, String venueWebsite,
			String dateTime, String latitude, String longitude, String checkinID) {

		Intent intent = new Intent(mContext, DoCommentActivity.class);
		Bundle bundle = new Bundle();
		bundle.putString("venueName", venueName);
		bundle.putString("venueWebsite", venueWebsite);
		bundle.putString("dateTime", dateTime);
		bundle.putString("latitude", latitude);
		bundle.putString("longitude", longitude);
		bundle.putString("checkinID", checkinID);
		intent.putExtras(bundle);
		mContext.startActivity(intent);
	}

	public void deleteComment(String commentID, String venueName,
			String venueWebsite, String dateTime, String latitude,
			String longitude, String checkinID) {

		CommentsRequest rest = new CommentsRequest(mContext);

		try {
			GenericResponseObject response = (GenericResponseObject) rest.removeComment(commentID);
			if (response.isSuccess()) {
				Utils.makeToast(mContext,
						Constants.COMMENT_SUCCESSFULLY_REMOVED,
						Toast.LENGTH_LONG);
				Intent intent = new Intent(mContext, ViewCheckinActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("venueName", venueName);
				bundle.putString("venueWebsite", venueWebsite);
				bundle.putString("dateTime", dateTime);
				bundle.putString("latitude", latitude);
				bundle.putString("longitude", longitude);
				bundle.putString("checkinID", checkinID);
				intent.putExtras(bundle);
				mContext.startActivity(intent);
			} else
				Utils.makeToast(mContext,
						Utils.mergeArrayList(response.getErrors()),
						Toast.LENGTH_LONG);
		} catch (Exception e) {
			Utils.makeToast(mContext, Constants.WEIRD_ERROR, Toast.LENGTH_LONG);
		}
	}
}
