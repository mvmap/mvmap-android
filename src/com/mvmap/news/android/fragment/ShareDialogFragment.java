package com.mvmap.news.android.fragment;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.AlertDialog;
import org.holoeverywhere.app.Dialog;
import org.holoeverywhere.app.DialogFragment;

import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;

import com.mvmap.news.R;
import com.mvmap.news.android.activity.DetailActivity;

public class ShareDialogFragment extends DialogFragment{

	private OnClickListener			mOnClickListener;
		
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		if(activity instanceof DetailActivity){
			mOnClickListener = (DetailActivity)activity;
		}
	}

	public Dialog onCreateDialog(Bundle savedInstanceState) {

		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle(R.string.dialog_share_title);
		builder.setItems(R.array.dialog_share, mOnClickListener);
		
		return builder.create();

	}
}
